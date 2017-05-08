package com.johan.dn_reader;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    private final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String urlToParse = "http://www.dn.se/nyheter/m/rss/";
        RssParser rss = new RssParser(urlToParse);
        rss.startParse();

        while (rss.parserWorking) ; // Do nothing and wait until parser is done.
        list = (ListView) findViewById(R.id.list);
        ArrayList<NewsItem> news = rss.getNews();

        ArrayAdapter adapter = new ArrayAdapter<NewsItem>(this, android.R.layout.simple_list_item_1, news);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsItem item = (NewsItem) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putString("title", item.getTitle());
                bundle.putString("description", item.getText());
                bundle.putString("url", item.getUrl());

                Intent intent = new Intent(context, NewsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }
}
