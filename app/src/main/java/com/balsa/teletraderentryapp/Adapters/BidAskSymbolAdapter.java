package com.balsa.teletraderentryapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.balsa.teletraderentryapp.Models.Symbol;
import com.balsa.teletraderentryapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BidAskSymbolAdapter extends RecyclerView.Adapter<BidAskSymbolAdapter.BidAskViewHolder> {

    private ArrayList<Symbol> symbols = new ArrayList<>();
    private Context context;
    public BidAskSymbolAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public BidAskViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.symbol_bid_list_item,parent,false);
        return new BidAskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BidAskSymbolAdapter.BidAskViewHolder holder, int position) {


        holder.txtSymbolName.setText(symbols.get(position).getSymbolName());
        //null checks
        if(symbols.get(position).getBid() != null){
            holder.txtSymbolBid.setText(symbols.get(position).getBid());
        }
        else{
            holder.txtSymbolBid.setText("-");
        }

        if(symbols.get(position).getAsk() != null){
            holder.txtSymbolAsk.setText(symbols.get(position).getAsk());
        }
        else{
            holder.txtSymbolBid.setText("-");
        }

        if(symbols.get(position).getHigh() != null){
            holder.txtSymbolHigh.setText(symbols.get(position).getHigh());
        }
        else{
            holder.txtSymbolBid.setText("-");
        }

        if(symbols.get(position).getLow() != null){
            holder.txtSymbolLow.setText(symbols.get(position).getLow());
        }
        else{
            holder.txtSymbolBid.setText("-");
        }

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 25.7.21. ODRADI OVO pavlicevicu

            }
        });
    }

    @Override
    public int getItemCount() {
        return symbols.size();
    }

    public void setSymbols(ArrayList<Symbol> symbols) {
        this.symbols = symbols;
        notifyDataSetChanged();
    }

    public class BidAskViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private TextView txtSymbolName,txtSymbolBid,txtSymbolHigh,txtSymbolAsk,txtSymbolLow;

        public BidAskViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            txtSymbolBid = itemView.findViewById(R.id.txtSymbolBid);
            txtSymbolHigh = itemView.findViewById(R.id.txtSymbolHigh);
            txtSymbolName = itemView.findViewById(R.id.txtSymbolNameBid);
            txtSymbolAsk = itemView.findViewById(R.id.txtSymbolAsk);
            txtSymbolLow = itemView.findViewById(R.id.txtSymbolLow);
        }


    }
}
