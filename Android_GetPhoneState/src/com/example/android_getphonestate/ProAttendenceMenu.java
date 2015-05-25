package com.example.android_getphonestate;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ProAttendenceMenu extends Activity implements AdapterView.OnItemClickListener {

	Button btn1, btn2;
	ArrayAdapter<String> adapter; 
	ListView list;
	ArrayList<String> courseList;
	String select, select2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proattendencemenu);
		// 실제로는 강의리스트를 Login 액티비티에서 전달 받아서 표현 해 줘야 함
		Intent myIntent = getIntent();
		Bundle myBundle = myIntent.getExtras();
		String course = myBundle.getString("course_list");
		String[] txt = course.split(" ");
		courseList = new ArrayList<String>();
		
		for(int i = 0; i< txt.length; i++)
		{
			//Log.d("text",txt[i]);
			if(!courseList.contains(txt[i]))
				courseList.add(txt[i]);
			//Log.d("courseList",courseList.get(i).toString());
		}
		
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,courseList);
	    list = (ListView)findViewById(R.id.ProList);
	    list.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 강의 리스트 중 하나가 터치되면 그 강의가 있었던 날짜를 다음 액티비티로 전달한다.
		Intent intent = new Intent(ProAttendenceMenu.this, ProOneAttend.class);
		intent.putExtra("Date","5월24일");
		startActivity(intent);
		
	}
}
