package com.example.the_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.the_project.databinding.ActivityRegisterBinding;
import com.example.the_project.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    static boolean flag = false;
    ProgressDialog progressDialog;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("New Account");
        progressDialog.setMessage("Creating new Account");

        binding.SignInText.setOnClickListener(v ->{
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        });

        binding.IdRegisterButton.setOnClickListener(v -> {
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            if(binding.IdREmail.getText().toString().equals("")) {
                binding.IdREmail.setError("Enter Email");
                progressDialog.hide();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                return;
            }
            if(binding.IdRName.getText().toString().equals("")) {
                binding.IdRName.setError("Enter Name");
                progressDialog.hide();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                return;
            }
            if(binding.IdRPassword.getText().toString().equals("")) {
                binding.IdRPassword.setError("Enter password");
                progressDialog.hide();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                return;
            }
            if(!(checkEmail() && checkPassword())){
                progressDialog.hide();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                return;
            }
            if(!(binding.IdRPassword.getText().toString().equals(binding.IdRConfirmPassword.getText().toString()))) {
                binding.IdRConfirmPassword.setError("Password not Matched");
                progressDialog.hide();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                return;
            }
            auth.createUserWithEmailAndPassword(
                    binding.IdREmail.getText().toString(),binding.IdRPassword.getText().toString()).
                    addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            progressDialog.setMessage("Account Created Successfully");
                            User user = new User(binding.IdRName.getText().toString(),binding.IdREmail.getText().toString(),
                                    binding.IdRPassword.getText().toString(),"");
                            String uid = task.getResult().getUser().getUid();
                            user.setUid(uid);
                            database.getReference().child("User").child(uid).setValue(user);
                            Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }
                    });
        });

        binding.IdRPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (binding.IdRPassword.getRight() -
                        binding.IdRPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                    if(!flag) {
                        binding.IdRPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        binding.IdRPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,
                                R.drawable.ic_hide_password,0);
                    }
                    else {
                        binding.IdRPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        binding.IdRPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                                R.drawable.ic_show_password, 0);
                    }
                    flag = !flag;
                    return true;
                }
            }
            return false;
        });
    }

    protected  boolean checkEmail() {
        String email = new String(binding.IdREmail.getText().toString());
        int index = email.indexOf("@");
        if(index == -1) {
            binding.IdREmail.setError("Enter valid email");
            return false;
        }
        email = email.substring(index+1);
        if(!email.equals("walchandsangli.ac.in"))
        {
            binding.IdREmail.setError("Enter valid email");
            return false;
        }
        return true;
    }

    protected  boolean checkPassword() {
        String password = binding.IdRPassword.getText().toString();
        int iPasswordScore = 0;

        if( password.length() < 8 ) {
            binding.IdRPassword.setError("must be greater than 8 digits");
            return false;
        }
        else
            iPasswordScore += 2;

        //if it contains one digit, add 2 to total score
        if( password.matches("(?=.*[0-9]).*") )
            iPasswordScore += 2;

        //if it contains one lower case letter, add 2 to total score
        if( password.matches("(?=.*[a-z]).*") )
            iPasswordScore += 2;

        //if it contains one upper case letter, add 2 to total score
        if( password.matches("(?=.*[A-Z]).*") )
            iPasswordScore += 2;

        //if it contains one special character, add 2 to total score
        if( password.matches("(?=.*[~!@#$%^&*()_-]).*") )
            iPasswordScore += 2;

        if(iPasswordScore < 10)
        {
            binding.IdRPassword.setError("must contain aA-zZ, 0-9, ~!@#$%^&*()_-");
            return  false;
        }
        return  true;
    }
}