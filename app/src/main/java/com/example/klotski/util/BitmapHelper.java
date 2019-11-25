package com.example.klotski.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.klotski.bean.Picture;

public class BitmapHelper {

    /**
     * 单例模式
     */
    public static volatile BitmapHelper bitmapHelper;
    public BitmapHelper(){

    }

    public static BitmapHelper getInstance(){
        if(bitmapHelper == null){
            synchronized (BitmapHelper.class){
                if(bitmapHelper == null){
                    bitmapHelper = new BitmapHelper();
                }
            }
        }
        return bitmapHelper;
    }

    /**
     * 获取屏幕的宽度
     *
     * @return
     */
    private int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 缩放图片
     * @param context
     * @param
     * @return
     */
    public Bitmap getBitmap(Context context, int id){


        Log.d("TAG","BITMAP");

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        //按屏幕宽铺满显示，算出缩放比例
        int screenWidth = getScreenWidth(context);
        float scale = 1.0f;
        if (screenWidth < bitmapWidth) {
            scale = screenWidth * 1.0f / bitmapWidth;  //同比例缩放高
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, false);
        return bitmap;
    }

    /**
     * 判断是否在空图片旁边，即是否可滑动
     * @param imageView
     * @param emptyPicture
     * @return
     */
    public boolean isNearEmptyPicture(ImageView imageView, ImageView emptyPicture){
        Picture image = (Picture) imageView.getTag();
        Picture empty = (Picture) emptyPicture.getTag();
        if(image != null && empty != null){
            //下边
            if(image.getOriginalX() == empty.getOriginalX() && image.getOriginalY() + 1 == empty.getOriginalY())
                return true;
            //上面
            if(image.getOriginalX() == empty.getOriginalX() && image.getOriginalY() - 1 == empty.getOriginalY())
                return true;
            //左边
            if(image.getOriginalX() + 1 == empty.getOriginalX() && image.getOriginalY()  == empty.getOriginalY())
                return true;
            //右边
            if(image.getOriginalX() - 1 == empty.getOriginalX() && image.getOriginalY()  == empty.getOriginalY())
                return true;
        }
        return false;
    }

    /**
     * 判断游戏是否结束
     * @param imageViews
     * @param emptyView
     * @return
     */
    public boolean isFinish(ImageView[][] imageViews,ImageView emptyView){
        int count = 0;
        for(int i = 0;i<imageViews.length;i++){
            for(int j = 0;j<imageViews[0].length;j++) {
                if (imageViews[i][j] != emptyView) {
                    Picture picture = (Picture) imageViews[i][j].getTag();
                    if (picture != null) {
                        if(picture.getOriginalX() == picture.getCurrentX() && picture.getOriginalY() == picture.getCurrentY())
                            count++;

                    }
                }
            }
        }
        if(count == imageViews.length * imageViews[0].length - 1)
            return true;
        return false;
    }

}