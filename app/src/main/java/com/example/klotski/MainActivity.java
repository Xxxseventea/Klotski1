package com.example.klotski;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button single;
    Button double1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.single:{
                        Intent intent = new Intent(MainActivity.this, SingleActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public void init(){
        single = findViewById(R.id.single);
        double1 = findViewById(R.id.double1);

    }
}

