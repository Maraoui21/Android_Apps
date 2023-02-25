package com.example.splash_screen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        TextView emailTxt = findViewById(R.id.Email);
        TextView passwordTxt = findViewById(R.id.Password);
        emailTxt.setText(email);
        passwordTxt.setText(password);
    }
}
