package com.example.the_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.the_project.R;
import com.example.the_project.model.QuizObject;

import java.util.ArrayList;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder>{

    ArrayList<QuizObject> list;
    Context context ;
    public QuizAdapter(ArrayList<QuizObject>list, Context context)
    {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public QuizAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_quiz_object, parent ,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizAdapter.ViewHolder holder, int position) {
        QuizObject quizObject =list.get(position);

        holder.tvQuestionNo.setText(quizObject.getQuestionNo());
        holder.tvQuestion.setText(quizObject.getQuestion());
        holder.tvOption1.setText(quizObject.getOption1());
        holder.tvOption2.setText(quizObject.getOption2());
        holder.tvOption3.setText(quizObject.getOption3());
        holder.tvOption4.setText(quizObject.getOption4());

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestionNo, tvQuestion;
        RadioButton tvOption1, tvOption2, tvOption3, tvOption4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvQuestionNo = itemView.findViewById(R.id.IdQuestionNo);
            tvQuestion = itemView.findViewById(R.id.IdQuestion);
            tvOption1 = itemView.findViewById(R.id.IdOption1);
            tvOption2 = itemView.findViewById(R.id.IdOption2);
            tvOption3 = itemView.findViewById(R.id.IdOption3);
            tvOption4 = itemView.findViewById(R.id.IdOption4);

        }
    }
}
