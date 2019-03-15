package com.toenniessen.alex.breakout;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;

class Paddle {
    private final int mContainerWidth = 1000, mContainerHeight = 1000;
    private float mPaddleX, mPaddleY, mStartX, mStartY, mPaddleSen = 0, mPaddleWidth, mPaddleHeight;
    private PaddleMovement mPaddleDirection = PaddleMovement.STOPPED;
    private float[] mPaddleDim;
    private RectF mHitBox = new RectF();

    private final Path mPaddlePath = new Path();
    private final Paint mPaint = new Paint();


    Paddle(float w, float h){
        mPaddleWidth = w;
        mPaddleHeight = h;
        mPaddleDim = new float[]{0, 0, mPaddleWidth, 0, mPaddleWidth, mPaddleHeight, 0, mPaddleHeight};

        mPaddleX = (mContainerWidth - mPaddleWidth) / 2;
        mPaddleY = mContainerHeight - 50;
        mStartX = mPaddleX;
        mStartY = mPaddleY;
        initPath();
        mPaint.setColor(Color.GRAY);
    }
    private void initPath(){
        mPaddlePath.moveTo(mPaddleDim[0], mPaddleDim[1]);
        mPaddlePath.lineTo(mPaddleDim[2], mPaddleDim[3]);
        mPaddlePath.lineTo(mPaddleDim[4], mPaddleDim[5]);
        mPaddlePath.lineTo(mPaddleDim[6], mPaddleDim[7]);
        mPaddlePath.close();
    }
    void resetPaddle(){
        mPaddleX = mStartX;
        mPaddleY = mStartY;
    }

    void setmPaddleSen(float mPaddleSen) {
        this.mPaddleSen = mPaddleSen;
    }

    boolean ChangeDirection(int dir, MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_UP)
            mPaddleDirection = PaddleMovement.STOPPED;
        else{
            if(dir == R.id.left)
                mPaddleDirection = PaddleMovement.LEFT;
            else if(dir == R.id.right)
                mPaddleDirection = PaddleMovement.RIGHT;
        }
        return true;
    }

    RectF getmHitBox() {
        return mHitBox;
    }

    public float getmPaddleX() {
        return mPaddleX;
    }

    public float getmPaddleY() {
        return mPaddleY;
    }

    public float getmPaddleWidth() {
        return mPaddleWidth;
    }

    public float getmPaddleHeight() {
        return mPaddleHeight;
    }

    void draw(Canvas canvas){
        int exposedPaddle = 10;
        if(mPaddleDirection == PaddleMovement.LEFT)
            mPaddleX = ((mPaddleX - mPaddleSen) >= exposedPaddle - mPaddleWidth) ?
                    mPaddleX - mPaddleSen : mPaddleX;
        else if(mPaddleDirection == PaddleMovement.RIGHT)
            mPaddleX = ((mPaddleX + mPaddleSen) <= mContainerWidth - exposedPaddle) ?
                    mPaddleX + mPaddleSen : mPaddleX;
        mHitBox.set((int)(mPaddleX), (int)(mPaddleY),
                (int)(mPaddleX + (mPaddleWidth)), (int)(mPaddleY + (mPaddleHeight)));
        canvas.save();
        canvas.translate(mPaddleX, mPaddleY);
        canvas.drawPath(mPaddlePath, mPaint);
        canvas.restore();
    }
}
enum PaddleMovement {
    STOPPED, LEFT, RIGHT
}
