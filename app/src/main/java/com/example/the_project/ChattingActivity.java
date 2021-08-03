package com.example.the_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.the_project.adapter.MessageAdapter;
import com.example.the_project.model.Messages;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChattingActivity extends AppCompatActivity {

    String ReceiverImage,ReceiverUid,ReceiverName,senderUid;
    CircleImageView profileImage;
    TextView receiverName;
    FirebaseDatabase database;
    FirebaseAuth auth;
    public static String sImage;
    public static String rImage;
    ImageView sentBtn;
    EditText editMessage;
    String senderRoom,receiveRoom;
    RecyclerView messageAdapter;
    ArrayList<Messages> messagesArrayList;
    MessageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        ReceiverImage=getIntent().getStringExtra("Receiver Image");
        ReceiverName=getIntent().getStringExtra("User Name");
        ReceiverUid=getIntent().getStringExtra("uid");
        auth=FirebaseAuth.getInstance();
        senderUid= auth.getUid();
        senderRoom=senderUid+ReceiverUid;
        receiveRoom=ReceiverUid+senderUid;
        messageAdapter=findViewById(R.id.messageAdapter);
        messagesArrayList=new ArrayList<>();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageAdapter.setLayoutManager(linearLayoutManager);
        adapter=new MessageAdapter(ChattingActivity.this,messagesArrayList);
        messageAdapter.setAdapter(adapter);


        profileImage=findViewById(R.id.profile_image);
        receiverName=findViewById(R.id.receiverName);
        receiverName.setText(ReceiverName.toString());



        Picasso.get().load(ReceiverImage).into(profileImage);


        database=FirebaseDatabase.getInstance();



        DatabaseReference reference= database.getReference().child("User").child(Objects.requireNonNull(auth.getUid()));
        DatabaseReference chatReference= database.getReference().child("chats").child(senderRoom).child("Messages");



        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Messages messages=dataSnapshot.getValue(Messages.class);
                    messagesArrayList.add(messages);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                sImage=snapshot.child("imageUri").getValue().toString();
                rImage=ReceiverImage;
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });



        sentBtn=(ImageView) findViewById(R.id.sendBtn);
        editMessage = (EditText) findViewById(R.id.edittextMessage);



        sentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message= editMessage.getText().toString();
                if(message.isEmpty()){
                    Toast.makeText(ChattingActivity.this,"Please Enter Valid Message",Toast.LENGTH_SHORT).show();
                    return;
                }

                editMessage.setText("");
                Date date=new Date();


                Messages messages=new Messages(message,senderUid, date.getTime());

                database=FirebaseDatabase.getInstance();
                database.getReference().child("chats")
                        .child(senderRoom)
                        .child("Messages")
                        .push()
                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        database.getReference().child("chats")
                                .child(receiveRoom)
                                .child("Messages")
                                .push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull  Task<Void> task) {

                            }
                        });
                    }
                });
            }
        });



    }
}