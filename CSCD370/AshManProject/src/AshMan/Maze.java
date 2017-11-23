package AshMan;

import AshMan.GameElements.*;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
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
    private ArrayList<Element> mElements = new ArrayList<>();
    private Wall mWall = new Wall();
    private Cake mCake = new Cake();
    public Canvas mCanvas;
    Maze(Canvas c, int curMaze){
        mMaze = mMazes[curMaze];
        mCanvas = c;
        addEntities(2, true);
        Draw();
        mTimer.start();
    }
    private void Draw(){
        int n = 0;
        for(int r = 0; r < mMaze.length; r++){
            for(int c = 0; c < mMaze.length; c++){
                if(mMaze[r][c] == wall){
                    mWall.draw(mCanvas, r * imageWidth, c * imageWidth);
                }
                else if(mMaze[r][c] == cake){
                    mCake.draw(mCanvas, r * imageWidth, c * imageWidth);
                }
                else if(mMaze[r][c] == ghost){
                    mElements.add(new Ghost(this, r,c));

                   mElements.get(n).draw(mCanvas);
                    n++;
                }
                else if(mMaze[r][c] == player){
                    mElements.add(0, new Ashman(this, r, c));
                    mElements.get(0).draw(mCanvas);
                }
            }
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
    public void updateMaze(int oldX, int oldY, int newX, int newY){

    }
    public boolean collision(int x, int y) {
        return x <= 0 || x >= 19*imageWidth || y <= 0 || y >= 19*imageWidth || mMaze[x/imageWidth][y/imageWidth] == wall;
    }
    public boolean ghostCollision(int x, int y){
        return mMaze[x][y] == ghost;
    }
    private boolean doSomeWork(){
        mWorkStep++;
        mElements.forEach(element -> element.draw(mCanvas));
        return true;
    }
}
