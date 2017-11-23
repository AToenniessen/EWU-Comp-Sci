package AshMan.GameElements;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Wall {
    private Image image = new Image("Images/Wall.png");
    public void draw(Canvas c, int x, int y){
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.drawImage(image, x, y);
    }
}
