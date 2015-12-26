package com.dabutvin.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class DeserializeJsonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deserialize_json);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String json = intent.getStringExtra(DisplayJsonActivity.JsonMessageKey);
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.deserialize);

        TextView textView = new TextView(this);
        layout.addView(textView);

        JSONObject jsonObject = null;
        Item item = new Item();

        try {
            jsonObject = new JSONObject(json);
            item.setUserId(jsonObject.getInt("userId"));
            item.setId(jsonObject.getInt("id"));
            item.setTitle(jsonObject.getString("title"));
            item.setBody(jsonObject.getString("body"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("userId: ");
        stringBuilder.append(item.getUserId());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append(System.getProperty("line.separator"));

        stringBuilder.append("id:      ");
        stringBuilder.append(item.getId());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append(System.getProperty("line.separator"));

        stringBuilder.append("title:   ");
        stringBuilder.append(item.getTitle());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append(System.getProperty("line.separator"));

        stringBuilder.append("body:    ");
        stringBuilder.append(item.getBody());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append(System.getProperty("line.separator"));
        
        textView.setText(stringBuilder.toString());
    }

}
