package com.example.osman.grandresturant.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.ItemsRecycler;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.classes.SallersClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by osman on 4/23/2018.
 */

public class SallerAdapter extends RecyclerView.Adapter<SallerAdapter.Holder> implements Filterable {
    private Context context;
    ArrayList<SallersClass> sellers;
    ArrayList<SallersClass> sellersCopy;

    public SallerAdapter(ArrayList<SallersClass> sellers, ArrayList<SallersClass> sellersCopy, Context context) {
        this.sellers = sellers;
        this.sellersCopy = sellers;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.saller_recycler_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final SallersClass sallersClass = sellers.get(position);
        holder.updateUI(sallersClass);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.Home_Filtter_sallerID = sallersClass.getId();
                context.startActivity(new Intent(context, ItemsRecycler.class));
            }
        });


        holder.whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Intent sendIntent = new Intent(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                    sendIntent.putExtra("jid", sallersClass.getMobile() + "@s.whatsapp.net"); //phone number without "+" prefix
                    sendIntent.setPackage("com.whatsapp");
                    if (sendIntent.resolveActivity(context.getPackageManager()) == null) {
                        Toast.makeText(context, "لا يوجد برنامج الواتس أب ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    context.startActivity(sendIntent);
                } catch (Exception e) {
                    Toast.makeText(context, sallersClass.getMobile(), Toast.LENGTH_SHORT).show();
                }


            }
        });


        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse(sallersClass.getMobile()));
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, sallersClass.getMobile(), Toast.LENGTH_SHORT).show();
                }


            }
        });


        holder.mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + sallersClass.getEmail()));
              /*  emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);*/
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

                    context.startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
                } catch (Exception e) {
                    Toast.makeText(context, sallersClass.getEmail(), Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return sellers.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView name, location;
        de.hdodenhof.circleimageview.CircleImageView image;
        ImageButton whats, call, mail;

        Holder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.saller_recycler_name);
            location = (TextView) itemView.findViewById(R.id.saller_recycler_country_icon);
            whats = (ImageButton) itemView.findViewById(R.id.saller_recycler_whats_icon);
            call = (ImageButton) itemView.findViewById(R.id.saller_recycler_call_icon);
            mail = (ImageButton) itemView.findViewById(R.id.saller_recycler_email_icon);
            image = (de.hdodenhof.circleimageview.CircleImageView) itemView.findViewById(R.id.saller_recycler_image);

        }

        void updateUI(SallersClass sallersClass) {
            name.setText(sallersClass.getUsername());
            location.setText(sallersClass.getCountry());
            Picasso.with(context).load(sallersClass.getProfile_image()).placeholder(image.getDrawable()).into(image);
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {


            @Override
            protected FilterResults performFiltering(CharSequence constraint) {


                ArrayList<SallersClass> filteredResults = null;
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
                sellers = (ArrayList<SallersClass>) results.values;
                SallerAdapter.this.notifyDataSetChanged();


            }
        };
    }

    private ArrayList<SallersClass> getFilteredResults(String constraint) {
        ArrayList<SallersClass> results = new ArrayList<>();

        for (SallersClass item : sellersCopy) {
            if (item.getUsername().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }

}
