package com.example.android_getphonestate;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ProOneAttend  extends Activity implements AdapterView.OnItemClickListener {

	TextView attend;
	ArrayAdapter<String> adapter; 
	ListView listView; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pro_one_attend);
		Intent intent = getIntent();
		ArrayList<String> datelist = new ArrayList<String>();
		datelist.add(intent.getStringExtra("Date"));
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datelist);
		listView = (ListView) findViewById(R.id.Datelist);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 날짜 중 하나가 터치되면 터치 된 날짜의 출석 결과를 전달.
		Intent intent = new Intent(ProOneAttend.this, ProAllAttend.class);
		intent.putExtra("Attend","최윤호 true");
		startActivity(intent);
		
	}
}
