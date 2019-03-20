package com.toenniessen.alex.breakout;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import java.io.Serializable;
import java.util.Arrays;

class GameBoard implements Serializable {
    private int[][] mGrid = new int[10][10];
    private RectF[][] mHitBoxes = new RectF[10][10];
    private float[] mBrickDim;
    private final Path mBrickPath = new Path();
    private final Paint mPaint = new Paint();

    private float mBrickWidth, mBrickHeight;
    private int mBrickCnt, mBricksLeft, mScore = 0, mLevel = 1;
    private int mBrickHP = -1;

    int getmBricksLeft() {
        return mBricksLeft;
    }

    int getmBrickCnt() {
        return mBrickCnt;
    }

    int getmBrickHP() {
        return mBrickHP;
    }

    int getmScore() {
        return mScore;
    }
    void setmLevel(int i){
        mLevel = i;
    }
    int getmLevel(){
        return mLevel;
    }
    boolean checkWinner(){
        if(mBricksLeft == 0 && mBrickCnt != 0) {
            mLevel++;
            return true;
        }
        return false;
    }

    void setmBrickCnt(int mBrickCnt) {
        this.mBrickCnt = mBrickCnt;
        this.mBricksLeft = mBrickCnt;
    }


    void setmBrickHP(int mBrickHP) {
        this.mBrickHP = mBrickHP;
    }

    GameBoard(float w, float h) {
        mBrickWidth = w;
        mBrickHeight = h;
        mBrickDim = new float[]{0, 0, mBrickWidth, 0, mBrickWidth, mBrickHeight, 0, mBrickHeight};
        initPath();
    }

    private void decrementHealth(int x, int y) {
        if (--mGrid[x][y] == 0) {
            mHitBoxes[x][y] = null;
            mBricksLeft--;
            mScore++;
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
    void resetHealth(){
        for(int row = 0; row < 10; row++){
            for( int col = 0; col < 10; col ++){
                mGrid[row][col] = mGrid[row][col] != 0 ? mBrickHP : 0;
            }
        }
    }

    void resetBoard() {
        mScore = 0;
        mBricksLeft = mBrickCnt;
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

    RectF ballCollison(RectF ball) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                RectF cell = mHitBoxes[x][y];
                if(mGrid[x][y] == 0 && cell != null) {
                    mHitBoxes[x][y] = null;
                    cell = null;
                }
                if (cell != null && ball.intersect(cell)) {
                    decrementHealth(x, y);
                    return cell;
                }
            }
        }
        return null;
    }
    SerializeableBoard save(){
        return new SerializeableBoard(mGrid, mBrickCnt, mBricksLeft, mScore, mLevel, mBrickHP);
    }
    void load(SerializeableBoard save){
        mGrid = save.getGrid();
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                mHitBoxes[row][col] = new RectF((int)(col * mBrickWidth),(int) (row * mBrickHeight),
                        (int)(col * mBrickWidth) + mBrickWidth, (int)(row * mBrickHeight) + mBrickHeight);
            }
        }
        mBrickCnt = save.getBrickCnt();
        mBricksLeft = save.getBricksLeft();
        mScore = save.getScore();
        mLevel = save.getLevel();
        mBrickHP = save.getHealth();
    }
}
class SerializeableBoard implements Serializable{
    private int[][] grid;
    private int brickCnt, bricksLeft, score, level, health;
    SerializeableBoard (int[][] g, int bc, int bl, int s, int l, int hp){
        grid = g;
        brickCnt = bc;
        bricksLeft = bl;
        score = s;
        level = l;
        health = hp;
    }
    int[][] getGrid() {
        return grid;
    }

    int getBrickCnt() {
        return brickCnt;
    }

    int getBricksLeft() {
        return bricksLeft;
    }

    int getScore() {
        return score;
    }

    int getLevel() {
        return level;
    }

    int getHealth(){
        return health;
    }
}
