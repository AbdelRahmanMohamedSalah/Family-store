package com.example.osman.grandresturant;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Edit_Ads extends AppCompatActivity {

    DatabaseReference databaseReference;
    DatabaseReference data_category;
    EditText name, desc, price;
    MaterialBetterSpinner Category;
    ImageView imageButton;
    Button save;
    ArrayAdapter<String> CategorySpinnerAdapter;
    ArrayList<String> arrayList_category;
    String current_position_category, current_position_Location;


    String s_name, s_desc, s_price, s_place, ItemType, CountryLocation;
    Uri imageUri;
    private static final int RUSLET_LOAD_IMAGE = 1;
    private StorageReference mStorageReference;
    boolean checkClick = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__ads);

        Toolbar ToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(ToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (EditText) findViewById(R.id.edit_ads_name);
        desc = (EditText) findViewById(R.id.edit_ads_desc);
        price = (EditText) findViewById(R.id.edit_ads_price);

        imageButton = (ImageView) findViewById(R.id.edit_ads_item_img);
        save = (Button) findViewById(R.id.edit_ads_update_btn);
        Category = (MaterialBetterSpinner) findViewById(R.id.edit_ads_spinner_category);


        mStorageReference = FirebaseStorage.getInstance().getReference();

        arrayList_category = new ArrayList<>();

        data_category = FirebaseDatabase.getInstance().getReference("Category");
        data_category.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    arrayList_category.add(data.child("name").getValue().toString());

                }

                CategorySpinnerAdapter = new ArrayAdapter<String>(Edit_Ads.this, android.R.layout.simple_dropdown_item_1line, arrayList_category);
                Category.setAdapter(CategorySpinnerAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        CategorySpinnerAdapter = new ArrayAdapter<String>(Edit_Ads.this, android.R.layout.simple_dropdown_item_1line, arrayList_category);
        Category.setAdapter(CategorySpinnerAdapter);
        Category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ItemType = adapterView.getItemAtPosition(i).toString();

            }
        });




        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent open = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(open, RUSLET_LOAD_IMAGE);


            }
        });


        Intent intent = getIntent();
        final String id_item = intent.getStringExtra("id");

        databaseReference = FirebaseDatabase.getInstance().getReference("Items").child(id_item);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name.setText(dataSnapshot.child("Name").getValue().toString());
                desc.setText(dataSnapshot.child("Description").getValue().toString());
                price.setText(dataSnapshot.child("Price").getValue().toString());
                Picasso.with(Edit_Ads.this).load(dataSnapshot.child("image").getValue().toString()).into(imageButton);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(price.getText()) || TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(desc.getText()) || TextUtils.isEmpty(CountryLocation) || TextUtils.isEmpty(ItemType)) {
                    Toast.makeText(Edit_Ads.this, "اكمل باقي البيانات", Toast.LENGTH_SHORT).show();

                } else {


                    AlertDialog.Builder builder = new AlertDialog.Builder(Edit_Ads.this);
                    builder.setMessage("هل انت متاكد من تعديل الاعلان ");
                    builder.setPositiveButton(" نعم ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            s_name = name.getText().toString();
                            s_desc = desc.getText().toString();
                            s_price = price.getText().toString();

                            //  System.out.println(s_name+"     "+s_price+"   "+s_desc+" "+s_place+"  "+" ");
                            databaseReference.child("Name").setValue(s_name);
                            databaseReference.child("Description").setValue(s_desc);
                            databaseReference.child("Price").setValue(s_price);
                            databaseReference.child("CountryLocation").setValue(CountryLocation);
                            databaseReference.child("UploadedTime").setValue(System.currentTimeMillis() / 1000);
                            databaseReference.child("ItemType").setValue(ItemType);

                            try {
                                if (checkClick == true) {

                                    mStorageReference.child(id_item).child("profile.jpg").putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                            if (task.isSuccessful()) {

                                                databaseReference.child("image").setValue(task.getResult().getDownloadUrl().toString());

//                                                 Toast.makeText(Edit_Ads.this, "تم التعديل", Toast.LENGTH_SHORT).show();

                                                finish();

                                            }
                                        }
                                    });
                                }


                            } catch (Exception e) {

                            }

                            finish();
                        }
                    });

                    builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });


                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                }
            }
        });


    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                imageUri = data.getData();
                checkClick = true;
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);

                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageButton.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(Edit_Ads.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(Edit_Ads.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
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
}



