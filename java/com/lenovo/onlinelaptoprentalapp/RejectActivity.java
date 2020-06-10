package com.lenovo.onlinelaptoprentalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RejectActivity extends AppCompatActivity {

    private EditText reason;
    private Button send;
    private ProgressDialog pd;
    String buyer,url,text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject);

        buyer=getIntent().getStringExtra("buyer");
        url=getIntent().getStringExtra("url");

        reason=findViewById(R.id.rejection);
        send=findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text=reason.getText().toString();
                if(TextUtils.isEmpty(text) ){
                    reason.setError("Enter valid reason");
                    reason.requestFocus();
                }
                else{
                    pd = new ProgressDialog(RejectActivity.this);
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
                                Toast.makeText(RejectActivity.this, "data uploaded", Toast.LENGTH_SHORT).show();
                                sendMail();
                                pd.dismiss();
                                startActivity(new Intent(RejectActivity.this, SuccessActivity.class));
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(RejectActivity.this, "Error in data uploading", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }
    private void sendMail() {
        String subject="Laptop Request Rejected";
        String message="Your Laptop request is rejected because of some technical reasons.\n"+text;

        JavaMailAPI javamailapi=new JavaMailAPI(this,buyer,subject,message);
        javamailapi.execute();
    }
}
