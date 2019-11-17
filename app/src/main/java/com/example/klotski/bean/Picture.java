package com.example.klotski.bean;

import android.graphics.Bitmap;

public class Picture {
    private int originalX;
    private int originalY;
    private Bitmap bitmap;
    private int currentX;
    private int currentY;


    public Picture(int originalX,int originalY,Bitmap bitmap){
        this.originalX = originalX;
        this.originalY = originalY;
        this.bitmap = bitmap;
        this.currentX = originalX;
        this.currentY = originalY;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public int getOriginalX() {
        return originalX;
    }

    public int getOriginalY() {
        return originalY;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public void setOriginalX(int originalX) {
        this.originalX = originalX;
    }

    public void setOriginalY(int originalY) {
        this.originalY = originalY;
    }

    @Override
    public String toString() {
        return "Picture{" +                "originalX=" + originalX +
                ", originalY=" + originalY +
                ", currentX=" + currentX +
                ", currentY=" + currentY +
                '}';
    }
}