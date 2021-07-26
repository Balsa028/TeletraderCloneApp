package com.balsa.teletraderentryapp.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
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

public class SymbolAdapter extends RecyclerView.Adapter<SymbolAdapter.ViewHolderSymbol> {

    private static final String TAG = "testing";
    private ArrayList<Symbol> symbols = new ArrayList<>();
    private Context context;
    private Handler handler = new Handler();
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
                    holder.txtSymbolChange.setText(symbols.get(position).getChangePrecent().substring(0,5)+"% ");
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
                            .addToBackStack(null)
                            .commit();
                }
            });
                ////brisanje iabranog simbola DUGIM KLIKOM NA SAM SIMBOL
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

    //--------simulacija Last vrednosti

        //uzimanje vrednosti Last-a i castovanje u float
        String value = holder.txtSymbolLast.getText().toString();
        float currentLastValue = Float.parseFloat(value);

        //handle klasa radi simulacije skokova i padova vrednosti u pozadini
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    double newLastValue = getRandomValue(currentLastValue);

                    if(newLastValue-currentLastValue >= 0){
                        if(String.valueOf(newLastValue).length()>=9){
                            holder.txtSymbolLast.setText(String.valueOf(newLastValue).substring(0,9));
                            holder.txtSymbolLast.setBackgroundColor(context.getResources().getColor(R.color.stocks_up));

                        }
                    }
                    else{
                        if (String.valueOf(newLastValue).length()>=9){
                            holder.txtSymbolLast.setText(String.valueOf(newLastValue).substring(0,9));
                            holder.txtSymbolLast.setBackgroundColor(context.getResources().getColor(R.color.stocks_down));
                        }

                    }
                    // za vracanje pozadine na crnu boju posle 2 sekunde
                    new CountDownTimer(2000,500){
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }
                        @Override
                        public void onFinish() {
                            holder.txtSymbolLast.setBackgroundColor(Color.BLACK);
                        }
                    }.start();
                }
            },getRandomMillis(3000,30000));


//--------------simulacija procenta

        //uzimanje vrednosti Change-a i castovanje u float
        String valueChange = symbols.get(position).getChangePrecent();

        //null check
        if(valueChange != null){
            float currentChangeValue = Float.parseFloat(valueChange);

            //handle klasa radi simulacije skokova i padova vrednosti u pozadini
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    double newChangeValue = getRandomValue(currentChangeValue);

                        if(newChangeValue > 0){
                            holder.txtSymbolChange.setText("+"+String.valueOf(newChangeValue).substring(0,4)+"%");
                            holder.txtSymbolChange.setTextColor(Color.GREEN);
                        }
                        else if(newChangeValue < 0){
                            holder.txtSymbolChange.setText(String.valueOf(newChangeValue).substring(0,5)+"%");
                            holder.txtSymbolChange.setTextColor(Color.RED);
                        }
                        else {
                            holder.txtSymbolChange.setText(String.valueOf(newChangeValue).substring(0,4)+"%");
                            holder.txtSymbolChange.setTextColor(Color.WHITE);
                        }
                }
            },getRandomMillis(3000,30000));
        }

    }

    @Override
    public int getItemCount() {
        return symbols.size();
    }

    //funkcija za dobijanje random millisekundi izmedju unetih parametra
    private Long getRandomMillis(int a, int b){
        double millis = Math.random() * (b-a) + a;
        return Math.round(millis);
    }
    //funkcija koja na osnovu proslednjenog float broja nalazi nasumican broj izmedju -20% i +20% od prosledjenog float-a
    private double getRandomValue(float number){
        double lower = number-(number*0.2); //max
        double upper = number+(number*0.2); //min
        String newLastValue = String.valueOf(Math.random() * (upper-lower) + lower);
        return Double.parseDouble(newLastValue);
    }
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
