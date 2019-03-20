package com.toenniessen.alex.breakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.TextView;

import java.io.Serializable;

class Ball implements Serializable {
    private float mBallX, mStartX;
    private float mBallY, mStartY;
    private float mBallSpeed, mBallInitialSpeed;
    private float mBallXVelocity;
    private float mBallYVelocity;
    private float mBallXRadius = 0, mBallYRadius = 0, mWidth, mHeight;
    private int mBallCnt = 0, mCurBalls = 0;
    private RectF mHitBox = new RectF();
    private Bitmap mBallMap;
    private final Paint mPaint = new Paint();
    Ball(float w, float h, float s, Bitmap bm){
        mWidth = w;
        mHeight = h;
        mBallSpeed = s ;
        mBallInitialSpeed = mBallSpeed;
        mBallXVelocity = 0;
        mBallYVelocity = mBallSpeed;
        mBallMap = bm;
    }

    void setmBallXRadius(float mBallXRadius) {
        this.mBallXRadius = mBallXRadius;
        if(mStartX == 0){
            mStartX = (mWidth - mBallXRadius) / 2;
        }
        if(mBallX == 0) {
            mBallX = mStartX;
        }
    }

    void setmBallYRadius(float mBallYRadius) {
        this.mBallYRadius = mBallYRadius;
        if(mStartY == 0) {
            mStartY = (mHeight - mBallYRadius) - 75;
        }
        if(mBallY == 0) {
            mBallY = mStartY;
        }

    }
    void setmBallMap(){
        mBallMap = Bitmap.createScaledBitmap(mBallMap, (int)mBallXRadius, (int)mBallYRadius, false);

    }
    boolean mBallRadiusSet(){
        return mBallXRadius != 0 && mBallYRadius != 0;
    }

    void setmBallCnt(int mBallCnt) {
        this.mBallCnt = mBallCnt;
        mCurBalls = mBallCnt;
    }

    int getmCurBalls() {
        return mCurBalls;
    }

    void increaseSpeed() {
        this.mBallSpeed = (float)(this.mBallSpeed * 1.33);
    }
    void resetSpeed(){
        mBallSpeed = mBallInitialSpeed;
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
    int bounceWall(int w, int h){
        if(mBallCnt > 0) {
            if (mHitBox.left <= 0) {
                mBallX = mBallX + 5;
                mBallXVelocity = -mBallXVelocity;
                return 1;
            } else if (mHitBox.top <= 0) {
                mBallY = mBallY + 5;
                mBallYVelocity = -mBallYVelocity;
                return 1;
            } else if (mHitBox.right >= w) {
                mBallY = mBallY - 5;
                mBallXVelocity = -mBallXVelocity;
                return 1;
            } else if (mHitBox.bottom >= h + mBallYRadius) {
                mCurBalls--;
                //resetBall();
                return 0;
            }
        }
        return 2;
    }
    void resetBall(){
        mBallX = mStartX;
        mBallY = mStartY;
        mBallXVelocity = 0;
        mBallYVelocity = mBallSpeed;
    }
    void resetBallCnt(){
        mCurBalls = mBallCnt;
    }

    RectF getmHitBox() {
        return mHitBox;
    }

    void draw(Canvas canvas, boolean paused){
        updatePos(paused);
        canvas.save();
        canvas.drawBitmap(mBallMap, mBallX, mBallY, mPaint);
        canvas.restore();
    }
    private void updatePos(boolean paused){
        if(!paused) {
            mBallX = mBallX + mBallXVelocity;
            mBallY = mBallY + mBallYVelocity;
            mHitBox.set((int) mBallX, (int) mBallY,
                    (int) (mBallX + (mBallXRadius)), (int) (mBallY + (mBallYRadius)));
        }
    }
    SerializableBall save(){
        return new SerializableBall(mBallX, mBallY, mBallXVelocity, mBallYVelocity, mBallCnt, mCurBalls);
    }
    void load(SerializableBall save){
        mBallX = save.getCurX();
        mBallY = save.getCurY();
        mBallXVelocity = save.getxVelocity();
        mBallYVelocity = save.getyVelocity();
        mBallCnt = save.getBallCnt();
        mCurBalls = save.getCurBall();
        mHitBox.set((int)mBallX, (int)mBallY,
                (int)(mBallX + (mBallXRadius)), (int)(mBallY + (mBallYRadius)));
    }
}
class SerializableBall implements Serializable{
    private float curX, curY, xVelocity, yVelocity;
    private int ballCnt, curBall;
    SerializableBall (float x, float y, float xv, float yv, int bc, int cb){
        curX = x;
        curY = y;
        xVelocity = xv;
        yVelocity = yv;
        ballCnt = bc;
        curBall = cb;
    }
    int getCurBall(){
        return curBall;
    }

    float getCurX() {
        return curX;
    }

    float getCurY() {
        return curY;
    }

    float getxVelocity() {
        return xVelocity;
    }

    float getyVelocity() {
        return yVelocity;
    }

    int getBallCnt() {
        return ballCnt;
    }
}
