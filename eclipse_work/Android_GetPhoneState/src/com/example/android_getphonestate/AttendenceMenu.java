package com.example.android_getphonestate;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AttendenceMenu extends ListActivity
{
	// 꼭 읽어주셔야 할 주의사항입니다.
	// subjects에 첫번째, 즉 subjects[0]에는 반드시 '출석'이라는 글자가 들어가야 합니다.
	// data 넣어주실때 꼭 생각해주시면 감사하겠습니다.
	String subjects[]; // 과목 받으셔서 여기에 저장하시면 됩니다.
	String date[]; // 날짜 저장하셔서 넘겨주실때 사용하시면 됩니다.
	String temp1[]; // 다른 보내야할거 여기에 저장시켜주시면 됩니다. 변수는 바꿔주시면 되겠습니다.
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subjects));
	}
	public void onListItemClick(ListView parent, View v, int position, long id)
	{
		if(position == 0)
		{
			startActivity(new Intent(this, Attend.class));
		}
		else
		{
			Intent nextIntent = new Intent(this, showAttendence.class);
			Bundle myData = new Bundle();
			for(int i=0; i<date.length; i++)
			{
				myData.putString("date"+i, date[i]);
			}
			for(int i=0; i<temp1.length; i++)
			{
				myData.putString("temp1"+i, temp1[i]);
			}
			myData.putInt("dateCount", date.length);
			myData.putInt("temp1Count", temp1.length);
			nextIntent.putExtras(myData);
			
			startActivityForResult(nextIntent, 101);
		}
	}
}
