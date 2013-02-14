package com.wss.sensorsnet;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;



public class SensorsNet extends TabActivity {

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






