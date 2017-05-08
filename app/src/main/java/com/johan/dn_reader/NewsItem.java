package com.johan.dn_reader;

/**
 * Created by Johan on 2017-05-08.
 */

public class NewsItem {
    private String title;
    private String text;
    private String url;

    public NewsItem(String title, String text, String url){
        this.title = title;
        this.text = text;
        this.url = url;
    }

    public String getTitle(){return title;}
    public String getText(){return text;}
    public String getUrl(){return url;}

    public String toString(){return title;}
}
