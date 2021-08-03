package com.example.the_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.the_project.databinding.ActivityCreateQuizTeacherBinding;

public class CreateQuizTeacherActivity extends AppCompatActivity {
    ActivityCreateQuizTeacherBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateQuizTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}