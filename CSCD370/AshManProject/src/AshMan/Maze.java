package AshMan;

import AshMan.GameElements.*;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleBooleanProperty;
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
    private SimpleBooleanProperty dead = new SimpleBooleanProperty();
    private AnimationTimer mTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            long elapsed = (now - mPreviousTime);
            if (elapsed > 1) { //TIMER_MSEC
                // do some of the work and quit when done
                if (doSomeWork()) {
                    dead.set(score != cakeCount);
                    mTimer.stop();
                    mWorkStep = 0;
                }
                mPreviousTime = now;
            }
        }
    };
    private int[][][] mMazes = {{
            {2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2},
            {2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2}}};
    private int[][] mMaze;
    private Element[][] mGame = new Element[20][20];
    ArrayList<Element> mElements = new ArrayList<>();
    private Wall mWall = new Wall();
    private Cake mCake = new Cake();
    Canvas mBoardCanvas;
    Canvas mGameCanvas;
    private int score = 0;
    private int cakeCount = 0;

    Maze(Canvas b, Canvas g, int curMaze) {
        mMaze = mMazes[curMaze];
        countCakes():
        mBoardCanvas = b;
        mGameCanvas = g;
        addEntities(2, true);
        initalizeGame();
        //mTimer.start();
    }
    private void countCakes(){
        for (int[] aMMaze : mMaze) {
            for(int i : aMMaze) {
                if (i == cake)
                    cakeCount++;
            }
        }
    }
    void go() {
        mTimer.start();
    }

    public void pause() {
        mTimer.stop();
    }

    private void initalizeGame() {
        for (int r = 0; r < mMaze.length; r++) {
            for (int c = 0; c < mMaze.length; c++) {
                draw(r, c);
            }
        }
    }

    private void draw(int r, int c) {
        if (mMaze[r][c] == wall) {
            mWall.draw(mBoardCanvas, r * imageWidth, c * imageWidth);
        } else if (mMaze[r][c] == empty) {
            mBoardCanvas.getGraphicsContext2D().drawImage(new Image("Images/Blank.png"), r * imageWidth, c * imageWidth);
        } else if (mMaze[r][c] == cake) {
            mCake.draw(mBoardCanvas, r * imageWidth, c * imageWidth);
        } else if (mMaze[r][c] == ghost) {
            mMaze[r][c] = cake;
            mElements.add(new Ghost(this, r, c));
            int n = mElements.size() - 1;
            mGame[r][c] = mElements.get(n);
            mElements.get(n).draw(mGameCanvas);
        } else if (mMaze[r][c] == player) {
            mMaze[r][c] = cake;
            mElements.add(0, new Ashman(this, r, c));
            mGame[r][c] = mElements.get(0);
            mElements.get(0).draw(mGameCanvas);
        }
    }

    private void addEntities(int n, boolean start) {
        while (n >= 0) {
            int r = (int) (Math.random() * 20);
            int c = (int) (Math.random() * 20);
            if (mMaze[r][c] == 2) {
                if (n > 0) {
                    mMaze[r][c] = 3;
                    n--;
                } else if (start) {
                    mMaze[r][c] = 4;
                    n--;
                }
            }
        }
    }

    public void updateMaze(boolean p, int oldX, int oldY, int newX, int newY) {
        Element temp = mGame[oldX / imageWidth][oldY / imageWidth];
        mGame[oldX / imageWidth][oldY / imageWidth] = null;
        mGame[newX / imageWidth][newY / imageWidth] = temp;
        if (mMaze[newX / imageWidth][newY / imageWidth] == cake) {
            if (p) {
                mMaze[newX / imageWidth][newY / imageWidth] = empty;
                score++;
            }
        }
        draw(oldX / imageWidth, oldY / imageWidth);

    }

    public boolean collision(int x, int y) {
        return x <= 0 - 1 || x >= 19 * imageWidth + 1 || y <= 0 - 1 || y >= 19 * imageWidth + 1 ||
                mMaze[x / imageWidth][y / imageWidth] == wall || mMaze[(x + imageWidth - 1) / imageWidth][y / imageWidth] == wall ||
                mMaze[x / imageWidth][(y + imageWidth - 1) / imageWidth] == wall || mMaze[(x + imageWidth - 1) / imageWidth][(y + imageWidth - 1) / imageWidth] == wall;
    }

    private boolean ghostCollision() {
        Element player = mElements.get(0);
        boolean collision = false;
        for(int i = 1; i < mElements.size(); i++){
            Element ghost = mElements.get(i);
            collision = player.getX()/imageWidth == ghost.getX()/imageWidth && player.getY()/imageWidth == ghost.getY()/imageWidth;
        }
        return collision;
    }

    private boolean doSomeWork() {
        mWorkStep++;
        mGameCanvas.getGraphicsContext2D().clearRect(0, 0, 520, 520);
        mElements.forEach(element -> element.draw(mGameCanvas));
        return ghostCollision() || score == cakeCount;
    }
}
