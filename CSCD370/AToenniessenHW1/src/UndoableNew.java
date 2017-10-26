import javafx.scene.canvas.Canvas;

import javax.swing.undo.AbstractUndoableEdit;

public class UndoableNew extends AbstractUndoableEdit {
    private Canvas mCurCanvas;
    private Canvas mCopCanvas;

    UndoableNew(Canvas c){
        mCurCanvas = c;
        mCopCanvas = Utilities.duplicateCanvas(c);
    }
    @Override
    public void undo(){
        Utilities.copyCanvas(mCopCanvas, mCurCanvas);
    }
    @Override
    public String getPresentationName(){
        return "New";
    }

}
