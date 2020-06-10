package com.lenovo.onlinelaptoprentalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddDevice extends AppCompatActivity {

    private ImageView img1;
    private Button choose_image;
    private Spinner company;
    private Spinner pr_level;
    private EditText ram;
    private EditText memory;
    private EditText phone;
    private Spinner os;
    private Spinner pro_speed;
    private Button rent;
    private Button features;
    private String time="1";

    private static final int PICK_IMAGE_REQUEST=1;

    private Uri mImageUri;
    private StorageReference mStorageRef;
    FirebaseFirestore db;

    String name;

    String coml="",prol="",osl="",prosl="",featl="";
    String[] feat_list;
    boolean[] check;
    ArrayList<Integer> selected=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        img1=findViewById(R.id.imageView3);
        choose_image=findViewById(R.id.imgbtn2);
        company=findViewById(R.id.company);
        pr_level=findViewById(R.id.proslevel);
        ram=findViewById(R.id.ram);
        memory=findViewById(R.id.memory);
        phone=findViewById(R.id.phone3);
        os=findViewById(R.id.os);
        pro_speed=findViewById(R.id.prospeed);
        features=findViewById(R.id.features);
        rent=findViewById(R.id.rentbtn);

        feat_list=getResources().getStringArray(R.array.features_list);
        check=new boolean[feat_list.length];

        name=getIntent().getStringExtra("gmail");

        features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(AddDevice.this);
                builder.setTitle(R.string.avail);
                builder.setMultiChoiceItems(feat_list, check, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {


                        if(isChecked){
                            if(!selected.contains(position)){
                                selected.add(position);
                            }
                            }
                        else if(selected.contains(position)){
                            selected.remove(Integer.valueOf(position));
                        }
                    }
                });
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item="";
                        for(int i=0;i<selected.size();i++){
                            item=item + feat_list[selected.get(i)];
                            if(i!=selected.size()-1){
                                item=item+", ";
                            }
                        }
                        featl=item;
                    }
                });
                builder.setNegativeButton(R.string.dismiss, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNeutralButton(R.string.clear, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i=0;i<check.length;i++){
                            check[i]=false;
                            selected.clear();
                            featl="";
                        }
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        if(true) {

            List<String> com1 = new ArrayList<>();
            List<String> pro1 = new ArrayList<>();
            List<String> os1 = new ArrayList<>();
            List<String> pro2 = new ArrayList<>();

            com1.add(0, "--Select Company--");
            com1.add(1, "Lenovo");
            com1.add(2, "HP");
            com1.add(3, "Dell");
            com1.add(4, "Apple");
            com1.add(5, "Assus");
            com1.add(6, "Acer");

            pro1.add(0, "--Select Processor level--");
            pro1.add(1, "I7");
            pro1.add(2, "I5");
            pro1.add(3, "I3");
            pro1.add(4, "Pentium MMX");
            pro1.add(5, "Atom");
            pro1.add(6, "Celeron");
            pro1.add(7, "Pentium Pro");
            pro1.add(8, "Pentium II");
            pro1.add(9, "Pentium III");
            pro1.add(10, "Pentium");

            os1.add(0, "--Select OS--");
            os1.add(1, "Windows 7");
            os1.add(2, "Windows X");
            os1.add(3, "Ubuntu");
            os1.add(4, "Linux");
            os1.add(5, "IOS");

            pro2.add(0, "--Select Processing speed--");
            pro2.add(1, "32 bit");
            pro2.add(2, "64 bit");
            pro2.add(3, "x84 bit");


            ArrayAdapter<String> dataAdapter1;
            ArrayAdapter<String> dataAdapter2;
            ArrayAdapter<String> dataAdapter3;
            ArrayAdapter<String> dataAdapter4;

            dataAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, com1);
            dataAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, pro1);
            dataAdapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, os1);
            dataAdapter4 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, pro2);

            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            company.setAdapter(dataAdapter1);
            pr_level.setAdapter(dataAdapter2);
            os.setAdapter(dataAdapter3);
            pro_speed.setAdapter(dataAdapter4);

            company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position).equals("--Select Company--")) {
                    } else {
                        coml = parent.getItemAtPosition(position).toString();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            pr_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position).equals("--Select Processor level--")) {
                    } else {
                        prol = parent.getItemAtPosition(position).toString();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            os.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position).equals("--Select OS--")) {
                    } else {
                        osl = parent.getItemAtPosition(position).toString();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            pro_speed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position).equals("--Select Processing speed--")) {
                    } else {
                        prosl = parent.getItemAtPosition(position).toString();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_ram=ram.getText().toString();
                String txt_memory=memory.getText().toString();
                String txt_phone=phone.getText().toString();

                if(name.isEmpty() || coml.equals("") || prol.equals("") || osl.equals("") || prosl.equals("") || TextUtils.isEmpty(txt_phone) || TextUtils.isEmpty(txt_ram) || TextUtils.isEmpty(txt_memory) || mImageUri==null){
                    Toast.makeText(AddDevice.this,"Empty credentials",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    UploadImage();
                    Toast.makeText(AddDevice.this,"Uploading",Toast.LENGTH_SHORT).show();

                    AddData();
                }
            }
        });
    }
    private void AddData() {

        db= FirebaseFirestore.getInstance();

        final ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        String memory1=memory.getText().toString();
        String raml=ram.getText().toString();
        String phone1=phone.getText().toString();

        Map<String,Object> user1=new HashMap<>();
        user1.put("company",coml);
        user1.put("processor level",prol);
        user1.put("memory",memory1);
        user1.put("ram",raml);
        user1.put("os",osl);
        user1.put("processor speed",prosl);
        user1.put("features",featl);
        user1.put("email",name);
        user1.put("phone",phone1);
        user1.put("Url",time);
        user1.put("buyer_no","");
        user1.put("buyer","");
        user1.put("status","");
        user1.put("availability","true");
        user1.put("address","");
        user1.put("center","");
        user1.put("hours","");



        db.collection("chissak999@gmail.com").document(time).set(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddDevice.this,"data uploaded",Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                    startActivity(new Intent(AddDevice.this,Main2Activity.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddDevice.this,"Error in data uploading",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void UploadImage() {

        time=System.currentTimeMillis()+"."+getFileExtension(mImageUri);

        mStorageRef= FirebaseStorage.getInstance().getReference().child(time);

        mStorageRef.putFile(mImageUri)
                .addOnSuccessListener(this,new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(AddDevice.this,"Error in Image uploading",Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private void openFileChooser() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            mImageUri=data.getData();

            Picasso.get().load(mImageUri).into(img1);
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}
