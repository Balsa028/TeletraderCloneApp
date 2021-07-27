package com.balsa.teletraderentryapp.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.balsa.teletraderentryapp.Fragments.SymbolDetailsFragment;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.symbol_bid_list_item, parent, false);
        return new BidAskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BidAskSymbolAdapter.BidAskViewHolder holder, int position) {


        holder.txtSymbolName.setText(symbols.get(position).getSymbolName());
        //null checks
        if (symbols.get(position).getBid() != null) {
            holder.txtSymbolBid.setText(symbols.get(position).getBid());
        } else {
            holder.txtSymbolBid.setText("-");
        }

        if (symbols.get(position).getAsk() != null) {
            holder.txtSymbolAsk.setText(symbols.get(position).getAsk());
        } else {
            holder.txtSymbolBid.setText("-");
        }

        if (symbols.get(position).getHigh() != null) {
            holder.txtSymbolHigh.setText(symbols.get(position).getHigh());
        } else {
            holder.txtSymbolBid.setText("-");
        }

        if (symbols.get(position).getLow() != null) {
            holder.txtSymbolLow.setText(symbols.get(position).getLow());
        } else {
            holder.txtSymbolBid.setText("-");
        }

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kreiranje i punjenje bundle i slanje objekta simbola na sledeci fragment
                Bundle bundle = new Bundle();
                bundle.putParcelable("selected_symbol", symbols.get(position));
                SymbolDetailsFragment fragment = new SymbolDetailsFragment();
                fragment.setArguments(bundle);
                ((AppCompatActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //alert dialog za potvrdu brisanja
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete selected symbol?")
                        .setIcon(R.drawable.ic_delete)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                symbols.remove(symbols.get(position));
                                notifyDataSetChanged();
                                Toast.makeText(context, "You have succesfully deleted symbol from list", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No, Thanks", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Deleting Symbol");
                alertDialog.show();
                return false;
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

    public class BidAskViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private TextView txtSymbolName, txtSymbolBid, txtSymbolHigh, txtSymbolAsk, txtSymbolLow;

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
