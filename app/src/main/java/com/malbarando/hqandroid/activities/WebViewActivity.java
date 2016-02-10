package com.malbarando.hqandroid.activities;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.malbarando.hqandroid.R;
import com.malbarando.hqandroid.helpers.DataRequestHelper;
import com.malbarando.hqandroid.objects.WebViewItem;

/**
 * Created by Maica Albarando on 2/10/2016.
 */
public class WebViewActivity extends AppCompatActivity {

    private ProgressBar pbLoader;
    private static final String TAG = "webViewUrl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        init();
    }

    private void init() {
        // Setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get data from intent
        WebViewItem item = getIntent().getParcelableExtra(WebViewItem.TAG);
        setTitle(item.key);

        pbLoader = (ProgressBar) findViewById(R.id.progressbar);
        pbLoader.setVisibility(item.cache ? View.INVISIBLE : View.VISIBLE);
        setupWebView(DataRequestHelper.filterUrl(item.url), item.cache);
    }

    private void setupWebView(String url, boolean cache) {
        Log.d(TAG, url);
        WebView webView = (WebView) findViewById(R.id.webview);
        // Disables caching if cache = false
        webView.getSettings().setCacheMode(!cache ? WebSettings.LOAD_NO_CACHE : WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // Handle the error
                Toast.makeText(WebViewActivity.this, failingUrl, Toast.LENGTH_SHORT).show();
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pbLoader.setProgress(newProgress);
                if (newProgress == 100) {
                    pbLoader.setVisibility(View.GONE);
                }
            }
        });
        webView.loadUrl(url);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
