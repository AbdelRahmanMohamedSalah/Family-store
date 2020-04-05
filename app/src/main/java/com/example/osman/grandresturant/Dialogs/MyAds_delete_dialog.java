package com.example.osman.grandresturant.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.HomeScreen;
import com.example.osman.grandresturant.NavigationActivities.MyAds;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.SallersRecycler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyAds_delete_dialog extends Dialog  {

    public Activity c;
    public Dialog d;
    Button yes, no;
    DatabaseReference mDatabaseReference;

    public MyAds_delete_dialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    public MyAds_delete_dialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MyAds_delete_dialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom_dialog);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Items");

        yes = (Button) findViewById(R.id.MyAds_delete_dialog_btn_yes);
        no = (Button) findViewById(R.id.MyAds_delete_dialog_btn_no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabaseReference.child(HelperMethods.delete_ads_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(c, "تم إزالة المنتج", Toast.LENGTH_SHORT).show();
                        c.startActivity(new Intent(c , HomeScreen.class));
                    }
                });

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


}
