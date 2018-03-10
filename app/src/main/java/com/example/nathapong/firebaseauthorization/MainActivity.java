package com.example.nathapong.firebaseauthorization;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {

    private EditText edtEnterName, edtEnterEmail, edtEnterPassword;
    private Button btnSignIn, btnSignUp;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEnterName = (EditText)findViewById(R.id.edtEnterName);
        edtEnterEmail = (EditText)findViewById(R.id.edtEnterEmail);
        edtEnterPassword = (EditText)findViewById(R.id.edtEnterPassword);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){

            Intent welcomeIntent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(welcomeIntent);
            finish();
        }


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = edtEnterEmail.getText().toString();
                String userPassword = edtEnterPassword.getText().toString();

                signUpUserWithEmailAndPassword(userEmail, userPassword);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = edtEnterEmail.getText().toString();
                String userPassword = edtEnterPassword.getText().toString();

                signInTheUserWithEmailAndPassword(userEmail, userPassword);
            }
        });


    }

    private void signUpUserWithEmailAndPassword(String userEmail, String userPassword){

        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()){
                    Toast.makeText(MainActivity.this,
                            "There was a problem. Try again.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,
                            "You are successfully signed up.", Toast.LENGTH_SHORT).show();

                    specifyUserProfile();
                }
            }
        });

    }


    private void specifyUserProfile(){

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null){

            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest
                    .Builder().setDisplayName(edtEnterName.getText().toString()).build();

            firebaseUser.updateProfile(userProfileChangeRequest)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){

                    }
                    else {

                    }
                }
            });
        }
    }


    private void signInTheUserWithEmailAndPassword(String userEmail, String userPassword){

        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()){
                    Toast.makeText(MainActivity.this,
                            "There was an error!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent welComeIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                    startActivity(welComeIntent);
                    finish();
                }
            }
        });
    }
}
