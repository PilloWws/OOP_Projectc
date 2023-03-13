package com.example.oop_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.oop_project.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait!");
        progressDialog.setCanceledOnTouchOutside(false);

        // go Register when click
        binding.goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        User user = new User();
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData(user);
            }
        });
    }
    private  void getData(User user){
        String email = "", password = "";
        email = binding.email.getText().toString().trim();
        password = binding.password.getText().toString().trim();
        user.setEmail(email);
        user.setPassword(password);
    }
    private void validateData(User user) {
        getData(user);
        if (!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            Toast.makeText(this, "Invalid email...!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(user.getPassword())) {
            Toast.makeText(this, "Enter password...!", Toast.LENGTH_SHORT).show();
        }else{
            loginUser(user);
        }
    }

    private void loginUser(User user) {
        //
        progressDialog.setMessage("Loggin In...");
        progressDialog.show();

        //login user
        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        checkUser();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, ""+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void checkUser(){
        progressDialog.setMessage("Checking user...");

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        // check in db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // get User type
                        String userType = ""+snapshot.child("accountType").getValue();
                        Log.i("test", userType);
                        if(userType.equals("user")){
                            startActivity(new Intent(LoginActivity.this, DashboardUserActivity.class));
                            finish();
                        }else if(userType.equals("admin")){
                            startActivity(new Intent(LoginActivity.this, DashboardAdminActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}