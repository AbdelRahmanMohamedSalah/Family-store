package com.example.osman.grandresturant.NavigationActivities;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
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

import com.bumptech.glide.Glide;
import com.example.osman.grandresturant.Adapters.MyAds_Adapter;
import com.example.osman.grandresturant.Adapters.NewRequests_Adapter;
import com.example.osman.grandresturant.Dialogs.MyAds_delete_dialog;
import com.example.osman.grandresturant.Edit_Ads;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.HomeScreen;
import com.example.osman.grandresturant.ItemScreen;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.classes.ItemClass;
import com.example.osman.grandresturant.classes.RequestsClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAds extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference mDatabaseReference, mDatabase;
    FirebaseAuth firebaseAuth;
    String profile_image_url, saller_name;
    ImageView SallerImageView;
    CollapsingToolbarLayout collapsingToolbar;
    RelativeLayout null_layout;
    private ArrayList<ItemClass> my_ads_list = new ArrayList<>();
    private MyAds_Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        null_layout = (RelativeLayout) findViewById(R.id.saller_recycler_reltivelayout_null);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Items");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        recyclerView = (RecyclerView) findViewById(R.id.my_ads_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        SallerImageView = (ImageView) findViewById(R.id.saller_app_bar_image);
        adapter = new MyAds_Adapter(my_ads_list, my_ads_list, MyAds.this);


        String id = firebaseAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(id);


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println(snapshot.getValue());
                try {
                    profile_image_url = snapshot.child("profile_image").getValue().toString();
                    collapsingToolbar.setTitle("  " +snapshot.child("username").getValue().toString()+"  ");


                } catch (Exception e) {

                }

                Glide.with(getApplicationContext()).load(profile_image_url).into(SallerImageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }

    @Override
    protected void onStart() {
        super.onStart();


        my_ads_list.clear();
        recyclerView.setAdapter(adapter);
        HelperMethods.showDialog(this, "Wait", "Loading...");
        mDatabaseReference.orderByChild("UserID").equalTo(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ItemClass request = child.getValue(ItemClass.class);

                    if (dataSnapshot.hasChildren())
                    {
                        my_ads_list.add(request);
                        null_layout.setVisibility(View.GONE);
                    }



                }
                adapter.notifyDataSetChanged();
                HelperMethods.hideDialog(MyAds.this);
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
              startActivity(new Intent(MyAds.this , HomeScreen.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MyAds.this , HomeScreen.class));
    }
}
