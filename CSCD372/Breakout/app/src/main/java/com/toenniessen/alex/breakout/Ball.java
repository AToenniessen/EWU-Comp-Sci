package com.toenniessen.alex.breakout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

class Ball {
    private float mBallX, mBallY, mBallSpeed, mBallXVelocity, mBallYVelocity;
    private float mBallXRadius, mBallYRadius;
    private RectF mHitBox = new RectF();
    private final Bitmap mBallMap;
    private final Paint mPaint = new Paint();
    Ball(float xR, float yR, float x, float y, float s, Bitmap bm){
        mBallXRadius = xR;
        mBallYRadius = yR;
        mBallX = x;
        mBallY = y;
        mBallSpeed = s ;
        mBallXVelocity = 0;
        mBallYVelocity = -mBallSpeed;
        mBallMap = Bitmap.createScaledBitmap(bm, (int)mBallXRadius, (int)mBallYRadius, false);
    }

    float getmBallXRadius(){
        return mBallXRadius;
    }
    float getmBallYRadius(){
        return mBallYRadius;
    }
    float getmBallX(){
        return mBallX;
    }
    float getmBallY(){
        return mBallY;
    }
    void bounce(RectF hitElement, float x, float y, float width, float height){             //major logic problems, fix later

        float bCenterX = mBallX * (mBallXRadius / 2), bCenterY = mBallY * (mBallYRadius / 2);
        float heCenterX = x * (width / 2), heCenterY = y * (height / 2);
        float bottom_collision = hitElement.bottom - mHitBox.top, top_collision = mHitBox.bottom - hitElement.top,
                left_collision = mHitBox.right - hitElement.left, right_collision = hitElement.right - mHitBox.left;
        if(top_collision < bottom_collision && top_collision < left_collision && top_collision < right_collision){
            float deltaX = (float)(((bCenterX - ((heCenterX - bCenterX) + bCenterX)) / heCenterX) * .9);
            mBallYVelocity = mBallYVelocity * deltaX;
            if(deltaX < 0)
                mBallXVelocity = (-1 - deltaX) * ((mBallSpeed));
            else
                mBallXVelocity = (1 - deltaX) * ((mBallSpeed));
        }
        if(bottom_collision < top_collision && bottom_collision < left_collision && bottom_collision < right_collision) {
            float deltaX = (float) (((bCenterX - ((heCenterX - bCenterX) + bCenterX)) / heCenterX) * .9);
            mBallYVelocity = mBallYVelocity * deltaX;
            if (deltaX < 0)
                mBallXVelocity = (-1 - deltaX) * ((mBallSpeed));
            else
                mBallXVelocity = (1 - deltaX) * ((mBallSpeed));
        }
        if(left_collision < right_collision && left_collision < top_collision && left_collision < bottom_collision){
            float deltaY = (float)(((bCenterY - ((heCenterY - bCenterY) + bCenterY)) / heCenterY) *.9);
            mBallXVelocity = mBallXVelocity * deltaY;
            if(deltaY < 0)
                mBallYVelocity = (-1 - deltaY) * ((mBallSpeed));
            else
                mBallYVelocity = (1 - deltaY) * ((mBallSpeed));
        }
        if(right_collision < left_collision && right_collision < top_collision && right_collision < bottom_collision){
            float deltaY = (float)(((bCenterY - ((heCenterY - bCenterY) + bCenterY)) / heCenterY) *.9);
            mBallXVelocity = mBallXVelocity * deltaY;
            if(deltaY < 0)
                mBallYVelocity = (-1 - deltaY) * ((mBallSpeed));
            else
                mBallYVelocity = (1 - deltaY) * ((mBallSpeed));
        }
    }
    void bounceBrick(RectF hitElement){
        float bottom_collision = hitElement.bottom - mHitBox.top, top_collision = mHitBox.bottom - hitElement.top,
                left_collision = mHitBox.right - hitElement.left, right_collision = hitElement.right - mHitBox.left;
        if((top_collision < bottom_collision && top_collision < left_collision && top_collision < right_collision) ||
                (bottom_collision < top_collision && bottom_collision < left_collision && bottom_collision < right_collision)) {
            bounceWall(false);
        }
        if((left_collision < right_collision && left_collision < top_collision && left_collision < bottom_collision) ||
                (right_collision < left_collision && right_collision < top_collision && right_collision < bottom_collision)){
            bounceWall(true);
        }
    }
    void bounceWall(boolean side){
        if(side){
            mBallXVelocity = -mBallXVelocity;
        }
        else
            mBallYVelocity = -mBallYVelocity;
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
