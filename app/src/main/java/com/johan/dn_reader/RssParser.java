package com.johan.dn_reader;

import java.util.ArrayList;

/**
 * Created by Johan on 2017-05-08.
 */

public class RssParser {
    private String URLtoParse;
    private ArrayList<NewsItem> news;

    public RSSParser(String url){URLtoParse = url;}

    public ArrayList<NewsItem> getNews(){return news;}




}
