package com.dron.detectinternetbroadcastexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static MainActivity ins;

    TextView internetStatus;
    ConstraintLayout parentContainer;

    InternetConnectorReceiver receiver;

    public static  MainActivity getInstance(){
        return ins;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ins = this;
        //Init our views
        initView();
        // Init and register BroadcastReceiver
        receiver = new InternetConnectorReceiver();
        registerReceiver(receiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    //Method to init the views
    private void initView() {
        internetStatus = findViewById(R.id.text_connection);
        parentContainer = findViewById(R.id.main_container);
    }

    // Method to change the text status
    public void changeUI(boolean isConnected) {
        // Change status according to boolean value
        MainActivity.this.runOnUiThread(() -> {
            if (isConnected) {
                Toast.makeText(MainActivity.this, R.string.yes_internet, Toast.LENGTH_SHORT).show();
                internetStatus.setText(R.string.yes_internet);
                parentContainer.setBackgroundColor(getResources().getColor(R.color.background_green,getTheme()));
            } else {
                Toast.makeText(MainActivity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                internetStatus.setText(R.string.no_internet);
                parentContainer.setBackgroundColor(getResources().getColor(R.color.background_red,getTheme()));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.activityResumed();// On Resume notify the Application
    }

    @Override
    protected void onPause() {
        super.onPause();
        App.activityPaused();// On Pause notify the Application
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Unregister BroadcastReceiver
        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}