package com.example.simplenfc;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.ArrayList;

public class MembershipForm extends Activity implements OnClickListener {

    EditText et1, et2, et3;
    Button bt1;
    String s_id, cour_id, date;
    String check="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        et1 = (EditText)findViewById(R.id.id);
        et2 = (EditText)findViewById(R.id.name);
        et3 = (EditText)findViewById(R.id.phone);
        bt1 = (Button)findViewById(R.id.confirm);

        bt1.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, Login.class));
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
            if (check.trim().equalsIgnoreCase("true")) {
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
            Log.d("U_ID", Typed.get(0).toString());
            Log.d("Cour_id", Typed.get(1).toString());
            Log.d("date", Typed.get(2).toString());

            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
            post.add(new BasicNameValuePair("U_ID", Typed.get(0).toString()));
            post.add(new BasicNameValuePair("C_ID", Typed.get(1).toString()));
            post.add(new BasicNameValuePair("DATE", Typed.get(2).toString()));

            // 연결 HttpClient 객체 생성
            HttpClient client = new DefaultHttpClient();

            // 객체 연결 설정 부분, 연결 최대시간 등등
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            HttpConnectionParams.setSoTimeout(params, 5000);

            // Post객체 생성
            HttpPost httpPost = new HttpPost("http://jdrive.synology.me"
                    + "/Attend.php?");
            check = null;
            try {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post,
                        "utf-8");
                httpPost.setEntity(entity);

                // return EntityUtils.getContentCharSet(entity);

                HttpResponse res = client.execute(httpPost);
                check = EntityUtils.toString((res.getEntity()));
                Log.d("Reulst", check);
                if(check.trim().equalsIgnoreCase("true")){

                    // Toast.makeText(getApplicationContext(), "Attend success\n check course information if you want ", Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    //Toast.makeText(getApplicationContext(), "failed \n please try again", Toast.LENGTH_LONG).show();
                }


            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}