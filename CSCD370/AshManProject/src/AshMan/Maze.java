package AshMan;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

class Maze {
    private final int empty = 0;
    private final int wall = 1;
    private final int cake = 2;
    private final int ghost = 3;
    private final int player = 4;
    private final int imageWidth = 26;
    private int [][] mGame = {
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
            {2,1,2,2,2,1,2,2,2,1,2,2,2,1,2,2,2,1,2,2}};
    Canvas mCanvas;
    Maze(Canvas c){
        mCanvas = c;
        addGhost(2);
        Draw();
    }
    private void Draw(){
        GraphicsContext gc = mCanvas.getGraphicsContext2D();
        for(int r = 0; r < mGame.length; r++){
            for(int c = 0; c < mGame.length; c++){
                if(mGame[r][c] == wall){
                    gc.drawImage(new Image("Images/Wall.png"), r * imageWidth, c * imageWidth);
                }
                else if(mGame[r][c] == cake){
                    gc.drawImage(new Image("Images/Cake.png"), r * imageWidth, c * imageWidth);
                }
                else if(mGame[r][c] == ghost){
                    gc.drawImage(new Image("Images/Ghost.png"), r * imageWidth, c * imageWidth);
                }
                else if(mGame[r][c] == player){
                    gc.drawImage(new Image("Images/AshMan-Closed.png"), r * imageWidth, c * imageWidth);
                }
            }
        }
    }
    private void addGhost(int n){
        while(n >= 0){
            int r = (int)(Math.random() * 20);
            int c = (int)(Math.random() * 20);
            if(mGame[r][c] == 2){
                if(n > 0) {
                    mGame[r][c] = 3;
                    n--;
                }
                else{
                    mGame[r][c] = 4;
                    n--;
                }
            }
        }
    }
}
