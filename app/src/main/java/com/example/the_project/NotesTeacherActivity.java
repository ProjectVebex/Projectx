package com.example.the_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.the_project.adapter.NotesTeacherAdapter;
import com.example.the_project.databinding.ActivityNotesTeacherBinding;
import com.example.the_project.model.NotesDetails;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotesTeacherActivity extends AppCompatActivity {
    ActivityNotesTeacherBinding binding;
    ArrayList<NotesDetails> list;
    FirebaseDatabase database;
    FirebaseStorage storage;
    FirebaseAuth auth;
    public static String subjectName;
    public static String subjectId;
    NotesTeacherAdapter adapter;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotesTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = new ArrayList<>();

        Intent intent = getIntent();
        subjectName = intent.getStringExtra("SubjectName");
        subjectId = intent.getStringExtra("SubjectId");

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        adapter = new NotesTeacherAdapter(list ,this);
        binding.IdNotesTeacherRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.IdNotesTeacherRecyclerView.setLayoutManager(layoutManager);

        //adapter.notifyDataSetChanged();
        database.getReference().child(subjectId).child(subjectName).child("2021").child("Notes").
                orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()) {
                    NotesDetails ns = snap.getValue(NotesDetails.class);
                    list.add(ns);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        binding.IdBtnUploadNotesTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 33);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData() != null && requestCode == 33) {
            Uri uri = data.getData();
            List<String> l= data.getData().getPathSegments();
            String fileName = l.get(l.size()-1);

            dialog = new Dialog(NotesTeacherActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setTitle("Uploading "+ fileName);
            dialog.setContentView(R.layout.progress_bar_dialog);
            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

            final StorageReference reference = storage.getReference().child(subjectId).child(subjectName)
                    .child("2021").child("Notes").child(fileName);
            UploadTask uploadTask = reference.putFile(uri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(NotesTeacherActivity.this, "File uploaded successfully",
                            Toast.LENGTH_SHORT).show();
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Date date =  new Date();
                            NotesDetails ns = new NotesDetails(fileName, uri.toString());
                            database.getReference().child(subjectId).child(subjectName)
                                    .child("2021").child("Notes").child(date.toString()).setValue(ns);
                            /*adapter.list = new ArrayList<>();
                            adapter.notifyDataSetChanged();

                            database.getReference().child(subjectId).child(subjectName).
                                        child("2021").child("Notes").orderByValue().addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot snap : snapshot.getChildren()) {
                                        NotesDetails ns = snap.getValue(NotesDetails.class);
                                        adapter.list.add(ns);
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {}
                            });*/

                            Log.d("TAG","  " + list );
                        }
                    });
                }
            });
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    final ProgressBar text = (ProgressBar) dialog.findViewById(R.id.IdProgressBar);
                    final TextView text2 = dialog.findViewById(R.id.IdProgressPercentage);

                    int progress = (int)(100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    System.out.println("Upload is " + progress + "% done");
                    text.setProgress(progress);
                    text2.setText(String.valueOf(progress));
                    if(progress == 100)
                        dialog.dismiss();
                }
            });
        }
    }
}
