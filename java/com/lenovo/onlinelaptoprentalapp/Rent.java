package com.lenovo.onlinelaptoprentalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rent extends AppCompatActivity {

    private Spinner center_l;
    private EditText hours;
    private EditText apartment;
    private EditText street;
    private EditText area;
    private EditText city;
    private Button confirm;
    private ProgressDialog pd;
    private CheckBox terms;

    private StorageReference mStorageRef;
    FirebaseFirestore db;

    String center1="1",address="",url,phn,gmail,mail,hr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        center_l=findViewById(R.id.center);
        hours=findViewById(R.id.hours12);
        apartment=findViewById(R.id.apartment);
        street=findViewById(R.id.street);
        area=findViewById(R.id.area);
        city=findViewById(R.id.city);
        confirm=findViewById(R.id.confirm);
        terms=findViewById(R.id.checkBox);

        url=getIntent().getStringExtra("url1");
        phn=getIntent().getStringExtra("number1");
        gmail=getIntent().getStringExtra("gmail");
        mail=getIntent().getStringExtra("mail");

        List<String> center = new ArrayList<>();

        center.add(0, "--Select center Near you--");
        center.add(1, "CENTER 1");
        center.add(2, "CENTER 2");
        center.add(3, "CENTER 3");
        center.add(4, "CENTER 4");
        center.add(5, "CENTER 5");
        center.add(6, "CENTER 6");

        ArrayAdapter<String> dataAdapter;

        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, center);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        center_l.setAdapter(dataAdapter);

        center_l.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("--Select center Near you--")) {
                } else {
                    center1 = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(TextUtils.isEmpty(hours.getText().toString()) ||TextUtils.isEmpty(apartment.getText().toString()) || TextUtils.isEmpty(street.getText().toString())
                            || TextUtils.isEmpty(area.getText().toString()) || TextUtils.isEmpty(city.getText().toString()) ||TextUtils.isEmpty(hours.getText().toString()))
                    {
                        Toast.makeText(Rent.this,"Enter Valid Details",Toast.LENGTH_SHORT).show();
                    }
                    else if(terms.isChecked())
                    {    address=apartment.getText().toString()+", "+street.getText().toString()+", "+area.getText().toString()+", "+city.getText().toString();


                        pd = new ProgressDialog(Rent.this);
                    pd.setMessage("Uploading");
                    pd.show();

                    hr=hours.getText().toString();

                    Map<String, Object> user1 = new HashMap<>();
                    user1.put("address", address);
                    user1.put("status", "pending");
                    user1.put("buyer", gmail);
                    user1.put("buyer_no", phn);
                    user1.put("hours", hr);
                    user1.put("center",center1);
                    user1.put("availability","false");

                    FirebaseFirestore.getInstance().collection("chissak999@gmail.com").document(url).update(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                sendMail();
                                pd.dismiss();
                                startActivity(new Intent(Rent.this, SuccessActivity.class));
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(Rent.this, "Error in data uploading", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                        Toast.makeText(Rent.this, "please accept the terms", Toast.LENGTH_SHORT).show();
                    }
            }
            });
    }

    private void sendMail() {
        String subject="Laptop Requested";
        String message="Your Laptop is requested for renting on ONLINE LAPTOP RENTAL APP. Confirm the request for renting for renting your laptop.";

        JavaMailAPI javamailapi=new JavaMailAPI(this,mail,subject,message);
        javamailapi.execute();

    }
}