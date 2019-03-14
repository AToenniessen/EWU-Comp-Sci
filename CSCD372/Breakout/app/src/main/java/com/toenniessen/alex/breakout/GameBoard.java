package com.toenniessen.alex.breakout;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import java.util.Arrays;

class GameBoard {
    private int[][] mGrid = new int[10][10];
    private RectF[][] mHitBoxes = new RectF[10][10];
    private float[] mBrickDim;
    private final Path mBrickPath = new Path();
    private final Paint mPaint = new Paint();

    private float mBrickWidth, mBrickHeight;
    private int mBrickCnt;

    void setmBrickCnt(int mBrickCnt) {
        this.mBrickCnt = mBrickCnt;
    }

    void setmBrickHP(int mBrickHP) {
        this.mBrickHP = mBrickHP;
    }

    private int mBrickHP;

    GameBoard(float w, float h) {
        mBrickWidth = w;
        mBrickHeight = h;
        mBrickDim = new float[]{0, 0, mBrickWidth, 0, mBrickWidth, mBrickHeight, 0, mBrickHeight};
        initPath();
    }

    private void decrementHealth(int x, int y) {
        if (--mGrid[x][y] == 0) {
            mHitBoxes[x][y] = null;
        }
    }

    void draw(Canvas canvas) {
        for (int row = 0; row < 10; row++) {
            for (int cell = 0; cell < 10; cell++) {
                canvas.save();
                canvas.translate(cell * mBrickWidth, row * mBrickHeight);
                changeColor(mGrid[row][cell]);
                canvas.drawPath(mBrickPath, mPaint);
                canvas.restore();
            }
        }
    }

    void resetBoard() {
        clearBoard();
        int cnt = 0;
        while (cnt < mBrickCnt) {
            int row = (int) (Math.random() * 10), col = (int) (Math.random() * 10), n = mGrid[row][col];
            if (n == 0) {
                cnt++;
                mGrid[row][col] = mBrickHP;
                mHitBoxes[row][col] = new RectF((int)(col * mBrickWidth),(int) (row * mBrickHeight),
                        (int)(col * mBrickWidth) + mBrickWidth, (int)(row * mBrickHeight) + mBrickHeight);
            }
        }
    }

    private void clearBoard() {
        mHitBoxes = new RectF[10][10];
        for (int[] row : mGrid) {
            Arrays.fill(row, 0);
        }
    }

    private void changeColor(int hp) {
        switch (hp) {
            case 0:
                mPaint.setColor(Color.TRANSPARENT);
                break;
            case 1:
                mPaint.setColor(Color.RED);
                break;
            case 2:
                mPaint.setColor(Color.BLUE);
                break;
            case 3:
                mPaint.setColor(Color.GREEN);
                break;
            case 4:
                mPaint.setColor(Color.BLACK);
                break;
        }
    }

    private void initPath() {
        mBrickPath.moveTo(mBrickDim[0], mBrickDim[1]);
        mBrickPath.lineTo(mBrickDim[2], mBrickDim[3]);
        mBrickPath.lineTo(mBrickDim[4], mBrickDim[5]);
        mBrickPath.lineTo(mBrickDim[6], mBrickDim[7]);
        mBrickPath.close();
    }

    float getmBrickWidth() {
        return mBrickWidth;
    }

    float getmBrickHeight() {
        return mBrickHeight;
    }
    RectF ballCollison(RectF ball) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                RectF cell = mHitBoxes[x][y];
                if (cell != null && ball.intersect(cell)) {
                    decrementHealth(x, y);
                    return cell;
                }
            }
        }
        return null;
    }
}
