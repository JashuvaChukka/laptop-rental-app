package com.lenovo.onlinelaptoprentalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailr;
    private EditText pass1;
    private EditText repass1;
    private Button register1;
    private TextView login1;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;

    String gmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailr=(EditText)findViewById(R.id.emailr);
        pass1=(EditText)findViewById(R.id.pass1);
        repass1=(EditText)findViewById(R.id.repass1);
        login1=(TextView)findViewById(R.id.login1);
        register1=(Button)findViewById(R.id.register1);

        auth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email=emailr.getText().toString();
                String txt_pass=pass1.getText().toString();
                String txt_repass=repass1.getText().toString();

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_pass) || TextUtils.isEmpty(txt_repass)){
                    Toast.makeText(RegisterActivity.this,"Empty credentials",Toast.LENGTH_SHORT).show();
                }
                else if(txt_pass.length()<=7)
                {
                    Toast.makeText(RegisterActivity.this,"Password too short",Toast.LENGTH_SHORT).show();
                }
                else if(!txt_pass.equals(txt_repass))
                {
                    Toast.makeText(RegisterActivity.this,"Password didnot match",Toast.LENGTH_SHORT).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(txt_email).matches()){
                    emailr.setError("Please enter valid mail address");
                }
                else
                {
                    registerUser(txt_email,txt_pass);
                }
            }
        });
        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    private void registerUser(String email, String pass) {
        progressDialog.setMessage("Registering...");
        progressDialog.show();
        gmail=email;
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            Intent i=new Intent(RegisterActivity.this,Main2Activity.class);
                            i.putExtra("gmail",gmail);
                            Toast.makeText(RegisterActivity.this,"Registering user successfull",Toast.LENGTH_SHORT).show();
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
