package com.example.android_getphonestate;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MembershipForm extends Activity implements OnClickListener,
		TextWatcher {

	EditText id, pw, phone, name;
	Button check, confirm;
	String checkID = "";
	private boolean check_ID = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(R.style.Theme_Material_Light);
		setContentView(R.layout.activity_membership);

		id = (EditText) findViewById(R.id.id);
		name = (EditText) findViewById(R.id.name);
		phone = (EditText) findViewById(R.id.phone);
		pw = (EditText) findViewById(R.id.pw);
		confirm = (Button) findViewById(R.id.confirm);
		check = (Button) findViewById(R.id.check);
		confirm.setOnClickListener(this);
		check.setOnClickListener(this);
		
		id.addTextChangedListener(this);
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
		if (v.getId() == check.getId()) {
			new checkId().execute();
		} else if (v.getId() == confirm.getId()) {
			if (check_ID == true) {
				if (id.getText().toString().startsWith("1")) {
					Bundle myBundle = new Bundle();

					myBundle.putString("userId", id.getText().toString());
					Intent myIntent = new Intent(MembershipForm.this,
							ProAttendenceMenu.class);
					myIntent.putExtras(myBundle);
					startActivity(myIntent);
				} else {
					Intent myIntent = new Intent(MembershipForm.this,
							AttendenceMenu.class);
					Bundle myBundle = new Bundle();
					myBundle.putString("userId", id.getText().toString());
					myIntent.putExtras(myBundle);
					startActivity(myIntent);
				}
			}
			else
			{
				Toast.makeText(this,"Check Id again!",Toast.LENGTH_SHORT).show();
				id.setText("");
				id.requestFocus();
			}
		}
	}

	public class checkId extends AsyncTask<Void, Void, Void> {
		ArrayList<String> Typed = new ArrayList<String>();

		protected Void doInBackground(Void... unused) {
			checkDB();
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (checkID.trim().equalsIgnoreCase("valid")) {
				Toast.makeText(getBaseContext(), "It's valid, you can use it.",
						Toast.LENGTH_SHORT).show();
				check_ID = true;
			} else {
				Toast.makeText(getBaseContext(),
						"It's invalid, use another id.", Toast.LENGTH_SHORT)
						.show();
				id.setText("");
			}
		}

		// 占쎈뼄占쎌젫 占쎌읈占쎈꽊占쎈릭占쎈뮉 �겫占썽겫占�
		public void checkDB() {

			ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
			post.add(new BasicNameValuePair("U_ID", id.getText().toString()));

			// 占쎈염野껓옙 HttpClient 揶쏆빘猿� 占쎄문占쎄쉐
			HttpClient client = new DefaultHttpClient();

			// 揶쏆빘猿� 占쎈염野껓옙 占쎄퐬占쎌젟 �겫占썽겫占�, 占쎈염野껓옙 筌ㅼ뮆占쏙옙�뻻揶쏉옙 占쎈쾻占쎈쾻
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);

			// Post揶쏆빘猿� 占쎄문占쎄쉐
			HttpPost httpPost = new HttpPost("http://jdrive.synology.me"
					+ "/checkId.php?");
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post,
						"utf-8");
				httpPost.setEntity(entity);

				// return EntityUtils.getContentCharSet(entity);
				HttpResponse res = client.execute(httpPost);
				checkID = EntityUtils.toString((res.getEntity()));
				Log.d("Reulst", checkID);

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		Log.d(getClass().getName(),"in onTextChanged");
		
		if (getCurrentFocus().getId() == R.id.id)
		{
			Log.d("nowChanged","nowChanged");
			check_ID = false;
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

}