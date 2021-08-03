package com.example.the_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.the_project.databinding.ActivityHomeBinding;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    FirebaseAuth mauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mauth=FirebaseAuth.getInstance();


        binding.chatbtn.setOnClickListener(v->{
            startActivity(new Intent(HomeActivity.this,ChattingActivity.class));
        });

        binding.notesbtn.setOnClickListener(v->{
            startActivity(new Intent(HomeActivity.this,NotesActivity.class));
        });

        binding.noticebtn.setOnClickListener(v->{
            startActivity(new Intent(HomeActivity.this,NoticeActivity.class));
        });

        binding.assignbtn.setOnClickListener(v->{
            startActivity(new Intent(HomeActivity.this,AssignmentActivity.class));
        });

        binding.quizbtn.setOnClickListener(v->{
            startActivity(new Intent(HomeActivity.this,QuizActivity.class));
        });

        binding.performbtn.setOnClickListener(v->{
            startActivity(new Intent(HomeActivity.this,PerformanceActivity.class));
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menuhome,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.setting:
                startActivity(new Intent(HomeActivity.this,SettingActivity.class));
                break;
            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                break;
        }
        return true;

    }
}