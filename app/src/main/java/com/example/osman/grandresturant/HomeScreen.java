package com.example.osman.grandresturant;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.osman.grandresturant.Adapters.Adapter_category;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.NavigationActivities.FeedBack;
import com.example.osman.grandresturant.NavigationActivities.MyAds;
import com.example.osman.grandresturant.NavigationActivities.MyBasket;
import com.example.osman.grandresturant.NavigationActivities.NavItemRecycler;
import com.example.osman.grandresturant.NavigationActivities.NavigationCategoriesRecycler;
import com.example.osman.grandresturant.NavigationActivities.NavigationSallerRecycler;
import com.example.osman.grandresturant.NavigationActivities.RequstsRecycler;
import com.example.osman.grandresturant.Registration.Login;
import com.example.osman.grandresturant.Registration.LoginSallers;
import com.example.osman.grandresturant.Registration.Sign;
import com.example.osman.grandresturant.Registration.UserProfile;
import com.example.osman.grandresturant.classes.ItemClass;
import com.example.osman.grandresturant.classes.Item_recycle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;
import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class HomeScreen extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mDatabase;
    TextView privTv;
    NavigationView navigationView;
    TextView Country_choose;
    boolean doubleBackToExitPressedOnce = false;
    FloatingActionButton fab;
    public static List<ItemClass> item_list = new ArrayList<>();
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    TextView nav_Text_view;
    de.hdodenhof.circleimageview.CircleImageView nav_Image_view;

    DatabaseReference databaseReference, databaseReferenceAds;
    // MaterialBetterSpinner Country;
    // ArrayAdapter<String> CountrySpinnerAdapter;
    ArrayList<Item_recycle> arrayList;
    String Country_name;
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    double latti;
    double longi;
    Timer timer;
    SliderLayout sliderLayout;
    HashMap<String, String> Hash_file_maps;
    private GoogleApiClient mGoogleApiClient;
    CountryCodePicker ccp;


    private FusedLocationProviderClient mLocationManager;
    private LocationCallback locationCallback;
    private LocationRequest mLocationRequest;
    MaterialSearchView searchView;
    LocationCallback mLocationCallback;
    private FusedLocationProviderClient mFusedLocationClient;

    String fifthImage, fifthName, fourthName, fourthImage, thirdName, thirdImage, secondName, secondImage, firstName, firstImage;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = (RecyclerView) findViewById(R.id.home_screen_card_recycler_view);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        //  Country_choose = (TextView) findViewById(R.id.home_screen_place);
        arrayList = new ArrayList<>();

        nav_Image_view = (de.hdodenhof.circleimageview.CircleImageView) headerLayout.findViewById(R.id.nav_image_user);
        nav_Text_view = (TextView) headerLayout.findViewById(R.id.nav_text_view);
        recyclerView.setNestedScrollingEnabled(false);
        Hash_file_maps = new HashMap<String, String>();
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {

                //  mobile_code=selectedCountry.getPhoneCode();

            }
        });

        privTv = (TextView) findViewById(R.id.privTv);
        privTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this, Privacy.class));
            }

        });


        databaseReferenceAds = FirebaseDatabase.getInstance().getReference("Ads").child("0");
        databaseReferenceAds.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                fifthImage = dataSnapshot.child("fifthImage").getValue().toString();
                fifthName = dataSnapshot.child("fifthName").getValue().toString();
                fourthName = dataSnapshot.child("fourthName").getValue().toString();
                fourthImage = dataSnapshot.child("fourthImage").getValue().toString();
                thirdName = dataSnapshot.child("thirdName").getValue().toString();
                thirdImage = dataSnapshot.child("thirdImage").getValue().toString();
                secondName = dataSnapshot.child("secondName").getValue().toString();
                secondImage = dataSnapshot.child("secondImage").getValue().toString();
                firstName = dataSnapshot.child("firstName").getValue().toString();
                firstImage = dataSnapshot.child("firstImage").getValue().toString();

                Hash_file_maps.put(fifthName, fifthImage);
                Hash_file_maps.put(secondName, secondImage);
                Hash_file_maps.put(thirdName,thirdImage );
                Hash_file_maps.put(fourthName,fourthImage );
                Hash_file_maps.put(fifthName,fifthImage );


                for (String name : Hash_file_maps.keySet()) {

                    TextSliderView textSliderView = new TextSliderView(HomeScreen.this);
                    textSliderView
                            .description(name)
                            .image(Hash_file_maps.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(HomeScreen.this);
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", name);
                    sliderLayout.addSlider(textSliderView);
            }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(HomeScreen.this);


        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic


                Toast.makeText(HomeScreen.this, query, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(HomeScreen.this, NavItemRecycler.class);
                intent.putExtra("Item_type", "Search");
                intent.putExtra("Filter", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    Log.i("MainActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                    Locale mLocale = new Locale("ar");
                    Geocoder geocoder = new Geocoder(HomeScreen.this, mLocale);
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    try {
                        assert addresses != null;


                        String countryName1 = addresses.get(0).getAdminArea();
                        String regex = "\\s*\\bمحافظة\\b\\s*";
                        countryName1 = countryName1.replaceAll(regex, "");
                        countryName1 = countryName1.replaceFirst("\u202C", "");
                        HelperMethods.Home_Filtter_Country_name = countryName1;


                    } catch (Exception e) {
                    }
                }


            }
        };


        ;

        /*Country = (MaterialBetterSpinner) findViewById(R.id.home_screen_spinner);
        CountrySpinnerAdapter = new ArrayAdapter<String>(HomeScreen.this, android.R.layout.simple_dropdown_item_1line, spinnerListCountry);
        Country.setAdapter(CountrySpinnerAdapter);


        Country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //timer.cancel();
                Country_name = adapterView.getItemAtPosition(i).toString();
                Country_choose.setText(Country_name);
                HelperMethods.Home_Filtter_Country_name = Country_name;
            }
        });*/


        NavigationView navigation = (NavigationView) findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(this);
        final View header = navigation.getHeaderView(0);


        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {


                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(id);

                    mDatabase.child("user_tpe").addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            System.out.println(snapshot.getValue());
                            if (Objects.equals(snapshot.getValue(), "User")) {
                                navigationView.getMenu().clear();
                                navigationView.inflateMenu(R.menu.activity_home_screen_drawer_user);
                                fab.setVisibility(View.GONE);


                            } else {
                                navigationView.getMenu().clear();
                                navigationView.inflateMenu(R.menu.activity_home_screen_drawer_company);
                                fab.setVisibility(View.VISIBLE);

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });


                    mDatabase.child("profile_image").addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            Glide.with(HomeScreen.this).load(snapshot.getValue()).placeholder(nav_Image_view.getDrawable()).into(nav_Image_view);


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
//comment
                    });

