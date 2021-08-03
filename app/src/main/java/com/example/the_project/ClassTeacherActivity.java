package com.example.the_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.the_project.adapter.ClassTeacherAdapter;
import com.example.the_project.databinding.ActivityClassTeacherBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClassTeacherActivity extends AppCompatActivity {
    ActivityClassTeacherBinding binding;
    public static int ACTIVITY;
    ArrayList<String> listSubjectName;
    ArrayList<String> listSubjectId;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClassTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        ACTIVITY = intent.getIntExtra("Activity",0);


        listSubjectId = new ArrayList<>();
        listSubjectName = new ArrayList<>();

        ClassTeacherAdapter adapter = new ClassTeacherAdapter(listSubjectName, listSubjectId ,this);
        binding.IdClassTeacherRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.IdClassTeacherRecyclerView.setLayoutManager(layoutManager);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        database.getReference().child("Teacher").child(auth.getCurrentUser().getUid()).child("Class").
                orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()) {
                    String subjectName = snap.getValue(String.class);
                    String subjectId = snap.getKey();
                    listSubjectName.add(subjectName);
                    listSubjectId.add(subjectId);
                }
                adapter.notifyDataSetChanged();
            }

            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}