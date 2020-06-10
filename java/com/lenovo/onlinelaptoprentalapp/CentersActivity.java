package com.lenovo.onlinelaptoprentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CentersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centers);

        Button continu = findViewById(R.id.continue0);

        continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String url=getIntent().getStringExtra("url1");
               String phn=getIntent().getStringExtra("number1");
               String gmail=getIntent().getStringExtra("gmail");
               String mail=getIntent().getStringExtra("mail");

                Intent intent=new Intent(getApplicationContext(),Rent.class);

                intent.putExtra("number1",phn);
                intent.putExtra("url1",url);
                intent.putExtra("gmail",gmail);
                intent.putExtra("mail",mail);

                startActivity(intent);


            }
        });
    }
}
