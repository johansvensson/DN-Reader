package com.johan.dn_reader;

import java.util.ArrayList;

/**
 * Created by Johan on 2017-05-08.
 */

public class RssParser {
    private String URLtoParse;
    private ArrayList<NewsItem> news;

    public RssParser(String url){URLtoParse = url;}

    public ArrayList<NewsItem> getNews(){return news;}

    
    // TODO fix network (use thread)
    // TODO parse and read Xml
    // TODO print news in console
    // TODO conntect news to gui




}
