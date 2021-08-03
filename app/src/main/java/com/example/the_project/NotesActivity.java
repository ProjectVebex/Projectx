package com.example.the_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.the_project.adapter.NotesAdapter;
import com.example.the_project.databinding.ActivityNotesBinding;
import com.example.the_project.model.NotesDetails;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    DownloadManager manager;
    ActivityNotesBinding binding;
    ArrayList<NotesDetails> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = new ArrayList<>();

        NotesAdapter adapter = new NotesAdapter(list ,this);
        binding.IdNotesRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.IdNotesRecyclerView.setLayoutManager(layoutManager);
    }

    public void download(Uri uri) {
        manager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        manager.enqueue(request);

    }
}