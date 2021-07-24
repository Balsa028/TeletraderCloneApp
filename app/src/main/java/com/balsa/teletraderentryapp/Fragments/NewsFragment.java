package com.balsa.teletraderentryapp.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Xml;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.balsa.teletraderentryapp.Adapters.NewsAdapter;
import com.balsa.teletraderentryapp.Models.NewsArticle;
import com.balsa.teletraderentryapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;


public class NewsFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private RecyclerView newsRecView;
    private ArrayList<NewsArticle> news;
    private NewsAdapter adapter;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        news = new ArrayList<>();
        bottomNavigationView = view.findViewById(R.id.bottomNavViewNews);
        newsRecView = view.findViewById(R.id.newsRecView);
        newsRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NewsAdapter(getActivity());
        newsRecView.setAdapter(adapter);
        initBottomNavigationView();
        //pozivanje async taska
        new GetNews().execute();

        return view;
    }

    //async task gde je logika fetchovanja podataka sa Xml-a
    private class GetNews extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... voids) {
            InputStream inputStream = getInputStream();
            if(inputStream != null){
                try {
                    initXmlPullParser(inputStream);
                } catch (XmlPullParserException | IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            adapter.setNews(news);
        }

        private void initXmlPullParser(InputStream inputStream) throws XmlPullParserException, IOException {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(inputStream,null);
            parser.next();

            parser.require(XmlPullParser.START_TAG,null,"Result");
            while(parser.next() != XmlPullParser.END_TAG){
                //ovaj uslov stavljam da bi izbegao meta data i dosao do podataka koji mi trebaju
                if(parser.getEventType() != XmlPullParser.START_TAG){
                    continue;
                }

                parser.require(XmlPullParser.START_TAG,null,"NewsList");
                while (parser.next() != XmlPullParser.END_TAG){
                    if(parser.getEventType() != XmlPullParser.START_TAG){
                        continue;
                    }

                    //parser.getName vraca trenutni tag
                    if(parser.getName().equals("NewsArticle")){
                        parser.require(XmlPullParser.START_TAG,null,"NewsArticle");
                        //definisanje polja koja moram popuniti radi kreiranja klase NewsArticle
                        String headline = "";
                        String author = parser.getAttributeValue(null,"author");
                        String date = parser.getAttributeValue(null,"dateTime");;
                        String id = parser.getAttributeValue(null,"id");;
                        String imageID = "";

                        while(parser.next() != XmlPullParser.END_TAG){
                            if(parser.getEventType() != XmlPullParser.START_TAG){
                                continue;
                            }
                            String tag = parser.getName();
                            if(tag.equals("Headline")){
                                headline = getContent(parser,"Headline");
                            }
                            else if(tag.equals("Tags")){
                                parser.require(XmlPullParser.START_TAG,null,"Tags");
                                while(parser.next() != XmlPullParser.END_TAG){
                                    if(parser.getEventType() != XmlPullParser.START_TAG){
                                        continue;
                                    }
                                    parser.require(XmlPullParser.START_TAG,null,"Tag");
                                    while(parser.next() != XmlPullParser.END_TAG){
                                        if(parser.getEventType() != XmlPullParser.START_TAG){
                                            continue;
                                        }
                                        //provera u slucaju kad se naidje na Tag koji ne sadrzi PicTT vec Simbole
                                        if(parser.getName().equals("PicTT")){

                                            parser.require(XmlPullParser.START_TAG,null,"PicTT");
                                            while(parser.next() != XmlPullParser.END_TAG){

                                                if(parser.getEventType() != XmlPullParser.START_TAG){
                                                    continue;
                                                }
                                                String tagName = parser.getName();
                                                if(tagName.equals("ImageID")){
                                                    imageID = getContent(parser,"ImageID");
                                                }
                                                else{
                                                    skipTag(parser);
                                                }
                                            }
                                        }
                                       else{
                                           skipTag(parser);
                                        }

                                    }
                                }

                            }
                            else{
                                skipTag(parser);
                            }
                        }
                        NewsArticle newsArticle = new NewsArticle(id,author,date,headline,imageID);
                        news.add(newsArticle);
                    }
                    else{
                        skipTag(parser);
                    }
                }
            }


        }
        //funkcija za preskakanje nepotrebnih tagova
        private void skipTag(XmlPullParser parser) throws XmlPullParserException, IOException {
            if(parser.getEventType() != XmlPullParser.START_TAG){
                //u slucaju da nije start tag
                throw new IllegalStateException();
            }

            int number = 1;
            while(number != 0){
                switch (parser.next()){
                    case XmlPullParser.START_TAG:
                        number++;
                        break;
                        case XmlPullParser.END_TAG:
                            number--;
                            break;
                    default:
                        break;
                }
            }

        }
        //funkcija za uzimanje podataka  izmedju tagova
        private String getContent (XmlPullParser parser , String tagName) throws IOException, XmlPullParserException {
            String result = "";
            parser.require(XmlPullParser.START_TAG,null,tagName);
            if(parser.next() == XmlPullParser.TEXT){
                result = parser.getText();
                parser.next();
            }
            return result;
        }
        private InputStream getInputStream(){
            try {
                URL url = new URL("https://www.teletrader.rs/downloads/tt_news_list.xml");
                Authenticator.setDefault(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("android_tt","Sk3M!@p9e".toCharArray());
                    }
                });
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                return connection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private void initBottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.news);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.primer1:
                        Toast.makeText(getActivity(), "Kliknuto", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.home:
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainer,new MainFragment())
                                .commit();
                        break;
                    case R.id.news:
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainer,new NewsFragment())
                                .commit();
                        break;
                    default:
                        break;
                }

                return false;
            }
        });
    }
}