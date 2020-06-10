package com.lenovo.onlinelaptoprentalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private CardView login;
    private TextView forgotpass;
    private CardView register;
    private ProgressDialog progressDialog;

    private String gmail;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        login=(CardView)findViewById(R.id.login);
        forgotpass=(TextView)findViewById(R.id.forgotpass);
        register=(CardView)findViewById(R.id.register);

        auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        progressDialog=new ProgressDialog(this);

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PasswordActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email=email.getText().toString();
                String txt_password=password.getText().toString();

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(MainActivity.this,"Empty credentials",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loginUser(txt_email,txt_password);
                }

            }
        });
        if(user!=null)
        {
            finish();
            Intent intent=new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent);
        }

    }

    private void loginUser(String email, String password) {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        gmail=email;
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                    intent.putExtra("gmail",gmail);
                    startActivity(intent);
                    finish();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"login Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
