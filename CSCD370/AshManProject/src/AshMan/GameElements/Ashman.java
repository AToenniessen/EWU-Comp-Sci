package AshMan.GameElements;

import AshMan.Maze;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Ashman extends Element {
    private Image closed = new Image("Images/AshMan-Closed.png");
    private Image open = new Image("Images/AshMan-Open.png");
    private long frame = 0;

    public Ashman(Maze m, int r, int c) {
        super(m, r, c);
    }

    public void draw(Canvas c) {
        move();
        maze.ghostCollision(x / imageWidth, y / imageWidth);
        frame++;
        GraphicsContext gc = c.getGraphicsContext2D();
        if (frame % 15 == 0)
            gc.drawImage(open, x, y);
        else
            gc.drawImage(closed, x, y);
    }
}
