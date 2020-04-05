package com.example.osman.grandresturant.Adapters;



import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.osman.grandresturant.NavigationActivities.NavItemRecycler;
import com.example.osman.grandresturant.classes.Item_recycle;
import com.example.osman.grandresturant.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Abdel Rahman on 06-Apr-18.
 */

public class nav_category_adapter extends RecyclerView.Adapter<nav_category_adapter.Holder> {
    private Context context;
    ArrayList<Item_recycle> arrayList;

    public nav_category_adapter(Context context, ArrayList<Item_recycle> arrayList) {
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    int postion = getLayoutPosition();
                    String category_name = arrayList.get(postion).getName();
                    Intent intent = new Intent(context, NavItemRecycler.class);
                    intent.putExtra("Item_type", "Category");
                    intent.putExtra("Filter", category_name);
                    context.startActivity(intent);
                }
            });


        }
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.item_recycle_category,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Item_recycle item_recycle=arrayList.get(position);
        holder.textView.setText(item_recycle.getName());
        Glide.with(context).load(item_recycle.getImage()).placeholder(holder.imageView.getDrawable()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
