package com.johan.dn_reader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String urlToParse = "http://www.dn.se/nyheter/m/rss/";
        RssParser rss = new RssParser(urlToParse);
        rss.startParse();

        while(rss.parserWorking); // Do nothing and wait until parser is done. Maybe change this solution later?
        list = (ListView) findViewById(R.id.list);
        ArrayList<NewsItem> news = rss.getNews();

        ArrayAdapter adapter = new ArrayAdapter<NewsItem>(this, android.R.layout.simple_list_item_1, news);
        list.setAdapter(adapter);



    }
}
