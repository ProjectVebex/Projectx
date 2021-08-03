package com.example.the_project.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.the_project.QuizTeacherActivity;
import com.example.the_project.R;
import com.example.the_project.model.Quiz;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class QuizTeacherAdapter extends RecyclerView.Adapter<QuizTeacherAdapter.ViewHolder>{
    public ArrayList<Quiz> list;
    Context context;
    static FirebaseDatabase database;

    public QuizTeacherAdapter(ArrayList<Quiz> list, Context context)
    {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public QuizTeacherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_quiz_teacher,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizTeacherAdapter.ViewHolder holder, int position) {
        Quiz quiz = list.get(position);
        holder.quizName.setText(quiz.getName());
        holder.quizMarks.setText(quiz.getMarks());
        holder.quizDateTime.setText(quiz.getDateAndTime());
        holder.quizTime.setText(quiz.getQuizTime());

        holder.quizDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(context)
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setTitle("Delete Quiz")
                        .setMessage("Delete this Quiz")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                database = FirebaseDatabase.getInstance();
                                database.getReference().child(QuizTeacherActivity.subjectId).child(QuizTeacherActivity.subjectName).child("2021").child("Quiz").child("Quiz Details").
                                        child(quiz.getName()).removeValue();
                                database.getReference().child(QuizTeacherActivity.subjectId).child(QuizTeacherActivity.subjectName).child("2021").child("Quiz").child("Quiz Questions").
                                        child(quiz.getName()).removeValue();
                                list.clear();
                                notifyDataSetChanged();

                                database.getReference().child(QuizTeacherActivity.subjectId).child(QuizTeacherActivity.subjectName).child("2021").child("Quiz").child("Quiz Details").
                                        orderByValue().addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot snap : snapshot.getChildren()) {
                                            Quiz quiz = snap.getValue(Quiz.class);
                                            list.add(quiz);
                                        }
                                        notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {}
                                });
                            }
                        }).setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView quizName, quizMarks, quizDateTime, quizTime;
        ImageView quizDelete;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            quizName = itemView.findViewById(R.id.IdQuizName);
            quizMarks = itemView.findViewById(R.id.IdQuizMarks);
            quizDateTime = itemView.findViewById(R.id.IdQuizDateTime);
            quizTime = itemView.findViewById(R.id.IdQuizTime);
            quizDelete = itemView.findViewById(R.id.IdQuizDelete);
        }
    }
}
