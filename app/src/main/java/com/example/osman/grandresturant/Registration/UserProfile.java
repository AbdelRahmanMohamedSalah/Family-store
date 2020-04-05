package com.example.osman.grandresturant.Registration;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.NavigationActivities.MyAds;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.classes.Model_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserProfile extends AppCompatActivity {
    EditText phoneEditProfile, emailEditProfile, password_old, password_new, password_new2;
    TextView tv_email;
    de.hdodenhof.circleimageview.CircleImageView imageButton;
    ImageView edit_profile ;
    FirebaseAuth auth;
    DatabaseReference database , mDatabase;
    TextView card_mail , card_nuber , card_place , card_country ;



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        phoneEditProfile = (EditText) findViewById(R.id.phoneEditProfile);
        emailEditProfile = (EditText) findViewById(R.id.emailEditProfile);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference().child("Users");
        final String id_user = auth.getCurrentUser().getUid();
        tv_email = (TextView) findViewById(R.id.tv_email);
        password_old = (EditText) findViewById(R.id.password_old);
        password_new = (EditText) findViewById(R.id.password_new);
        password_new2 = (EditText) findViewById(R.id.password_new2);
        imageButton = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.imagebutton_user);
        edit_profile = (ImageView) findViewById(R.id.editBtnProfile);

        card_mail = (TextView) findViewById(R.id.my_ads_card_email);
        card_nuber = (TextView) findViewById(R.id.my_ads_card_number);
        card_place = (TextView) findViewById(R.id.my_ads_card_place);
        card_country = (TextView) findViewById(R.id.my_ads_card_country);


        String id = auth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(id);


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println(snapshot.getValue());
                try
                {

                    card_mail.setText(snapshot.child("email").getValue().toString());
                    card_nuber.setText(snapshot.child("mobile").getValue().toString());
                    card_country.setText(snapshot.child("country").getValue().toString());
                    card_place.setText(snapshot.child("place").getValue().toString());
                    tv_email.setText(snapshot.child("username").getValue().toString());


                }
                catch (Exception e)
                {

                }

                Glide.with(UserProfile.this).load(snapshot.child("profile_image").getValue().toString()).into(imageButton);
                mDatabase.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });




        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(UserProfile.this , UserEdit.class));

            }
        });





    }


    @Override
    protected void onStop() {
        super.onStop();
    }
}

