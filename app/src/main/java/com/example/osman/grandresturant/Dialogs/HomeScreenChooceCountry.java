package com.example.osman.grandresturant.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.SallersRecycler;
import com.google.firebase.database.DatabaseReference;

public class HomeScreenChooceCountry extends Dialog {

    public Activity c;
    public Dialog d;
    ArrayAdapter<String> CountryListViewAdapter, CityListViewAdapter;
    ListView countryList, cityList;
    String[] spinnerListEgy = {"القاهرة", "البحيرة", "الإسكندرية", "المنوفية", "الإسماعيلية", "أسوان", "أسيوط", "الأقصر", "البحر الأحمر", "البحيرة", "بني سويف", "بورسعيد", "جنوب سيناء", "الدقهلية", "دمياط", "سوهاج", "السويس", "الشرقية", "شمال سيناء", "الغربية", "الفيوم", "القليوبية", "قنا", "كفر الشيخ", "مطروح", "المنيا", "الوادي الجديد"};
    String[] spinnerListSudi = {"الرياض", "مكة", "المدينة المنورة", "بريدة", "بريدة", "تبوك", "الدمام", "الاحساء", "القطيف", "خميس مشيط", "الطائف", "نجران", "حفر الباطن", "الجبيل", "ضباء", "الخرج", "الثقبة", "ينبع البحر", "الخبر", "عرعر", "الحوية", "عنيزة", "سكاكا", "جيزان", "القريات", "الظهران", "الباحة", "الزلفي", "الرس", "وادي الدواسر", "بيشه", "سيهات", "شروره", "بحره", "تاروت", "الدوادمي", "صبياء", "بيش", "أحد رفيدة", "الفريش", "بارق", "الحوطة", "الأفلاج"};
    String[] spinnerListCountries = {"مصر", "السعودية"};
    String[] spinnerListDefualt = {};

    public HomeScreenChooceCountry(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }


    public HomeScreenChooceCountry(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected HomeScreenChooceCountry(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_chooce_country);
        countryList = (ListView) findViewById(R.id.chooce_country_listView);
        cityList = (ListView) findViewById(R.id.chooce_city_listView);
        CountryListViewAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_dropdown_item_1line, spinnerListCountries);
        CityListViewAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_dropdown_item_1line, spinnerListDefualt);
        countryList.setAdapter(CountryListViewAdapter);
        cityList.setAdapter(CityListViewAdapter);

        countryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                if (adapterView.getItemAtPosition(i).toString() == "مصر") {

                    CityListViewAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_dropdown_item_1line, spinnerListEgy);
                    cityList.setAdapter(CityListViewAdapter);
                    cityList.setVisibility(View.VISIBLE);
                    countryList.setVisibility(View.GONE);

                } else {

                    CityListViewAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_dropdown_item_1line, spinnerListSudi);
                    cityList.setAdapter(CityListViewAdapter);
                    cityList.setVisibility(View.VISIBLE);
                    countryList.setVisibility(View.GONE);

                }


            }
        });


        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                HelperMethods.Home_Filtter_Country_name=adapterView.getItemAtPosition(i).toString();
                c.startActivity(new Intent(c,SallersRecycler.class));

            }
        });

    }
}

