package com.example.the_project;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.the_project.adapter.QuizTeacherAdapter;
import com.example.the_project.databinding.ActivityQuizTeacherBinding;
import com.example.the_project.model.Quiz;
import com.example.the_project.model.QuizObject;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class QuizTeacherActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    ActivityQuizTeacherBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    public static String subjectId, subjectName;
    public static int flag = -1;
    FirebaseStorage storage;
    ArrayList<Quiz> list;
    Dialog dialog;
    QuizTeacherAdapter adapter;
    Quiz quiz;
    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        subjectId = intent.getStringExtra("SubjectId");
        subjectName = intent.getStringExtra("SubjectName");
        list = new ArrayList<>();

        adapter = new QuizTeacherAdapter(list, this);
        binding.IdRecyclerViewQuizTeacher.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.IdRecyclerViewQuizTeacher.setLayoutManager(layoutManager);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        database.getReference().child(subjectId).child(subjectName).child("2021").child("Quiz").child("Quiz Details").
                orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Quiz quiz = snap.getValue(Quiz.class);
                    list.add(quiz);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        binding.IdBtnUploadQuizQuizTeacher.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(QuizTeacherActivity.this, CreateQuizTeacherActivity.class);
                intent1.putExtra("SubjectId", subjectId);
                intent1.putExtra("SubjectName", subjectName);

                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_GET_CONTENT);
                intent2.setType("text/plain");
                dialog = new MaterialAlertDialogBuilder(QuizTeacherActivity.this)
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setTitle("Quiz")
                        .setMessage("Upload a Quiz")
                        .setPositiveButton("Create Quiz", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(intent1);
                            }
                        })
                .setNegativeButton("Upload File", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
                        startActivityForResult(intent, 33);
                    }
                })
                .setNeutralButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        if (data.getData() != null && requestCode == 33) {
            Uri uri = data.getData();
            String selectedFile = data.getData().getPath();
            selectedFile = selectedFile.substring(selectedFile.lastIndexOf(":") + 1);
            try {
                File myFile = new File((Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + selectedFile));
                FileInputStream fis = new FileInputStream(myFile);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line;
                String quizName = br.readLine();
                if (quizName.startsWith("Name") || quizName.startsWith("name")) {
                    quizName = quizName.substring(quizName.lastIndexOf(":") + 1).trim();
                }

                String quizDate = br.readLine();
                if (quizDate.startsWith("Date") || quizDate.startsWith("date")) {
                    quizDate = quizDate.substring(quizDate.lastIndexOf(":") + 1).trim();
                }

                String quizTime = br.readLine();
                if (quizTime.startsWith("Time") || quizTime.startsWith("time")) {
                    quizTime = quizTime.substring(quizTime.indexOf(":") + 1).trim();
                }

                String quizTimePeriod = br.readLine();
                if (quizTimePeriod.startsWith("Time Period") || quizTimePeriod.startsWith("time period")) {
                    quizTimePeriod = quizTimePeriod.substring(quizTimePeriod.lastIndexOf(":") + 1).trim();
                }

                String quizMarks = br.readLine();
                if (quizMarks.startsWith("Marks") || quizMarks.startsWith("marks")) {
                    quizMarks = quizMarks.substring(quizMarks.lastIndexOf(":") + 1).trim();
                }

                ArrayList<QuizObject> quizList = new ArrayList<>();
                QuizObject ref;
                String question;
                String[] option;
                String answer;
                int questionNo = 1;
                while ((line = br.readLine()) != null) {

                    if (line.length() < 1)
                        continue;
                    question = line.trim();
                    option = new String[4];
                    option[0] = br.readLine().trim();
                    option[1] = br.readLine().trim();
                    option[2] = br.readLine().trim();
                    option[3] = br.readLine().trim();
                    answer = br.readLine().trim();

                    ref = new QuizObject(questionNo + "", question, option[0], option[1], option[2], option[3], answer);
                    quizList.add(ref);
                    questionNo++;
                }
                quiz = new Quiz(quizName, quizMarks, quizDate + " " + quizTime, quizTimePeriod);
                database.getReference().child(subjectId).child(subjectName).child("2021").child("Quiz").child("Quiz Details").
                        child(quizName).setValue(quiz);
                database.getReference().child(subjectId).child(subjectName).child("2021").child("Quiz").child("Quiz Questions").
                        child(quizName).setValue(quizList);
                br.close();
            } catch (Exception e) {
                Log.d("Exception", e.getMessage());
                return;
            }

            dialog = new Dialog(QuizTeacherActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setTitle("Uploading " + selectedFile);
            dialog.setContentView(R.layout.progress_bar_dialog);
            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

            final StorageReference reference = storage.getReference().child(subjectId).child(subjectName)
                    .child("2021").child("Quiz");
            UploadTask uploadTask = reference.putFile(uri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(QuizTeacherActivity.this, "File uploaded successfully",
                            Toast.LENGTH_SHORT).show();
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Date date = new Date();
                            adapter.list.clear();
                            adapter.notifyDataSetChanged();

                            database.getReference().child(subjectId).child(subjectName).
                                    child("2021").child("Quiz").child("Quiz Details").orderByValue().addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot snap : snapshot.getChildren()) {
                                        Quiz quiz = snap.getValue(Quiz.class);
                                        adapter.list.add(quiz);
                                    }
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });

                            Log.d("TAG", "  " + list);
                        }
                    });
                }
            });
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    final ProgressBar text = (ProgressBar) dialog.findViewById(R.id.IdProgressBar);
                    final TextView text2 = dialog.findViewById(R.id.IdProgressPercentage);

                    int progress = (int) (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    System.out.println("Upload is " + progress + "% done");
                    text.setProgress(progress);
                    text2.setText(String.valueOf(progress));
                    if (progress == 100) {
                        dialog.dismiss();
                    }
                }
            });
        }
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(QuizTeacherActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(QuizTeacherActivity.this, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(QuizTeacherActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}