package com.example.osman.grandresturant.NavigationActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.osman.grandresturant.Adapters.nav_item_adapter;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.classes.ItemClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class NavItemRecycler extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference mDatabaseReference;
    ImageView secrchItem;
    ArrayList<ItemClass> arrayList;
    LinearLayout barsearch;
    DatabaseReference databaseReference;
    String Item_type, Filter;
    RelativeLayout null_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_item_recycler_saller);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        null_layout = (RelativeLayout) findViewById(R.id.saller_recycler_reltivelayout_null);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                Item_type = null;
                Filter = null;
            } else {
                Item_type = extras.getString("Item_type");
                Filter = extras.getString("Filter");
            }
        } else {
            Item_type = (String) savedInstanceState.getSerializable("Item_type");
            Filter = (String) savedInstanceState.getSerializable("Filter");
        }


        arrayList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Items");

        recyclerView = (RecyclerView) findViewById(R.id.nav_item_saller_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);


        if (Objects.equals(Item_type, "Saller")) {

            loadDataSaller();
        } else if (Objects.equals(Item_type, "Search")) {

            loadDataSearch();

        } else {
            loadDataCategory();
        }


    }


    public void loadDataSaller() {
        HelperMethods.showDialog(NavItemRecycler.this, "Wait", "Loading data...");
        final nav_item_adapter adapter = new nav_item_adapter(this, arrayList);


        databaseReference.orderByChild("UserID").equalTo(Filter).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (dataSnapshot.hasChildren()) {
                    null_layout.setVisibility(View.GONE);
                    for (DataSnapshot data : dataSnapshot.getChildren()) {


                        String id = data.getRef().getKey();
                        String Itemid = data.child("idItem").getValue().toString();
                        String Name = data.child("Name").getValue().toString();
                        String image = data.child("image").getValue().toString();
                        String CountryLocation = data.child("CountryLocation").getValue().toString();
                        String Description = data.child("Description").getValue().toString();
                        String ItemType = data.child("ItemType").getValue().toString();
                        String PlaceLocation = data.child("PlaceLocation").getValue().toString();
                        String Price = data.child("Price").getValue().toString();
                        String UserID = data.child("UserID").getValue().toString();
                        String UserName = data.child("UserName").getValue().toString();
                        String UserEmail = data.child("UserEmail").getValue().toString();
                        String UserNumber = data.child("UserNumber").getValue().toString();
                        long UploadedTime = (long) data.child("UploadedTime").getValue();

                        arrayList.add(new ItemClass(id, Itemid, Name, image, CountryLocation, Description, ItemType, PlaceLocation, Price, UserID, UserName, UserEmail, UserNumber, UploadedTime));
                        adapter.notifyDataSetChanged();


                    }
                    HelperMethods.hideDialog(NavItemRecycler.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    HelperMethods.hideDialog(NavItemRecycler.this);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void loadDataCategory() {
        HelperMethods.showDialog(NavItemRecycler.this, "Wait", "Loading data...");
        final nav_item_adapter adapter = new nav_item_adapter(this, arrayList);


        databaseReference.orderByChild("ItemType").equalTo(Filter).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()) {

                    null_layout.setVisibility(View.GONE);

                    for (DataSnapshot data : dataSnapshot.getChildren()) {


                        String id = data.getRef().getKey();
                        String Itemid = data.child("idItem").getValue().toString();
                        String Name = data.child("Name").getValue().toString();
                        String image = data.child("image").getValue().toString();
                        String CountryLocation = data.child("CountryLocation").getValue().toString();
                        String Description = data.child("Description").getValue().toString();
                        String ItemType = data.child("ItemType").getValue().toString();
                        String PlaceLocation = data.child("PlaceLocation").getValue().toString();
                        String Price = data.child("Price").getValue().toString();
                        String UserID = data.child("UserID").getValue().toString();
                        String UserName = data.child("UserName").getValue().toString();
                        String UserEmail = data.child("UserEmail").getValue().toString();
                        String UserNumber = data.child("UserNumber").getValue().toString();
                        long UploadedTime = (long) data.child("UploadedTime").getValue();

                        arrayList.add(new ItemClass(id, Itemid, Name, image, CountryLocation, Description, ItemType, PlaceLocation, Price, UserID, UserName, UserEmail, UserNumber, UploadedTime));
                        adapter.notifyDataSetChanged();


                    }
                    HelperMethods.hideDialog(NavItemRecycler.this);
                    recyclerView.setAdapter(adapter);
                } else {

                    HelperMethods.hideDialog(NavItemRecycler.this);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void loadDataSearch() {
        HelperMethods.showDialog(NavItemRecycler.this, "Wait", "Loading data...");
        final nav_item_adapter adapter = new nav_item_adapter(this, arrayList);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {


                        if (data.child("Name").getValue().toString().toLowerCase().contains(Filter.toLowerCase())) {

                            String id = data.getRef().getKey();
                            String Itemid = data.child("idItem").getValue().toString();
                            String Name = data.child("Name").getValue().toString();
                            String image = data.child("image").getValue().toString();
                            String CountryLocation = data.child("CountryLocation").getValue().toString();
                            String Description = data.child("Description").getValue().toString();
                            String ItemType = data.child("ItemType").getValue().toString();
                            String PlaceLocation = data.child("PlaceLocation").getValue().toString();
                            String Price = data.child("Price").getValue().toString();
                            String UserID = data.child("UserID").getValue().toString();
                            String UserName = data.child("UserName").getValue().toString();
                            String UserEmail = data.child("UserEmail").getValue().toString();
                            String UserNumber = data.child("UserNumber").getValue().toString();
                            long UploadedTime = (long) data.child("UploadedTime").getValue();

                            arrayList.add(new ItemClass(id, Itemid, Name, image, CountryLocation, Description, ItemType, PlaceLocation, Price, UserID, UserName, UserEmail, UserNumber, UploadedTime));
                            adapter.notifyDataSetChanged();
                            if (arrayList.isEmpty()) {




                            }
                            else
                            {    null_layout.setVisibility(View.GONE);

                            }


                        } else {


                        }


                    }
                    HelperMethods.hideDialog(NavItemRecycler.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    null_layout.setVisibility(View.VISIBLE);
                    HelperMethods.hideDialog(NavItemRecycler.this);
                }


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
