package com.example.osman.grandresturant.NavigationActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.osman.grandresturant.Adapters.NewRequests_Adapter;
import com.example.osman.grandresturant.Adapters.SallerAdapter;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.Helper.OrderStatus;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.SallersRecycler;
import com.example.osman.grandresturant.classes.Order;
import com.example.osman.grandresturant.classes.OrderList;
import com.example.osman.grandresturant.classes.RequestsClass;
import com.example.osman.grandresturant.classes.SallersClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RequstsRecycler extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference mDatabaseReference;
    FirebaseAuth firebaseAuth;
    RelativeLayout null_layout;
    private ArrayList<Order> request_list = new ArrayList<>();
    private NewRequests_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requsts_recycler);
        Toolbar ToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(ToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        null_layout = (RelativeLayout) findViewById(R.id.saller_recycler_reltivelayout_null);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Requests");
        recyclerView = (RecyclerView) findViewById(R.id.requests_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        firebaseAuth = FirebaseAuth.getInstance();
        adapter = new NewRequests_Adapter(request_list, request_list, this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        request_list.clear();
        recyclerView.setAdapter(adapter);
        HelperMethods.showDialog(this, "Wait", "Loading...");
        mDatabaseReference.child(firebaseAuth.getCurrentUser().getUid()).orderByChild("status").equalTo(String.valueOf(OrderStatus.SENT)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        OrderList order = child.getValue(OrderList.class);
                        request_list.addAll(order.getOrderList());
                    }
                    null_layout.setVisibility(View.GONE);

                    adapter.notifyDataSetChanged();
                }
                HelperMethods.hideDialog(RequstsRecycler.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

