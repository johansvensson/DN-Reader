package com.johan.dn_reader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class NewsActivity extends AppCompatActivity {
    private String title;
    private String description;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        TextView header = (TextView) findViewById(R.id.header);
        TextView description = (TextView) findViewById(R.id.text);

        Bundle bundle = getIntent().getExtras();
        header.setText(bundle.getString("title"));
        description.setText(bundle.getString("description"));
    }
}
