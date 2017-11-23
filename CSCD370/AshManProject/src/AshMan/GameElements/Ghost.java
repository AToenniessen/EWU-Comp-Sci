package AshMan.GameElements;

import AshMan.Maze;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;


public class Ghost extends Element {
    private Image image = new Image("Images/Ghost.png");

    public Ghost(Maze m, int r, int c) {
        super(m, r, c, false);

    }

    public void draw(Canvas c) {
        move();
        c.getGraphicsContext2D().drawImage(image, x, y);
    }
}
