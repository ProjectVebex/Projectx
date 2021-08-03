package com.example.the_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.the_project.adapter.UserAdapter;
import com.example.the_project.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChattingHomeActivity extends AppCompatActivity {

    FirebaseAuth auth;
    RecyclerView mainUserRecycleView;
    UserAdapter adapter;
    FirebaseDatabase database;
    ArrayList<User> usersArrayList;
    ImageView imageView;
    ImageView settingImageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_home);
        getSupportActionBar().hide();

        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()==null){
            startActivity(new Intent(ChattingHomeActivity.this,RegisterActivity.class));
        }


        imageView=findViewById(R.id.chattingHomeBack);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChattingHomeActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });


        settingImageView=findViewById(R.id.chattingHomeSettings);
        settingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChattingHomeActivity.this,ChattingSettingActivity.class));
            }
        });
    }
}