package com.balsa.teletraderentryapp.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balsa.teletraderentryapp.Adapters.BidAskSymbolAdapter;
import com.balsa.teletraderentryapp.Adapters.SymbolAdapter;
import com.balsa.teletraderentryapp.Models.Symbol;
import com.balsa.teletraderentryapp.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BidAskFragment extends Fragment {

    //private SwipeRefreshLayout swipeRefreshLayout;
    private TextView txtSymbolName;
    private ImageView sortAsc, sortDesc;
    private RecyclerView symbolRecView;
    private BidAskSymbolAdapter adapter;
    private ArrayList<Symbol> symbols;

    public BidAskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ask_bid, container, false);
        initViews(view);
        adapter = new BidAskSymbolAdapter(getActivity());
        symbols = new ArrayList<>();
        symbolRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
        symbolRecView.setAdapter(adapter);
//        //refreshovanje recycler viewa sa podatcima
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new GetSymbolData().execute();
//            }
//        });

        new GetSymbolData().execute();

        return view;
    }
    private class GetSymbolData extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            InputStream inputStream = getInputStream();
            if(inputStream != null){
                try {
                    initXMLParser(inputStream);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            adapter.setSymbols(symbols);
        }

        private void initXMLParser(InputStream inputStream) throws XmlPullParserException, IOException, ParseException {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(inputStream,null);
            parser.next();

            //getting in Result
            parser.require(XmlPullParser.START_TAG,null,"Result");
            while(parser.next() != XmlPullParser.END_TAG){
                if(parser.getEventType() != XmlPullParser.START_TAG){
                    continue;
                }

                //getting in Symbol list
                parser.require(XmlPullParser.START_TAG,null,"SymbolList");
                while (parser.next() != XmlPullParser.END_TAG){
                    if(parser.getEventType() != XmlPullParser.START_TAG){
                        continue;
                    }

                    //getting in Symbol
                    if(parser.getName().equals("Symbol")){
                        parser.require(XmlPullParser.START_TAG,null,"Symbol");
                        //definisanje polja koja nam trebaju za keriranje klase simbol
                        String id = parser.getAttributeValue(null,"id");
                        String symbolName = parser.getAttributeValue(null,"name");
                        String tickerSymbol = parser.getAttributeValue(null,"tickerSymbol");
                        String last="";
                        String changePrecent="";
                        String high="";
                        String low="";
                        String bid="";
                        String ask="";
                        String volume="";
                        Date dateTime = new Date();
                        String change="";

                        while (parser.next() != XmlPullParser.END_TAG){
                            if(parser.getEventType() != XmlPullParser.START_TAG){
                                continue;
                            }
                            if(parser.getName().equals("Quote")){
                                parser.require(XmlPullParser.START_TAG,null,"Quote");
                                last = parser.getAttributeValue(null,"last");
                                high = parser.getAttributeValue(null,"high");
                                low = parser.getAttributeValue(null,"low");
                                bid = parser.getAttributeValue(null,"bid");
                                ask = parser.getAttributeValue(null,"ask");
                                volume = parser.getAttributeValue(null,"volume");
                                dateTime = parseDateString(parser.getAttributeValue(null,"dateTime"));
                                change = parser.getAttributeValue(null,"change");
                                changePrecent = parser.getAttributeValue(null,"changePercent");
                                while(parser.next() != XmlPullParser.END_TAG){
                                    continue;
                                }

                            }
                            else{
                                skipTag(parser);
                            }

                        }
                        Symbol symbol = new Symbol(id,symbolName,tickerSymbol,last,high,low,bid,ask,volume,dateTime,change,changePrecent);
                        symbols.add(symbol);
                    }
                    else{
                        skipTag(parser);
                    }

                }

            }

        }

        private Date parseDateString(String dateTime) throws ParseException {
            String date = dateTime;
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date parsedDate = inputFormat.parse(date);
            return parsedDate;

        }
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
        private InputStream getInputStream() {
            try {
                URL url = new URL("https://www.teletrader.rs/downloads/tt_symbol_list.xml");
                Authenticator.setDefault(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("android_tt","Sk3M!@p9e".toCharArray());
                    }
                });
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                return connection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private void initViews(View view) {
        txtSymbolName = view.findViewById(R.id.txtNameBidAsk);
        sortAsc = view.findViewById(R.id.imageAscending);
        sortDesc = view.findViewById(R.id.imageDescending);
        symbolRecView = view.findViewById(R.id.RecViewBidAsk);
       //swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLay);
    }
}