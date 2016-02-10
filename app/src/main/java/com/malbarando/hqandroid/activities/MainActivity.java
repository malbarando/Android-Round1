package com.malbarando.hqandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.malbarando.hqandroid.R;
import com.malbarando.hqandroid.adapters.WebViewListAdapter;
import com.malbarando.hqandroid.helpers.DataRequestHelper;
import com.malbarando.hqandroid.objects.WebViewItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataRequestHelper.WebRequestListener {

    List<WebViewItem> mData;
    private WebViewListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    private void init() {
        ListView mListView = (ListView) findViewById(R.id.listview);
        mData = new ArrayList<>();
        mAdapter = new WebViewListAdapter(this, mData);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(itemClickListener);
        DataRequestHelper requestHelper = new DataRequestHelper(this, this);
        requestHelper.getUrlData();
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra(WebViewItem.TAG, mData.get(i));
            startActivity(intent);
        }
    };

    @Override
    public void onDataUpdate(List<WebViewItem> itemList) {
        mData.addAll(itemList);
        mAdapter.notifyDataSetChanged();
    }
}
