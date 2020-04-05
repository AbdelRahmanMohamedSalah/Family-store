package com.example.osman.grandresturant.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.ItemScreen;
import com.example.osman.grandresturant.NavigationActivities.MyBasket;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.classes.Order;
import com.example.osman.grandresturant.classes.RequestsClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by A.taher on 4/25/2018.
 */

public class MyBasket_Adapter extends RecyclerView.Adapter<MyBasket_Adapter.Holder> implements Filterable {
    private Context context;
    public List<Order> sellers;
    public List<Order> sellersCopy;
    public static ArrayList<Integer> quantities;

    public MyBasket_Adapter(List<Order> sellers, List<Order> sellersCopy, Context context) {
        this.sellers = sellers;
        this.sellersCopy = sellers;
        this.context = context;
        quantities = new ArrayList<>(Collections.nCopies(sellers.size(), 1));
        System.out.println(quantities.size());
        Collections.fill(quantities, 1);
    }

    @Override
    public MyBasket_Adapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.my_basket_item, parent, false);
        return new MyBasket_Adapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(final MyBasket_Adapter.Holder holder, final int position) {
        final RequestsClass itemClass = sellers.get(position).getItem();
        holder.updateUI(itemClass, position);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, ItemScreen.class);
                intent.putExtra("Item_ID", itemClass.getRequestItemID());
                context.startActivity(intent);

            }
        });

        holder.RequestDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("هل متاكد من حذف هذا المنتج");

                builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sellers.remove(position);
                        HelperMethods.orders.get(itemClass.getRequestSallerID()).remove(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                        ((MyBasket) context).clearArray();
                    }
                });

                builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }



        });


    }

    @Override
    public int getItemCount() {
        return sellers.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        View view;
        ImageView RequestItemImage, RequestDeleteBtn;
        TextView RequestItemName, RequestItemPrice, quantity, subTotal;
        ImageButton incBtn, decBtn;

        Holder(View itemView) {
            super(itemView);
            view = itemView;
            RequestItemName = (TextView) view.findViewById(R.id.My_Basket_item_name);
            RequestItemPrice = (TextView) view.findViewById(R.id.My_Basket_item_price);
            RequestItemImage = (ImageView) view.findViewById(R.id.My_Basket_item_image);
            RequestDeleteBtn = (ImageView) view.findViewById(R.id.My_Basket_delete_btn);
            incBtn = (ImageButton) view.findViewById(R.id.increase_btn);
            decBtn = (ImageButton) view.findViewById(R.id.dec_btn);
            quantity = (TextView) view.findViewById(R.id.quantity_text);
            subTotal = (TextView) view.findViewById(R.id.subtotal_text);

        }

        void updateUI(final RequestsClass itemsClass, final int position) {

            RequestItemName.setText(itemsClass.getRequestItemName());
            RequestItemPrice.setText(itemsClass.getRequestItemPrice());
            quantity.setText(String.valueOf(quantities.get(position)));

            Glide.with(view.getContext()).load(itemsClass.getRequestItemImage()).placeholder(RequestItemImage.getDrawable()).fitCenter().into(RequestItemImage);

            decBtn.setEnabled(true);

            subTotal.setText(String.valueOf(Integer.valueOf(itemsClass.getRequestItemPrice()) * quantities.get(position)));

            incBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer currentQuantity = Integer.valueOf(quantity.getText().toString());
                    quantity.setText(String.valueOf(++currentQuantity));
                    quantities.set(position, currentQuantity);
                    decBtn.setEnabled(true);
                    subTotal.setText(String.valueOf(Integer.valueOf(itemsClass.getRequestItemPrice()) * quantities.get(position)));
                }
            });

            decBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer currentQuantity = Integer.valueOf(quantity.getText().toString());
                    if (currentQuantity == 1) return;
                    quantity.setText(String.valueOf(--currentQuantity));
                    quantities.set(position, currentQuantity);
                    if (currentQuantity == 1) decBtn.setEnabled(false);
                    subTotal.setText(String.valueOf(Integer.valueOf(itemsClass.getRequestItemPrice()) * quantities.get(position)));
                }
            });

        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Order> filteredResults = null;
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
                sellers = (ArrayList<Order>) results.values;
                MyBasket_Adapter.this.notifyDataSetChanged();
            }
        };
    }

    private ArrayList<Order> getFilteredResults(String constraint) {
        ArrayList<Order> results = new ArrayList<>();

        for (Order item : sellersCopy) {
            if (item.getItem().getRequestItemName().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }

}