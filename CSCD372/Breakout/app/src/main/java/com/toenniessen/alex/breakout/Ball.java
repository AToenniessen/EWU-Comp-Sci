package com.toenniessen.alex.breakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.TextView;

class Ball {
    private float mBallX, mStartX;
    private float mBallY, mStartY;
    private float mBallSpeed;
    private float mBallXVelocity;
    private float mBallYVelocity;
    private float mBallXRadius, mBallYRadius;
    private int mBallCnt;
    private RectF mHitBox = new RectF();
    private final Bitmap mBallMap;
    private final Paint mPaint = new Paint();
    Ball(float xR, float yR, float x, float y, float s, Bitmap bm){
        mBallXRadius = xR;
        mBallYRadius = yR;
        mBallX = x;
        mStartX = x;
        mBallY = y;
        mStartY = y;
        mBallSpeed = s ;
        mBallXVelocity = 0;
        mBallYVelocity = mBallSpeed;
        mBallMap = Bitmap.createScaledBitmap(bm, (int)mBallXRadius, (int)mBallYRadius, false);
    }

    void setmBallCnt(int mBallCnt) {
        this.mBallCnt = mBallCnt;
    }

    int getmBallCnt() {
        return mBallCnt;
    }

    public void setmBallSpeed(float mBallSpeed) {
        this.mBallSpeed = mBallSpeed;
    }
    void bounce(float x, float width){
        float bCenterX = mBallX + (mBallXRadius / 2);
        float heMid = (width / 2);
            float deltaX = (float)(((bCenterX - (x + heMid)) / heMid) * .9);
            double absV = Math.sqrt((mBallXVelocity * mBallXVelocity) + (mBallYVelocity * mBallYVelocity));
            mBallYVelocity = (float)(-Math.cos(deltaX) * absV);
            mBallXVelocity = (float)(Math.sin(deltaX) * absV);

    }
    void bounceBrick(RectF hitElement){
        float bottom_collision = hitElement.bottom - mHitBox.top, top_collision = mHitBox.bottom - hitElement.top,
                left_collision = mHitBox.right - hitElement.left, right_collision = hitElement.right - mHitBox.left;
        if((top_collision < bottom_collision && top_collision < left_collision && top_collision < right_collision)){
            mBallY = mBallY - 5;
            mBallYVelocity = -mBallYVelocity;
        }
        if(bottom_collision < top_collision && bottom_collision < left_collision && bottom_collision < right_collision) {
            mBallY = mBallY + 5;
            mBallYVelocity = -mBallYVelocity;
        }
        if((left_collision < right_collision && left_collision < top_collision && left_collision < bottom_collision)){
            mBallX = mBallX - 5;
            mBallXVelocity = -mBallXVelocity;
        }
         if(right_collision < left_collision && right_collision < top_collision && right_collision < bottom_collision){
             mBallX = mBallX + 5;
             mBallXVelocity = -mBallXVelocity;
        }
    }
    boolean bounceWall(int w, int h){
        if(mHitBox.left <= 0){
            mBallX = mBallX + 5;
            mBallXVelocity = -mBallXVelocity;
            return true;
        }
        else if(mHitBox.top <= 0){
            mBallY = mBallY + 5;
            mBallYVelocity = -mBallYVelocity;
            return true;
        }
        else if(mHitBox.right >= w){
            mBallY = mBallY - 5;
            mBallXVelocity = -mBallXVelocity;
            return true;
        }
        else if(mHitBox.bottom >= h + mBallYRadius) {
            mBallCnt--;
            resetBall();
            return false;
        }
        return true;
    }
    void resetBall(){
        mBallX = mStartX;
        mBallY = mStartY;
        mBallXVelocity = 0;
        mBallYVelocity = mBallSpeed;
    }

    RectF getmHitBox() {
        return mHitBox;
    }

    void draw(Canvas canvas){
        updatePos();
        canvas.save();
        canvas.drawBitmap(mBallMap, mBallX, mBallY, mPaint);
        canvas.restore();
    }
    private void updatePos(){
        mBallX = mBallX + mBallXVelocity;
        mBallY = mBallY + mBallYVelocity;
        mHitBox.set((int)mBallX, (int)mBallY,
                (int)(mBallX + (mBallXRadius)), (int)(mBallY + (mBallYRadius)));
    }
}
