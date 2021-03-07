package com.dron.detectinternetbroadcastexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnectorReceiver extends BroadcastReceiver {
    public InternetConnectorReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            // Check if activity is visible or not
            boolean isVisible = App.isActivityVisible();
            // If it is visible then trigger the task else do nothing
            if (isVisible) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager
                        .getActiveNetworkInfo();

                // Check internet connection and according to state change the
                // text of activity by calling method
                if (networkInfo != null && networkInfo.isConnected()) {
                    MainActivity.getInstance().changeUI(true);
                } else {
                   MainActivity.getInstance().changeUI(false);
                }
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
