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

import android.app.Fragment;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AttendanceMenuFragment extends Fragment {
	ArrayAdapter<String> adapter2;
	int position = 0;
	ListView attendList;
	TextView txt;
	ArrayList<String> course_list;
	ArrayList<String> myAttend;
	String userID, attend = "";

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		txt = (TextView) getActivity().findViewById(R.id.course);
		attendList = (ListView) getActivity().findViewById(R.id.myAttendList);
		
		position = ((AttendenceMenu) getActivity()).Position;
		userID = ((AttendenceMenu) getActivity()).id;
		myAttend = new ArrayList<String>();
		
		course_list = new ArrayList<String>();
		course_list = ((AttendenceMenu) getActivity()).courseList;
		//for(String course : course_list)
		//{
		//	Log.d("course_list", course);
		//}
		//for(String course : ((AttendenceMenu) getActivity()).courseList)
		//{
		//	Log.d("Activity courseList",course);
		//}
		//Log.d("position = ", position+"");
		
		// adapter = new
		// ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,);
		// menulist = (ListView)findViewById(R.id.myList);
		//txt.setText(course_list.get(position).toString());
		new CheckAttend().execute();
		adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,myAttend);
	    attendList.setAdapter(adapter2);
		
	}

	public class CheckAttend extends AsyncTask<Void, Void, Void> {
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
			
			// Log.d("5", check);

		}

		// ���� �����ϴ� �κ�
		public void checkDB() {

			ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
			post.add(new BasicNameValuePair("U_ID", userID));
		

			// ���� HttpClient ��ü ����
			HttpClient client = new DefaultHttpClient();

			// ��ü ���� ���� �κ�, ���� �ִ�ð� ���
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);

			// Post��ü ����
			HttpPost httpPost = new HttpPost("http://jdrive.synology.me"
					+ "/checkAttendance.php?");
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post,
						"utf-8");
				httpPost.setEntity(entity);

				// return EntityUtils.getContentCharSet(entity);
				String check = "";
				HttpResponse res = client.execute(httpPost);
				check = EntityUtils.toString((res.getEntity()));
				Log.d("Reulst", check);
				String[] split = check.split(" ");
				Log.d("before if",split[0]);
				if (!(split[0].trim().equalsIgnoreCase("noData"))) {
					
					for (int j = 1; j < split.length; j++) {
						Log.d("beforeSpilit",split[j]);
						String[] attend = split[j].split("/");
						Log.d("attend[0],attend[1]", attend[0] + " " + attend[1]);
						myAttend.add(attend[0]+" "+attend[1]); 
					}
				} 
				else
					myAttend.add("noData");

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
