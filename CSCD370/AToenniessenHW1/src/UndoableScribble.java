import javafx.scene.canvas.Canvas;

import javax.swing.undo.AbstractUndoableEdit;

public class UndoableScribble extends AbstractUndoableEdit {
    private Canvas mCurCanvas;
    private Canvas mUndoCanvas;
    private Canvas mRedoCanvas;

    UndoableScribble(Canvas c) {
        mCurCanvas = c;
        mUndoCanvas = Utilities.duplicateCanvas(c);
    }

    @Override
    public void undo() {
        mRedoCanvas = Utilities.duplicateCanvas(mCurCanvas);
        Utilities.copyCanvas(mUndoCanvas, mCurCanvas);
    }

    @Override
    public void redo() {
        Utilities.copyCanvas(mRedoCanvas, mCurCanvas);
    }

    @Override
    public String getPresentationName() {
        return "Scribble";
    }

    @Override
    public boolean canRedo() {
        return true;
    }
}