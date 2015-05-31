package com.example.android_getphonestate;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("Registered")
public class MainActivity extends Activity implements OnClickListener {

    Button btn1;
    TextView tv1, tv2, tv3;
    private static final String LOG_TAG = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD
        btn1 = (Button)findViewById(R.id.macBtn);
        tv1 = (TextView)findViewById(R.id.macTv);
        tv2 = (TextView)findViewById(R.id.ipTv);
        tv3 =(TextView)findViewById(R.id.phoneTv);

        btn1.setOnClickListener(this);
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
        if(v.getId() == btn1.getId())
        {
            getCurrentMacAddress();
            getLocalIpAddress();
            getLocalPhoneNumber();
        }
    }

    public void getCurrentMacAddress(){
        String macAddress="";
        boolean bIsWifiOff=true;

        WifiManager wfManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);

        WifiInfo wfInfo = wfManager.getConnectionInfo();
        macAddress = wfInfo.getMacAddress();

=======
        /*btn1 = (Button)findViewById(R.id.macBtn);
        tv1 = (TextView)findViewById(R.id.macTv);
        tv2 = (TextView)findViewById(R.id.ipTv);
        tv3 =(TextView)findViewById(R.id.phoneTv);*/

        btn1.setOnClickListener(this);
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
        if(v.getId() == btn1.getId())
        {
            getCurrentMacAddress();
            getLocalIpAddress();
            getLocalPhoneNumber();
        }
    }

    public void getCurrentMacAddress(){
        String macAddress="";
        boolean bIsWifiOff=true;

        WifiManager wfManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);

        WifiInfo wfInfo = wfManager.getConnectionInfo();
        macAddress = wfInfo.getMacAddress();

>>>>>>> a450caae1bc423f10a32daf162dfc56f0c98f2da
        tv1.setText(macAddress+"");
    }
    public void getLocalIpAddress()
    {
        try {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = (NetworkInterface)en.nextElement();
                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = (InetAddress)enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        tv2.setText(inetAddress.getHostAddress().toString());
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(LOG_TAG, ex.toString());
        }
    }
<<<<<<< HEAD
    public void getLocalPhoneNumber(){ //占쏙옙화占쏙옙호
=======
    public void getLocalPhoneNumber(){ 
>>>>>>> a450caae1bc423f10a32daf162dfc56f0c98f2da
        TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tv3.setText(manager.getLine1Number());
    }
}
