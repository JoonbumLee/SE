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

import com.example.android_getphonestate.ProMenuFragment.CheckAttend;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class ProDateFragment extends Fragment {
	int position = 0;
	String userID = "", date = "";
	ListView AttendList;
	ArrayList<String> StAttend;
	ArrayAdapter<String> adapter3;
	LinearLayout activityList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(getClass().getName(), " onCreateView() enterance ");
		View v = inflater.inflate(R.layout.pro_date_fragment, container, false);
		// getActivity().setVisible(View.GONE);

		return v;
	}

	public void onStart() {
		// TODO Auto-generated method stub
		// txt = (TextView) getActivity().findViewById(R.id.course);

		super.onStart();
		date = ((ProAttendenceMenu) getActivity()).Date;
		position = ((ProAttendenceMenu) getActivity()).Position;
		userID = ((ProAttendenceMenu) getActivity()).user_id;

		StAttend = new ArrayList<String>();
		
		AttendList = (ListView) getActivity().findViewById(R.id.dateList);

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
			for (String attend : StAttend) {
				Log.d("attend", attend);
			}

			adapter3 = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, StAttend);
			// if(attendList == null)
			// Log.d("list","null");
			// if(adapter2 == null)
			// Log.d("adapter2","null");

			activityList = (LinearLayout) getActivity().findViewById(
					R.id.pLayout);
			activityList.setVisibility(View.GONE);
			AttendList.setAdapter(adapter3);
			// Log.d("5", check);

		}

		// 占쎈뼄占쎌젫 占쎌읈占쎈꽊占쎈릭占쎈뮉 �겫占썽겫占�
		public void checkDB() {

			ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
			post.add(new BasicNameValuePair("U_ID", userID));
			post.add(new BasicNameValuePair("C_ID",
					((ProAttendenceMenu) getActivity()).course_id_list.get(
							position).toString()));
			post.add(new BasicNameValuePair("Date", date));
			// 占쎈염野껓옙 HttpClient 揶쏆빘猿� 占쎄문占쎄쉐
			HttpClient client = new DefaultHttpClient();

			// 揶쏆빘猿� 占쎈염野껓옙 占쎄퐬占쎌젟 �겫占썽겫占�, 占쎈염野껓옙 筌ㅼ뮆占쏙옙�뻻揶쏉옙 占쎈쾻占쎈쾻
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);

			// Post揶쏆빘猿� 占쎄문占쎄쉐
			HttpPost httpPost = new HttpPost("http://jdrive.synology.me"
					+ "/checkDateAttend.php?");
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
						String[] txt = split[j].split("/");
						if (!(txt[2].startsWith("1")))
							StAttend.add("Date : " + txt[0] + " Name : "
									+ txt[1] + " U_Id : " + txt[2]
									+ " Point : " + txt[3]);
					}
				} else
					StAttend.add("noData");

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}