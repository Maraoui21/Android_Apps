package com.example.splash_screen;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginBtn = (Button) findViewById(R.id.login);
        Intent intent = new Intent(this, Activity2.class);
        EditText email = findViewById(R.id.Email);
        EditText password = findViewById(R.id.Password);
        loginBtn.setOnClickListener(e->{
            intent.putExtra("email",email.getText().toString());
            intent.putExtra("password",password.getText().toString());
            startActivity(intent);
        });
    }
}
