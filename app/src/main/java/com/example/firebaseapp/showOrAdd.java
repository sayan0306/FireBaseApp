package com.example.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class showOrAdd extends AppCompatActivity {

    private Button addVendors;
    private Button showVendors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_or_add);
        addVendors= findViewById(R.id.addVendors);
        showVendors= findViewById(R.id.showList);
        addVendors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(showOrAdd.this,name_page.class));
            }
        });
        showVendors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(showOrAdd.this,lists.class));
            }
        });
    }
}