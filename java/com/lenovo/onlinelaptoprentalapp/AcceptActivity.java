package com.lenovo.onlinelaptoprentalapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AcceptActivity extends AppCompatActivity {
    private TimePicker time_Picker;

    int h, m;
    String x;

    String buyer,url;

    String center;
    ProgressDialog pd;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);

        buyer=getIntent().getStringExtra("buyer1");
        center=getIntent().getStringExtra("center");
        url=getIntent().getStringExtra("url");


        time_Picker = findViewById(R.id.timePicker);
        Button ok = findViewById(R.id.ok);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h = time_Picker.getHour();
                m = time_Picker.getMinute();
                if(h>12){
                    x=(h-12)+":"+m+"PM";
                }
                else x=h+":"+m+"AM";

                Toast.makeText(getApplicationContext(),x,Toast.LENGTH_SHORT).show();
                if(x.equals("")){
                    Toast.makeText(getApplicationContext(),"Select time",Toast.LENGTH_SHORT).show();
                }
                else {

                    pd = new ProgressDialog(AcceptActivity.this);
                    pd.setMessage("Uploading");
                    pd.show();

                    Map<String, Object> user1 = new HashMap<>();
                    user1.put("status", "Accepted");

                    FirebaseFirestore.getInstance().collection("chissak999@gmail.com").document(url).update(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AcceptActivity.this, "data uploaded", Toast.LENGTH_SHORT).show();
                                sendMail();
                                pd.dismiss();
                                startActivity(new Intent(AcceptActivity.this, RentingSuccess.class));
                                startActivity(new Intent(getApplicationContext(),Main2Activity.class));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(AcceptActivity.this, "Error in data uploading", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    private void sendMail() {
        String subject="Laptop Request Accepted";
        String message="Your Laptop request is Accepted.Pick your laptop  at "+center+".Your Estimated time to reach center is "+x;

        JavaMailAPI javamailapi=new JavaMailAPI(this,buyer,subject,message);
        javamailapi.execute();
    }

}