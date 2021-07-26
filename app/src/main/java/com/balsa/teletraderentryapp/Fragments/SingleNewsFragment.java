package com.balsa.teletraderentryapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balsa.teletraderentryapp.GitHubActivity;
import com.balsa.teletraderentryapp.MainActivity;
import com.balsa.teletraderentryapp.Models.NewsArticle;
import com.balsa.teletraderentryapp.R;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.nio.BufferUnderflowException;


public class SingleNewsFragment extends Fragment {

    private ImageView image;
    private BottomNavigationView bottomNavigationView;
    private TextView txtHeadlineOver,txtDate,txtAuthor;

    public SingleNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_news, container, false);
        initViews(view);
        //preuzima se dolazeci bundle i proverava da li ima podataka u njemu
        Bundle bundle = getArguments();
        if(bundle != null){
            NewsArticle newsArticle = bundle.getParcelable("news_article");
            if(newsArticle != null){
                //setovanje podataka na trenutni fragment
                txtHeadlineOver.setText(newsArticle.getHeadline());
                //potrudicu se da ispravim ako stignem :)
                String date = newsArticle.getDateTime().substring(0,10);
                txtDate.setText(date);
                txtAuthor.setText(newsArticle.getAuthor());

                //pokusao sam odraditi sa BitmapFactory kao sto je zakomentarisano u NewsFragment-u ,ali jako je lose
                Glide.with(getActivity())
                        .asBitmap()
                        .load("https://cdn.ttweb.net/News/images/"+newsArticle.getImageId()+".jpg?preset=w320_q50")
                        .into(image);
            }
        }
        initBottomNavigationView();
        return view;
    }


    private void initBottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.news);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.web:
                        Intent webintent = new Intent(getActivity(), GitHubActivity.class);
                        startActivity(webintent);
                        break;
                    case R.id.home:
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.fragmentContainer,new MainFragment())
//                                .commit();
                        break;
                    case R.id.news:
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainer,new NewsFragment())
                                .addToBackStack(null)
                                .commit();
                        break;
                    default:
                        break;
                }

                return false;
            }
        });
    }
    private void initViews(View view) {
        image = view.findViewById(R.id.singleNewsImage);
        txtHeadlineOver = view.findViewById(R.id.txtHeadlineOver);
        txtDate = view.findViewById(R.id.txtDate);
        bottomNavigationView = view.findViewById(R.id.bottomNavViewSingleNews);
        txtAuthor = view.findViewById(R.id.txtAuthor);
    }
}