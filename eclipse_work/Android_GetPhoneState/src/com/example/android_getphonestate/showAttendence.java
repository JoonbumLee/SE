package com.example.android_getphonestate;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class showAttendence extends ListActivity{
    
	String getDate[];
	String getTemp1[];
	String hap[];
	@Override
	public void onCreate(Bundle savedInstanceState)
	{ 
		super.onCreate(savedInstanceState);
			
		Intent getDataIntent = getIntent();
		
		Bundle myBundle = getDataIntent.getExtras();
		hap = new String[myBundle.getInt("dateCount")];
		
		for(int i=0; i<myBundle.getInt("dateCount"); i++)
		{
			hap[i] = myBundle.getString("date"+i);
		}
		
		for(int i=0; i<myBundle.getInt("temp1Count"); i++)
		{
			hap[i] = hap[i] + " " + myBundle.getString("temp1"+i);
		}
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, hap));
	}
}
