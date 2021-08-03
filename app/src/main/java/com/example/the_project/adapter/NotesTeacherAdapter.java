package com.example.the_project.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.the_project.NotesTeacherActivity;
import com.example.the_project.R;
import com.example.the_project.model.NotesDetails;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotesTeacherAdapter extends RecyclerView.Adapter<NotesTeacherAdapter.ViewHolder> {

    public ArrayList<NotesDetails> list;
    Context context;
    static FirebaseDatabase database;

    public NotesTeacherAdapter(ArrayList<NotesDetails> list, Context context)
    {
        this.list = list;
        this.context = context;
    }

    public void setList(ArrayList<NotesDetails> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public NotesTeacherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_notes_teacher,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull NotesTeacherAdapter.ViewHolder holder, int position) {
        NotesDetails notesDetails = list.get(position);
        holder.subjectName.setText(notesDetails.getSubjectName());
        holder.subjectName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (holder.subjectName.getRight() -
                            holder.subjectName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                        new MaterialAlertDialogBuilder(context)
                                .setIcon(R.drawable.ic_baseline_warning_24)
                                .setTitle("Delete File")
                                .setMessage("Delete this file")
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        database = FirebaseDatabase.getInstance();
                                        database.getReference().child(NotesTeacherActivity.subjectId).child(NotesTeacherActivity.subjectName).child("2021").child("Notes").
                                                orderByChild("subjectName").equalTo(notesDetails.getSubjectName()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                database.getReference().child(NotesTeacherActivity.subjectId).child(NotesTeacherActivity.subjectName).child("2021").child("Notes").
                                                        child(snapshot.getValue().toString().substring(1,35)).removeValue();

                                                /*list.clear();
                                                notifyDataSetChanged();

                                                database.getReference().child(NotesTeacherActivity.subjectId).child(NotesTeacherActivity.subjectName).
                                                        child("2021").child("Notes").orderByValue().addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        for(DataSnapshot snap : snapshot.getChildren()) {
                                                            NotesDetails ns = snap.getValue(NotesDetails.class);
                                                            list.add(ns);
                                                        }
                                                        notifyDataSetChanged();
                                                    }
                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {}
                                                });*/
                                            }

                                            @Override public void onCancelled(@NonNull DatabaseError error) {}
                                        });
                                    }
                                }).setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView subjectName;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            subjectName=itemView.findViewById(R.id.IdSubjectNameNotesUpload);
        }
    }
}
