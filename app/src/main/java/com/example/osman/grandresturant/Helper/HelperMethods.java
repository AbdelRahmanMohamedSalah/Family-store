package com.example.osman.grandresturant.Helper;

/**
 * Created by A.taher on 4/8/2018.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;

import com.example.osman.grandresturant.classes.Encaps_Basket;
import com.example.osman.grandresturant.classes.Order;
import com.google.common.collect.ArrayListMultimap;

import java.util.ArrayList;


public class HelperMethods {




    public static String Home_Filtter_categoryName;
    public static String Home_Filtter_sallerID;
    public static String Home_Filtter_Country_name;

    public static ArrayListMultimap<String, Order> orders = ArrayListMultimap.create();



    public static String delete_ads_id;


    public static ProgressDialog blg , blg2;

    public static void showDialog(Activity currentActivity, String title, String msg) {
        blg = new ProgressDialog(currentActivity);
        blg.setTitle(title);
        blg.setCanceledOnTouchOutside(false);
        blg.setMessage(msg);
        blg.setCancelable(false);
        blg.show();
    }

    public static void showDialog2(Activity currentActivity, String title, String msg) {
        blg2 = new ProgressDialog(currentActivity);
        blg2.setTitle(title);
        blg2.setCanceledOnTouchOutside(false);
        blg2.setMessage(msg);
        blg2.show();
    }

    public static void hideDialog(Activity currentActivity) {
        blg.cancel();
    }
    public static void hideDialog2(Activity currentActivity) {
        blg2.cancel();
    }
}
