package com.example.osman.grandresturant.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.osman.grandresturant.Dialogs.MyAds_delete_dialog;
import com.example.osman.grandresturant.Edit_Ads;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.ItemScreen;
import com.example.osman.grandresturant.ItemsRecycler;
import com.example.osman.grandresturant.NavigationActivities.MyAds;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.classes.ItemClass;
import com.example.osman.grandresturant.classes.RequestsClass;
import com.example.osman.grandresturant.classes.SallersClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by A.taher on 4/25/2018.
 */

public class MyAds_Adapter extends RecyclerView.Adapter<MyAds_Adapter.Holder> implements Filterable {
    private Context context;
    ArrayList<ItemClass> sellers;
    ArrayList<ItemClass> sellersCopy;

    public MyAds_Adapter(ArrayList<ItemClass> sellers, ArrayList<ItemClass> sellersCopy, Context context) {
        this.sellers = sellers;
        this.sellersCopy = sellers;
        this.context = context;
    }

    @Override
    public MyAds_Adapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.my_ads_item, parent, false);
        return new MyAds_Adapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(MyAds_Adapter.Holder holder, int position) {
        final ItemClass itemClass = sellers.get(position);
        holder.updateUI(itemClass);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ItemScreen.class);

                intent.putExtra("Item_ID", itemClass.getIdItem());


                context.startActivity(intent);

            }
        });

        holder.myAds_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Edit_Ads.class);
                intent.putExtra("id", itemClass.getIdItem());
                context.startActivity(intent);

            }
        });


        holder.myAds_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelperMethods.delete_ads_id = itemClass.getIdItem();
                MyAds_delete_dialog cdd = new MyAds_delete_dialog((Activity) context);
                cdd.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return sellers.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView myAds_img, myAds_delete, myAds_edit;
        TextView myAds_name, myAds_price, myAds_country, myAds_username;

        Holder(View itemView) {
            super(itemView);

            myAds_name = (TextView) itemView.findViewById(R.id.my_ads_recycler_name);
            myAds_price = (TextView) itemView.findViewById(R.id.my_ads_recycler_price);
            myAds_country = (TextView) itemView.findViewById(R.id.my_ads_recycler__place);
            myAds_username = (TextView) itemView.findViewById(R.id.my_ads_recycler_user_name);
            myAds_img = (ImageView) itemView.findViewById(R.id.my_ads_recycler_img);
            myAds_delete = (ImageView) itemView.findViewById(R.id.my_ads_recycler_delete);
            myAds_edit = (ImageView) itemView.findViewById(R.id.my_ads_recycler_edit);

        }

        void updateUI(ItemClass itemClass) {

            myAds_name.setText(itemClass.getName());
            myAds_country.setText(itemClass.getCountryLocation());
            myAds_price.setText(itemClass.getPrice());
            myAds_username.setText(itemClass.getItemType());


            Glide.with(context).load(itemClass.getImage()).placeholder(myAds_img.getDrawable()).fitCenter().into(myAds_img);

        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<ItemClass> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = sellersCopy;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                sellers = (ArrayList<ItemClass>) results.values;
                MyAds_Adapter.this.notifyDataSetChanged();
            }
        };
    }

    private ArrayList<ItemClass> getFilteredResults(String constraint) {
        ArrayList<ItemClass> results = new ArrayList<>();

        for (ItemClass item : sellersCopy) {
            if (item.getName().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }

}