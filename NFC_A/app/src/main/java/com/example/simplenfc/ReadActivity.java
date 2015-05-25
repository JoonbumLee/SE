package com.example.simplenfc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.common.base.Charsets;
import com.google.common.primitives.Bytes;
import com.example.simplenfc.NFC.*;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.media.*;
import android.content.*;
import android.os.Build;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ReadActivity extends ActionBarActivity {

    TextView readResult;

    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;

    private String s_id, time, date, cour_id, check = "false", course_list = "";

    public static final int TYPE_TEXT = 1;
    public static final int TYPE_URI = 2;

    long now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getExtras();
        s_id= myBundle.getString("s_id");
        cour_id = myBundle.getString("c_id");
        time = myBundle.getString("time");
        date = myBundle.getString("date");


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }

        readResult = (TextView) findViewById(R.id.readResult);

        // Making Objects which relative NFC
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        Intent targetIntent = new Intent(this, ReadActivity.class);
        targetIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mPendingIntent = PendingIntent.getActivity(this, 0, targetIntent, 0);

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("*/*");
        } catch (MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this.getApplicationContext());

        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Please activate NFC and press Back to return to the application!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }

        mFilters = new IntentFilter[]{ndef,};

        mTechLists = new String[][]{new String[]{NfcF.class.getName()}};

        Intent passedIntent = getIntent();
        if (passedIntent != null) {
            String action = passedIntent.getAction();
            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
                processTag(passedIntent);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.read, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_read, container,
                    false);
            return rootView;
        }
    }

    /**
     * *********************************
     * NFC
     * **********************************
     */
    public void onResume() {
        super.onResume();

        if (mAdapter != null) {
            mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters,
                    mTechLists);
        }
    }

    public void onPause() {
        super.onPause();

        if (mAdapter != null) {
            mAdapter.disableForegroundDispatch(this);
        }
    }

    // event occur when NFC tag scanned
    public void onNewIntent(Intent passedIntent) {
        // NFC Tag
        Tag tag = passedIntent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
            byte[] tagId = tag.getId();
            //readResult.append("Tag ID : " + toHexString(tagId) + "\n"); // adding Tag ID at TextView
        }

        if (passedIntent != null) {
            Context context = this;
            processTag(passedIntent); // processTag method call
            AudioManager aManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            aManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

        }
    }

    // NFC Tag ID return method
    public static final String CHARS = "0123456789ABCDEF";

    public static String toHexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; ++i) {
            sb.append(CHARS.charAt((data[i] >> 4) & 0x0F)).append(
                    CHARS.charAt(data[i] & 0x0F));
        }
        return sb.toString();
    }


    // This method is called after onNewIntent
    private void processTag(Intent passedIntent) {
        Parcelable[] rawMsgs = passedIntent
                .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawMsgs == null) {
            return;
        }

        //  rawMsgs.length : the number of scanning tag
        Toast.makeText(getApplicationContext(), "Scan success\nYour phone will be a silent mode ", Toast.LENGTH_LONG).show();

        NdefMessage[] msgs;
        if (rawMsgs != null) {
            msgs = new NdefMessage[rawMsgs.length];
            for (int i = 0; i < rawMsgs.length; i++) {
                msgs[i] = (NdefMessage) rawMsgs[i];
                showTag(msgs[i]); // showTag method call
            }
        }
    }

    // method for reading NFC Tag information
    private int showTag(NdefMessage mMessage) {
        List<ParsedRecord> records = NdefMessageParser.parse(mMessage);
        final int size = records.size();
        for (int i = 0; i < size; i++) {
            ParsedRecord record = records.get(i);

            int recordType = record.getType();
            String recordStr = ""; // for text value from NFC tag
            if (recordType == ParsedRecord.TYPE_TEXT) {
                recordStr = "TEXT : " + ((TextRecord) record).getText();
            } else if (recordType == ParsedRecord.TYPE_URI) {
                recordStr = "URI : " + ((UriRecord) record).getUri().toString();
            }


            //TODO 시간 읽기 만약에 NFC 태그가 읽힌 시간을 기준으로 한다면
            /*
            now = System.currentTimeMillis();
            Date n_date = new Date(now);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
            String strDate = dateFormat.format(n_date);
            date = strDate.substring(0,11);
            time = strDate.substring(strDate.indexOf(' ')+1);
            */
            readResult.append(recordStr + "\n" +"Time:"+time+"\nDate:"+date); // append read text value to TextView
        }

        return size;
    }

    private class TryAttend extends AsyncTask<Void, Void, Void> {
        ArrayList<String> Typed = new ArrayList<String>();

        protected Void doInBackground(Void... unused) {
            Log.d("ReadAvt.doingbackground", "2");
            checkDB();
            return null;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            Log.d("ReadAvt.onPreExecute", "1");
            Typed.add(s_id);
            Typed.add(cour_id);
            Typed.add(date);

        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            // Log.d("5", check);
            if (check.equalsIgnoreCase("true")) {
                Toast.makeText(getApplication(), "Attendance Success",
                        Toast.LENGTH_SHORT).show();
            }

            else {
                Toast.makeText(getApplication(), "Invalid, try again!",
                        Toast.LENGTH_SHORT).show();

            }
        }

        // 실제 전송하는 부분
        public void checkDB() {
            ArrayList<String> Typed = new ArrayList<String>();

            Typed.add(s_id);
            Typed.add(cour_id);
            Typed.add(date);

            int i;
            Log.d(getClass().getName(), " Start name value pair");
            Log.d("ID", Typed.get(0).toString());
            Log.d("PW", Typed.get(1).toString());
            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
            post.add(new BasicNameValuePair("ID", Typed.get(0).toString()));
            post.add(new BasicNameValuePair("PW", Typed.get(1).toString()));

            // 연결 HttpClient 객체 생성
            HttpClient client = new DefaultHttpClient();

            // 객체 연결 설정 부분, 연결 최대시간 등등
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            HttpConnectionParams.setSoTimeout(params, 5000);

            // Post객체 생성
            HttpPost httpPost = new HttpPost("http://jdrive.synology.me"
                    + "/checkLogin.php?");
            check = null;
            try {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post,
                        "utf-8");
                httpPost.setEntity(entity);

                // return EntityUtils.getContentCharSet(entity);

                HttpResponse res = client.execute(httpPost);
                check = EntityUtils.toString((res.getEntity()));
                Log.d("Reulst", check);
                String[] split = check.split(" ");

                if (split[0].trim().equalsIgnoreCase("true")) {
                    check = split[0];
                    for (int j = 1; j < split.length; j++) {
                        course_list = course_list + split[j] + " ";
                    }
                } else
                    check = split[0];
                check = check.trim();

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
