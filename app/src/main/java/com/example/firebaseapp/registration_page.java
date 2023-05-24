package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registration_page extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button registerButton;
    private FirebaseAuth firebaseAuth;
    ConstraintLayout registrationPageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        email=findViewById(R.id.registerEmail);
        password=findViewById(R.id.registerPassword);
        registerButton=findViewById(R.id.registerButton);
        registrationPageLayout=findViewById(R.id.registrationPageLayout);
        registrationPageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId()==R.id.registrationPageLayout){
                    InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
                }
            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1=email.getText().toString();
                String password1=password.getText().toString();
                if(TextUtils.isEmpty(email1)||TextUtils.isEmpty(password1)){
                    Toast.makeText(registration_page.this, "Please fill the necessary fields", Toast.LENGTH_SHORT).show();
                }else{
                    reg(email1, password1);
                }
            }
        });
    }

    private void reg(String email1, String password1) {
        firebaseAuth.createUserWithEmailAndPassword(email1, password1).addOnCompleteListener(registration_page.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(registration_page.this, "Success", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(registration_page.this, "Already Registered , Please login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}