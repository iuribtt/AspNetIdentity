package com.hintdesk.android.aspnetwebapi;

import android.app.ListActivity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.hintdesk.core.utils.JSONHttpClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_value)
public class ValueActivity extends ListActivity {
    SampleApi api = SampleApiFactory.getInstance();
    String[] values;

    @Extra("AccessToken")
    String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void afterViews() {
        new GetValuesTask().execute();

    }

    class GetValuesTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                values = api.GetValues(String.format("Bearer %s", accessToken)).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
