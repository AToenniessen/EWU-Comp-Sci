package AshMan.GameElements;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Cake {
    private Image image = new Image("Images/Cake.png");

    public int getPointVal(){
        return 1;
    }
    public void draw(Canvas c, int x, int y){
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.drawImage(image, x, y);
    }
}
