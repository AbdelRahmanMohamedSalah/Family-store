package com.example.osman.grandresturant.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osman.grandresturant.classes.Encaps_Basket;
import com.example.osman.grandresturant.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by osman on 4/12/2018.
 */

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.Holder> {
    private Context context;
    ArrayList<Encaps_Basket> arrayList;

    public BasketAdapter(Context context, ArrayList<Encaps_Basket> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.form_basket, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Encaps_Basket encaps_basket = arrayList.get(position);

        holder.nameBasket.setText(encaps_basket.getName());
        holder.priceBasket.setText(encaps_basket.getPrice());
        holder.placeItemBasket.setText(encaps_basket.getPlace());
        holder.companyNameBasket.setText(encaps_basket.getCompanyName());
        Picasso.with(context).load(encaps_basket.getImage()).into(holder.imageItemBasket);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "hiii", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView nameBasket, priceBasket, companyNameBasket, placeItemBasket, numberItemBasket;
        ImageView imageItemBasket;
        ImageView decresedItemBasket, incresdItemBasket;
        int i = 0;

        public Holder(View itemView) {
            super(itemView);
            nameBasket = (TextView) itemView.findViewById(R.id.nameBasket);
            priceBasket = (TextView) itemView.findViewById(R.id.priceBasket);
            companyNameBasket = (TextView) itemView.findViewById(R.id.companyNameBasket);
            placeItemBasket = (TextView) itemView.findViewById(R.id.placeItemBasket);
            imageItemBasket = (ImageView) itemView.findViewById(R.id.imageItemBasket);

            decresedItemBasket = (ImageView) itemView.findViewById(R.id.decresedItemBasket);
            incresdItemBasket = (ImageView) itemView.findViewById(R.id.incresdItemBasket);
            numberItemBasket = (TextView) itemView.findViewById(R.id.numberItemBasket);

            incresdItemBasket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    numberItemBasket.setText(String.valueOf(++i));
                }
            });
            decresedItemBasket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (i < 1) {
                        numberItemBasket.setText(String.valueOf(0));

                    } else {
                        numberItemBasket.setText(String.valueOf(--i));

                    }

                }
            });


        }
    }
}
