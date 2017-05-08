package com.johan.dn_reader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import org.w3c.dom.Text;

import static com.johan.dn_reader.R.styleable.View;

public class NewsActivity extends AppCompatActivity {
    private String title;
    private String text;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        TextView header = (TextView) findViewById(R.id.header);
        TextView description = (TextView) findViewById(R.id.text);

        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        text = bundle.getString("description");
        link = bundle.getString("url");

        header.setText(title);
        description.setText(text);
    }

        public void openWeb(View view){
            Intent intent = new Intent(this, WebActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("link", link);
            bundle.putString("title", title);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

