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
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("Registered")
public class Login extends Activity implements OnClickListener {
	Button btn1, btn2;
	EditText ID, PW;
	private String id, pw, check = "false";
	ArrayList<String> course_name_list;
	ArrayList<String> course_id_list;
	ArrayList<String> course_time_list;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setTheme(R.style.Theme_Material_Light);

		setContentView(R.layout.activity_login);
		btn1 = (Button) findViewById(R.id.membership);
		btn2 = (Button) findViewById(R.id.confirm);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		ID = (EditText) findViewById(R.id.id);
		PW = (EditText) findViewById(R.id.pw);
		course_name_list = new ArrayList<String>();
		course_id_list = new ArrayList<String>();
		course_time_list = new ArrayList<String>();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		course_name_list.clear();
		course_id_list.clear();
		course_time_list.clear();
	}

	public void onClick(View v) {
		if (v.getId() == R.id.confirm) {

			// TODO : Check username and password here before starting
			// MainPageActivity
			new CheckID().execute();
			// Log.d(getClass().getName(), check);
			// Log.d("onClcik", "" + 4);

		} else {
			Intent tarIntent = new Intent(this, MembershipForm.class);
			startActivity(tarIntent);
		}

	}

	public class CheckID extends AsyncTask<Void, Void, Void> {
		ArrayList<String> Typed = new ArrayList<String>();

		protected Void doInBackground(Void... unused) {
			Log.d("doingbackground", "2");
			checkDB();
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Log.d("onPreExecute", "1");
			id = ID.getText().toString();
			pw = PW.getText().toString();
			Typed.add(id);
			Typed.add(pw);
		}

		@Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            // Log.d("5", check);
            
            if (check.equalsIgnoreCase("true")) {
                if (id.startsWith("1")) {
                    Bundle myBundle = new Bundle();
                    myBundle.putStringArrayList("course_name_list", course_name_list);
                    myBundle.putStringArrayList("course_id_list", course_id_list);
                    myBundle.putStringArrayList("course_time_list", course_time_list);
                    myBundle.putString("userId",id);
                    Intent myIntent = new Intent(Login.this,
                            ProAttendenceMenu.class);
                    myIntent.putExtras(myBundle);
                    startActivity(myIntent);
                } else {
                    Intent myIntent = new Intent(Login.this,
                            AttendenceMenu.class);
                    Bundle myBundle = new Bundle();
                    myBundle.putStringArrayList("course_name_list", course_name_list);
                    myBundle.putStringArrayList("course_id_list", course_id_list);
                    myBundle.putStringArrayList("course_time_list", course_time_list);
                    myBundle.putString("userId",id);
                    myIntent.putExtras(myBundle);
                    startActivity(myIntent);
                }
            }

            else {
                ID.setText("");
                PW.setText("");
                Toast.makeText(getApplication(), "Invalid, tpye again!",
                        Toast.LENGTH_SHORT).show();

            }
            
        }

<<<<<<< HEAD
		// 占쎈뼄占쎌젫 占쎌읈占쎈꽊占쎈릭占쎈뮉 �겫占썽겫占�
=======
>>>>>>> a450caae1bc423f10a32daf162dfc56f0c98f2da
		public void checkDB() {
			ArrayList<String> Typed = new ArrayList<String>();

			Typed.add(id);
			Typed.add(pw);
			int i;
			Log.d(getClass().getName(), " Start name value pair");
			Log.d("ID", Typed.get(0).toString());
			Log.d("PW", Typed.get(1).toString());
			ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
			post.add(new BasicNameValuePair("ID", Typed.get(0).toString()));
			post.add(new BasicNameValuePair("PW", Typed.get(1).toString()));

<<<<<<< HEAD
			// 占쎈염野껓옙 HttpClient 揶쏆빘猿� 占쎄문占쎄쉐
			HttpClient client = new DefaultHttpClient();

			// 揶쏆빘猿� 占쎈염野껓옙 占쎄퐬占쎌젟 �겫占썽겫占�, 占쎈염野껓옙 筌ㅼ뮆占쏙옙�뻻揶쏉옙 占쎈쾻占쎈쾻
=======
			HttpClient client = new DefaultHttpClient();

>>>>>>> a450caae1bc423f10a32daf162dfc56f0c98f2da
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);

<<<<<<< HEAD
			// Post揶쏆빘猿� 占쎄문占쎄쉐
=======
>>>>>>> a450caae1bc423f10a32daf162dfc56f0c98f2da
			HttpPost httpPost = new HttpPost("http://jdrive.synology.me"
					+ "/checkLogin.php?");
			check = null;
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post,
						"utf-8");
				httpPost.setEntity(entity);

				// return EntityUtils.getContentCharSet(entity);

				HttpResponse res = client.execute(httpPost);
				check = EntityUtils.toString((res.getEntity()));
				Log.d("Reulst", check);
				String[] split = check.split(" ");

				if (split[0].trim().equalsIgnoreCase("true")) {
					check = split[0];
					for (int j = 1; j < split.length; j++) {
						String[] list = split[j].split("/");
						course_name_list.add(list[0]);
						course_id_list.add(list[1]);
						course_time_list.add(list[2]);
					}
				} else
					check = split[0];
				check = check.trim();

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
