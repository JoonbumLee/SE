package com.example.android_getphonestate;

import java.util.ArrayList;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AttendenceMenu extends Activity implements OnClickListener {
	
	ArrayAdapter<String> adapter;
	ArrayList<String> courseList;
	ListView list;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendencemenu);
		Intent myIntent = getIntent();
		Bundle myBundle = myIntent.getExtras();
		String course = myBundle.getString("course_list");
		String[] txt = course.split(" ");
		courseList = new ArrayList<String>();
		if(!(courseList.contains("Attendance")))
		{
			courseList.add("Attendance");
		}
		for(int i = 0; i< txt.length; i++)
		{
			//Log.d("text",txt[i]);
			if(!courseList.contains(txt[i]))
				courseList.add(txt[i]);
			//Log.d("courseList",courseList.get(i).toString());
		}
		
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,courseList);
	    list = (ListView)findViewById(R.id.myList);
	    list.setAdapter(adapter);
	    /*
	    //update list
	    adapter.clear();
	    courseList.clear();
	    courseList = new ArrayList<String>();
	    //adapter.notifyDataSetInvalidated();
	    courseList.add("Attendance");
		for(int i = 0; i< txt.length; i++)
		{
			//Log.d("text",txt[i]);
			courseList.add(txt[i]);
			//Log.d("courseList",courseList.get(i).toString());
		}
		adapter.addAll(courseList);
		
		adapter.notifyDataSetChanged();
		*/
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
}
