package com.example.android_getphonestate;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ProAllAttend  extends Activity {

	ArrayAdapter<String> adapter; 
	ListView listView; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proallattend);
		Intent intent = getIntent();
		ArrayList<String> AttendList = new ArrayList<String>();
		AttendList.add(intent.getStringExtra("Attend"));
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,AttendList);
		listView = (ListView) findViewById(R.id.attendlist);
		listView.setAdapter(adapter);
	}
}
