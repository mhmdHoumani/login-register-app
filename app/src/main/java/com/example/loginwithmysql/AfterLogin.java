package com.example.loginwithmysql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AfterLogin extends AppCompatActivity {

    private TextView txt;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        txt = findViewById(R.id.txtWelcome);
        Intent intent = getIntent();
        String username = intent.getStringExtra("user");
        txt.setText("Welcome back " + username);
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(this,
                "You need to logout to go back",
                Toast.LENGTH_LONG).show();
    }

    public void logout(View view){
        setResult(RESULT_OK);
        finish();
    }
}