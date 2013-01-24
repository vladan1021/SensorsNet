package com.wss.sensorsnet;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import at.abraxas.amarino.Amarino;
import at.abraxas.amarino.AmarinoIntent;


public class SensorsNet extends Activity {
	
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
        
        
        setContentView(R.layout.activity_sensors_net);
        
    
        
     // get references to views defined in our main.xml layout file
        on_off_monitoring = (ToggleButton) findViewById(R.id.on_off_bttn);
        text_view1 = (TextView) findViewById(R.id.textView1);
        
         on_off_monitoring.setOnClickListener(new View.OnClickListener() {
        	
            public void onClick(View v) {
                if(on_off_monitoring.isChecked()) 
                {
                	Toast.makeText(getApplicationContext(),"Monitoring is set ON!", Toast.LENGTH_SHORT).show();
                	Amarino.sendDataToArduino(getApplicationContext(), DEVICE_ADDRESS, 'z', 88);
                }
                else 
                {
                	
                	Toast.makeText(getApplicationContext(),"Monitoring is set OFF!", Toast.LENGTH_SHORT).show();
                }
            }
        });      
       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sensors_net, menu);
        return true;
    }
    
    
   // ------ Reference this code
    
	@Override
	protected void onStart() {
		super.onStart();
		// in order to receive broadcasted intents we need to register our receiver
		registerReceiver(arduinoReceiver, new IntentFilter(AmarinoIntent.ACTION_RECEIVED));
		
		// this is how you tell Amarino to connect to a specific BT device from within your own code
		Amarino.connect(this, DEVICE_ADDRESS);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		// if you connect in onStart() you must not forget to disconnect when your app is closed
		Amarino.disconnect(this, DEVICE_ADDRESS);
		
		// do never forget to unregister a registered receiver
		unregisterReceiver(arduinoReceiver);
	}
	

	/**
	 * ArduinoReceiver is responsible for catching broadcasted Amarino
	 * events.
	 * 
	 **/
	public class ArduinoReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String data = null;
			
			// the device address from which the data was sent, we don't need it here but to demonstrate how you retrieve it
			final String address = intent.getStringExtra(AmarinoIntent.EXTRA_DEVICE_ADDRESS);
			
			// the type of data which is added to the intent
			final int dataType = intent.getIntExtra(AmarinoIntent.EXTRA_DATA_TYPE, -1);
			
			data = intent.getStringExtra(AmarinoIntent.EXTRA_DATA);
			text_view1.setText(data);
			
			//play alarm ringtone
			Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
			r.play();
			
			// we only expect String data though, but it is better to check if really string was sent
			// later Amarino will support differnt data types, so far data comes always as string and
			// you have to parse the data to the type you have sent from Arduino, like it is shown below
//			if (dataType == AmarinoIntent.STRING_EXTRA){
//				
//				
//				if (data != null){
//					
//					System.out.print(data);   //get data from Arduino and fill out text field
//
//				}
//			}
		}
		
		/////---END OF COPIED CODE
	}
}

