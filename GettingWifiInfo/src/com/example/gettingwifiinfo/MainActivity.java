package com.example.gettingwifiinfo;

import java.text.DateFormat;
import java.util.Date;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.support.v4.content.LocalBroadcastManager;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ConnectivityManager connectivityManager;
	NetworkInfo networkInfo;
	static WifiManager wifiMgr;
	WifiInfo wifiInfo;
	int ip;

	static TextView tv_type;
	static TextView tv_name;
	static TextView tv_ipadd;
	String type, name, address;
	int flag = 1;
	Intent loacalintent =  new Intent();

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv_ipadd = (TextView) findViewById(R.id.textView3);
		tv_name = (TextView) findViewById(R.id.textView2);
		tv_type = (TextView) findViewById(R.id.textView1);

		wifiMgr = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if (wifiMgr.isWifiEnabled()) {
			if (flag == 1) {

				connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

				wifiInfo = wifiMgr.getConnectionInfo();
				networkInfo = connectivityManager.getActiveNetworkInfo();
				DhcpInfo dhcp = wifiMgr.getDhcpInfo();

				type = networkInfo.getTypeName();
				name = wifiInfo.getSSID();
				address = Formatter.formatIpAddress(dhcp.gateway);
				Toast.makeText(this, "wifi network connected",
						Toast.LENGTH_SHORT).show();
			} 
		}else if (wifiMgr.getWifiState()==wifiMgr.WIFI_STATE_DISABLED) {
			Toast.makeText(getApplication(), "wi-fi is not avialable",
					Toast.LENGTH_SHORT).show();
			flag=0;
		}

	}

	@Override
	protected void onResume() {
		super.onResume();

		if (flag == 1) {
			getTheInfo(type, name, address);
			flag = 0;
		}
	}

	public void getTheInfo(String type, String name, String address) {
		if (wifiMgr.isWifiEnabled()) {
			String currentDateTimeString = DateFormat.getDateTimeInstance()
					.format(new Date());
			tv_type.append("\n" + currentDateTimeString + " " + type);
			tv_name.append("\n" + currentDateTimeString + " " + name);
			tv_ipadd.append("\n" + currentDateTimeString + " " + address);
		}
	}
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	public void sendbroadcast()
	
	{
        Toast.makeText(getApplication(), "Broadcast called", Toast.LENGTH_SHORT).show();

		
		loacalintent .setAction("com.example.gettingwifiinfo.BROADCASTACTION");
		//loacalintent.setAction("android.net.conn.CONNECTIVITY_CHANGE");
		loacalintent.addCategory(getPackageName()+".CATEGORY");
		//loacalintent.putExtra(Constants.EXTENDED_DATA_STATUS, "vivek");

		// loacalintent.addCategory(Constants.EXTENDED_DATA_STATUS);

		//sendBroadcast(loacalintent);
		
		LocalBroadcastManager.getInstance(this)
				.sendBroadcast(loacalintent);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_refresh:
	            sendbroadcast();
	            //Toast.makeText(getApplication(), "Broadcast called", Toast.LENGTH_SHORT).show();
	            return true;
	        case R.id.action_settings:
	            
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	

}
