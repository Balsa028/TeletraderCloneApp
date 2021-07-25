package com.balsa.teletraderentryapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.balsa.teletraderentryapp.Fragments.SymbolDetailsFragment;
import com.balsa.teletraderentryapp.Models.Symbol;
import com.balsa.teletraderentryapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SymbolAdapter extends RecyclerView.Adapter<SymbolAdapter.ViewHolderSymbol> {

    private ArrayList<Symbol> symbols = new ArrayList<>();
    private Context context;
    public SymbolAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderSymbol onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.symbol_list_item,parent,false);
        return new ViewHolderSymbol(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SymbolAdapter.ViewHolderSymbol holder, int position) {


            holder.txtSymbolName.setText(symbols.get(position).getSymbolName());
            holder.txtSymbolLast.setText(symbols.get(position).getLast());

            //----------change precent
            //CHECKING FOR NULL VALUES
            if(symbols.get(position).getChangePrecent() != null){
                //checking is it above or below zero for font color
                if(Float.parseFloat(symbols.get(position).getChangePrecent())>0){
                    holder.txtSymbolChange.setTextColor(Color.GREEN);
                    holder.txtSymbolChange.setText("+"+symbols.get(position).getChangePrecent().substring(0,4)+"%");
                }
                else if(Float.parseFloat(symbols.get(position).getChangePrecent())<0){
                    holder.txtSymbolChange.setTextColor(Color.RED);
                    holder.txtSymbolChange.setText(symbols.get(position).getChangePrecent().substring(0,4)+"%");
                }
                else{
                    holder.txtSymbolChange.setTextColor(Color.WHITE);
                    holder.txtSymbolChange.setText(symbols.get(position).getChangePrecent().substring(0,4)+"%");
                }
            }
            else{
                holder.txtSymbolChange.setText("-");
                holder.txtSymbolChange.setTextColor(Color.WHITE);
            }

            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //kreiranje i punjenje bundle i slanje objekta simbola na sledeci fragment
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("selected_symbol",symbols.get(position));
                    SymbolDetailsFragment fragment = new SymbolDetailsFragment();
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
        return symbols.size();
    }
    //setter for array


    public void setSymbols(ArrayList<Symbol> symbols) {
        this.symbols = symbols;
        notifyDataSetChanged();
    }

    public class ViewHolderSymbol extends RecyclerView.ViewHolder{

        private CardView parent;
        private TextView txtSymbolName,txtSymbolChange,txtSymbolLast;

        public ViewHolderSymbol(@NonNull @NotNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            txtSymbolChange = itemView.findViewById(R.id.txtSymbolChange);
            txtSymbolLast = itemView.findViewById(R.id.txtSymbolLast);
            txtSymbolName = itemView.findViewById(R.id.txtSymbolName);
        }
    }
}
