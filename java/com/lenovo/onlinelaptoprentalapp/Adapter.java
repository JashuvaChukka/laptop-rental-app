package com.lenovo.onlinelaptoprentalapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.List;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private List<UploadModel> uploadModels;
    Context context;

    public String company2;
    public String pro_level2;
    public String memory2;
    public String ram2;
    public String os2;
    public String pro_speed2;
    public String features2;
    public String phone2;
    public String email2;
    public String url2;
    public String buyer2;
    public String buyer_no2;
    public String status2;
    public String availability2;
    public String address2;

    public Adapter(Context context,List<UploadModel> uploadModels) {
        this.uploadModels = uploadModels;
        this.context=context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        company2=uploadModels.get(position).getCompany();
        pro_level2=uploadModels.get(position).getPro_level();
        memory2=uploadModels.get(position).getMemory();
        ram2=uploadModels.get(position).getRam();
        os2=uploadModels.get(position).getOs();
        pro_speed2=uploadModels.get(position).getPro_speed();
        features2=uploadModels.get(position).getFeatures();
        phone2=uploadModels.get(position).getPhone();
        email2=uploadModels.get(position).getEmail_id();
        url2=uploadModels.get(position).getUrl();
        buyer2=uploadModels.get(position).getBuyer();
        buyer_no2=uploadModels.get(position).getBuyer_no();
        status2=uploadModels.get(position).getStatus();
        availability2=uploadModels.get(position).getAvailability();
        address2=uploadModels.get(position).getAddress();

        holder.setData(company2,ram2,memory2,url2,availability2);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent=new Intent(context,SingleDataActivity.class);
                        intent.putExtra("company1",uploadModels.get(position).getCompany());
                        intent.putExtra("pro_level1",uploadModels.get(position).getPro_level());
                        intent.putExtra("memory1",uploadModels.get(position).getMemory());
                intent.putExtra("ram1",uploadModels.get(position).getRam());
                intent.putExtra("os1",uploadModels.get(position).getOs());
                intent.putExtra("pro_speed1",uploadModels.get(position).getPro_speed());
                        intent.putExtra("features1",uploadModels.get(position).getFeatures());
                        intent.putExtra("phone1",uploadModels.get(position).getPhone());
                        intent.putExtra("email1",uploadModels.get(position).getEmail_id());
                intent.putExtra("url1",uploadModels.get(position).getUrl());
                intent.putExtra("buyer1",uploadModels.get(position).getBuyer());
                intent.putExtra("buyer_no1",uploadModels.get(position).getBuyer_no());
                        intent.putExtra("status1",uploadModels.get(position).getStatus());
                        intent.putExtra("availability1",uploadModels.get(position).getAvailability());
                        intent.putExtra("address1",uploadModels.get(position).getAddress());
                        intent.putExtra("gmail",uploadModels.get(position).getGmail());

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return uploadModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView brand;
        private TextView processor;
        private TextView memory;
        private ImageView image;
        LinearLayout view;
        private TextView avail;
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageReference;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            brand=itemView.findViewById(R.id.brand_1);
            processor=itemView.findViewById(R.id.ram);
            memory=itemView.findViewById(R.id.memory_1);
            view=itemView.findViewById(R.id.view1);
            image=itemView.findViewById(R.id.lap_img2);
            avail=itemView.findViewById(R.id.availability);
        }
        private void setData(String company1,String ram,String memory1,String url1,String availablility1){

            brand.setText(company1.toUpperCase());
        processor.setText("Ram : "+ram+"GB");
           memory.setText("Memory : "+memory1+"GB");
           if(availablility1.equals("true")){ avail.setText("Available Now");}
           else avail.setText("Not available!!!");


            storageReference=storage.getReferenceFromUrl("gs://online-laptop-rental-app.appspot.com").child(url1);
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
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}