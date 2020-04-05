package com.example.osman.grandresturant.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.osman.grandresturant.classes.Encaps_Basket;
import com.example.osman.grandresturant.classes.Request_Encap;

import java.util.ArrayList;

/**
 * Created by osman on 5/1/2018.
 */

public class Adapter_Requests extends RecyclerView.Adapter<Adapter_Requests.VH> {

    private Context context;
    ArrayList<Request_Encap> arrayList;

    public Adapter_Requests(Context context, ArrayList<Request_Encap> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        public VH(View itemView) {
            super(itemView);


        }
    }
}
