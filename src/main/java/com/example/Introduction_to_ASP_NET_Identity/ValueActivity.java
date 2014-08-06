package com.example.Introduction_to_ASP_NET_Identity;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import com.hintdesk.core.utils.JSONHttpClient;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ServusKevin on 8/2/14.
 */
@EActivity(R.layout.value)
public class ValueActivity extends ListActivity {

    String[] values;

    @Extra("AccessToken")
    String accessToken;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void afterViews() {
        new GetValuesTask().execute();

    }



    class GetValuesTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> headers = new ArrayList<NameValuePair>();
            headers.add(new BasicNameValuePair("Authorization", String.format("Bearer %s", accessToken)));

            JSONHttpClient httpClient = new JSONHttpClient();
            values = httpClient.GetWithHeader(ServiceUrl.VALUES,headers,new ArrayList<NameValuePair>(),String[].class );
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            ListAdapter adapter = new ArrayAdapter(ValueActivity.this,android.R.layout.simple_list_item_1,values);
            setListAdapter(adapter);
            super.onPostExecute(s);
        }
    }
}
