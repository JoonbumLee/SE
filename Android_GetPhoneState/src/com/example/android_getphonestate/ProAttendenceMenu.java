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
	ListView listView; 
	String select, select2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proattendencemenu);
		// 실제로는 강의리스트를 Login 액티비티에서 전달 받아서 표현 해 줘야 함
		ArrayList<String> courselist = new ArrayList<String>();
		courselist.add("알고리즘");
		courselist.add("소프트웨어공학");
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,courselist);
		listView = (ListView) findViewById(R.id.courselist);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
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
