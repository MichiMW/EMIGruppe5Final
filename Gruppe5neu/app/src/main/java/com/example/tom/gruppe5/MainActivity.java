package com.example.tom.gruppe5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button  btnTextEditor,
                    btnSprachMemo,
                    btnSavedDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeApp();
    }

    private void InitializeApp(){

        btnSprachMemo = (Button)findViewById(R.id.btnSprachMemo);
        btnTextEditor = (Button)findViewById(R.id.btnTextEditor);


        btnTextEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent A = new Intent(MainActivity.this, TextEditor.class);
                startActivity(A);

            }
        });

        btnSprachMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent A = new Intent(MainActivity.this, SprachMemos.class);
                startActivity(A);
            }
        });
    }

}
