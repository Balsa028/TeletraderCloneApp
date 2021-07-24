package com.balsa.teletraderentryapp.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.balsa.teletraderentryapp.Fragments.SingleNewsFragment;
import com.balsa.teletraderentryapp.Models.NewsArticle;
import com.balsa.teletraderentryapp.R;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<NewsArticle> news = new ArrayList<>();
    private Context context;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NewsAdapter.ViewHolder holder, int position) {

        holder.headline.setText(news.get(position).getHeadline());
        Glide.with(context)
                .asBitmap()
                .centerCrop()
                .load("https://cdn.ttweb.net/News/images/"+news.get(position).getImageId()+".jpg?preset=w220_q40")  //https://cdn.ttweb.net/News/images/145854.jpg?preset=w220_q40
                .into(holder.newsImage);

        //making onClick listener for each item/card
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 24.7.21. Do logic here for clicking on specific item
                //pravljenje bundle radi prenosa podataka sa fragmenta na fragment
                SingleNewsFragment fragment = new SingleNewsFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("news_article",news.get(position));
                fragment.setArguments(bundle);
                ((AppCompatActivity)context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer,fragment)
                        .addToBackStack("tag")
                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return news.size();
    }


    //setter for array
    public void setNews(ArrayList<NewsArticle> news) {
        this.news = news;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView parent;
        private TextView headline;
        private ImageView newsImage;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            headline = itemView.findViewById(R.id.txtHeadline);
            newsImage = itemView.findViewById(R.id.imageNewsItem);
        }
    }
}
