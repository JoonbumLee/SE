package com.example.android_getphonestate;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Attend extends Activity{
	
	TextView attend;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attend);

		attend = (TextView) findViewById(R.id.myattend);
	}

}
