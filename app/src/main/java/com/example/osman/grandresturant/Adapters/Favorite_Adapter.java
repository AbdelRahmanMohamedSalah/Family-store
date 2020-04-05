package com.example.osman.grandresturant.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osman.grandresturant.Favorite_item;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.classes.ItemClass;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Abdel Rahman on 15-Apr-18.
 */

public class Favorite_Adapter extends RecyclerView.Adapter<Favorite_Adapter.ViewHolder> {

    private Context context;
    private List<ItemClass> my_data;

    public Favorite_Adapter(Context context, List<ItemClass> my_data) {
        this.context = context;
        this.my_data = my_data;
    }


    @Override
    public Favorite_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item_details, parent, false);

        return new Favorite_Adapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final ItemClass itemClass = my_data.get(position);

        holder.item_name.setText(itemClass.getName());
        holder.item_price.setText(itemClass.getPrice());
        holder.item_place.setText(itemClass.getPlaceLocation());
        holder.item_category.setText(itemClass.getItemType());
        Picasso.with(context).load(itemClass.getImage()).into(holder.item_image);

        holder.unfavoritBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setCancelable(false);
                dialog.setTitle("تنبيه");
                dialog.setMessage("هل متاكد من حذف هذا الاعلان ");
                dialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String firebaseAuth = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Favorite");
                        database.child(firebaseAuth).child(itemClass.getIdItem()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, " تم حزف " + itemClass.getName(), Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context , Favorite_item.class));
                            }
                        });




                    }
                })
                        .setNegativeButton("لا ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView item_image, unfavoritBtn;
        TextView item_name, item_price, item_type, item_place, item_category;

        public ViewHolder(View view) {
            super(view);

            item_name = (TextView) view.findViewById(R.id.favorite_item_name);
            item_price = (TextView) view.findViewById(R.id.favorite_item_price);
            item_place = (TextView) view.findViewById(R.id.favorite_item_place);
            item_category = (TextView) view.findViewById(R.id.favorite_item_category);
            item_image = (ImageView) view.findViewById(R.id.favorite_item_image);
            unfavoritBtn = (ImageView) view.findViewById(R.id.unfavoritBtn);


        }
    }

}