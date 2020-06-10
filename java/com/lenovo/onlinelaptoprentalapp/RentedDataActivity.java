package com.lenovo.onlinelaptoprentalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RentedDataActivity extends AppCompatActivity {

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
    private Button details;
    private Button cancel;
    ProgressDialog pd;

    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference storageReference;

    String gmail,com,pr_l,osl,mr,raml,pr_s,feat,ph,em,url,avail,status,buyer,buyer_no,hours,center,address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rented_data);


        company=findViewById(R.id.brand99);
        pro_level=findViewById(R.id.pro_level99);
        os=findViewById(R.id.os99);
        memory=findViewById(R.id.memory99);
        ram=findViewById(R.id.ram99);
        pro_speed=findViewById(R.id.pro_speed99);
        features=findViewById(R.id.features99);
        phone=findViewById(R.id.phone99);
        email=findViewById(R.id.email99);
        availability=findViewById(R.id.status99);

        details=findViewById(R.id.rent99);
        cancel=findViewById(R.id.remove99);

        image=findViewById(R.id.lap_img99);
        pd=new ProgressDialog(this);

        details.setEnabled(false);

        setData();
        getData();

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),DetailsActivity.class);
//                intent.putExtra("url1",url);
//                intent.putExtra("gmail",gmail);
//                intent.putExtra("mail",em);
//
//                intent.putExtra("status",status);
//                intent.putExtra("buyer1",buyer);
//                intent.putExtra("buyer_no1",buyer_no);
//                intent.putExtra("hours",hours);
//                intent.putExtra("center",center);
//                intent.putExtra("address1",address);
//
//                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(RentedDataActivity.this);
                pd.setMessage("Uploading");
                pd.show();

                Map<String, Object> user1 = new HashMap<>();
                user1.put("address", "");
                user1.put("status", "");
                user1.put("buyer", "");
                user1.put("buyer_no", "");
                user1.put("hours", "");
                user1.put("center","");
                user1.put("availability","true");

                FirebaseFirestore.getInstance().collection("chissak999@gmail.com").document(url).update(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Request cancelled", Toast.LENGTH_SHORT).show();
                            sendMail();
                            pd.dismiss();
                            startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Error in data uploading", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
    private void sendMail() {
        String subject="Laptop Request Cancelled";
        String message="Laptop request is cencelled by user for some reasons.";

        JavaMailAPI javamailapi=new JavaMailAPI(this,em,subject,message);
        javamailapi.execute();
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
        status=getIntent().getStringExtra("status1");
        buyer=getIntent().getStringExtra("buyer1");
        buyer_no=getIntent().getStringExtra("buyer_no1");
        hours=getIntent().getStringExtra("hours");
        center=getIntent().getStringExtra("center");
        address=getIntent().getStringExtra("address1");
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
        if(avail.equals("false")) {
            if(status.equals("Accepted")){
                availability.setText("Your device is Accepted");
                details.setEnabled(true);
            }
            else if(status.equals("pending"))  availability.setText("Wait until lendor confirms request");
        }

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
                    Toast.makeText(RentedDataActivity.this,"Image Failed to load",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
