package com.johan.dn_reader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Johan on 2017-05-08.
 */

public class RssParser {
    private String URLtoParse;
    private ArrayList<NewsItem> news;
    private static final String ns = null;
    public volatile boolean parserWorking = true;

    public RssParser(String url){URLtoParse = url;}

    public ArrayList<NewsItem> getNews(){return news;}


    // TODO parse and read Xml
    // TODO print news in console
    // TODO conntect news to gui

    private void parse(XmlPullParser parser){
        news = new ArrayList<NewsItem>();

        try{
            parser.require(XmlPullParser.START_TAG, ns, "channel");
            while (parser.next() != XmlPullParser.END_TAG){
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Searching for next item-tag, and passing it to the method for extracting the data.
                if (name.equals("item")) {
                    news.add(readItem(parser));
                }else{
                    // Function used to move parser to next tag of interest
                    skip(parser);
                }
            }
            // Alert when parsing is done.
            // Connected to an while(boolean) in the main act.
            parserWorking = false;
        }catch(Exception e){
            Log.d("Error", "Excep in parse()-function");
            Log.e("Exc", "Exception: " + e.getMessage());
        }
    }

    private NewsItem readItem(XmlPullParser parser) throws XmlPullParserException, IOException{
        // Return a full object ready to put in a list.
        // Passing on parts of the item to their respective read-method.
        // Has a skip method if a tag not match the three tags we are looking for.
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String description = null;
        String url = null;
        while(parser.next() != XmlPullParser.END_TAG){
            if(parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            if(name.equals("title")){
                title = readTitle(parser);
            }else if(name.equals("description")){
                description = readDescription(parser);
            }else if(name.equals("link")){
                url = readLink(parser);
            }else{
                skip(parser);
            }

        }
        Log.d("title", title);
        Log.d("desc", description);
        Log.d("url", url);
        return new NewsItem(title,description,url);
    }

    // Process title-tags
    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;

    }
    // Process description-tags
    private String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return description;
    }
    // Process link-tags (URL´s)
    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException{
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }



    public void startParse(){
        Thread t= new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try{
                            //Initiate connection
                            URL url = new URL(URLtoParse);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setReadTimeout(10000 /* milliseconds */);
                            conn.setConnectTimeout(15000 /* milliseconds */);
                            conn.setRequestMethod("GET");
                            conn.setDoInput(true);
                            conn.connect();
                            // Future fet: Toast if network is disabled.

                            // Create XMLParser. Start it and close ntw-stream when done.
                            InputStream stream = conn.getInputStream();
                            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
                            XmlPullParser parser = xmlFactoryObject.newPullParser();
                            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                            parser.setInput(stream, null);
                            parser.nextTag();
                            parser.nextTag();
                            parse(parser);
                            stream.close();

                        }catch(Exception e){
                            Log.d("NETWORK ERROR", "Error while initiating network");
                        }
                    }
                }
        );
        t.start();
    }


}
