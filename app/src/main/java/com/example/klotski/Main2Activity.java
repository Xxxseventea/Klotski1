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

public class Main2Activity extends AppCompatActivity {
    Button tiaozhan;
    Button beilianjie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tiaozhan = findViewById(R.id.tiaozhan);
        beilianjie = findViewById(R.id.dengdai);

        tiaozhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpopwinds();
            }
        });

        beilianjie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showpopwind();
            }
        });
    }


    public void showpopwind(){
        View view = LayoutInflater.from(this).inflate(R.layout.popwindow,null);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,true);

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
                    Intent intent = new Intent(Main2Activity.this,DoubleActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Main2Activity.this,"输入ip地址有误",Toast.LENGTH_SHORT).show();
                }
            }
        });
        View root = LayoutInflater.from(this).inflate(R.layout.activity_main,null);
        popupWindow.showAtLocation(root, Gravity.CENTER,0,0);
    }
    public void showpopwinds(){

        View view = LayoutInflater.from(this).inflate(R.layout.popwindows,null);
        final PopupWindow popupWindow = new PopupWindow(view,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,true);

        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        Button cancel = view.findViewById(R.id.cancel);
        Button comfirm = view.findViewById(R.id.comfirm);
        EditText editText = view.findViewById(R.id.et);
        EditText editText1 = view.findViewById(R.id.et_ip);
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
                    Intent intent = new Intent(Main2Activity.this,DoubleActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Main2Activity.this,"输入ip地址有误",Toast.LENGTH_SHORT).show();
                }
            }
        });
        View root = LayoutInflater.from(this).inflate(R.layout.activity_main,null);
        popupWindow.showAtLocation(root, Gravity.CENTER,0,0);
    }
}
