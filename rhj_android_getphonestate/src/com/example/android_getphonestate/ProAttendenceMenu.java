package com.example.android_getphonestate;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ProAttendenceMenu extends Activity {

	Button btn1, btn2;
	ArrayAdapter<String> adapter;
	ListView list;
	String Date;
	int Position = 0;
	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;
	ProMenuFragment frag1;
	ProDateFragment frag2;
	String user_id,course_id;
	ArrayList<String> course_name_list;
	ArrayList<String> course_id_list;
	ArrayList<String> course_time_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proattendencemenu);
		// 占쎈뼄占쎌젫嚥≪뮆�뮉 揶쏅벡�벥�뵳�딅뮞占쎈뱜�몴占� Login 占쎈만占쎈뼒�뜮袁る뼒占쎈퓠占쎄퐣 占쎌읈占쎈뼎 獄쏆룇釉섓옙苑� 占쎈ご占쎌겱 占쎈퉸 餓μ꼷鍮� 占쎈맙
		Intent myIntent = getIntent();
		Bundle myBundle = myIntent.getExtras();
		course_name_list = myBundle.getStringArrayList("course_name_list");
		course_id_list = myBundle.getStringArrayList("course_id_list");
		course_time_list = myBundle.getStringArrayList("course_time_list");

		user_id = myBundle.getString("userId");

		// String[] txt = course.split(" ");
		// courseList = new ArrayList<String>();

		// for(int i = 0; i< txt.length; i++)
		// {
		// Log.d("text",txt[i]);
		// if(!courseList.contains(txt[i]))
		// courseList.add(txt[i]);
		// Log.d("courseList",courseList.get(i).toString());
		// }

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, course_name_list);
		list = (ListView) findViewById(R.id.ProList);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Position = position;
				course_id = course_id_list.get(position).toString();
				changeFragment();

			}
		});
	}

	public void changeFragment() {
		frag1 = new ProMenuFragment();
		fragmentManager = getFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(android.R.id.content, frag1);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	public void dateAttendance(String date) {
		Date = date;
		Log.d("here is dateAttendance", "1");
		frag2 = new ProDateFragment();
		fragmentManager = getFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(android.R.id.content, frag2);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
}