//comment
                    mDatabase.child("username").addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            nav_Text_view.setText(snapshot.getValue().toString());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });


                } else {
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.activity_home_screen_drawer);
                    fab.setVisibility(View.GONE);


                }
            }
        };

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(HomeScreen.this, Activity_upload.class));

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        navigationView.setNavigationItemSelectedListener(this);

        loadData();


    }


    public void loadData() {
        HelperMethods.showDialog(HomeScreen.this, "Wait", "Loading data...");
        final Adapter_category adapter = new Adapter_category(this, arrayList);

        databaseReference = FirebaseDatabase.getInstance().getReference("Category");
        databaseReference.keepSynced(true);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {


                    String name = data.child("name").getValue().toString();
                    String image = data.child("image").getValue().toString();
                    String id = data.child("id").getValue().toString();

                    arrayList.add(new Item_recycle(name, image, id));
                    adapter.notifyDataSetChanged();


                }
                HelperMethods.hideDialog(HomeScreen.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(HomeScreen.this, 2));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            if (doubleBackToExitPressedOnce) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_Sallers) {
            startActivity(new Intent(HomeScreen.this, NavigationSallerRecycler.class));
        } else if (id == R.id.nav_Categories) {

            startActivity(new Intent(HomeScreen.this, NavigationCategoriesRecycler.class));

        } else if (id == R.id.nav_AboutUs) {

            startActivity(new Intent(HomeScreen.this, AboutAs.class));

        } else if (id == R.id.nav_company_favorite_Ads) {

            startActivity(new Intent(HomeScreen.this, Favorite_item.class));

        } else if (id == R.id.nav_Login) {
        }else if (id==R.id.nav_company_Add_new_item){

            startActivity(new Intent(HomeScreen.this, Activity_upload.class));

        }

        else if (id == R.id.nav_Login) {
            mAuth.signOut();
            startActivity(new Intent(HomeScreen.this, Login.class));

        } else if (id == R.id.nav_Login_sallers) {
            startActivity(new Intent(HomeScreen.this, LoginSallers.class));

        } else if (id == R.id.nav_company_sallers) {

            startActivity(new Intent(HomeScreen.this, NavigationSallerRecycler.class));

        } else if (id == R.id.nav_company_Add_new_item) {

            startActivity(new Intent(HomeScreen.this, Activity_upload.class));

        } else if (id == R.id.nav_company_Categories) {

            startActivity(new Intent(HomeScreen.this, NavigationCategoriesRecycler.class));

        } else if (id == R.id.nav_company_New_Requests) {
            FirebaseUser user = mAuth.getCurrentUser();

            if (user != null) {
                startActivity(new Intent(HomeScreen.this, RequstsRecycler.class));
            } else {
            }


        } else if (id == R.id.nav_company_My_Requests) {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                startActivity(new Intent(HomeScreen.this, MyBasket.class));
            } else {
            }

        } else if (id == R.id.nav_company_My_Ads) {
            startActivity(new Intent(HomeScreen.this, MyAds.class));

        } else if (id == R.id.nav_company_favorite_Ads) {


            Intent intent = new Intent(HomeScreen.this, Favorite_item.class);
            startActivity(intent);
        } else if (id == R.id.nav_company_FeedBack) {
            startActivity(new Intent(HomeScreen.this, FeedBack.class));

        } else if (id == R.id.nav_company_Abouts_Us) {

            startActivity(new Intent(HomeScreen.this, AboutAs.class));

        } else if (id == R.id.nav_company_Edite_Profile) {

            startActivity(new Intent(HomeScreen.this, UserProfile.class));


        } else if (id == R.id.nav_company_Log_Out) {
            mAuth.signOut();
            startActivity(new Intent(HomeScreen.this, HomeScreen.class));


        } else if (id == R.id.nav_User_Sallers) {

            startActivity(new Intent(HomeScreen.this, NavigationSallerRecycler.class));

        } else if (id == R.id.nav_Use_Categories) {

            startActivity(new Intent(HomeScreen.this, NavigationCategoriesRecycler.class));

        } else if (id == R.id.nav_User_My_Requests) {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                startActivity(new Intent(HomeScreen.this, MyBasket.class));
            } else {
            }

        } else if (id == R.id.nav_User_favorite_Ads) {

        } else if (id == R.id.nav_User_FeedBack) {
            startActivity(new Intent(HomeScreen.this, FeedBack.class));

        } else if (id == R.id.nav_User_Abouts_Us) {

            startActivity(new Intent(HomeScreen.this, AboutAs.class));

        } else if (id == R.id.nav_User_Edite_Profile) {
            startActivity(new Intent(HomeScreen.this, UserProfile.class));

        } else if (id == R.id.nav_User_Log_Out) {

            mAuth.signOut();
            startActivity(new Intent(HomeScreen.this, HomeScreen.class));


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }

        sliderLayout.stopAutoCycle();

        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_screen, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;

    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient != null && mFusedLocationClient != null) {
            requestLocationUpdates();
        } else {
            buildGoogleApiClient();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        requestLocationUpdates();
    }

    public void requestLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }


    }

}
