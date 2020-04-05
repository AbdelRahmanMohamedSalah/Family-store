package com.example.osman.grandresturant.NavigationActivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.osman.grandresturant.Adapters.nav_saller_adapter;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.ItemsRecycler;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.SallersRecycler;
import com.example.osman.grandresturant.classes.SallersClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NavigationSallerRecycler extends AppCompatActivity {
    RecyclerView recyclerView, adminRecyclerView;
    DatabaseReference mDatabaseReference;
    ImageView secrchItem;
    LinearLayout barsearch;
    RelativeLayout null_layout;
    ImageButton goHome;
    private TextView sallerRecyclerItemName;
    private ImageView sallerRecyclerItemImage;
    private TextView sallerRecyclerItemLocation;
    private TextView sallerRecyclerItemMobile;
    private CardView adminAdminContainer;
    private String id;

    private SearchView searchView;
    private TextView toolbarTitle;
    ImageButton whats, call, mail;
    private ArrayList<SallersClass> sellers = new ArrayList<>();
    private nav_saller_adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_navigation_saller_recycler);

        initView();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        null_layout = (RelativeLayout) findViewById(R.id.saller_recycler_reltivelayout_null);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        //goHome = (ImageButton) findViewById(R.id.goHome);

        recyclerView = (RecyclerView) findViewById(R.id.saller_recycler_item_image_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        LinearLayoutManager layoutManagerAdmin = new LinearLayoutManager(this);
        layoutManagerAdmin.setReverseLayout(true);
        layoutManagerAdmin.setStackFromEnd(true);

        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setLayoutManager(layoutManager);


//        goHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SallersRecycler.this, HomeScreen.class));
//                finish();
//            }
//        });


        adapter = new nav_saller_adapter(sellers, sellers, this);


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                toolbarTitle.setVisibility(View.VISIBLE);
                adminAdminContainer.setVisibility(View.VISIBLE);
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarTitle.setVisibility(View.GONE);
                adminAdminContainer.setVisibility(View.GONE);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        initAdminView();

    }

    private void initAdminView() {

        mDatabaseReference.orderByChild("user_tpe").equalTo("admin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                final DataSnapshot child = dataSnapshot.getChildren().iterator().next();
                final SallersClass user = child.getValue(SallersClass.class);

                sallerRecyclerItemName.setText(user.getUsername());
                sallerRecyclerItemLocation.setText(user.getCountry());
//                sallerRecyclerItemMobile.setText(user.getMobile());
                Glide.with(getApplicationContext()).load(user.getProfile_image()).placeholder(sallerRecyclerItemImage.getDrawable()).fitCenter().into(sallerRecyclerItemImage);

                id = child.getKey();

                whats.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            String smsNumber = "201206007713"; // E164 format without '+' sign
                            Intent sendIntent = new Intent(Intent.ACTION_SEND);
                            sendIntent.setType("text/plain");
                            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                            sendIntent.putExtra("jid", user.getMobile() + "@s.whatsapp.net"); //phone number without "+" prefix
                            sendIntent.setPackage("com.whatsapp");
                            if (sendIntent.resolveActivity(NavigationSallerRecycler.this.getPackageManager()) == null) {
                                Toast.makeText(NavigationSallerRecycler.this, "لا يوجد برنامج الواتس أب ", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            startActivity(sendIntent);
                        } catch (Exception e) {
                            Toast.makeText(NavigationSallerRecycler.this, user.getMobile(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });


                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + user.getMobile()));
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(NavigationSallerRecycler.this, user.getMobile(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });


                mail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        try {
                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + user.getEmail()));
              /*  emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);*/
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

                            startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
                        } catch (Exception e) {
                            Toast.makeText(NavigationSallerRecycler.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adminAdminContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id != null) {
                    HelperMethods.Home_Filtter_sallerID = id;
                    startActivity(new Intent(NavigationSallerRecycler.this, ItemsRecycler.class));
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        sellers.clear();
        recyclerView.setAdapter(adapter);
        HelperMethods.showDialog(this, "Wait", "Loading...");
        mDatabaseReference.orderByChild("user_tpe").equalTo("company").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    SallersClass seller = child.getValue(SallersClass.class);
                    seller.setId(child.getKey());

                    sellers.add(seller);

                }
                adapter.notifyDataSetChanged();
                HelperMethods.hideDialog(NavigationSallerRecycler.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void initView() {
        sallerRecyclerItemName = (TextView) findViewById(R.id.saller_recycler_item_name);
        sallerRecyclerItemImage = (ImageView) findViewById(R.id.saller_recycler_item_image);
        sallerRecyclerItemLocation = (TextView) findViewById(R.id.saller_recycler_item_location);
        call = (ImageButton) findViewById(R.id.saller_recycler_call_icon_admin);
        mail = (ImageButton) findViewById(R.id.saller_recycler_email_icon_admin);
        whats = (ImageButton) findViewById(R.id.saller_recycler_whats_icon_admin);
        adminAdminContainer = (CardView) findViewById(R.id.admin_admin_container);
        searchView = (SearchView) findViewById(R.id.search_view);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
    }

    public static class holder extends RecyclerView.ViewHolder {


        public View view;
        de.hdodenhof.circleimageview.CircleImageView saller_img;
        TextView saller_name, saller_location, saller_number;


        public holder(View itemView) {
            super(itemView);
            view = itemView;

            saller_name = (TextView) view.findViewById(R.id.saller_recycler_name);
            saller_location = (TextView) view.findViewById(R.id.saller_recycler_country_icon);


            saller_img = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.saller_recycler_image);

        }

        public void setItemName(String name) {

            saller_name.setText(name);
        }

        public void setLocation(String location) {

            saller_location.setText(location);
        }


        public void setItemImage(String image) {
            Glide.with(view.getContext()).load(image).placeholder(saller_img.getDrawable()).fitCenter().into(saller_img);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (searchView.isIconified()) {
            searchView.setIconified(false);
        } else {
            super.onBackPressed();
        }

    }
}
