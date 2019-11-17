package com.example.klotski.util;

import android.view.MotionEvent;

public class GestureHelper {
    public static final int NOT = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    private static final int LEFT_OR_RIGHT = 5;
    private static final int UP_OR_DOWN = 6;

    private static volatile GestureHelper gestureHelper;

    /**
     * 单列模式
     */

    private GestureHelper() {

    }

    public static GestureHelper getInstance() {
        if (gestureHelper == null) {
            synchronized (GestureHelper.class) {
                if (gestureHelper == null) {
                    gestureHelper = new GestureHelper();
                }
            }
        }
        return gestureHelper;
    }

    public int getPosition(MotionEvent start, MotionEvent end) {
        int startX = (int) start.getX();
        int startY = (int) start.getY();
        int endX = (int) end.getX();
        int endY = (int) end.getY();

        int gestureDirection = Math.abs(startX - endX) > Math.abs(startY - endY) ? LEFT_OR_RIGHT : UP_OR_DOWN;
        //具体判断滑动方向
        switch (gestureDirection) {
            case LEFT_OR_RIGHT:
                if (start.getX() < end.getX()) { //开始的x坐标小于结束的x坐标
                    //手指向右移动
                    return RIGHT;
                } else {
                    //手指向左移动
                    return LEFT;
                }
            case UP_OR_DOWN:
                if (start.getY() < end.getY()) {//  开始的y坐标小于结束的y坐标
                    //手指向下移动
                    return DOWN;
                } else {
                    //手指向上移动
                    return UP;
                }
        }
        return NOT;

    }
}
