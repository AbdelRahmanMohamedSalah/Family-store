package com.example.osman.grandresturant.Registration;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.classes.Model_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class UserEdit extends AppCompatActivity {
    EditText phoneEditProfile, emailEditProfile, password_old, password_new, password_new2, PlaceEditProfile;
    TextView tv_email;
    Button change_password;
    de.hdodenhof.circleimageview.CircleImageView imageButton;
    Button editBtnProfile;
    FirebaseAuth auth;
    DatabaseReference database;

    LinearLayout allLiniar;
    Uri imageUri;
    private static final int RUSLET_LOAD_IMAGE = 1;
    private StorageReference mStorageReference;
    boolean checkClick = false;
    boolean checkPass = false;
    ArrayAdapter<String> arrayAdaptercity, arrayAdapterCountry;
    String[] spinnerListEgy = {"القاهرة", "البحيرة", "الإسكندرية", "المنوفية", "الإسماعيلية", "أسوان", "أسيوط", "الأقصر", "البحر الأحمر", "البحيرة", "بني سويف", "بورسعيد", "جنوب سيناء", "الدقهلية", "دمياط", "سوهاج", "السويس", "الشرقية", "شمال سيناء", "الغربية", "الفيوم", "القليوبية", "قنا", "كفر الشيخ", "مطروح", "المنيا", "الوادي الجديد"};
    String[] spinnerListSudi = {"الرياض", "مكة", "المدينة المنورة", "بريدة", "بريدة", "تبوك", "الدمام", "الاحساء", "القطيف", "خميس مشيط", "الطائف", "نجران", "حفر الباطن", "الجبيل", "ضباء", "الخرج", "الثقبة", "ينبع البحر", "الخبر", "عرعر", "الحوية", "عنيزة", "سكاكا", "جيزان", "القريات", "الظهران", "الباحة", "الزلفي", "الرس", "وادي الدواسر", "بيشه", "سيهات", "شروره", "بحره", "تاروت", "الدوادمي", "صبياء", "بيش", "أحد رفيدة", "الفريش", "بارق", "الحوطة", "الأفلاج"};
    String[] spinnerListCountries = {"مصر", "السعودية"};
    MaterialBetterSpinner spinnerCountry, spinnerCity;
    String[] spinnerListDefualt = {};
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    double latti;
    double longi;
    Timer timer;
    String countryName1, user_country;
    Button auto;
    MaterialBetterSpinner spinner;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        phoneEditProfile = (EditText) findViewById(R.id.phoneEditProfile);
        emailEditProfile = (EditText) findViewById(R.id.emailEditProfile);
        editBtnProfile = (Button) findViewById(R.id.editBtnProfile);
        PlaceEditProfile = (EditText) findViewById(R.id.placeEditProfile);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference().child("Users");
        final String id_user = auth.getCurrentUser().getUid();
        tv_email = (TextView) findViewById(R.id.tv_email);
        password_old = (EditText) findViewById(R.id.password_old);
        password_new = (EditText) findViewById(R.id.password_new);
        password_new2 = (EditText) findViewById(R.id.password_new2);
        imageButton = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.imagebutton_user);
        auto = (Button) findViewById(R.id.location_dialog_btn_auto);
        change_password = (Button) findViewById(R.id.change_password);
        mStorageReference = FirebaseStorage.getInstance().getReference();
        allLiniar = (LinearLayout) findViewById(R.id.allLiniar);

        spinnerCountry = (MaterialBetterSpinner) findViewById(R.id.edit_profile_spinner_country);
        spinnerCity = (MaterialBetterSpinner) findViewById(R.id.edit_profile__spinner_city);

        arrayAdapterCountry = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerListCountries);
        arrayAdaptercity = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerListDefualt);
        spinnerCountry.setAdapter(arrayAdapterCountry);
        spinnerCity.setAdapter(arrayAdaptercity);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent open = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(open, RUSLET_LOAD_IMAGE);


            }
        });


        spinnerCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (Objects.equals(adapterView.getItemAtPosition(i).toString(), "مصر")) {
                    arrayAdaptercity = new ArrayAdapter<String>(UserEdit.this, android.R.layout.simple_dropdown_item_1line, spinnerListEgy);
                    spinnerCity.setAdapter(arrayAdaptercity);
                } else {
                    arrayAdaptercity = new ArrayAdapter<String>(UserEdit.this, android.R.layout.simple_dropdown_item_1line, spinnerListSudi);
                    spinnerCity.setAdapter(arrayAdaptercity);
                }


            }
        });


        spinnerCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                user_country = adapterView.getItemAtPosition(i).toString();
                timer.cancel();
                auto.setText("إختيار المكان الحالى");

            }
        });


        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auto.setText(countryName1);
                user_country = countryName1;
                timer.cancel();
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkPass) {
                    allLiniar.setVisibility(View.VISIBLE);
                    checkPass = true;
                } else {
                    allLiniar.setVisibility(View.GONE);
                    checkPass = false;
                }


            }
        });


        database.child(id_user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Model_user model_user = dataSnapshot.getValue(Model_user.class);
                tv_email.setText(model_user.getEmail());

                phoneEditProfile.setText(model_user.getMobile());
                emailEditProfile.setText(model_user.getUsername());

                Glide.with(UserEdit.this).load(model_user.getProfile_image()).placeholder(imageButton.getDrawable()).into(imageButton);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        editBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {


                    if (allLiniar.getVisibility() == View.VISIBLE) {
                        change();
                    } else if (!password_new2.getText().toString().isEmpty()) {

                    } else {


                        final AlertDialog.Builder builder = new AlertDialog.Builder(UserEdit.this);
                        builder.setMessage("هل انت متاكد من تعديل معلوماتك الشخصيه");

                        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                HelperMethods.showDialog(UserEdit.this, "Wait", "Editing your data...");
                                String phone = phoneEditProfile.getText().toString().trim();
                                String email = emailEditProfile.getText().toString().trim();
                                // database.child(id_user).child("profile_image").setValue(imageUri.toString());

                                database.child(id_user).child("country").setValue(countryName1);
                                database.child(id_user).child("username").setValue(email);
                                database.child(id_user).child("mobile").setValue(phone);
                                database.child(id_user).child("place").setValue(PlaceEditProfile.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        HelperMethods.hideDialog(UserEdit.this);
                                        Toast.makeText(UserEdit.this, "تم التعديل", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                try {
                                    if (checkClick == true) {


                                        mStorageReference.child(id_user).child("profile.jpg").putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                                if (task.isSuccessful()) {

                                                    database.child(id_user).child("profile_image").setValue(task.getResult().getDownloadUrl().toString());

                                                    Toast.makeText(UserEdit.this, "تم التعديل", Toast.LENGTH_SHORT).show();
                                                    HelperMethods.hideDialog(UserEdit.this);
                                                    finish();
                                                }

                                            }
                                        });
                                    } else {
                                        finish();
                                    }


                                } catch (Exception e) {

                                    HelperMethods.hideDialog(UserEdit.this);

                                }

                            }


                        });


                        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        //creating alert dialog
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }

                } catch (Exception e) {

                }

            }
        });


        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        getLocation();
                        Locale mLocale = new Locale("ar");

                        Geocoder geocoder = new Geocoder(UserEdit.this, mLocale);
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocation(latti, longi, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        try {
                            assert addresses != null;
                            countryName1 = addresses.get(0).getAdminArea();
                            String regex = "\\s*\\bمحافظة\\b\\s*";
                            countryName1 = countryName1.replaceAll(regex, "");
                            countryName1 = countryName1.replaceFirst("\u202C", "");

                        } catch (Exception e) {
                        }

                    }
                });


            }
        }, 0, 1 * (1000 * 1));


    }

    public void change() {
        if (password_old.getText().toString().isEmpty()) {
            Toast.makeText(this, "ادخل الرقم السري الحالي", Toast.LENGTH_SHORT).show();
        } else if (password_new.getText().toString().isEmpty()) {
            Toast.makeText(this, "ادخل الرقم السري الجديد", Toast.LENGTH_SHORT).show();
        } else if (password_new2.getText().toString().isEmpty()) {
            Toast.makeText(this, "اعد ادخال الرقم السري الجديد", Toast.LENGTH_SHORT).show();
        } else if (password_new.getText().toString().equals(password_new2.getText().toString())) {

            auth.signInWithEmailAndPassword(auth.getCurrentUser().getEmail(), password_old.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        auth.getCurrentUser().updatePassword(password_new.getText().toString());
                        Toast.makeText(UserEdit.this, "تم التعديل ", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {

                        Toast.makeText(UserEdit.this, "كلمة السر غير صحيحة", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "اعد كتابه كلمة السر صحيحة", Toast.LENGTH_SHORT).show();
        }
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
                Toast.makeText(UserEdit.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(UserEdit.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
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

    void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {


                latti = location.getLatitude();
                longi = location.getLongitude();


            } else {

            }
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}

