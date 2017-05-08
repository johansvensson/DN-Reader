package com.johan.dn_reader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView webView = (WebView) findViewById(R.id.web);
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String url = bundle.getString("url");
        // Change the view title corresponding to the news title
        setTitle(title);
        // Loads the URL
        webView.loadUrl(url);

    }
}
