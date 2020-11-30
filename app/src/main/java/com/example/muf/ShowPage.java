package com.example.muf;
//Author; Guangyu Wang
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ShowPage extends AppCompatActivity  {

    private Button account;
    private Button makeup;
    private Button logout;
    private Button takenote;
    private Button match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pages);

        takenote=(Button) findViewById(R.id.takenote);
        takenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowPage.this,Images.class));
            }
        });

        account=(Button) findViewById(R.id.account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowPage.this,ProfileActivity.class));
            }
        });

        makeup=(Button) findViewById(R.id.makeup);
        makeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowPage.this,Upload.class));
            }
        });



        logout=(Button) findViewById(R.id.singOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ShowPage.this,MainActivity.class));
            }
        });

        match=(Button) findViewById(R.id.match);
        match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowPage.this,Match.class));
            }
        });

    }


}