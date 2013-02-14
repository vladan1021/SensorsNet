package com.wss.sensorsnet;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;



public class SensorsNet extends TabActivity {
/** Called when the activity is first created. */
	
	private static final String TAG = "SensorGraph";
	
	// change this to your Bluetooth device address 
	private static final String DEVICE_ADDRESS =  "00:11:12:31:06:54"; //Bluetooth Module MAC Address
	
	private ArduinoReceiver arduinoReceiver = new ArduinoReceiver();
	
	//declare layout elements
	
	private ToggleButton on_off_monitoring;
	private TextView text_view1;
	
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.main);
TabHost th = (TabHost)findViewById(android.R.id.tabhost);
TabSpec ts1 = th.newTabSpec("tab_id_1");
TabSpec ts2 = th.newTabSpec("tab_id_2");
ts1.setIndicator("Home").setContent(new Intent(this,FirstActivity.class));
ts2.setIndicator("Nodes").setContent(new Intent(this,SecondActivity.class));
th.addTab(ts1);
th.addTab(ts2);
}
}






