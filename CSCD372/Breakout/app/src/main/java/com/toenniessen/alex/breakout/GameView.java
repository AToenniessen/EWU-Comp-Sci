package com.toenniessen.alex.breakout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View {
    private final int mContainerWidth = 1000, mContainerHeight = 100;
    private final float[] mBrickDim = {0, 0, 100, 0, 100, 10, 0, 10};
    private int[][] mGrid = new int[10][10];
    private boolean mIsPaused = true;
    private int mBrickCnt = 0, mBrickHP = 0, mBallcnt = 0, mPaddleSen = 0;
    private Path mBrickPath = new Path();



    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context,AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    boolean isPaused(){
        return mIsPaused;
    }
    void start(){
        mIsPaused = false;
    }
    void pause(){
        mIsPaused = true;
    }
    void onInit(int bCnt, int bHP, int blCnt, int pSens){
        onPreferenceChanged(bCnt, bHP, blCnt, pSens);
        resetBoard();
    }
    void resetBoard(){
        int cnt = 0;
        while(cnt <= mBrickCnt) {
            for (int n = 0; n < 10; n++) {
                for (int m = 0; m < 10; m++) {
                    if ((Math.random() * 100) <= mBrickCnt && cnt <= mBrickCnt && mGrid[n][m] == 0) {
                        cnt++;
                        mGrid[n][m] = mBrickHP;
                    } else {
                        mGrid[n][m] = 0;
                    }
                }
            }
        }
    }
    void onPreferenceChanged(int bCnt, int bHP, int blCnt, int pSens){
        mBrickCnt = bCnt;
        mBrickHP = bHP;
        mBallcnt = blCnt;
        mPaddleSen = pSens;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {     //figure out 2:! aspect (width : height)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int originalwidth = MeasureSpec.getSize(widthMeasureSpec);
        int originalHeight = MeasureSpec.getSize(heightMeasureSpec);
        int calculatedHeight;
        if (originalwidth > originalHeight) {
            calculatedHeight = originalwidth / 2;
        } else
            calculatedHeight = originalwidth;
        setMeasuredDimension(originalwidth, calculatedHeight);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRGB(255, 255, 255);

        mBrickPath.moveTo(mBrickDim[0], mBrickDim[1]);
        mBrickPath.lineTo(mBrickDim[2], mBrickDim[3]);
        mBrickPath.lineTo(mBrickDim[4], mBrickDim[5]);
        mBrickPath.lineTo(mBrickDim[6], mBrickDim[7]);
        mBrickPath.close();

        canvas.scale((float) getWidth() / mContainerWidth, (float) getHeight() / mContainerHeight);

        updateCanvas(canvas);
    }
    private void updateCanvas(Canvas canvas){

    }
}
