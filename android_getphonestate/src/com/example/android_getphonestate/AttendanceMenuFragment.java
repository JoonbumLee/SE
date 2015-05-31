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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AttendanceMenuFragment extends Fragment {
	ListView attendList;
	ArrayAdapter<String> adapter2;
	int position = 0;

	ArrayList<String> myAttend;
	String userID, attend = "";
	LinearLayout activityList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(getClass().getName(), " onCreateView() enterance ");
		View v = inflater.inflate(R.layout.attendance_menu_fragment, container,
				false);
		// getActivity().setVisible(View.GONE);

		return v;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		// txt = (TextView) getActivity().findViewById(R.id.course);

		super.onStart();
		position = ((AttendenceMenu) getActivity()).Position;
		userID = ((AttendenceMenu) getActivity()).id;
		myAttend = new ArrayList<String>();

		attendList = (ListView) getActivity().findViewById(R.id.myAttendList);

		// for(String course : course_list)
		// {
		// Log.d("course_list", course);
		// }
		// for(String course : ((AttendenceMenu) getActivity()).courseList)
		// {
		// Log.d("Activity courseList",course);
		// }
		// Log.d("position = ", position+"");

		// adapter = new
		// ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,);
		// menulist = (ListView)findViewById(R.id.myList);
		// txt.setText(course_list.get(position).toString());
		new CheckAttend().execute();

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		activityList.setVisibility(View.VISIBLE);
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
			for (String attend : myAttend) {
				Log.d("attend", attend);
			}

			adapter2 = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, myAttend);
			// if(attendList == null)
			// Log.d("list","null");
			// if(adapter2 == null)
			// Log.d("adapter2","null");

			activityList = (LinearLayout) getActivity().findViewById(
					R.id.Layout);
			activityList.setVisibility(View.GONE);
			attendList.setAdapter(adapter2);
			// Log.d("5", check);

		}

<<<<<<< HEAD
		// �떎�젣 �쟾�넚�븯�뒗 遺�遺�
=======
>>>>>>> a450caae1bc423f10a32daf162dfc56f0c98f2da
		public void checkDB() {

			ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
			post.add(new BasicNameValuePair("U_ID", userID));
			post.add(new BasicNameValuePair("C_ID",
					((AttendenceMenu) getActivity()).course_id_list.get(
							position).toString()));
			Log.d("course_Id", ((AttendenceMenu) getActivity()).course_id_list
					.get(position).toString());

<<<<<<< HEAD
			// �뿰寃� HttpClient 媛앹껜 �깮�꽦
			HttpClient client = new DefaultHttpClient();

			// 媛앹껜 �뿰寃� �꽕�젙 遺�遺�, �뿰寃� 理쒕��떆媛� �벑�벑
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);

			// Post媛앹껜 �깮�꽦
=======
			HttpClient client = new DefaultHttpClient();

			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);
			
>>>>>>> a450caae1bc423f10a32daf162dfc56f0c98f2da
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
				Log.d("before if", split[0]);
				if (!(split[0].trim().equalsIgnoreCase("noData"))) {

					for (int j = 1; j < split.length; j++) {
						Log.d("beforeSpilit", split[j]);
						String[] attend = split[j].split("/");
						Log.d("attend[0],attend[1]", "Date :" + attend[0] + " "
								+ "point " + attend[1]);
						myAttend.add("Date : " + attend[0].trim() + " "
								+ "point : " + attend[1].trim());
					}
				} else
					myAttend.add("noData");

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}