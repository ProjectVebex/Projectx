package com.example.the_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.the_project.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity{
    ActivitySettingBinding binding;
    ImageView imageView;
    EditText Name;
    EditText password;
    EditText cpassword;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imageView=findViewById(R.id.profile_image);
        Name=findViewById(R.id.Name);
        password=findViewById(R.id.Password);
        cpassword=findViewById(R.id.cPassword);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i,"Select image"),10);

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==10){
                Uri img=data.getData();
                imageView.setImageURI(img);
            }
    }
}