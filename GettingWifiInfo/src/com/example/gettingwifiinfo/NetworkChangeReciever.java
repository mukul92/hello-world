package com.example.gettingwifiinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.widget.Toast;

public class NetworkChangeReciever extends BroadcastReceiver {

	WifiManager wifiManager;
	ConnectivityManager connectivityManager;
	WifiInfo wifiInfo;
	NetworkInfo networkInfo;
	String type;
	String name;
	String address;

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {

		wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager.isWifiEnabled()) {
			connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager.getNetworkInfo(
					ConnectivityManager.TYPE_WIFI).isConnected()) {
				networkInfo = connectivityManager.getActiveNetworkInfo();
				wifiInfo = wifiManager.getConnectionInfo();
				DhcpInfo dhcp = wifiManager.getDhcpInfo();

				type = networkInfo.getTypeName();
				name = wifiInfo.getSSID();
				address = Formatter.formatIpAddress(dhcp.gateway);

				MainActivity mainActivity = new MainActivity();
				mainActivity.getTheInfo(type, name, address);
				if (wifiManager.getWifiState() == wifiManager.WIFI_STATE_ENABLED) {
					Toast.makeText(context, "wifi network connected",
							Toast.LENGTH_SHORT).show();
				} 
				

			}
		} else if (!wifiManager.isWifiEnabled()) {
			Toast.makeText(context, "wifi is not available", Toast.LENGTH_SHORT)
					.show();
		}

	}

}
