package com.example.osman.grandresturant.Registration;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.HomeScreen;
import com.example.osman.grandresturant.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginSallers extends AppCompatActivity {

    private EditText email, password;
    private Button logInBtn;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressBar progress;
    TextView Tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sallers);
        email = (EditText) findViewById(R.id.emailEt);
        password = (EditText) findViewById(R.id.passwordEt);
        logInBtn = (Button) findViewById(R.id.loginBtn);

        Tv = (TextView) findViewById(R.id.Tv);
        Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSallers.this, SignSallers.class));

            }
        });


        auth = FirebaseAuth.getInstance();

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myEmail = email.getText().toString().trim();
                String myPassword = password.getText().toString().trim();
                if (!TextUtils.isEmpty(myEmail) || !TextUtils.isEmpty(myPassword)) {


                }
                logIn();

            }
        });


    }



    private void logIn() {
        String myEmail = email.getText().toString().trim();
        String myPassword = password.getText().toString().trim();
        if (TextUtils.isEmpty(myEmail) || TextUtils.isEmpty(myPassword)) {
            Toast.makeText(this, "أكمل البيانات", Toast.LENGTH_SHORT).show();
        } else {
            HelperMethods.showDialog(LoginSallers.this, "Wait...", "loading data");
            auth.signInWithEmailAndPassword(myEmail, myPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {

                        Toast.makeText(LoginSallers.this, "email or password is incorrect", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        startActivity(new Intent(LoginSallers.this , HomeScreen.class));
                    }
                    HelperMethods.hideDialog(LoginSallers.this);

                }
            });

        }

    }

    @Override
    public void onBackPressed() {


        startActivity(new Intent(this , HomeScreen.class));

    }
}
