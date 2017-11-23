package AshMan.GameElements;

import AshMan.Maze;
import javafx.scene.canvas.Canvas;

public abstract class Element {
    final int imageWidth = 26;
    int x, y;
    Direction direction;
    Maze maze;
    private boolean player;
    Element(Maze m, int r, int c, boolean p){
        maze = m;
        x = r * imageWidth;
        y = c * imageWidth;
        player = p;
        if(!p)
            direction = Direction.values()[(int)(Math.random() * 4)];
        else
            direction = Direction.stop;

    }
    public void setDirection(Direction d){
        if(this.isPlayer())
            direction = d;
    }
    public abstract void draw(Canvas c);
    public boolean isPlayer(){
        return player;
    }

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
            case stop:
                break;
        }
        if(!maze.collision(X, Y)){
            maze.updateMaze(player, x/imageWidth, y/imageWidth, X/imageWidth, Y/imageWidth);
            x = X;
            y = Y;
        }
        else if(!player)
            direction =  Direction.values()[(int)(Math.random() * 4)];
    }
}