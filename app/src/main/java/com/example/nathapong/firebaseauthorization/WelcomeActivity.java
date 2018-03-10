package com.example.nathapong.firebaseauthorization;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    private TextView txtWelcome;
    private Button btnSignOut;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        txtWelcome = (TextView)findViewById(R.id.txtWelcome);
        btnSignOut = (Button)findViewById(R.id.btnSignOut);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null){

            Intent mainActivityIntent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(mainActivityIntent);
            finish();
        }

        if (firebaseAuth.getCurrentUser() != null){

            txtWelcome.setText("Welcome ! - " + firebaseAuth.getCurrentUser().getDisplayName());
        }

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();

                Intent mainActivityIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);
                finish();
            }
        });
    }
}
