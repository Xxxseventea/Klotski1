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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button single;
    Button double1;



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
                showpop();
                break;
            }
        }
    }

    public void showpop(){
        View view = LayoutInflater.from(this).inflate(R.layout.popwindow,null);
        final PopupWindow popupWindow = new PopupWindow(view,LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,true);

        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        Button cancel = view.findViewById(R.id.cancel);
        Button comfirm = view.findViewById(R.id.comfirm);
        EditText editText = view.findViewById(R.id.et);
        String s = String.valueOf(editText.getText());
        final boolean a = s.matches("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a){
                    Intent intent = new Intent(MainActivity.this,DoubleActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this,"输入ip地址有误",Toast.LENGTH_SHORT).show();
                }
            }
        });
        View root = LayoutInflater.from(this).inflate(R.layout.activity_main,null);
        popupWindow.showAtLocation(root, Gravity.CENTER,0,0);
    }
}

