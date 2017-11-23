package AshMan;

import AshMan.GameElements.*;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Maze {
    private final int empty = 0;
    private final int wall = 1;
    private final int cake = 2;
    private final int ghost = 3;
    private final int player = 4;
    private final int imageWidth = 26;
    private long mPreviousTime = 0;
    private int mWorkStep = 0;
    private AnimationTimer mTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            long elapsed = (now - mPreviousTime) ;
            if (elapsed > 1) { //TIMER_MSEC
                // do some of the work and quit when done
                if (!doSomeWork()) {
                    mTimer.stop();
                    mWorkStep = 0;
                }
                mPreviousTime = now;
            }
        }
    };
    private int [][][] mMazes = {{
            {2,2,2,1,2,2,2,1,2,2,2,1,2,2,2,1,2,1,2,2},
            {2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2},
            {2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2},
            {2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2},
            {2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2},
            {2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2},
            {2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2},
            {2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2},
            {2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2},
            {2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2},
            {2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2},
            {2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2},
            {2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2},
            {2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2},
            {2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2},
            {2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2},
            {2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2},
            {2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2},
            {2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2},
            {2,1,2,2,2,1,2,2,2,1,2,2,2,1,2,2,2,1,2,2}}};
    private int [][] mMaze;
    private Element [][] mGame = new Element[20][20];
    ArrayList<Element> mElements = new ArrayList<>();
    private Wall mWall = new Wall();
    private Cake mCake = new Cake();
    Canvas mBoardCanvas;
    Canvas mGameCanvas;
    private int score = 0;
    Maze(Canvas b, Canvas g, int curMaze){
        mMaze = mMazes[curMaze];
        mBoardCanvas = b;
        mGameCanvas = g;
        addEntities(2, true);
        initalizeGame();
        mTimer.start();
    }
    private void initalizeGame(){
        for(int r = 0; r < mMaze.length; r++){
            for(int c = 0; c < mMaze.length; c++){
                draw(r, c);
            }
        }
    }
    private void draw(int r, int c){
        if(mMaze[r][c] == wall){
            mWall.draw(mBoardCanvas, r * imageWidth, c * imageWidth);
        }
        else if(mMaze[r][c] == empty){
            mBoardCanvas.getGraphicsContext2D().drawImage(new Image("Images/Blank.png"), r * imageWidth, c * imageWidth);
        }
        else if(mMaze[r][c] == cake){
            mCake.draw(mBoardCanvas, r * imageWidth, c * imageWidth);
        }
        else if(mMaze[r][c] == ghost){
            mMaze[r][c] = cake;
            mElements.add(new Ghost(this, r,c));
            int n = mElements.size() - 1;
            mGame[r][c] = mElements.get(n);
            mElements.get(n).draw(mGameCanvas);
        }
        else if(mMaze[r][c] == player){
            mMaze[r][c] = cake;
            mElements.add(0, new Ashman(this, r, c));
            mGame[r][c] = mElements.get(0);
            mElements.get(0).draw(mGameCanvas);
        }
    }
    private void addEntities(int n, boolean start){
        while(n >= 0){
            int r = (int)(Math.random() * 20);
            int c = (int)(Math.random() * 20);
            if(mMaze[r][c] == 2){
                if(n > 0) {
                    mMaze[r][c] = 3;
                    n--;
                }
                else if(start){
                    mMaze[r][c] = 4;
                    n--;
                }
            }
        }
    }
    public void updateMaze(boolean p, int oldX, int oldY, int newX, int newY){
        Element temp = mGame[oldX][oldY];
        mGame[oldX][oldY] = null;
        mGame[newX][newY] = temp;
        if(p && mMaze[oldX][oldY] == cake){
            mMaze[oldX][oldY] = empty;
            score++;
        }
        draw(oldX, oldY);
    }
    public boolean collision(int x, int y) {//implement Collision based on 4 corners of object image
        return x <= 0 || x >= 19*imageWidth || y <= 0 || y >= 19*imageWidth || mMaze[x/imageWidth][y/imageWidth] == wall;
    }
    public boolean ghostCollision(int x, int y){
        return !mGame[x][y].isPlayer();
    }
    private boolean doSomeWork(){
        mWorkStep++;
        mGameCanvas.getGraphicsContext2D().clearRect(0,0,520,520);
        mElements.forEach(element -> element.draw(mGameCanvas));
        return true;
    }
}
