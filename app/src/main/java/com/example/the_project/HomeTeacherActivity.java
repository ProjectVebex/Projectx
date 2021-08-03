package com.example.the_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.the_project.databinding.ActivityHomeBinding;
import com.example.the_project.databinding.ActivityHomeTeacherBinding;
import com.example.the_project.model.ModelClass;
import com.google.firebase.auth.FirebaseAuth;

public class HomeTeacherActivity extends AppCompatActivity {
    ActivityHomeTeacherBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityHomeTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.chatbtn.setOnClickListener(v->{
            Intent intent = new Intent(HomeTeacherActivity.this, ChattingHomeActivity.class);
            startActivity(intent);
        });

        binding.notesbtn.setOnClickListener(v->{
            Intent intent = new Intent(HomeTeacherActivity.this, ClassTeacherActivity.class);
            intent.putExtra("Activity", ModelClass.NOTES_TEACHER_ACTIVITY);
            startActivity(intent);
        });

        binding.noticebtn.setOnClickListener(v->{
            Intent intent = new Intent(HomeTeacherActivity.this, ClassTeacherActivity.class);
            intent.putExtra("Activity", ModelClass.NOTICE_TEACHER_ACTIVITY);
            startActivity(intent);
        });

        binding.assignbtn.setOnClickListener(v->{
            Intent intent = new Intent(HomeTeacherActivity.this, ClassTeacherActivity.class);
            intent.putExtra("Activity", ModelClass.ASSIGNMENT_TEACHER_ACTIVITY);
            startActivity(intent);
        });

        binding.quizbtn.setOnClickListener(v->{
            Intent intent = new Intent(HomeTeacherActivity.this, ClassTeacherActivity.class);
            intent.putExtra("Activity", ModelClass.QUIZ_TEACHER_ACTIVITY);
            startActivity(intent);
        });

        binding.performbtn.setOnClickListener(v->{
            Intent intent = new Intent(HomeTeacherActivity.this, ClassTeacherActivity.class);
            intent.putExtra("Activity", ModelClass.PERFORMANCE_TEACHER_ACTIVITY);
            startActivity(intent);
        });
    }
}