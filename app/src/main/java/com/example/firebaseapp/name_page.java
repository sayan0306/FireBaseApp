package com.example.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class name_page extends AppCompatActivity {
    private EditText  name;
    private EditText age;
    private Button add;
    ConstraintLayout namePageLayout;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_page);
        name= findViewById(R.id.name);
        age= findViewById(R.id.age);
        add= findViewById(R.id.addButton);
        namePageLayout=findViewById(R.id.namePageLayout);

        namePageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId()==R.id.namePageLayout){
                    InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> map = new HashMap<>();
                if(Integer.parseInt(age.getText().toString())<18){
                    Toast.makeText(name_page.this, "Age must be greater than or equal to 18", Toast.LENGTH_SHORT).show();
                }else {
                    map.put("name", name.getText().toString());
                    map.put("age", age.getText().toString());
                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                    firebaseFirestore.collection("Vendor"+FirebaseAuth.getInstance().getUid()).add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                        @Override
                        public void onSuccess(DocumentReference unused) {
                            Toast.makeText(name_page.this, "Added successfully", Toast.LENGTH_SHORT).show();
                        }

                    });

                }

            }
        });
    }
}

