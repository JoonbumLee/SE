package com.example.simplenfc;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;

import com.google.common.base.Charsets;
import com.google.common.primitives.Bytes;
import com.example.simplenfc.NFC.*;

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

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.Date;
import java.text.SimpleDateFormat;


public class AttendenceMenu extends Activity implements OnClickListener {

    static final boolean DEBUG_MODE = true;// 발표때 변수의 디폴트 값을 위한 모드

    ListViewCustomAdapter adapter; // customized adapter for UI
    ArrayList<String> course_name_list;
    ArrayList<String> course_id_list;
    ArrayList<String> course_time_list;

//    ArrayAdapter adapter;
    ListView list;
    int Position=0;
    String id;
    AttendanceMenuFragment frag1;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendencemenu);
        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getExtras();
        course_name_list = new ArrayList<String>();
        course_id_list = new ArrayList<String>();
        course_time_list = new ArrayList<String>();

        ArrayList<String> photos = new ArrayList<String>();

        course_name_list.add("attendance");
        course_id_list.add("0");
        course_time_list.add("0");

        for(String course : myBundle.getStringArrayList("course_name_list") ) {
            course_name_list.add(course);
            photos.add("A");

        }
        for(String course : myBundle.getStringArrayList("course_id_list") ) {
            course_id_list.add(course);
            photos.add("A");
        }
        for(String course : myBundle.getStringArrayList("course_time_list") ) {
            course_time_list.add(course);
            photos.add("A");
        }

        id = myBundle.getString("userId");


        adapter = new ListViewCustomAdapter(this, course_name_list, course_time_list);

        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,course_name_list);
        list = (ListView)findViewById(R.id.myList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long l_id) {
                // TODO Auto-generated method stub
                Position = position;
                if(position == 0) {
                    Log.d("Attendance","call attendance method");
                    attendance();
                }
                else
                    changeFragment();

            }
        });

    }

    private void attendance(){
        long now = System.currentTimeMillis();
        Date n_date = new Date(now);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
        String strDate = dateFormat.format(n_date);
        String date = strDate.substring(0,11);
        String time = strDate.substring(strDate.indexOf(' ')+1);

        Log.d("Time", time);

        int hour = Integer.parseInt(time.substring(0,2));
        int minute = Integer.parseInt(time.substring(time.indexOf(':')+1));

        String table_time="";

        for(int i=1; i< course_time_list.size(); i++) {
            table_time = course_time_list.get(i);
            Log.d("Course_T", table_time);

            //내가 지금 출석체크해야 하는 강의가 맞는지 확인
            if (AttendChecker(hour, minute, table_time)) {
                            /* TO DO
                            맞으면 course_id 가지고 오기
                            */
                Bundle tarBundle = new Bundle();
                tarBundle.putString("s_id", id);
                tarBundle.putString("c_id", course_id_list.get(i));
                tarBundle.putString("time", time);
                tarBundle.putString("date", date);
                Intent tarIntent = new Intent(AttendenceMenu.this, ReadActivity.class);
                tarIntent.putExtras(tarBundle);
                startActivity(tarIntent);
            }
        }

        if(DEBUG_MODE) {
            Bundle tarBundle = new Bundle();
            tarBundle.putString("s_id", id);
            tarBundle.putString("c_id", course_id_list.get(1));
            tarBundle.putString("time", time);
            tarBundle.putString("date", date);
            Intent tarIntent = new Intent(AttendenceMenu.this, ReadActivity.class);
            tarIntent.putExtras(tarBundle);
            startActivity(tarIntent);
        }



    }
    private boolean AttendChecker(int hour,int min, String course_time){

        int s_hour=Integer.parseInt(course_time.substring(0,2) );
        int s_min=Integer.parseInt(course_time.substring(3,5 ) );
        int e_hour=Integer.parseInt(course_time.substring(6,8 ) );
        int e_min= Integer.parseInt(course_time.substring(9) );

        int time = hour * 60 + min;

        int sTime = s_hour * 60 + s_min;
        int eTime = e_hour * 60 + e_min;

        if( time > sTime -10 &&time < sTime)
        {
            return true;
        }
        else if(time > eTime -10 && time < eTime)
            return true;
        else return false;
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
        // TODO Auto-generated method stub

    }

    public void changeFragment() {

        frag1 = new AttendanceMenuFragment();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, frag1);
        fragmentTransaction.commit();
    }

}
