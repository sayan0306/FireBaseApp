package com.example.firebaseapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;

public class lists extends AppCompatActivity {
    private ListView listView;
    private Button showValue;;
    ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        showValue= findViewById(R.id.showValue);
        listView = findViewById(R.id.listView);
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(lists.this, android.R.layout.simple_list_item_1,arrayList);
        showValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listView.setAdapter(arrayAdapter);
                FirebaseFirestore.getInstance().collection("Vendor"+FirebaseAuth.getInstance().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        arrayList.clear();
                        for (DocumentSnapshot documentSnapshot:value){
                            arrayList.add(documentSnapshot.getString("name")+" : "+documentSnapshot.getString("age"));
                        }
                        arrayAdapter.notifyDataSetChanged();
                        listView.setAdapter(arrayAdapter);
                    }
                });
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str =listView.getItemAtPosition(i).toString();
                new AlertDialog.Builder(lists.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this vendor?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseFirestore.getInstance().collection("Vendor"+FirebaseAuth.getInstance().getUid()).whereEqualTo("name", str.substring(0,str.length()-5)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        WriteBatch writeBatch= FirebaseFirestore.getInstance().batch();
                                        List<DocumentSnapshot> documentSnapshots= queryDocumentSnapshots.getDocuments();
                                        for(DocumentSnapshot snapshot : documentSnapshots){
                                            writeBatch.delete(snapshot.getReference());
                                            arrayAdapter.notifyDataSetChanged();
                                        }
                                        writeBatch.commit().addOnSuccessListener(new OnSuccessListener<Void>(){
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(lists.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                return true;
            }
        });
    }

    public static class Paths {

    }
}
