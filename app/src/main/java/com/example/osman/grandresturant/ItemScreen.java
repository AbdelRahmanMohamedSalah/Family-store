package com.example.osman.grandresturant;

import android.content.Intent;
import android.os.Build;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.osman.grandresturant.Dialogs.MyAds_delete_dialog;
import com.example.osman.grandresturant.Dialogs.Registration_Dialog;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.NavigationActivities.MyAds;
import com.example.osman.grandresturant.NavigationActivities.MyBasket;
import com.example.osman.grandresturant.classes.Order;
import com.example.osman.grandresturant.classes.RequestsClass;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ItemScreen extends AppCompatActivity {

    TextView Item_name, Item_country, Item_place, Item_time, Item_price, Item_desc, user_name, user_number, user_mail;
    ImageView user_image, Item_image;
    Button item_screen_add_btn;
    android.support.v7.widget.Toolbar itemToolbar;

    DatabaseReference databaseReference, databaseReferenceUser, databaseReferenceRequests, databaseReferenceAddRequests, databaseReferenceCheckRequests;
    String ItemID, ItemName, ItemPrice, ItemImage, UserID, UserImage, Username, UserEmail, UserMobile, SallerID;
    FirebaseAuth auth;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        item_screen_add_btn = (Button) findViewById(R.id.item_screen_add_btn);

        Item_name = (TextView) findViewById(R.id.item_screen_name);
        Item_country = (TextView) findViewById(R.id.item_screen_country);
        Item_place = (TextView) findViewById(R.id.item_screen_place);
        Item_time = (TextView) findViewById(R.id.item_screen_time);
        Item_price = (TextView) findViewById(R.id.item_screen_price);
        Item_desc = (TextView) findViewById(R.id.item_screen_desc);
        user_name = (TextView) findViewById(R.id.item_screen_company_name);
        user_number = (TextView) findViewById(R.id.item_screen_company_number);
        user_mail = (TextView) findViewById(R.id.item_screen_company_mail);
        Item_image = (ImageView) findViewById(R.id.item_screen_item_img);
        user_image = (ImageView) findViewById(R.id.item_screen_company_image);
        auth = FirebaseAuth.getInstance();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                ItemID = null;
            } else {
                ItemID = extras.getString("Item_ID");
            }
        } else {
            ItemID = (String) savedInstanceState.getSerializable("Item_ID");
        }


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Items").child(ItemID);

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {


                Item_name.setText(snapshot.child("Name").getValue().toString());
                Item_country.setText(snapshot.child("CountryLocation").getValue().toString());
                Item_place.setText(snapshot.child("PlaceLocation").getValue().toString());
                Item_time.setText(snapshot.child("CountryLocation").getValue().toString());
                Item_price.setText(snapshot.child("Price").getValue().toString());
                Item_desc.setText(snapshot.child("Description").getValue().toString());
                user_name.setText(snapshot.child("UserName").getValue().toString());
                user_number.setText(snapshot.child("UserNumber").getValue().toString());
                user_mail.setText(snapshot.child("UserEmail").getValue().toString());

                //       Glide.with(ItemScreen.this).load(snapshot.child("UserImage").getValue().toString()).placeholder(user_image.getDrawable()).fitCenter().into(user_image);
                Glide.with(ItemScreen.this).load(snapshot.child("image").getValue().toString()).placeholder(Item_image.getDrawable()).fitCenter().into(Item_image);



                long timestamp = Long.parseLong(String.valueOf(snapshot.child("UploadedTime").getValue().toString())) * 1000L;
                Item_time.setText(getDate(timestamp));


                ItemName = snapshot.child("Name").getValue().toString();
                ItemPrice = snapshot.child("Price").getValue().toString();
                ItemImage = snapshot.child("image").getValue().toString();
                SallerID = snapshot.child("UserID").getValue().toString();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        final FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            UserID = auth.getCurrentUser().getUid();

            databaseReferenceUser = FirebaseDatabase.getInstance().getReference().child("Users").child(UserID);
            databaseReferenceUser.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot snapshot) {


                    Username = snapshot.child("username").getValue().toString();
//                    UserImage = snapshot.child("profile_image").getValue().toString();
                    UserEmail = snapshot.child("email").getValue().toString();
                    UserMobile = snapshot.child("mobile").getValue().toString();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            databaseReferenceRequests = FirebaseDatabase.getInstance().getReference().child("Requests");


        } else {

        }


        item_screen_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user != null) {
//                    databaseReferenceRequests.child(ItemID + UserID).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//                        @Override
//                        public void onDataChange(DataSnapshot snapshot) {
//
//                            if (snapshot.hasChildren()) {
//
//                                Toast.makeText(ItemScreen.this, "لقد قمت بطلب هذا المنتج مسبقاً", Toast.LENGTH_SHORT).show();
//
//                            } else {
//                                sendRequest();
//
//                            }
//
//
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//
//                    });
                    RequestsClass order = new RequestsClass(Username, UserID, UserEmail, UserMobile, UserImage, ItemID, ItemName, ItemPrice, ItemImage, SallerID, System.currentTimeMillis() / 1000);

                    HelperMethods.orders.put(SallerID, new Order(order, 1));
                    Toast.makeText(ItemScreen.this, "تم اضافه الاعلان الي سله الطلبات", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Registration_Dialog cdd = new Registration_Dialog(ItemScreen.this);
                    cdd.show();
                }


            }

        });


    }


    private String getDate(long timeStamp) {

        try {
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "xx";
        }
    }


    public void sendRequest() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {


            HelperMethods.showDialog(ItemScreen.this, "Wait", "sending Request...");

            databaseReferenceAddRequests = databaseReferenceRequests.child(ItemID + UserID);
            databaseReferenceAddRequests.child("RequestUserName").setValue(Username);
            databaseReferenceAddRequests.child("RequestUserID").setValue(UserID);
            databaseReferenceAddRequests.child("RequestUserEmail").setValue(UserEmail);
            databaseReferenceAddRequests.child("RequestUserNumber").setValue(UserMobile);
            databaseReferenceAddRequests.child("RequestUserImage").setValue(UserImage);
            databaseReferenceAddRequests.child("RequestItemID").setValue(ItemID);
            databaseReferenceAddRequests.child("RequestItemName").setValue(ItemName);
            databaseReferenceAddRequests.child("RequestItemPrice").setValue(ItemPrice);
            databaseReferenceAddRequests.child("RequestItemImage").setValue(ItemImage);
            databaseReferenceAddRequests.child("RequestSallerID").setValue(SallerID);
            databaseReferenceAddRequests.child("RequestID").setValue(databaseReferenceAddRequests.getKey());
            databaseReferenceAddRequests.child("RequestTime").setValue(System.currentTimeMillis() / 1000).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    HelperMethods.hideDialog(ItemScreen.this);
                }
            });



            Toast.makeText(ItemScreen.this, "تم إرسال الطلب والإضافة الى السلة", Toast.LENGTH_SHORT).show();
        } else {


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
