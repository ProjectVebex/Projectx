package com.example.the_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.the_project.adapter.QuizAdapter;
import com.example.the_project.databinding.ActivityQuizBinding;
import com.example.the_project.model.QuizObject;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
    ActivityQuizBinding binding;
    ArrayList<QuizObject> list;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());

        QuizAdapter adapter = new QuizAdapter(list ,this);
        binding.IdQuizRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.IdQuizRecyclerView.setLayoutManager(layoutManager);

        adapter.notifyDataSetChanged();
    }
}