package com.lenovo.onlinelaptoprentalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class NumberActivity extends AppCompatActivity {

    private EditText number;
    private EditText otp;
    private Button send;
    private Button next;
    FirebaseAuth auth;

    String code;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        number=findViewById(R.id.otp_num);
        send=findViewById(R.id.send_otp);
        otp=findViewById(R.id.otp);
        next=findViewById(R.id.next);


        auth=FirebaseAuth.getInstance();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifySignInCode();
            }
        });
    }

    private void verifySignInCode() {
        String code1=otp.getText().toString();
        PhoneAuthCredential c=PhoneAuthProvider.getCredential(code,code1);
        signInWithPhoneAuthCredential(c);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String url=getIntent().getStringExtra("url1");
                            String gmail=getIntent().getStringExtra("gmail");
                            String mail=getIntent().getStringExtra("mail");

                            Intent intent=new Intent(getApplicationContext(),CentersActivity.class);

                            intent.putExtra("number1",phone);
                            intent.putExtra("url1",url);
                            intent.putExtra("gmail",gmail);
                            intent.putExtra("mail",mail);

                            startActivity(intent);
                            finish();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                otp.setError("Incorrect verificationcode");
                                otp.requestFocus();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                otp.setError("Incorrect verificationcode");
                otp.requestFocus();
            }
        });
    }




    private void sendVerificationCode() {
        phone=number.getText().toString();
        if(phone.isEmpty()){
            number.setError("Phone number is required");
            number.requestFocus();
            return;
        }
        if(phone.length()<10){
            number.setError("Please enter valid Phone number");
            number.requestFocus();
            return;
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phone,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            code=s;
        }
    };
}
