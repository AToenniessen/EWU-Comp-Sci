package AshMan;

import AshMan.GameElements.*;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
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
            long elapsed = (now - mPreviousTime);
            if (elapsed > 1) { //TIMER_MSEC
                // do some of the work and quit when done
                if (doSomeWork()) {
                    mTimer.stop();
                    if(score != cakeCount)
                        deadAlert();
                    else
                        winAlert();
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
    private Element[][] mGame;
    ArrayList<Element> mElements;
    private Wall mWall = new Wall();
    private Cake mCake = new Cake();
    Canvas mBoardCanvas;
    Canvas mGameCanvas;
    private int score = 0;
    private int cakeCount = 0;

    Maze(Canvas b, Canvas g, int curMaze) {
        mMaze = deepClone(mMazes[curMaze]);
        mGame = new Element[20][20];
        mElements = new ArrayList<>();
        countCakes();
        mBoardCanvas = b;
        mGameCanvas = g;
        addEntities(2, true);
        initalizeGame();
    }
    private void countCakes(){
        for (int[] aMMaze : mMaze) {
            for(int i : aMMaze) {
                if (i == cake)
                    cakeCount++;
            }
        }
    }
    private void deadAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You Lose!");
        alert.setHeaderText("Looks like you died");
        alert.show();
    }
    private void winAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You Win!");
        alert.setHeaderText("You ate all the cakes!");
        alert.show();
    }
    void reset(){
        pause();
        cakeCount = 0;
        score = 0;
        mGame = new Element[20][20];
        mMaze = deepClone(mMazes[0]);
        mElements.clear();
        countCakes();
        mBoardCanvas.getGraphicsContext2D().clearRect(0,0,mBoardCanvas.getWidth(), mBoardCanvas.getHeight());
        mGameCanvas.getGraphicsContext2D().clearRect(0,0,mGameCanvas.getWidth(), mGameCanvas.getHeight());
        addEntities(2, true);
        initalizeGame();
    }
    private int[][] deepClone(int[][] array){
        int[][] clone = new int[array.length][array.length];
        for(int r = 0; r < array.length; r++){
            System.arraycopy(array[r], 0, clone[r], 0, array.length);
        }
        return clone;
    }
    void go() {
        mTimer.start();
    }

    void pause() {
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

    private boolean ghostCollision() { //fix collision detection for ghosts. works on one ghost
        Element ashman = mElements.get(0);
        boolean collision = false;
        for(int i = 1; i < mElements.size(); i++){
            Element ghost = mElements.get(i);
            int ax = ashman.getX()/imageWidth;
            int ax1 = (ashman.getX() + 1)/imageWidth;
            int ay = ashman.getY()/imageWidth;
            int ay1 = (ashman.getY() + 1)/imageWidth;
            int gx = ghost.getX()/imageWidth;
            int gx1 = (ghost.getX() + 1)/imageWidth;
            int gy = ghost.getY()/imageWidth;
            int gy1 = (ghost.getY() + 1)/imageWidth;
            collision = (ax == gx || ax == gx1 || ax1 == gx || ax1 == gx1)&&(ay == gy || ay == gy1 || ay1 == gy || ay1 == gy1);
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
