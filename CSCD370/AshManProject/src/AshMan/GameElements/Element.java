package AshMan.GameElements;

import AshMan.Maze;
import javafx.scene.canvas.Canvas;

public abstract class Element {
    final int imageWidth = 26;
    int x, y;
    private Direction direction;
    Maze maze;
    Element(Maze m, int r, int c){
        maze = m;
        x = r * imageWidth;
        y = c * imageWidth;
        direction = Direction.values()[(int)(Math.random() * 4)];

    }
    public abstract void draw(Canvas c);

    void move(){
        int X = x;
        int Y = y;
        switch (direction){
            case up:
                Y++;
                break;
            case down:
                Y--;
                break;
            case left:
                X--;
                break;
            case right:
                X++;
                break;
        }
        if(!maze.collision(X, Y)){
            maze.updateMaze(x/imageWidth, y/imageWidth, X/imageWidth, Y/imageWidth);
            x = X;
            y = Y;
        }
    }
}