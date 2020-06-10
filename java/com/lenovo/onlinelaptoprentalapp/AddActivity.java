package com.lenovo.onlinelaptoprentalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<UploadModel> modelList = new ArrayList<>();
    ProgressDialog pd;

    String gmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        gmail=getIntent().getStringExtra("gmail");

        FloatingActionButton fl = findViewById(R.id.floating);
        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent(AddActivity.this, AddDevice.class);
                        intent.putExtra("gmail",gmail);
                        startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.recycler_view3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        pd=new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();

        db.collection("chissak999@gmail.com")
                .whereEqualTo("email",gmail)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document:task.getResult()){
                    String company=document.getString("company");
                    String pro_level=document.getString("processor level");
                    String memory=document.getString("memory");
                    String ram=document.getString("ram");
                    String os=document.getString("os");
                    String pro_speed=document.getString("processor speed");
                    String features=document.getString("features");
                    String phone=document.getString("phone");
                    String email=document.getString("email");
                    String url=document.getString("Url");
                    String buyer=document.getString("buyer");
                    String status=document.getString("status");
                    String availability=document.getString("availability");
                    String address=document.getString("address");
                    String buyer_no=document.getString("buyer_no");
                    String center=document.getString("center");
                    String hours=document.getString("hours");

                    modelList.add(new UploadModel(company,pro_level,memory,ram,os,pro_speed,features,
                            phone,email,url,buyer,status,availability,address,buyer_no,center,hours,gmail));
                    }
                MyAdapter adapter=new MyAdapter(AddActivity.this,modelList);
                recyclerView.setAdapter(adapter);
                pd.dismiss();
                adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
            }
        });

    }
}