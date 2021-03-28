package com.dron.detectinternetbroadcastexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.dron.detectinternetbroadcastexample.adapter.MyRVAdapter;
import com.dron.detectinternetbroadcastexample.model.User;
import com.dron.detectinternetbroadcastexample.parser.UserResourceParser;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MyRVAdapter adapter;
    ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        mRecyclerView = findViewById(R.id.users_rv);
        parseXml();
        adapter = new MyRVAdapter(users,this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    private void parseXml() {
        try {
            XmlPullParser xpp = getResources().getXml(R.xml.users);
            UserResourceParser parser = new UserResourceParser();
            if (parser.parse(xpp)) {
                for(User prod: parser.getUsers()){
                    User user = new User();
                    user = prod;
                    Log.d("XML", prod.toString());
                   users.add(user);
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
}