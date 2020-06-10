package com.lenovo.onlinelaptoprentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    private TextView mailmy;
    private TextView phonemy;
    private TextView hoursmy;
    private Button centermy;
    private TextView addressmy;
    private Button reject;
    private Button accept;

    String buyer,buyer_no,hours,center,address,url,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mailmy=findViewById(R.id.mailmy);
        phonemy=findViewById(R.id.phonemy);
        hoursmy=findViewById(R.id.hoursmy);
        centermy=findViewById(R.id.centermy);
        addressmy=findViewById(R.id.addressmy);

        reject=findViewById(R.id.reject);
        accept=findViewById(R.id.accept);

        buyer=getIntent().getStringExtra("buyer1");
        buyer_no=getIntent().getStringExtra("buyer_no1");
        hours=getIntent().getStringExtra("hours");
        center=getIntent().getStringExtra("center");
        address=getIntent().getStringExtra("address1");
        url=getIntent().getStringExtra("url1");
        status=getIntent().getStringExtra("status");

        mailmy.setText(" : "+buyer);
        phonemy.setText(" : "+buyer_no);
        hoursmy.setText(" : "+hours);
        addressmy.setText(" : "+address);

        centermy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x="";
                if(center.equals("CENTER 1")){
                    x="Opposite VR siddhartha college, kanuru, vijayawada, krishna, Andhra pradesh.";
                }
                else if(center.equals("CENTER 2")){
                    x="At VR siddhartha college library(beside Auditorium), kanuru, vijayawada, krishna, Andhra pradesh.";
                }
                else if(center.equals("CENTER 3")){
                    x="Opposite panchayathi office, kanuru, vijayawada, krishna, Andhra pradesh.";
                }
                else if(center.equals("CENTER 4")){
                    x="Beside scoops, kanuru main road, kanuru, vijayawada, krishna, Andhra pradesh.";
                }
                else if(center.equals("CENTER 5")){
                    x="Kamayyathopu center, kanuru, vijayawada, krishna, Andhra pradesh.";
                }
                else if(center.equals("CENTER 6")){
                    x="Near Ranga bomma, kanuru, vijayawada, krishna, Andhra pradesh.";
                }
                openDialog(x);
            }
        });

        if(status.equals("Accepted"))
        {
            accept.setEnabled(false);
        }
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),RejectActivity.class);

                intent.putExtra("buyer",buyer);
                intent.putExtra("url",url);

                startActivity(intent);
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AcceptActivity.class);
                intent.putExtra("buyer1",buyer);
                intent.putExtra("center",center);
                intent.putExtra("url",url);

                startActivity(intent);
            }
        });

    }

    private void openDialog(String txt_center) {
        CenterDialog centerDialog=new CenterDialog(txt_center,center);
        centerDialog.show(getSupportFragmentManager(),"center");
    }
}