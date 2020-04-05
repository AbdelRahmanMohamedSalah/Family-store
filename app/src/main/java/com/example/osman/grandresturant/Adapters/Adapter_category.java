package com.example.osman.grandresturant.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osman.grandresturant.Dialogs.HomeScreenChooceCountry;
import com.example.osman.grandresturant.Dialogs.MyAds_delete_dialog;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.NavigationActivities.MyAds;
import com.example.osman.grandresturant.classes.Item_recycle;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.SallersRecycler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Abdel Rahman on 06-Apr-18.
 */

public class Adapter_category extends RecyclerView.Adapter<Adapter_category.Holder> {
private Context context;
ArrayList<Item_recycle> arrayList;

    public Adapter_category(Context context, ArrayList<Item_recycle> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public  class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public Holder(View itemView) {
            super(itemView);

            imageView=(ImageView)itemView.findViewById(R.id.iv_item_category);
            textView=(TextView)itemView.findViewById(R.id.tv_item_category);



        }
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

View v= LayoutInflater.from(context).inflate(R.layout.item_recycle_category,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        final Item_recycle item_recycle=arrayList.get(position);
        holder.textView.setText(item_recycle.getName());
        Picasso.with(context).load(item_recycle.getImage()).placeholder(holder.imageView.getDrawable()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(HelperMethods.Home_Filtter_Country_name == null)
                {

                    HelperMethods.Home_Filtter_categoryName = item_recycle.getName();
                    HomeScreenChooceCountry cdd=new HomeScreenChooceCountry((Activity) context);
                    cdd.show();

                }
                else
                {


                    HelperMethods.Home_Filtter_categoryName =  item_recycle.getName();
                    context.startActivity(new Intent(context, SallersRecycler.class));
                }



            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
