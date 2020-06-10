package com.lenovo.onlinelaptoprentalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class SingleDataActivity extends AppCompatActivity {
    private TextView company;
    private TextView pro_level;
    private TextView os;
    private TextView memory;
    private TextView ram;
    private TextView pro_speed;
    private TextView features;
    private TextView phone;
    private TextView email;
    private ImageView image;
    private TextView availability;
    private Button rent;

    String gmail;


    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference storageReference;

    String com,pr_l,osl,mr,raml,pr_s,feat,ph,em,url,avail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_data);

        company=findViewById(R.id.brand1);
        pro_level=findViewById(R.id.pro_level1);
        os=findViewById(R.id.os1);
        memory=findViewById(R.id.memory1);
        ram=findViewById(R.id.ram1);
        pro_speed=findViewById(R.id.pro_speed1);
        features=findViewById(R.id.features1);
        phone=findViewById(R.id.phone1);
        email=findViewById(R.id.email1);
        availability=findViewById(R.id.status1);

        rent=findViewById(R.id.rent8);
        image=findViewById(R.id.lap_img);

        setData();
        getData();

        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NumberActivity.class);
                intent.putExtra("url1",url);
                intent.putExtra("gmail",gmail);
                intent.putExtra("mail",em);
                startActivity(intent);
            }
        });

    }

    private void setData() {
            com=getIntent().getStringExtra("company1");
            pr_l=getIntent().getStringExtra("pro_level1");
            osl=getIntent().getStringExtra("os1");
            mr=getIntent().getStringExtra("memory1");
            raml=getIntent().getStringExtra("ram1");
            pr_s=getIntent().getStringExtra("pro_speed1");
            feat=getIntent().getStringExtra("features1");
            ph=getIntent().getStringExtra("phone1");
            em=getIntent().getStringExtra("email1");
            url=getIntent().getStringExtra("url1");
            avail=getIntent().getStringExtra("availability1");
            gmail=getIntent().getStringExtra("gmail");

            Toast.makeText(getApplicationContext(),"Details",Toast.LENGTH_SHORT).show();
    }
    private void getData() {
        company.setText(com.toUpperCase());
        pro_level.setText("Processor level : "+pr_l);
        os.setText(osl);
        memory.setText("Memory : "+mr+"GB");
        ram.setText("RAM : "+raml+"GB");
        pro_speed.setText(pr_s);
        features.setText(feat);
        phone.setText("Contact No. : "+ph);
        email.setText("Mail-ID : "+em);
        if(avail.equals("true"))
            availability.setText("Available Now");
        else
            availability.setText("Not Available!!!");


        storageReference=storage.getReferenceFromUrl("gs://online-laptop-rental-app.appspot.com").child(url);
        try {
            final File file=File.createTempFile("image","jpeg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                    image.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SingleDataActivity.this,"Image Failed to load",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(gmail.equals(em) || avail.equals("false")){
            rent.setEnabled(false);
            rent.setBackgroundColor(Color.TRANSPARENT);
            rent.setTextColor(Color.TRANSPARENT);
        }
    }
}