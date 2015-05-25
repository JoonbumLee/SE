package com.example.android_getphonestate;

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

public class AttendenceMenu extends Activity implements OnClickListener {
	
	ArrayList<String> course_name_list;
	ArrayList<String> course_id_list;
	ArrayList<String> course_time_list; 
	ArrayAdapter adapter;
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
	
		course_name_list.add("attendance");
		course_id_list.add("0");
		course_time_list.add("0");
		
		for(String course : myBundle.getStringArrayList("course_name_list") )
			course_name_list.add(course);
		for(String course : myBundle.getStringArrayList("course_id_list") )
			course_id_list.add(course);
		for(String course : myBundle.getStringArrayList("course_time_list") )
			course_time_list.add(course);
		
		id = myBundle.getString("userId");
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,course_name_list);
	    list = (ListView)findViewById(R.id.myList);
	    list.setAdapter(adapter);
	    
	    list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Position = position;
				if(Position == 0)
					attendance();
				else
					changeFragment();
				
			}
		});
	    
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
	/*
	@Override
	public void onClick(View v) {
		if(v.getId() == btn1.getId())
		{
			startActivity(new Intent(this, Attend.class));
		}
		if(v.getId() == btn2.getId())
		{
			startActivity(new Intent(this, showAttendence.class));
		}
	}
	*/
	public void changeFragment() {
		
		frag1 = new AttendanceMenuFragment();
		fragmentManager = getFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(android.R.id.content, frag1);
		fragmentTransaction.commit();
	}
	public void attendance() {
		
	}
	
}
