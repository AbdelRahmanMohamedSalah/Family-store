package com.example.osman.grandresturant.Registration;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Sign extends AppCompatActivity {
    Button signUpBtnSignUp, signUpBtnCancel;
    EditText emailEtSignUp, passwordEtSignUp, surepassSignUp, nameEt, user_mobile , user_place;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener listener;
    String User_Type, myEmail, mobile_code;
    private DatabaseReference mDatabaseUsers;
    private StorageReference mStorageRef;
    DatabaseReference currentuser_db;
    String user_country;

    Button auto, manual;
    TextView location;
    ArrayAdapter<String> arrayAdaptercity ,arrayAdapterCountry ;
    String[] spinnerListEgy = {"القاهرة", "البحيرة", "الإسكندرية", "المنوفية", "الإسماعيلية", "أسوان", "أسيوط", "الأقصر", "البحر الأحمر", "البحيرة", "بني سويف", "بورسعيد", "جنوب سيناء", "الدقهلية", "دمياط", "سوهاج", "السويس", "الشرقية", "شمال سيناء", "الغربية", "الفيوم", "القليوبية", "قنا", "كفر الشيخ", "مطروح", "المنيا", "الوادي الجديد"};
    String[] spinnerListSudi = {"الرياض", "مكة", "المدينة المنورة", "بريدة",  "بريدة", "تبوك", "الدمام", "الاحساء", "القطيف", "خميس مشيط", "الطائف", "نجران", "حفر الباطن", "الجبيل", "ضباء", "الخرج", "الثقبة", "ينبع البحر","الخبر","عرعر","الحوية","عنيزة","سكاكا","جيزان","القريات","الظهران","الباحة","الزلفي","الرس","وادي الدواسر","بيشه","سيهات","شروره","بحره","تاروت","الدوادمي","صبياء","بيش","أحد رفيدة","الفريش","بارق","الحوطة","الأفلاج"};
    String[] spinnerListCountries = {"مصر",  "السعودية"};
    MaterialBetterSpinner spinnerCountry , spinnerCity;
    String[] spinnerListDefualt = {};
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    double latti;
    double longi;
    Timer timer;
    CountryCodePicker ccp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign);

        signUpBtnCancel = (Button) findViewById(R.id.signUpBtnCancel);


        mStorageRef = mStorageRef = FirebaseStorage.getInstance().getReference();
        ccp = (CountryCodePicker) findViewById(R.id.ccp);

        spinnerCountry = (MaterialBetterSpinner) findViewById(R.id.location_dialog_spinner_country);
        spinnerCity = (MaterialBetterSpinner) findViewById(R.id.location_dialog_spinner);
        auto = (Button) findViewById(R.id.location_dialog_btn_auto);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        arrayAdapterCountry = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerListCountries);
        arrayAdaptercity = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerListDefualt);
        spinnerCountry.setAdapter(arrayAdapterCountry);
        spinnerCity.setAdapter(arrayAdaptercity);
        signUpBtnSignUp = (Button) findViewById(R.id.signUpBtnSignUp);
        signUpBtnCancel = (Button) findViewById(R.id.signUpBtnCancel);

        emailEtSignUp = (EditText) findViewById(R.id.emailEtSignUp);
        passwordEtSignUp = (EditText) findViewById(R.id.passwordEtSignUp);
        surepassSignUp = (EditText) findViewById(R.id.surepassSignUp);
        nameEt = (EditText) findViewById(R.id.nameEt);
        user_mobile = (EditText) findViewById(R.id.phone_Et);
        user_place = (EditText) findViewById(R.id.placeEt);
        auth = FirebaseAuth.getInstance();

        mobile_code = ccp.getSelectedCountryCode();

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {

                mobile_code=selectedCountry.getPhoneCode();

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

                        Geocoder geocoder = new Geocoder(Sign.this, mLocale);
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocation(latti, longi, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        try {
                            assert addresses != null;
                            String countryName1 = addresses.get(0).getAdminArea();

                            String regex = "\\s*\\bمحافظة\\b\\s*";
                            countryName1 = countryName1.replaceAll(regex, "");
                            user_country  = countryName1.replaceFirst("\u202C", "");
                            auto.setText(user_country);


                        } catch (Exception e) {
                        }

                    }
                });


            }
        }, 0, 1 * (1000 * 1));


        signUpBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(auto.getText() != ("إختيار المكان الحالى"))
                {
                    Toast.makeText(Sign.this, "يجب التأكد من تفعيل GPS والإتصال بالإنترنت", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    getLocation();
                    Locale mLocale = new Locale("ar");

                    Geocoder geocoder = new Geocoder(Sign.this, mLocale);
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(latti, longi, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    try {
                        assert addresses != null;


                        String countryName1 = addresses.get(0).getAdminArea();
                        String regex = "\\s*\\bمحافظة\\b\\s*";
                        countryName1 = countryName1.replaceAll(regex, "");
                        user_country  = countryName1.replaceFirst("\u202C", "");
                        auto.setText(user_country);

                        auto.setText(countryName1);
                        Toast.makeText(Sign.this, countryName1, Toast.LENGTH_SHORT).show();


                    } catch (Exception e) {
                    }
                }





            }
        });

        spinnerCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               if(Objects.equals(adapterView.getItemAtPosition(i).toString(), "مصر"))
               {
                   arrayAdaptercity = new ArrayAdapter<String>(Sign.this, android.R.layout.simple_dropdown_item_1line, spinnerListEgy);
                   spinnerCity.setAdapter(arrayAdaptercity);
               }
               else
               {
                   arrayAdaptercity = new ArrayAdapter<String>(Sign.this, android.R.layout.simple_dropdown_item_1line, spinnerListSudi);
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


        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        signUpBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUp();


            }
        });


    }


    private void signUp() {
        myEmail = emailEtSignUp.getText().toString().trim();
        String myPass = passwordEtSignUp.getText().toString().trim();
        String passSure = surepassSignUp.getText().toString().trim();
        String mobileNum = user_mobile.getText().toString().trim();

        if (TextUtils.isEmpty(myEmail)) {

            emailEtSignUp.setError("أدخل البريد الإليكترونى!");


        }
        else if (mobileNum.length() != 10 || mobileNum.contains(" ")) {
            user_mobile.setError("تأكد من إدخال رقم الهاتف صحيح بدون 0");
        }
        else if (!myPass.equals(passSure)) {
            passwordEtSignUp.setError("كلمة السر غير متطابفة!");
        }
        else if (TextUtils.isEmpty(nameEt.getText().toString())) {

            nameEt.setError("أدخل المستخدم!");

        }
        else if (TextUtils.isEmpty(passwordEtSignUp.getText().toString())) {

            passwordEtSignUp.setError("أدخل كلمة السر!");

        } else if (!myPass.equals(passSure)) {
            Toast.makeText(this, "كلمة السر غير متطابقة", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(user_country)) {
            Toast.makeText(this, "أختار مدينة المستخدم  ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(user_place.getText().toString())) {
            Toast.makeText(this, "أختار عنوان المستخدم  ", Toast.LENGTH_SHORT).show();
        }
        else {

            HelperMethods.showDialog(Sign.this, "Wait...", "Create new user");

            auth.createUserWithEmailAndPassword(myEmail, myPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {

                        Toast.makeText(Sign.this, "password less than 6 character !", Toast.LENGTH_SHORT).show();
                    } else if (task.isSuccessful()) {
                        String token = auth.getCurrentUser().getUid();
                        String myName = nameEt.getText().toString();

                        try {
                            String user_id = auth.getCurrentUser().getUid();
                            currentuser_db = mDatabaseUsers.child(user_id);
                            currentuser_db.child("username").setValue(nameEt.getText().toString());
                            currentuser_db.child("id").setValue(auth.getUid());
                            currentuser_db.child("user_tpe").setValue("user");
                            currentuser_db.child("email").setValue(myEmail);
                            currentuser_db.child("mobile").setValue(mobile_code + user_mobile.getText().toString());
                            currentuser_db.child("country").setValue(user_country);
                            currentuser_db.child("place").setValue(user_place.getText().toString());
                            currentuser_db.child("profile_image").setValue("https://firebasestorage.googleapis.com/v0/b/clashbook-3a339.appspot.com/o/default-user-icon-profile.png?alt=media&token=27cc7679-276a-497e-90a5-b558c26275ab");


                            HelperMethods.hideDialog(Sign.this);
                            startActivity(new Intent(Sign.this, User_Profile_image.class));


                        } catch (Exception e) {


                        }


                    }

                }
            });

        }

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

    /*public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {

            case R.id.radio_user:
                if (checked)
                    User_Type = "user";
                break;
            case R.id.radio_company:
                if (checked)
                    User_Type = "company";
                break;
        }


    }*/
}
