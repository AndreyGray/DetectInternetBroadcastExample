package com.dron.detectinternetbroadcastexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ReceiverCallback{

    TextView internetStatus;
    Button counter;//кнопа
    ConstraintLayout parentContainer;
    int count = 0;//счетчик

    private final String PUT_KEY_COUNT = "count";

    InternetConnectorReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Init our views
        initView();
        // Init and register BroadcastReceiver with callback
        receiver = new InternetConnectorReceiver(MainActivity.this);
        registerReceiver(receiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    //Method to init the views
    private void initView() {
        internetStatus = findViewById(R.id.text_connection);
        counter = findViewById(R.id.counter);
        parentContainer = findViewById(R.id.main_container);
    }

    //called when button will pressed
    public void clickCounter(View view) {
        Toast.makeText(this, "Count = " + ++count, Toast.LENGTH_SHORT).show();
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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PUT_KEY_COUNT,count);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        count = savedInstanceState.getInt(PUT_KEY_COUNT);
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