package com.toenniessen.alex.breakout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;

public class GameView extends View {
    private final int mContainerWidth = 1000, mContainerHeight = 1000;

    private boolean mIsPaused = true;
    private int mBallCnt;
    private Ball mBall;
    private Paddle mPaddle;
    private GameBoard mBoard;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void onInit(int bCnt, int bHP, int blCnt, int pSens) {
        float mBrickWidth = (float)mContainerWidth / 10, mBrickHeight = (float)(mContainerHeight - 500) / 10;

        mBoard = new GameBoard(mBrickWidth, mBrickHeight);
        mPaddle = new Paddle((float)(mBrickWidth * 1.5), mBrickHeight / 2);

        onPreferenceChanged(bCnt, bHP, blCnt, pSens);
    }
    boolean changeDirection(int dir, MotionEvent event){
        return mPaddle.ChangeDirection(dir, event);
    }

    boolean isPaused() {
        return mIsPaused;
    }

    void start() {
        mIsPaused = false;
    }

    void pause() {
        mIsPaused = true;
    }

    void onPreferenceChanged(int bCnt, int bHP, int blCnt, int pSens) {
        mBoard.setmBrickCnt(bCnt);
        mBoard.setmBrickHP(bHP);
        mBallCnt = blCnt;
        mPaddle.setmPaddleSen(pSens);
        mBoard.resetBoard();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float mScaledX = (float) getWidth() / mContainerWidth;
        float mScaledY = (float) getHeight() / mContainerHeight;
        if(mBall == null){
              mBall = new Ball((int)(75 * mScaledY), (int)(75 * mScaledX), (float)((mContainerWidth - (40 / 2)) / 2),
                       mContainerHeight - 150, 5, BitmapFactory.decodeResource(getResources(), R.drawable.game_ball));
        }
        invalidate();
        canvas.drawRGB(200, 200, 255);
        
        canvas.scale(mScaledX, mScaledY);

        updateCanvas(canvas);
    }
    private void updateCanvas(Canvas canvas) {
        checkCollision();
        mBoard.draw(canvas);
        mPaddle.draw(canvas);
        mBall.draw(canvas);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {     //figure out 2:! aspect (width : height)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int originalwidth = MeasureSpec.getSize(widthMeasureSpec);
        int originalHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (originalwidth > originalHeight) {
            setMeasuredDimension(originalwidth, (int)(originalwidth/2.5));
        } else {
            setMeasuredDimension(originalwidth, originalHeight / 2);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void checkCollision(){
        RectF ball = mBall.getmHitBox();
        RectF paddle = mPaddle.getmHitBox();
        RectF cell;
        if((cell = mBoard.ballCollison(ball)) != null){
            mBall.bounceBrick(cell);
        }
        else if(ball.intersect(paddle)){
            mBall.bounce(paddle, mPaddle.getmPaddleX(), mPaddle.getmPaddleY(), mPaddle.getmPaddleWidth(), mPaddle.getmPaddleHeight());
        }
        else{
            if(ball.left < 0 || ball.right > mContainerWidth)
                mBall.bounceWall(true);
            else if( ball.top < 0)
                mBall.bounceWall(false);
        }
    }
}