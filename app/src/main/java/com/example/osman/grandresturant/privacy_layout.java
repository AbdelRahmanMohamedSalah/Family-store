package com.example.osman.grandresturant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class privacy_layout extends AppCompatActivity {
ImageButton imageButton_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_layout);
    imageButton_back=(ImageButton)findViewById(R.id.btn_back_privacy);

    imageButton_back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(privacy_layout.this,HomeScreen.class);
            startActivity(intent);
            finish();
        }
    });

    }
}
