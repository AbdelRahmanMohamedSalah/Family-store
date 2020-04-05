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
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.osman.grandresturant.ItemScreen;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.classes.ItemClass;

import java.util.List;



public class nav_item_adapter extends RecyclerView.Adapter<nav_item_adapter.ViewHolder> {

    private Context context;
    private List<ItemClass> my_data;

    public nav_item_adapter(Context context, List<ItemClass> my_data) {
        this.context = context;
        this.my_data = my_data;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView item_image ;
        TextView item_name, item_price, item_type, item_place, item_category;

        public ViewHolder(View view) {
            super(view);

            item_name = (TextView) view.findViewById(R.id.item_recycler_name);
            item_price = (TextView) view.findViewById(R.id.item_recycler_price);
            item_place = (TextView) view.findViewById(R.id.item_recycler_place);
            item_category = (TextView) view.findViewById(R.id.item_recycler_category);
            item_image = (ImageView) view.findViewById(R.id.item_recycler_image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int postion = getLayoutPosition();
                            String id = my_data.get(postion).getId();
                            Intent intent = new Intent(context, ItemScreen.class);
                            intent.putExtra("Item_ID", id);
                            context.startActivity(intent);
                            
                        }
                    });
                }
            });



        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final ItemClass itemClass = my_data.get(position);


        holder.item_name.setText(itemClass.getName());
        holder.item_price.setText(itemClass.getPrice());
        holder.item_place.setText(itemClass.getPlaceLocation());
        holder.item_category.setText(itemClass.getItemType());

        final String id = itemClass.getIdItem();


        Glide.with(context).load(my_data.get(position).getImage()).placeholder(holder.item_image.getDrawable()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(holder.item_image);

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

}