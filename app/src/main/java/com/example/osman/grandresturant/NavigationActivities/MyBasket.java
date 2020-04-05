package com.example.osman.grandresturant.NavigationActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.osman.grandresturant.Adapters.BasketAdapter;
import com.example.osman.grandresturant.Adapters.MyAds_Adapter;
import com.example.osman.grandresturant.Adapters.MyBasket_Adapter;
import com.example.osman.grandresturant.Dialogs.MyBasket_delete_dialog;
import com.example.osman.grandresturant.Favorite_item;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.Helper.OrderStatus;
import com.example.osman.grandresturant.HomeScreen;
import com.example.osman.grandresturant.ItemScreen;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.classes.ItemClass;
import com.example.osman.grandresturant.classes.Order;
import com.example.osman.grandresturant.classes.OrderList;
import com.example.osman.grandresturant.classes.RequestsClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.common.collect.ArrayListMultimap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBasket extends AppCompatActivity {
    RecyclerView recycleBasket;
    RecyclerView recyclerView;
    DatabaseReference mDatabaseReference;
    FirebaseAuth firebaseAuth;
    ImageButton goHome;
    RelativeLayout null_layout;
    private ArrayList<RequestsClass> my_ads_list = new ArrayList<>();
    private MyBasket_Adapter adapter;

    private Button sendBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_basket);

        Toolbar ToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(ToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sendBtn = (Button) findViewById(R.id.send_btn);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Requests");
        null_layout = (RelativeLayout) findViewById(R.id.saller_recycler_reltivelayout_null);
        recyclerView = (RecyclerView) findViewById(R.id.basket_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        firebaseAuth = FirebaseAuth.getInstance();
        goHome = (ImageButton) findViewById(R.id.goHome);
        adapter = new MyBasket_Adapter(new ArrayList<>(HelperMethods.orders.values()), new ArrayList<>(HelperMethods.orders.values()), MyBasket.this);

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyBasket.this, HomeScreen.class));
                finish();
            }
        });


        clearArray();



        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayListMultimap<String, Order> orders = ArrayListMultimap.create();

                for (int i = 0; i < adapter.sellers.size(); i++) {
                    orders.put(adapter.sellers.get(i).getItem().getRequestSallerID(), new Order(adapter.sellers.get(i).getItem(), adapter.quantities.get(i)));
                }

                for (String sellerID : orders.keySet()) {
                    ArrayList<Order> currentOrder = new ArrayList<>(orders.get(sellerID));
                    OrderList orderList = new OrderList(currentOrder, OrderStatus.SENT, System.currentTimeMillis());
                    mDatabaseReference.child(sellerID).push().setValue(orderList);
                }

                Toast.makeText(MyBasket.this, "تم ارسال طلباتك للتجار", Toast.LENGTH_SHORT).show();



                adapter.sellers.clear();
                adapter.notifyDataSetChanged();
                null_layout.setVisibility(View.VISIBLE);
                HelperMethods.orders.clear();



            }
        });
    }

    public void clearArray() {

        if (adapter.sellers.isEmpty()){

            null_layout.setVisibility(View.VISIBLE);


        }
    }

    @Override
    protected void onStart() {
        super.onStart();


        my_ads_list.clear();
        recyclerView.setAdapter(adapter);
//        HelperMethods.showDialog(this, "Wait", "Loading...");
//        mDatabaseReference.orderByChild("RequestUserID").equalTo(firebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot child : dataSnapshot.getChildren()) {
//                    RequestsClass request = child.getValue(RequestsClass.class);
//
//                    if (dataSnapshot.hasChildren()) {
//                        my_ads_list.add(request);
//                        null_layout.setVisibility(View.GONE);
//                    }
//
//
//                }
//                adapter.notifyDataSetChanged();
//                HelperMethods.hideDialog(MyBasket.this);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(MyBasket.this, HomeScreen.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MyBasket.this, HomeScreen.class));
    }
}

