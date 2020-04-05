package com.example.osman.grandresturant.NavigationActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.osman.grandresturant.HomeScreen;
import com.example.osman.grandresturant.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FeedBack extends AppCompatActivity {

    Button feedback_btn;
    EditText subject, massage;
    FirebaseAuth auth;
    DatabaseReference databaseReference, mDatabase;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        Toolbar ToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(ToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        feedback_btn = (Button) findViewById(R.id.feedback_send);
        subject = (EditText) findViewById(R.id.feedback_title);
        massage = (EditText) findViewById(R.id.feedback_massage);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("FeedBack");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid());


        mDatabase.child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println(snapshot.getValue());
                try {
                    userName = snapshot.getValue().toString();
                } catch (Exception e) {
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        feedback_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    String sub = subject.getText().toString();
                    String msg = massage.getText().toString();

                    if (TextUtils.isEmpty(sub) || TextUtils.isEmpty(msg)) {
                        Toast.makeText(FeedBack.this, "أكمل البيانات", Toast.LENGTH_SHORT).show();
                    } else {
                        databaseReference = databaseReference.push();
                        databaseReference.child("Subject").setValue(sub);
                        databaseReference.child("Massage").setValue(msg);
                        databaseReference.child("UserID").setValue(auth.getCurrentUser().getUid());
                        databaseReference.child("UserName").setValue(userName).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(FeedBack.this, "تم إرسال رأيك بنجاح", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(FeedBack.this, HomeScreen.class));
                            }
                        });
                    }


                } else {
                    Toast.makeText(FeedBack.this, "تسجيل دخول", Toast.LENGTH_SHORT).show();
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
}
