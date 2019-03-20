package com.toenniessen.alex.breakout;

import android.animation.TimeAnimator;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class GameView extends View implements Serializable {
    private final int mContainerWidth = 1000, mContainerHeight = 1000;
    private int mBounceID, mLooseBallID;
    private boolean waiting = false;
    private SoundPool mSoundPool;

    private Ball mBall;
    private Paddle mPaddle;
    private GameBoard mBoard;

    private TimeAnimator mTimer = new TimeAnimator();


    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Ball getmBall() {
        return mBall;
    }

    Paddle getmPaddle() {
        return mPaddle;
    }

    GameBoard getmBoard() {
        return mBoard;
    }
    void load(){
        updateLevel();
        updateBricks();
        updateScore();
        updateBalls();
    }

    public void onInit() {
        float mBrickWidth = (float) mContainerWidth / 10, mBrickHeight = (float) (mContainerHeight - 500) / 10;

        mBoard = new GameBoard(mBrickWidth, mBrickHeight);
        mPaddle = new Paddle((float) (mBrickWidth * 1.5), mBrickHeight / 2);
        mBall = new Ball(mContainerWidth, mContainerHeight,
                5, BitmapFactory.decodeResource(getResources(), R.drawable.game_ball));
        initializeTimer();
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mBounceID = mSoundPool.load(getContext(), R.raw.bounce, 1);
        mLooseBallID = mSoundPool.load(getContext(), R.raw.loose, 1);
    }

    void updateLevel() {
        ((TextView) this.getRootView().findViewById(R.id.level)).setText((getResources().getString(R.string.level) + " " + mBoard.getmLevel()));
    }

    void updateScore() {
        if(mBoard.checkWinner()){
            Toast.makeText(getContext(),getResources().getString(R.string.win), Toast.LENGTH_SHORT).show();
            pauseTimer();
            resetGame();
            mBall.increaseSpeed();
        }

            ((TextView) this.getRootView().findViewById(R.id.score)).setText((getResources().getString(R.string.score) + " " + mBoard.getmScore()));
    }

    void updateBricks() {
        ((TextView) this.getRootView().findViewById(R.id.bricks)).setText((getResources().getString(R.string.bricks) + " " + mBoard.getmBricksLeft()));
    }
    boolean isWaiting(){
        return waiting;
    }

    void updateBalls() {
        if (mBall.getmCurBalls() <= 0) {
            pauseTimer();
            waiting = true;
            Snackbar snackbar = Snackbar.make(this, getResources().getString(R.string.lost), Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction(getResources().getString(R.string.confirm), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    invalidate();
                    mBall.resetSpeed();
                    restartGame();
                    waiting = false;
                }
            });
            snackbar.show();
        }
        else{
            invalidate();
        }

        ((TextView) this.getRootView().findViewById(R.id.balls)).setText((getResources().getString(R.string.balls) + " " + mBall.getmCurBalls()));

    }
    void restartGame(){
        mBoard.setmLevel(1);
        resetGame();
    }
    void resetGame(){
        updateLevel();
        mBoard.resetBoard();
        mPaddle.resetPaddle();
        updateBricks();
        updateScore();
        if(mBall != null) {
            mBall.resetBall();
            mBall.resetBallCnt();
            updateBalls();
        }
        invalidate();
    }

    boolean changeDirection(int dir, MotionEvent event) {
        return mPaddle.ChangeDirection(dir, event);
    }

    void onPreferenceChanged(int bCnt, int bHP, int blCnt, int pSens) {
        if(mBoard.getmBrickHP() != bHP) {
            mBoard.setmBrickHP(bHP);
            mBoard.resetHealth();
        }
        mBall.setmBallCnt(blCnt);
        updateBalls();
        mPaddle.setmPaddleSen(pSens);
        if(mBoard.getmBrickCnt() != bCnt) {
            mBoard.setmBrickCnt(bCnt);
            resetGame();
        }
        else{
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float scaledX = (float) getWidth() / mContainerWidth;
        float scaledY = (float) getHeight() / mContainerHeight;
        if(!mBall.mBallRadiusSet()) {
            mBall.setmBallXRadius(75 * scaledY);
            mBall.setmBallYRadius(75 * scaledX);
            mBall.setmBallMap();
        }

        canvas.drawRGB(200, 200, 255);

        canvas.scale(scaledX, scaledY);

        updateCanvas(canvas);

    }

    private void updateCanvas(Canvas canvas) {
        mBoard.draw(canvas);
        mPaddle.draw(canvas);
        mBall.draw(canvas, isPaused());
        if(!isPaused())
            checkCollision();
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
    void playClip(int id){
        mSoundPool.play(id, 1, 1, 1, 0, 1f);
    }

    private void checkCollision() {
        RectF ball = mBall.getmHitBox();
        RectF paddle = mPaddle.getmHitBox();
        RectF cell;
        if ((cell = mBoard.ballCollison(ball)) != null) {
            playClip(mBounceID);
            mBall.bounceBrick(cell);
            updateScore();
            updateBricks();
        } else if (ball.intersect(paddle)) {
            playClip(mBounceID);
            mBall.bounce(mPaddle.getmPaddleX(), mPaddle.getmPaddleWidth());
        } else {
            int res;
            if ((res = mBall.bounceWall(mContainerWidth, mContainerHeight)) == 1) {
                playClip(mBounceID);
            }
            else if(res == 0){
                pauseTimer();
                playClip(mLooseBallID);
                updateBalls();
                mPaddle.resetPaddle();
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
        startTimer();
        pauseTimer();
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