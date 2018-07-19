package com.eventbusapp;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class MainActivity extends AppCompatActivity {
    EventBus eventBus = EventBus.getDefault();
    NetworkStateReceiver myReceiver = new NetworkStateReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkStateChanged.activityname = "main";


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(myReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }

        if (!eventBus.isRegistered(this)) {
            Log.w("-------", " register bus 1111 ");
            eventBus.register(this);
        }


    }

    @Subscribe
    public void onEvent(String name) {
        if (name.equalsIgnoreCase("main")) {

            Log.w("------", "on event ");
            if (NetworkStateChanged.isConnectingToInternet(this)) {
                Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Dis-Connected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            unregisterReceiver(myReceiver);
        }

        eventBus.unregister(this);
    }


}
