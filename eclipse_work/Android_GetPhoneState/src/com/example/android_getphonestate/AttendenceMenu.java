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
	// �� �о��ּž� �� ���ǻ����Դϴ�.
	// subjects�� ù��°, �� subjects[0]���� �ݵ�� '�⼮'�̶�� ���ڰ� ���� �մϴ�.
	// data �־��ֽǶ� �� �������ֽø� �����ϰڽ��ϴ�.
	String subjects[]; // ���� �����ż� ���⿡ �����Ͻø� �˴ϴ�.
	String date[]; // ��¥ �����ϼż� �Ѱ��ֽǶ� ����Ͻø� �˴ϴ�.
	String temp1[]; // �ٸ� �������Ұ� ���⿡ ��������ֽø� �˴ϴ�. ������ �ٲ��ֽø� �ǰڽ��ϴ�.
	
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
