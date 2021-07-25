package com.balsa.teletraderentryapp.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.balsa.teletraderentryapp.MainActivity;
import com.balsa.teletraderentryapp.Models.Symbol;
import com.balsa.teletraderentryapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;


public class SymbolDetailsFragment extends Fragment {
    private static final String TAG = "SymbolDetailsFragment";
    private TextView txtSymbolName,txtTickerSymbol,txtLast,txtChange,txtChangePrecent,txtHigh,txtLow,
            txtDatetime,txtVolume,txtAsk,txtBid;
    private BottomNavigationView bottomNavigationView;
    public SymbolDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_symbol_details, container, false);
        initViews(view);
        initBottomNavigationView();
        //prima se dolazeci bundle
        Bundle bundle = getArguments();
        if(bundle != null){
            Symbol symbol = bundle.getParcelable("selected_symbol");
            if(symbol != null){
                txtSymbolName.setText(symbol.getSymbolName());
                txtTickerSymbol.setText("("+symbol.getTickerSymbol()+")");
                //proveravanje null vrednosti i provera vrednosti
                //----change
                if(symbol.getChange() != null){
                    if(Float.parseFloat(symbol.getChange())>0){
                        txtChange.setText("+"+symbol.getChange().substring(0,4));
                        txtChange.setTextColor(Color.GREEN);
                    }
                    else if(Float.parseFloat(symbol.getChange())<0){
                        txtChange.setText(symbol.getChange().substring(0,4));
                        txtChange.setTextColor(Color.RED);
                    }
                    else{
                        txtChange.setText(symbol.getChange());
                        txtChange.setTextColor(Color.WHITE);
                    }
                }
                else{
                    txtChange.setText("None");
                    txtChange.setTextColor(Color.WHITE);
                }
                //------change precent and last
                if(symbol.getChangePrecent() != null){
                    if(Float.parseFloat(symbol.getChangePrecent())>0){
                        txtChangePrecent.setText("+"+symbol.getChangePrecent().substring(0,4)+"%");
                        txtChangePrecent.setTextColor(Color.GREEN);
                        txtLast.setText(symbol.getLast());
                        txtLast.setTextColor(Color.GREEN);
                    }
                    else if(Float.parseFloat(symbol.getChangePrecent())<0){
                        txtChangePrecent.setText(symbol.getChangePrecent().substring(0,6)+"%");
                        txtChangePrecent.setTextColor(Color.RED);
                        txtLast.setText(symbol.getLast());
                        txtLast.setTextColor(Color.RED);
                    }
                    else{
                        txtChangePrecent.setText(symbol.getChangePrecent());
                        txtChangePrecent.setTextColor(Color.WHITE);
                        txtLast.setText(symbol.getLast());
                        txtLast.setTextColor(Color.WHITE);
                    }
                }
                else{
                    txtChangePrecent.setText("None");
                    txtChangePrecent.setTextColor(Color.WHITE);
                }
                if(symbol.getLast() == null){
                    txtLast.setText("None");
                    txtLast.setTextColor(Color.WHITE);
                }
                //-------high
                if(symbol.getHigh() != null){
                    txtHigh.setText(symbol.getHigh());
                }
                else{
                    txtHigh.setText("None");
                }
                //-------low
                if(symbol.getLow() != null){
                    txtLow.setText(symbol.getHigh());
                }
                else{
                    txtLow.setText("None");
                }
                //-------volume
                if(symbol.getVolume() != null){
                    txtVolume.setText(symbol.getVolume());
                }
                else{
                    txtVolume.setText("None");
                }
                //-------ask
                if(symbol.getAsk() != null){
                    txtAsk.setText(symbol.getAsk());
                }
                else{
                    txtAsk.setText("None");
                }
                //-------bid
                if(symbol.getAsk() != null){
                    txtBid.setText(symbol.getBid());
                }
                else{
                    txtBid.setText("None");
                }

                txtDatetime.setText(String.valueOf(symbol.getDateTime()));
            }
            else {
                Log.d(TAG, "onCreateView: vrednost simbola je null");
            }
        }
        else{
            Log.d(TAG, "onCreateView: Vrednost bundla je null");
        }
        return view;
    }

    private void initViews(View view) {
        txtSymbolName = view.findViewById(R.id.txtSymbolNameDetails);
        txtTickerSymbol = view.findViewById(R.id.txtTickerSymbolDetails);
        txtLast = view.findViewById(R.id.txtSymbolLastDetails);
        txtChange = view.findViewById(R.id.txtSymbolChangeDetails);
        txtChangePrecent = view.findViewById(R.id.txtSymbolChangePreDetails);
        txtHigh = view.findViewById(R.id.txtSymbolHighDetails);
        txtLow = view.findViewById(R.id.txtSymbolLowDetails);
        txtVolume = view.findViewById(R.id.txtSymbolVolumeDetails);
        txtAsk = view.findViewById(R.id.txtSymbolAskDetails);
        txtBid = view.findViewById(R.id.txtSymbolBidDetails);
        txtDatetime = view.findViewById(R.id.txtDateTimeDetails);
        bottomNavigationView = view.findViewById(R.id.bottomNavViewSymDetails);
    }
    private void initBottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.primer1:
                        Toast.makeText(getActivity(), "Kliknuto", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.home:
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
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