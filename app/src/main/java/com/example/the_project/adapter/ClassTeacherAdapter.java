package com.example.the_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.the_project.AssignmentTeacherActivity;
import com.example.the_project.ChattingHomeActivity;
import com.example.the_project.ChattingTeacherActivity;
import com.example.the_project.ClassTeacherActivity;
import com.example.the_project.NotesTeacherActivity;
import com.example.the_project.NoticeTeacherActivity;
import com.example.the_project.PerformanceTeacherActivity;
import com.example.the_project.QuizTeacherActivity;
import com.example.the_project.R;

import java.util.ArrayList;

public class ClassTeacherAdapter extends RecyclerView.Adapter<ClassTeacherAdapter.ViewHolder>{
    ArrayList<String> listSubjectName;
    ArrayList<String> listSubjectId;
    Context context;

    public ClassTeacherAdapter(ArrayList<String> listSubjectName, ArrayList<String> listSubjectId, Context context)
    {
        this.listSubjectId = listSubjectId;
        this.listSubjectName = listSubjectName;
        this.context = context;
    }


    @NonNull
    @Override
    public ClassTeacherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_class_teacher,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassTeacherAdapter.ViewHolder holder, int position) {
        String subjectName = listSubjectName.get(position);
        String subjectId = listSubjectId.get(position);
        holder.subjectName.setText(subjectName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (ClassTeacherActivity.ACTIVITY)
                {
                    case 1:
                        intent = new Intent(context, ChattingHomeActivity.class);
                        intent.putExtra("SubjectName", subjectName);
                        intent.putExtra("SubjectId",subjectId);
                        context.startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(context, NotesTeacherActivity.class);
                        intent.putExtra("SubjectName", subjectName);
                        intent.putExtra("SubjectId",subjectId);
                        context.startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(context, NoticeTeacherActivity.class);
                        intent.putExtra("SubjectName", subjectName);
                        intent.putExtra("SubjectId",subjectId);
                        context.startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(context, QuizTeacherActivity.class);
                        intent.putExtra("SubjectName", subjectName);
                        intent.putExtra("SubjectId",subjectId);
                        context.startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(context, AssignmentTeacherActivity.class);
                        intent.putExtra("SubjectName", subjectName);
                        intent.putExtra("SubjectId",subjectId);
                        context.startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(context, PerformanceTeacherActivity.class);
                        intent.putExtra("SubjectName", subjectName);
                        intent.putExtra("SubjectId",subjectId);
                        context.startActivity(intent);
                        break;
                    default:
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSubjectName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView subjectName;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            subjectName=itemView.findViewById(R.id.IdSubjectNameClassTeacher);
        }
    }
}
