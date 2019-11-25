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

import com.example.klotski.socket.Constant;

import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    Button tiaozhan;
    Button beilianjie;

    EditText et_ip_c;
    EditText et_port_c;
    EditText et_port_s;
    int random;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tiaozhan = findViewById(R.id.tiaozhan);
        beilianjie = findViewById(R.id.dengdai);


        random = new Random().nextInt(7);
        tiaozhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.connected = 1;
                showpopwinds();
            }
        });

        beilianjie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.connected = 0;
               showpopwind();
            }
        });
    }


    public void showpopwind(){
        View view = LayoutInflater.from(this).inflate(R.layout.popwindow,null);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,true);

        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        Button cancel = view.findViewById(R.id.cancel_s);
        Button comfirm = view.findViewById(R.id.comfirm_s);
        et_port_s = view.findViewById(R.id.et);
        String s = String.valueOf(et_port_s.getText());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(Main2Activity.this, ServerActivity.class);
                    startActivity(intent);
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
        et_ip_c = view.findViewById(R.id.et_ip);
        et_port_c = view.findViewById(R.id.et_port);
//        String s = String.valueOf(et_port_c.getText());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Main2Activity.this, ClientActivity.class);
                    intent.putExtra("ip",et_ip_c.getText().toString());
                    startActivity(intent);

            }
        });
        View root = LayoutInflater.from(this).inflate(R.layout.activity_main,null);
        popupWindow.showAtLocation(root, Gravity.CENTER,0,0);
    }
}
