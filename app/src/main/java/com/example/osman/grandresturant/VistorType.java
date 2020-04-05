package com.example.osman.grandresturant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.osman.grandresturant.Registration.Login;

public class VistorType extends AppCompatActivity {
Button visitorBtn,sallerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vistor_type);
        visitorBtn=(Button)findViewById(R.id.visitorBtn);
        sallerBtn=(Button)findViewById(R.id.sallerBtn);
        sallerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VistorType.this,Login.class));
                
            }
        });

    }
}
