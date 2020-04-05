package com.example.osman.grandresturant;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.osman.grandresturant.Helper.HelperMethods;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

public class Activity_upload extends AppCompatActivity {

    EditText  title, price, description;

    Button upload;

    ArrayList<String> arrayList;
    DatabaseReference databaseReference, mDatabaseCurrentUser;
    MaterialBetterSpinner Category;
    ArrayAdapter<String> CategorySpinnerAdapter, CountrySpinnerAdapter;
    String[] spinnerListDefualt = {};
    FirebaseAuth mAuth;
    String CountryLocation, ItemType, UserName, UserEmail, UserNumber, UserImage, UserCountry , UserPlace;
    ImageView imgebtn;
    private static final int RUSLET_LOAD_IMAGE = 1;
    static Uri image_item;
    private StorageReference mStorageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        android.support.v7.widget.Toolbar ToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(ToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();

        mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        mStorageReference = FirebaseStorage.getInstance().getReference();
        HelperMethods.showDialog(Activity_upload.this, "wait", "Loading...");
        mDatabaseCurrentUser.child("profile_image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println(snapshot.getValue());
                try {
                    UserImage = snapshot.getValue().toString();
                } catch (Exception e) {

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        mDatabaseCurrentUser.child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println(snapshot.getValue());
                try {
                    UserName = snapshot.getValue().toString();
                } catch (Exception e) {

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        mDatabaseCurrentUser.child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println(snapshot.getValue());
                try {
                    UserEmail = snapshot.getValue().toString();
                } catch (Exception e) {

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        mDatabaseCurrentUser.child("mobile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println(snapshot.getValue());
                try {
                    UserNumber = snapshot.getValue().toString();
                } catch (Exception e) {

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        mDatabaseCurrentUser.child("place").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println(snapshot.getValue());
                try {
                    UserPlace = snapshot.getValue().toString();
                } catch (Exception e) {

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        mDatabaseCurrentUser.child("country").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println(snapshot.getValue());
                try {
                    UserCountry = snapshot.getValue().toString();
                    HelperMethods.hideDialog(Activity_upload.this);

                } catch (Exception e) {

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        Category = (MaterialBetterSpinner) findViewById(R.id.type_design_spinner);



        CategorySpinnerAdapter = new ArrayAdapter<String>(Activity_upload.this, android.R.layout.simple_dropdown_item_1line, spinnerListDefualt);
        Category.setAdapter(CategorySpinnerAdapter);

        Category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ItemType = adapterView.getItemAtPosition(i).toString();

            }
        });




        arrayList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Category");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String name = data.child("name").getValue().toString();
                    arrayList.add(name);

                }
                CategorySpinnerAdapter = new ArrayAdapter<String>(Activity_upload.this, android.R.layout.simple_dropdown_item_1line, arrayList);

                Category.setAdapter(CategorySpinnerAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        title = (EditText) findViewById(R.id.et_upload_title);
        price = (EditText) findViewById(R.id.et_upload_price);
        description = (EditText) findViewById(R.id.et_upload_description);
        imgebtn = (ImageView) findViewById(R.id.image_new_item);

        upload = (Button) findViewById(R.id.upload_button);

        imgebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        try {


                Intent open = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(open, RUSLET_LOAD_IMAGE);
        }catch (Exception e){}


            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (image_item == null) {


                    Toast.makeText(Activity_upload.this, "إختار صورة المنتج ", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(price.getText())) {


                } else if (TextUtils.isEmpty(title.getText())) {
                    title.setError("أدخل أسم المنتج");


                } else if (TextUtils.isEmpty(description.getText())) {

                    description.setError("أدخل الوصف ");

//                } else if (TextUtils.isEmpty(CountryLocation)) {
//
//
//                    Toast.makeText(Activity_upload.this, "أدخل المدينة ", Toast.LENGTH_SHORT).show();
//                } else if (TextUtils.isEmpty(ItemType)) {


                    Toast.makeText(Activity_upload.this, "أختار القسم ", Toast.LENGTH_SHORT).show();
                } else {


                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    HelperMethods.showDialog(Activity_upload.this, "Please Wait", "Uploading...");

                    StorageReference filepath = mStorageReference.child("Photos").child(image_item.getLastPathSegment());
                    filepath.putFile(image_item).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri uri_load = taskSnapshot.getDownloadUrl();

                            databaseReference = FirebaseDatabase.getInstance().getReference("Items").push();

                            databaseReference.child("Name").setValue(title.getText().toString());
                            databaseReference.child("Price").setValue(price.getText().toString());
                            databaseReference.child("Description").setValue(description.getText().toString());
                            databaseReference.child("image").setValue(uri_load.toString());
                            databaseReference.child("UploadedTime").setValue(System.currentTimeMillis() / 1000);
                            databaseReference.child("UserImage").setValue(UserImage);
                            databaseReference.child("UserName").setValue(UserName);
                            databaseReference.child("UserNumber").setValue(UserNumber);
                            databaseReference.child("UserEmail").setValue(UserEmail);
                            databaseReference.child("UserID").setValue(mAuth.getCurrentUser().getUid());
                            databaseReference.child("idItem").setValue(databaseReference.getKey());
                            databaseReference.child("PlaceLocation").setValue(UserPlace);


                            databaseReference.child("CountryLocation").setValue(UserCountry);
                            databaseReference.child("ItemType").setValue(ItemType);
                            HelperMethods.hideDialog(Activity_upload.this);
                            Toast.makeText(Activity_upload.this, "Added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Activity_upload.this, HomeScreen.class));


                        }


                    });

                }


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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            Uri selectedimage = data.getData();
            imgebtn.setImageURI(selectedimage);
            image_item = selectedimage;
        } catch (Exception e) {
        }
    }


}
