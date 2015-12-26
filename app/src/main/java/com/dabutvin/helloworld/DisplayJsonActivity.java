package com.dabutvin.helloworld;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DisplayJsonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_json);
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

        textView = new TextView(this);
        textView.setTextSize(20);

        textView.setText("fetching...");

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.jsonContent);
        layout.addView(textView);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            new DownloadJsonTask().execute("http://jsonplaceholder.typicode.com/posts/1");
        }else{
            textView.setText("no network :(");
        }
    }

    private TextView textView;

    private class DownloadJsonTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            HttpURLConnection urlConnection = null;
            String error = null;

            try {
                urlConnection = (HttpURLConnection) new URL(url[0]).openConnection();
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                StringBuilder result = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = reader.readLine()) != null){
                    result.append(line);
                }

                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                error = e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                error = e.getMessage();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return error;
        }

        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
        }
    }

}
