package com.johan.dn_reader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

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
                // Searching for next item-tag, and thereby next item.
                if (name.equals("item")) {
                    news.add(readItem(parser));
                }else{
                    // Function used to move parser to next tag of interest
                    skip.(parser);
                }
                // Alert when parsing is done.
            }
        }catch(Exception e){
            Log.d("Error", "Excep in parse()-function");
        }
    }

    private NewsItem readItem(XmlPullParser parser){

        // Return a full object ready to put in a list.
        return null;
    }

    // Process title-tags
    private String readTitle(XmlPullParser parser){

        return null;
    }
    // Process description-tags
    private String readDescription(XmlPullParser parser){

        return null;
    }
    // Process link-tags (URL´s)
    private String readLink(XmlPullParser parser){

        return null;
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

                            // Create XMLParser.
                            InputStream stream = conn.getInputStream();
                            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
                            XmlPullParser parser = xmlFactoryObject.newPullParser();
                            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                            parser.setInput(stream, null);
                            // Start parsing
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
