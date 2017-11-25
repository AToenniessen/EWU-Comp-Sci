package AshMan.GameElements;

import AshMan.Maze;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ashman extends Element {
    private Image closed = new Image("Images/AshMan-Closed.png");
    private Image open = new Image("Images/AshMan-Open.png");
    private long frame = 0;

    public Ashman(Maze m, int r, int c) {
        super(m, r, c, true);
    }
    public void draw(Canvas c) {
        move();
        frame++;
        GraphicsContext gc = c.getGraphicsContext2D();
        if ((frame/13) % 2 == 0) {
            ImageView temp = new ImageView(open);
            switch(direction) {
                case down:
                    temp.setRotate(-90);
                    break;
                case up:
                    temp.setRotate(90);
                    break;
                case left:
                    temp.setRotate(180);
                    break;
                case right:
                    break;
                case stop:
                    temp = new ImageView(closed);
            }
                gc.drawImage(temp.snapshot(new SnapshotParameters(), null), x, y);
        }
        else
            gc.drawImage(closed, x, y);
    }
}
