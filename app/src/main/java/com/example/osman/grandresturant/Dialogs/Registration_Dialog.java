package com.example.osman.grandresturant.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.Registration.Login;
import com.example.osman.grandresturant.Registration.Sign;
import com.google.firebase.database.DatabaseReference;

public class Registration_Dialog extends Dialog {

    public Context c;
    public Dialog d;
    Button SignUp, SignIn;
    DatabaseReference mDatabaseReference;

    public Registration_Dialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    public Registration_Dialog(@NonNull Context context) {
        super(context);
    }

    public Registration_Dialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected Registration_Dialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registration__dialog);

        SignUp = (Button) findViewById(R.id.registration_dialog_sign_up);
        SignIn = (Button) findViewById(R.id.registration_dialog_sign_in);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getContext().startActivity(new Intent(getContext() , Sign.class));

            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getContext().startActivity(new Intent(getContext() , Login.class));

            }
        });
    }
}
