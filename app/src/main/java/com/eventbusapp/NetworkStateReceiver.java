package com.eventbusapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;


public class NetworkStateReceiver extends BroadcastReceiver {


    static EventBus bus = EventBus.getDefault();

    @Override
    public void onReceive(final Context context, final Intent intent) {

        try {
            String status = NetworkUtil.getConnectivityStatusString(context);

            Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
            if (status != null) {
                if (status.equals("Not connected to Internet")) {
                    Toast.makeText(context, "Not connected to Internet", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "connected", Toast.LENGTH_SHORT).show();
                    try {
                        bus.post(NetworkStateChanged.activityname);
                        Log.w("-------", "connect ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


