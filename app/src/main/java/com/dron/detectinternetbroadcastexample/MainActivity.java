package com.dron.detectinternetbroadcastexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ReceiverCallback{

    private static final String MY_SETTINGS = "settings";
    private static final String COUNTER ="counter" ;

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

        //check count state
        if(savedInstanceState!=null){
            count = savedInstanceState.getInt(PUT_KEY_COUNT);
        }else {
            count = getCountFromShared();
        }

        // if this is the first initialization then show the dialog
        if (isFirstInitShared()){
            showHelloFirstDialog();
        }

        //Init our views
        initView();
        // Init and register BroadcastReceiver with callback
        receiver = new InternetConnectorReceiver(MainActivity.this);
        registerReceiver(receiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     * get count value from SharedPreferences
     * @return counter
     */

    private int getCountFromShared() {
        SharedPreferences sp = getSharedPreferences(MY_SETTINGS,MODE_PRIVATE);

        return sp.getInt(COUNTER,0);
    }

    /**
     * set count value from SharedPreferences
     */

    private void setCountFromShared() {
        SharedPreferences sp = getSharedPreferences(MY_SETTINGS,MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putInt(COUNTER, count);
        e.apply();

    }

    /**
     * show dialog with AlertDialog
     */
    private void showHelloFirstDialog() {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)

                .setPositiveButton(R.string.ok, null)
                .show();
    }

    /**
     * check is the first launch of the application with uses SharedPreferences
     */
    private boolean isFirstInitShared() {
        SharedPreferences sp = getSharedPreferences(MY_SETTINGS, MODE_PRIVATE);
        boolean hasVisited = sp.getBoolean("hasVisited", false);

        if (!hasVisited) {
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("hasVisited", true);
            e.apply(); // applying changes
        }
        return !hasVisited;
    }

    /**
     * Method to init the views
     */

    private void initView() {
        internetStatus = findViewById(R.id.text_connection);
        counter = findViewById(R.id.counter);
        parentContainer = findViewById(R.id.main_container);
    }

    /**
     * called when button will pressed
     * @param view
     */
    public void clickCounter(View view) {
        Toast.makeText(MainActivity.this, "Count = " + ++count, Toast.LENGTH_SHORT).show();
    }


    /**
     * Method to change the text and toast and change background
     */
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
    protected void onStop() {
        super.onStop();
        //save count state to SharedPreferences
        setCountFromShared();
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