package com.example.the_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.the_project.ChattingActivity;
import com.example.the_project.R;
import com.example.the_project.model.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context chattingHomeActivity;
    ArrayList<User> userArrayList;



    public UserAdapter(Context chattingHomeActivity, ArrayList<User> userArrayList) {
        this.chattingHomeActivity = chattingHomeActivity;
        this.userArrayList = userArrayList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(chattingHomeActivity).inflate(R.layout.chatting_item_user_row,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User user=userArrayList.get(position);
        holder.name.setText(user.getName());
        holder.status.setText("Jai Hind!");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(chattingHomeActivity, ChattingActivity.class);
                intent.putExtra("Name ",user.getName());
                intent.putExtra("Receiver Image",user.getProfile_pic());
                intent.putExtra("uid",user.getUid());
                chattingHomeActivity.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() { return userArrayList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView profile;
        TextView name;
        TextView status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile=itemView.findViewById(R.id.chattingHomeUserProfile);
            name=itemView.findViewById(R.id.chattingHomeUserName);
            status=itemView.findViewById(R.id.chattingHomeUserStatus);
        }
    }
}
