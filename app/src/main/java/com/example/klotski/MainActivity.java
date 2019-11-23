package com.example.klotski;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
   public Button single;
   public Button double1;
   RelativeLayout relativeLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        single.setOnClickListener(this);
        double1.setOnClickListener(this);
    }

    public void init(){
        single = findViewById(R.id.single);
        double1 = findViewById(R.id.double1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.single:{
                Intent intent = new Intent(MainActivity.this, SingleActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.double1:{
               Intent intent = new Intent(MainActivity.this,Main2Activity.class);
               startActivity(intent);
                break;
            }
        }
    }

}

