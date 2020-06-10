package com.lenovo.onlinelaptoprentalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference;
    private List<UploadModel> modelList=new ArrayList<>();
    ProgressDialog pd;

    private static final String SHARED_PREFS="sharedPrefs";
    private static final String TEXT="text";
    private static final String SWITCH1="switch1";

    private String text;
    private boolean switchon;
    String gmail="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        pd=new ProgressDialog(this);


        recyclerView = findViewById(R.id.recycler_view2);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        loadData();
        updateViews();

        if(gmail.equals("")) {
            gmail = getIntent().getStringExtra("gmail");
            saveData();
        }


        pd.setMessage("Loading...");
        pd.show();
        collectionReference=db.collection("chissak999@gmail.com");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    String company=snapshot.getString("company");
                    String pro_level=snapshot.getString("processor level");
                    String memory=snapshot.getString("memory");
                    String ram=snapshot.getString("ram");
                    String os=snapshot.getString("os");
                    String pro_speed=snapshot.getString("processor speed");
                    String features=snapshot.getString("features");
                    String phone=snapshot.getString("phone");
                    String email=snapshot.getString("email");
                    String url=snapshot.getString("Url");
                    String buyer=snapshot.getString("buyer");
                    String status=snapshot.getString("status");
                    String availability=snapshot.getString("availability");
                    String address=snapshot.getString("address");
                    String buyer_no=snapshot.getString("buyer_no");
                    String center=snapshot.getString("center");
                    String hours=snapshot.getString("hours");

                    modelList.add(new UploadModel(company,pro_level,memory,ram,os,pro_speed,features,
                            phone,email,url,buyer,status,availability,address,buyer_no,center,hours,gmail));
                }
                Adapter adapter=new Adapter(Main2Activity.this,modelList);
                recyclerView.setAdapter(adapter);
                pd.dismiss();
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
            }
        });
   }

    private void saveData() {
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(TEXT,gmail);
        editor.putBoolean(SWITCH1,true);

        editor.apply();
    }
    public void loadData(){
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        text=sharedPreferences.getString(TEXT,"");
        switchon=sharedPreferences.getBoolean(SWITCH1,true);
    }
    public void updateViews(){
        gmail=text;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_device:
                Intent intent=new Intent(Main2Activity.this,AddActivity.class);
                intent.putExtra("gmail",gmail);
                startActivity(intent);
                return true;
            case R.id.rented_device:
                Intent intent1=new Intent(Main2Activity.this,MyRentedDevices.class);
                intent1.putExtra("gmail",gmail);
                startActivity(intent1);
                return true;
            case R.id.info:
                startActivity(new Intent(Main2Activity.this,AboutActivity.class));
                return true;
            case R.id.logout:
                gmail="";
                text="";
                saveData();
                Toast.makeText(getApplicationContext(),"Thank You",Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        startActivity(new Intent(Main2Activity.this,MainActivity.class));
                return true;
            default:        return super.onOptionsItemSelected(item);
        }
    }
}