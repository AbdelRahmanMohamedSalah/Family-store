package com.example.osman.grandresturant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.osman.grandresturant.Adapters.Favorite_Adapter;
import com.example.osman.grandresturant.classes.ItemClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Favorite_item extends AppCompatActivity {
    ArrayList<ItemClass> list = new ArrayList<>();
    RecyclerView recyclerView;
    DatabaseReference database, itemfav;
    String auth;
    ImageButton goHome;
    RelativeLayout null_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_item);

        Toolbar ToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(ToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        null_layout = (RelativeLayout) findViewById(R.id.saller_recycler_reltivelayout_null);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        auth = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance().getReference().child("Favorite").child(auth);
        itemfav = FirebaseDatabase.getInstance().getReference().child("Items");
        final com.example.osman.grandresturant.Adapters.Favorite_Adapter favorite_adapter = new Favorite_Adapter(this, list);
        goHome = (ImageButton) findViewById(R.id.goHome);
        recyclerView.setAdapter(favorite_adapter);
        favorite_adapter.notifyDataSetChanged();


//        database.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot allData : dataSnapshot.getChildren()) {
//
//                    itemfav.child(allData.getKey()).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                            if (dataSnapshot.hasChildren()) {
//                             null_layout.setVisibility(View.GONE);
//                            } else {
//                                ItemClass itemClass = dataSnapshot.getValue(ItemClass.class);
//                                list.add(itemClass);
//                                favorite_adapter.notifyDataSetChanged();
//
//                            }
//
//
//                        }
//
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        try {


        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataFavorit: dataSnapshot.getChildren()) {
                    if (dataFavorit.getKey() == null) {



                    } else {


                        null_layout.setVisibility(View.GONE);
                        itemfav.child(dataFavorit.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                ItemClass itemClass = dataSnapshot.getValue(ItemClass.class);
                                list.add(itemClass);
                                favorite_adapter.notifyDataSetChanged();



                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        }catch (Exception e){

        }
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Favorite_item.this, HomeScreen.class));
                finish();
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
