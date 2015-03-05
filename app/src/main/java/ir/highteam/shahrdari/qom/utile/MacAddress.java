package ir.highteam.shahrdari.qom.utile;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class MacAddress {

	private Context ctx;

	public MacAddress(Context c)
	{
		this.ctx = c;
	}
	
	public String getMacAddress()
	{
		WifiManager manager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		String address = info.getMacAddress();
		return address;
	}
}
