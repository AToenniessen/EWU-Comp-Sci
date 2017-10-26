import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;

class Utilities {
    static Canvas duplicateCanvas(Canvas c){
        WritableImage image = c.snapshot(null, null);
        Canvas copy = new Canvas(image.getWidth(),image.getHeight());
        copy.getGraphicsContext2D().drawImage(image, 0, 0);
        return copy;
    }
    static void copyCanvas(Canvas source, Canvas destination){
        WritableImage image = source.snapshot(null, null);
        destination.getGraphicsContext2D().drawImage(image, 0, 0);
    }
}
