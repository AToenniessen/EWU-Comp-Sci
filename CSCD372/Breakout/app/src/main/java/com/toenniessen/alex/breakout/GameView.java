package com.toenniessen.alex.breakout;

import android.animation.TimeAnimator;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class GameView extends View {
    private final int mContainerWidth = 1000, mContainerHeight = 1000;
    private int mBallCnt;

    private Ball mBall;
    private Paddle mPaddle;
    private GameBoard mBoard;

    private final TimeAnimator mTimer = new TimeAnimator();


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
        float mBrickWidth = (float) mContainerWidth / 10, mBrickHeight = (float) (mContainerHeight - 500) / 10;

        mBoard = new GameBoard(mBrickWidth, mBrickHeight);
        mPaddle = new Paddle((float) (mBrickWidth * 1.5), mBrickHeight / 2);
        updateLevel();
        updateScore();
        initializeTimer();

        onPreferenceChanged(bCnt, bHP, blCnt, pSens);
    }

    void updateLevel() {
        ((TextView) this.getRootView().findViewById(R.id.level)).setText((getResources().getString(R.string.level) + " " + mBoard.getmLevel()));
    }

    void updateScore() {
        ((TextView) this.getRootView().findViewById(R.id.score)).setText((getResources().getString(R.string.score) + " " + mBoard.getmScore()));
    }

    void updateBricks() {
        ((TextView) this.getRootView().findViewById(R.id.bricks)).setText((getResources().getString(R.string.bricks) + " " + mBoard.getmBricksLeft()));
    }
    void updateBalls(){
        if(mBall.getmBallCnt() < 0){
            mBall.setmBallCnt(0);
            invalidate();
            Snackbar snackbar = Snackbar.make(this, getResources().getString(R.string.lost), Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction(getResources().getString(R.string.confirm), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBall.setmBallCnt(mBallCnt);
                    mBoard.resetBoard();
                    mPaddle.resetPaddle();
                    startTimer();
                    pauseTimer();
                    updateBalls();
                    updateScore();
                    updateBricks();
                }
            });

            snackbar.show();
        }
        ((TextView) this.getRootView().findViewById(R.id.balls)).setText((getResources().getString(R.string.balls) + " " + mBall.getmBallCnt()));
    }

    boolean changeDirection(int dir, MotionEvent event) {
        return mPaddle.ChangeDirection(dir, event);
    }

    void onPreferenceChanged(int bCnt, int bHP, int blCnt, int pSens) {
        mBoard.setmBrickCnt(bCnt);
        mBoard.setmBrickHP(bHP);
        mBallCnt = blCnt;
        mPaddle.setmPaddleSen(pSens);
        mBoard.resetBoard();
        updateBricks();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float mScaledX = (float) getWidth() / mContainerWidth;
        float mScaledY = (float) getHeight() / mContainerHeight;
        if (mBall == null) {
            mBall = new Ball((int) (75 * mScaledY), (int) (75 * mScaledX), ((mContainerWidth - ((75 * mScaledX))) / 2),
                    mContainerHeight - 150, 5, BitmapFactory.decodeResource(getResources(), R.drawable.game_ball));
            mBall.setmBallCnt(mBallCnt);
            updateBalls();
        }
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int originalwidth = MeasureSpec.getSize(widthMeasureSpec);
        int originalHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (originalwidth > originalHeight) {
            setMeasuredDimension(originalwidth, (int) (originalwidth / 2.5));
        } else {
            setMeasuredDimension(originalwidth, originalHeight / 2);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void checkCollision() {
        RectF ball = mBall.getmHitBox();
        RectF paddle = mPaddle.getmHitBox();
        RectF cell;
        if ((cell = mBoard.ballCollison(ball)) != null) {
            mBall.bounceBrick(cell);
            updateScore();
            updateBricks();
        } else if (ball.intersect(paddle)) {
            mBall.bounce(mPaddle.getmPaddleX(), mPaddle.getmPaddleWidth());
        } else {
            if (!mBall.bounceWall(mContainerWidth, mContainerHeight)) {
                updateBalls();
                mPaddle.resetPaddle();
                pauseTimer();
            }
        }
    }

    private void initializeTimer() {
        mTimer.setTimeListener(new TimeAnimator.TimeListener() {
            @Override
            public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
                invalidate();
            }
        });
    }

    void pauseTimer() {
        mTimer.pause();
    }

    void startTimer() {
        mTimer.start();
    }

    boolean isPaused() {
        return mTimer.isPaused();
    }

    boolean isStarted() {
        return mTimer.isStarted();
    }
}