package com.example.the_project.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.the_project.NotesActivity;
import com.example.the_project.R;
import com.example.the_project.model.NotesDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URI;
import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    Context context;
    ArrayList <NotesDetails> arrayList;



    public NotesAdapter(ArrayList<NotesDetails> arrayList, Context context) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_notes_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  NotesAdapter.ViewHolder holder, int position) {
        NotesDetails ns=arrayList.get(position);
        holder.subjectName.setText(ns.getSubjectName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesActivity nss=new NotesActivity();
                nss.download(Uri.parse(ns.getUri()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView subjectName;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            subjectName=itemView.findViewById(R.id.subjectName);

        }
    }
}
