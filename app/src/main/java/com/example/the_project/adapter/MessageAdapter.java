package com.example.the_project.adapter;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.the_project.ChattingActivity;
import com.example.the_project.R;
import com.example.the_project.model.Messages;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.the_project.R.id.profile_image;
import static com.example.the_project.R.id.txtMessage;

public class MessageAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Messages> messageArrayList;
    int ITEM_SEND=1,ITEM_RECEIVE=2;

    public MessageAdapter(Context context, ArrayList<Messages> messageArrayList, int ITEM_SEND, int ITEM_RECEIVE) {
        this.context = context;
        this.messageArrayList = messageArrayList;
        this.ITEM_SEND = ITEM_SEND;
        this.ITEM_RECEIVE = ITEM_RECEIVE;
    }

    public MessageAdapter(ChattingActivity chattingActivity, ArrayList<Messages> messagesArrayList) {
        this.messageArrayList=messagesArrayList;
        this.context=chattingActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        if(viewType==ITEM_SEND){
            View view=LayoutInflater.from(context).inflate(R.layout.sender_item_layout,parent,false);
            return  new SenderViewHolder(view);
        }else{
            View view=LayoutInflater.from(context).inflate(R.layout.receiver_item_layout,parent,false);
            return  new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position) {
        Messages messages=messageArrayList.get(position);

        if(holder.getClass()==SenderViewHolder.class){
            SenderViewHolder viewHolder=(SenderViewHolder)holder;
            viewHolder.textMsg.setText(messages.getMessage());
            Picasso.get().load(ChattingActivity.sImage).into(viewHolder.circleImageView);
        }else{

            ReceiverViewHolder viewHolder=(ReceiverViewHolder) holder;
            viewHolder.textMsg.setText(messages.getMessage());
            Picasso.get().load(ChattingActivity.rImage).into(viewHolder.circleImageView);
        }

    }


    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    @Override
    public int getItemViewType(int position){
        Messages message=messageArrayList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(message.getSenderid())){
            return ITEM_SEND;
        }else return ITEM_RECEIVE;
    }

    class SenderViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView textMsg;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=circleImageView.findViewById(profile_image);
            textMsg= textMsg.findViewById(txtMessage);
        }
    }

    class ReceiverViewHolder extends  RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView textMsg;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView= circleImageView.findViewById(profile_image);
            textMsg=textMsg.findViewById(txtMessage);
        }
    }

}
