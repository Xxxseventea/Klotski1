package com.example.klotski;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.klotski.R;
import com.example.klotski.bean.Picture;
import com.example.klotski.server.SocketServer;
import com.example.klotski.util.BitmapHelper;
import com.example.klotski.util.GestureHelper;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServerActivity extends AppCompatActivity {


    private GridLayout gridLayout;

    private ImageView[][] imageViews = new ImageView[4][4];

    private ImageView emptyImage;

    private GestureDetector gestureDetector;

    //动画是否在执行，避免快速点击导致动画重复执行
    private boolean isAnimated;

    int position = 0;
    Bitmap bitmap;


    int count = 0;
    boolean a = false;



    TextView textView;

    TextView textView1;

    Chronometer chronometer;

    SocketServer socketServer;

    int[] id = {R.drawable.picture1,R.drawable.picture2,R.drawable.picture3,R.drawable.picture4,R.drawable.picture5,R.drawable.picture6,R.drawable.picture7,R.mipmap.chongyou};

    //handler更新ui
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    textView.setText(String.valueOf(count));
                    socketServer.sendMessage(String.valueOf(count));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double);
        gridLayout = findViewById(R.id.gridlayout);
        textView = findViewById(R.id.wode);
        textView1 = findViewById(R.id.testttt);
        Button start = findViewById(R.id.start);
        Button button = findViewById(R.id.touxiang);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ServerActivity.this,"你输了！",Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        gridLayout.setVisibility(View.INVISIBLE);

        socketServer = new SocketServer(6666);
        socketServer.beginListen();


        SocketServer.ServerHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                textView1.setText(msg.obj.toString());
            }
        };

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gridLayout.setVisibility(View.VISIBLE);
            }
        });
        /**
         * 手势问题
         */
        gestureDetector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                position = GestureHelper.getInstance().getPosition(e1,e2);
                handleFlingGesture(position,true);
                return true;
            }
        });

        /**
         * 图片初始化
         */


        /**
         * handler来处理ui
         */
        int random = (int) (Math.random() % 8);
        bitmap = BitmapHelper.getInstance().getBitmap(ServerActivity.this,id[new Random().nextInt(7)]);

        initPicture(bitmap);

        /**
         * 随机数
         */

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                random();
            }
        },200);

    }

    /**
     * 随机函数
     */
    private void random(){

        for(int i = 0;i<100;i++) {
            int p = (int) (Math.random() * 4 + 1);

            handleFlingGesture(p,isAnimated);
        }


    }

    /**
     * 第三步，分割图片和初始化图片
     * @param bitmap
     */
    private void initPicture(Bitmap bitmap){
        int itemWidth = bitmap.getWidth() / 4;
        int itemHeight = bitmap.getHeight() / 4;

        //切割16张存入gridlayout里
        for (int i = 0; i < imageViews.length; i++) {
            for (int j = 0; j < imageViews[0].length; j++) {
                //初始化小bitmap
                Bitmap bitmap1 = Bitmap.createBitmap(bitmap, j * itemWidth, i * itemHeight, itemWidth, itemHeight);
                ImageView imageView = new ImageView(this);
                imageView.setImageBitmap(bitmap1);
                imageView.setPadding(2, 2, 2, 2);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //点击事件触发动画函数
                        //判断是否在白块旁边，必须在白块旁边才能移动
                        if (BitmapHelper.getInstance().isNearEmptyPicture((ImageView) v, emptyImage)) {


                            handleClickItem((ImageView) v, true);
                        }
                    }
                });

                //设置子图片的位置数据
                imageView.setTag(new Picture(i, j, bitmap1));
                //添加子图片在gridlayout里
                imageViews[i][j] = imageView;
                gridLayout.addView(imageView);
            }
        }

        //将最后一张图设置为空图片
        ImageView imageView = (ImageView) gridLayout.getChildAt(gridLayout.getChildCount() - 1);
        imageView.setImageBitmap(null);
        emptyImage = imageView;
    }


    /**
     * 第一步，写好改变位置的函数
     * @param imageView
     */
    public void change(ImageView imageView){

        Picture picture = (Picture) imageView.getTag();
        Picture empty = (Picture) emptyImage.getTag();

        //点击时改变位置，白的设置为图片，图片设置为白的
        emptyImage.setImageBitmap(picture.getBitmap());
        imageView.setImageBitmap(null);

        //位置数据更新

        empty.setCurrentX(picture.getCurrentX());
        empty.setCurrentY(picture.getCurrentY());
        empty.setBitmap(picture.getBitmap());

        emptyImage = imageView;
    }

    //处理点击拼图的移动事件

    /**
     * 第二步，处理动画
     * @param imageView
     */
    private void handleClickItem(final ImageView imageView){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(!isAnimated){
                    TranslateAnimation translateAnimation = null;
                    //右往左
                    if(imageView.getX() > emptyImage.getX()){

                        translateAnimation = new TranslateAnimation(0,-imageView.getWidth(),0,0);
                    }
                    //左往右
                    if(imageView.getX() < emptyImage.getX()){
                        translateAnimation = new TranslateAnimation(0,imageView.getWidth(),0,0);
                    }
                    //上往下
                    if(imageView.getY() > emptyImage.getY()){
                        translateAnimation = new TranslateAnimation(0,0,0,-imageView.getHeight());
                    }

                    //下往上
                    if(imageView.getY() < emptyImage.getY()){
                        translateAnimation = new TranslateAnimation(0,0,0,imageView.getHeight());
                    }

                    if(translateAnimation != null){
                        translateAnimation.setDuration(80);  //动画移动时长
                        translateAnimation.setFillAfter(true); //停止时留在最后一帧，否则回到原位
                        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                //这时正在移动中，点击其他的图片就不能移动鸟
                                isAnimated = true;
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {

                                //移动结束后清除动画
                                isAnimated = false;
                                imageView.clearAnimation(); //停止动画

                                //交换数据
                                change(imageView);
                                //每次移动完后都要判断是否完成
                                boolean isFinish = BitmapHelper.getInstance().isFinish(imageViews,emptyImage);
                                if(isFinish){
                                    Toast.makeText(ServerActivity.this,"恭喜你完成了！",Toast.LENGTH_SHORT).show();
                                    chronometer.stop();
                                }
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                    imageView.startAnimation(translateAnimation);
                    count++;
                    //count加完后发送消息

                    Message message = new Message();
                    message.arg1 = 0;
                    handler.sendMessage(message);
                }
            }
        }).start();

    }


    /**
     * 第五步，判断手势的方向
     * @param position
     * @param animation
     */

    public void handleFlingGesture(int position,boolean animation){
        ImageView imageView = null;
        Picture emptyImageTag = (Picture) emptyImage.getTag();
        switch (position){
            //手指向左滑动，即图片在白块的右边,以下同理
            case GestureHelper.LEFT:{
                if(emptyImageTag.getOriginalY() + 1<= gridLayout.getColumnCount() - 1){
                    imageView = imageViews[emptyImageTag.getOriginalX()][emptyImageTag.getOriginalY() + 1];
                }
                break;
            }
            case GestureHelper.RIGHT:{
                if(emptyImageTag.getOriginalY() -1 >= 0){
                    imageView = imageViews[emptyImageTag.getOriginalX()][emptyImageTag.getOriginalY() -1];
                }
                break;
            }

            case GestureHelper.UP:{
                if(emptyImageTag.getOriginalX() + 1 <= gridLayout.getRowCount() -1){
                    imageView = imageViews[emptyImageTag.getOriginalX() + 1][emptyImageTag.getOriginalY()];
                }
                break;
            }
            case GestureHelper.DOWN:{
                if(emptyImageTag.getOriginalX()- 1 >= 0){

                    imageView = imageViews[emptyImageTag.getOriginalX() - 1][emptyImageTag.getOriginalY()];
                }
                break;
            }
            default:break;
        }
        if(imageView != null){
            handleClickItem(imageView,animation);
        }


    }

    /**
     * 第四步
     * @param imageView
     * @param animation
     */

    public void handleClickItem(final ImageView imageView,boolean animation){
        if(animation){
            handleClickItem(imageView);
        }else {
            change(imageView);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}

