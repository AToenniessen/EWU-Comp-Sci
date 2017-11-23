package AshMan;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

class Maze {
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
                if(mGame[r][c] == 1){
                    gc.drawImage(new Image("Images/Wall.png"), r * 26, c * 26);
                }
                if(mGame[r][c] == 2){
                    gc.drawImage(new Image("Images/Cake.png"), r * 26, c * 26);
                }
            }
        }
    }
    private void addGhost(int n){
        while(n != 0){
            int r = (int)(Math.random() * 20);
            int c = (int)(Math.random() * 20);
            if(mGame[r][c] == 2){
                //create ghost object
            }
        }
    }
}