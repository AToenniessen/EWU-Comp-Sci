import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class Main extends Application {
    private Stage mPrimaryStage;
    private Label mStatus;
    private Canvas mTempCanvas, mPermCanvas;
    private ArrayList<LineObject> mLines = new ArrayList<>();
    private int mWidthind = 0;
    private int mColorind = 0;
    private ToggleGroup mWidthGroup = new ToggleGroup();
    private ToggleGroup mColorGroup = new ToggleGroup();
    private ToolBarPs mToolBarPos = ToolBarPs.LEFT;
    private BorderPane mRoot = new BorderPane();
    private Point2D mStart, mEnd;
    private double mWidthCur;
    private Color mColorCur = Color.BLACK;
    private ToolBar mToolBar = buildToolBar();
    private MenuBar mTopMenu = buildMenus();
    private boolean mSave = false;
    private File mCurrentSave = null;
    private static double[] mWidthMap = new double[]{
            1.0, 4.0, 8.0
    };
    private static final Color[] mColorMap = new Color[]{
            Color.BLACK, Color.RED, Color.GREEN, Color.BLUE
    };

    @Override
    public void start(Stage primaryStage) {
        mPrimaryStage = primaryStage;
        mTempCanvas = blankCanvas(400, 400, Color.WHITE);
        mPermCanvas = blankCanvas(400, 400, Color.TRANSPARENT);
        StackPane tPane = new StackPane();
        tPane.getChildren().addAll(mTempCanvas, mPermCanvas);
        ScrollPane pane = new ScrollPane();
        pane.setContent(tPane);
        mRoot.setCenter(pane);

        VBox menu = new VBox(mTopMenu);

        mRoot.setTop(menu);
        mRoot.setLeft(buildToolBar());

        mStatus = new Label("Untitled");
        ToolBar toolBar = new ToolBar(mStatus);
        mRoot.setBottom(toolBar);

        Scene scene = new Scene(mRoot, 400, 400);

        mPermCanvas.setOnMousePressed((event) -> {
            setStatus("Mouse Pressed");
            mStart = getPoint(event);
        });
        mPermCanvas.setOnMouseDragged((event) -> {
            setStatus("Mouse Dragging");
            mEnd = getPoint(event);
            clearCanvas(mTempCanvas);
            if (mTempCanvas.contains(mEnd))
                drawLine();
        });
        mPermCanvas.setOnMouseReleased((event) -> {
            setStatus("Mouse released");
            mEnd = getPoint(event);
            clearCanvas(mTempCanvas);
            mSave = true;
            if (mPermCanvas.contains(mEnd)){
                LineObject line = new LineObject(mStart, mEnd, mWidthCur, mColorCur);
                line.drawLine(mPermCanvas);
                mLines.add(line);
            }
            if(!mPrimaryStage.getTitle().contains("*"))
                mPrimaryStage.setTitle(mPrimaryStage.getTitle() + "*");
        });
        mPrimaryStage.setOnCloseRequest(actionEvent -> {
            actionEvent.consume();
            exit();
        });
        mPrimaryStage.setTitle("Untitled");
        mPrimaryStage.setScene(scene);
        mPrimaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setStatus(String s) {
        mStatus.setText(s);
    }

    private void newFile() {
        mPrimaryStage.setTitle("Untitled");
        mCurrentSave = null;
        mSave = false;
        clearCanvas(mPermCanvas);
    }

    private ToolBar buildToolBar() {
        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        Button one = new Button();
        Button two = new Button();
        Button three = new Button();
        Button four = new Button();
        Button five = new Button();
        Button six = new Button();

        one.setTooltip(new Tooltip("New"));
        one.setGraphic(new ImageView("ToolBar_Images/New.png"));
        one.setOnAction(actionEvent -> unsaved());

        toolBar.getItems().add(one);

        two.setTooltip(new Tooltip("Open"));
        two.setGraphic(new ImageView("ToolBar_Images/Open.png"));
        two.setOnAction(actionEvent -> {
            onOpen();
        });

        toolBar.getItems().add(two);

        three.setTooltip(new Tooltip("Save"));
        three.setGraphic(new ImageView("ToolBar_Images/Save.png"));
        three.setOnAction(actionEvent -> {
            saveAs();
        });

        toolBar.getItems().addAll(three, new Separator());

        four.setTooltip(new Tooltip("Width"));
        four.setGraphic(new ImageView("ToolBar_Images/Width.png"));
        four.setOnAction(actionEvent -> {
            onWidth(mWidthind, true);
        });

        toolBar.getItems().addAll(four, new Separator());

        five.setTooltip(new Tooltip("Color"));
        five.setGraphic(new ImageView("ToolBar_Images/Color.png"));
        five.setOnAction(actionEvent -> {
            onColor(mColorind, true);
        });

        toolBar.getItems().addAll(five, new Separator());

        six.setTooltip(new Tooltip("Move this toolbar"));
        six.setGraphic(new ImageView("ToolBar_Images/Move.png"));
        six.setOnAction(actionEvent -> {
            onToolBarMoved();
        });

        toolBar.getItems().add(six);

        return toolBar;
    }

    private void save() {
        if (mSave)
            onSave(false);
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("File up to date");
            alert.setHeaderText("There is nothing to save");
            alert.showAndWait();
        }
    }
    private void saveAs(){
        onSave(true);
    }
    private void unsaved(){
        if(mSave) {
            if (discardPrompt()) {
                newFile();
            }
        }
        else{
            newFile();
        }
    }

    private void onSave(boolean prompt) {
        File curFile = mCurrentSave;
        if (mCurrentSave == null || prompt) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Line File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Line Files", "*.line"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            if (mCurrentSave != null) {
                fileChooser.setInitialFileName(mCurrentSave.getName());
            }
            curFile = fileChooser.showSaveDialog(mPrimaryStage);
        }
        if (curFile != null) {
            try {
                FileOutputStream out = new FileOutputStream(curFile);
                ObjectOutputStream obj = new ObjectOutputStream(out);
                obj.writeObject(mLines);
                obj.close();
            } catch (Exception e) {
                errorHandle(e);
                return;
            }
            mCurrentSave = curFile;
            mPrimaryStage.setTitle(mCurrentSave.getName());
            mSave = false;
        }
    }
    private boolean discardPrompt(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Unsaved work detected");
        alert.setHeaderText("You are about to discard unsaved work");
        alert.setContentText("Would you like to continue?");

        alert.showAndWait();
        return alert.getResult() == ButtonType.OK;
    }
    private ButtonType savePrompt() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Save?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.setTitle("Unsaved work detected");
        alert.setHeaderText("Would you like to save your work?");
        alert.showAndWait();

        return alert.getResult();
    }
    private void onOpen(){
        if(mSave) {
            ButtonType d = savePrompt();
            if (d == ButtonType.YES) {
                save();
                clearCanvas(mPermCanvas);
                open();
            }
            else if (d == ButtonType.NO){
                clearCanvas(mPermCanvas);
                open();
            }
        }
        else {
            clearCanvas(mPermCanvas);
            open();
        }
    }
    private void open(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a Line File");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Line Files", "*.line"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File curFile = fileChooser.showOpenDialog(mPrimaryStage);
        if (curFile == null) return;
        try {
            FileInputStream in = new FileInputStream(curFile);
            ObjectInputStream obj = new ObjectInputStream(in);
            mLines = (ArrayList<LineObject>) obj.readObject();
            for(LineObject l : mLines){
                l.drawLine(mPermCanvas);
            }
            obj.close();
            mSave = false;
        } catch (Exception e) {
            errorHandle(e);
            return;
        }
        mCurrentSave = curFile;
        mPrimaryStage.setTitle(mCurrentSave.getName());
    }
    private void errorHandle(Exception e){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(e.getMessage());
        alert.showAndWait();
    }

    private void onToolBarMoved() {
        switch (mToolBarPos) {
            case LEFT:
                mToolBarPos = ToolBarPs.TOP;
                break;
            case TOP:
                mToolBarPos = ToolBarPs.RIGHT;
                break;
            case RIGHT:
                mToolBarPos = ToolBarPs.LEFT;
                break;
        }
        switch (mToolBarPos) {
            case LEFT:
                mRoot.setRight(null);
                mRoot.setLeft(mToolBar);
                break;
            case TOP:
                mRoot.setLeft(null);
                mToolBar.setOrientation(Orientation.HORIZONTAL);
                mRoot.setTop(new VBox(mTopMenu, mToolBar));
                break;
            case RIGHT:
                mRoot.setTop(null);
                mRoot.setTop(new VBox(mTopMenu));
                mToolBar.setOrientation(Orientation.VERTICAL);
                mRoot.setRight(mToolBar);
                break;
        }
    }

    private Menu buildFile() {
        Menu fileMenu = new Menu("_File");

        MenuItem[] fileItems = new MenuItem[]{
                new MenuItem("_New"), new MenuItem("_Open"),
                new MenuItem("_Save"), new MenuItem("_Save As"),
                new SeparatorMenuItem(), new MenuItem("_Exit")
        };
        fileItems[0].setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        fileItems[0].setOnAction(actionEvent -> unsaved());

        fileItems[1].setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        fileItems[1].setOnAction(actionEvent -> {
            onOpen();
        });

        fileItems[2].setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        fileItems[2].setOnAction(actionEvent -> {
            save();
        });

        fileItems[3].setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        fileItems[3].setOnAction(actionEvent -> {
            saveAs();
        });

        fileItems[5].setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        fileItems[5].setOnAction(actionEvent -> {
            exit();
        });

        fileMenu.getItems().addAll(fileItems);
        return fileMenu;
    }
    private void exit(){
        if(mPrimaryStage.getTitle().contains("*")){
            if(discardPrompt()){
                Platform.exit();
            }
        }
        else {
            Platform.exit();
        }
    }

    private Menu buildWidth() {
        Menu widthMenu = new Menu("_Width");

        RadioMenuItem[] widthItems = new RadioMenuItem[]{
                new RadioMenuItem("_1 Pixel"), new RadioMenuItem("_4 Pixels"),
                new RadioMenuItem("_8 Pixels")
        };
        widthItems[0].setOnAction(actionEvent -> {
            setStatus("Pixel Width 1");
            onWidth(0, false);
        });
        widthItems[0].setToggleGroup(mWidthGroup);
        widthItems[0].setSelected(true);

        widthItems[1].setOnAction(actionEvent -> {
            setStatus("Pixel Width 4");
            onWidth(1, false);
        });
        widthItems[1].setToggleGroup(mWidthGroup);

        widthItems[2].setOnAction(actionEvent -> {
            setStatus("Pixel Width 8");
            onWidth(2, false);
        });
        widthItems[2].setToggleGroup(mWidthGroup);

        widthMenu.getItems().addAll(widthItems);

        widthMenu.setOnShowing(event -> onWidthShowing());

        return widthMenu;
    }

    private void onWidthShowing() {
        mWidthGroup.selectToggle(mWidthGroup.getToggles().get(mWidthind));
    }

    private Menu buildColor() {
        Menu colorMenu = new Menu("_Color");

        RadioMenuItem[] colorItems = new RadioMenuItem[]{
                new RadioMenuItem("_Black"), new RadioMenuItem("_Red"),
                new RadioMenuItem("_Green"), new RadioMenuItem("_Blue")
        };
        colorItems[0].setOnAction(actionEvent -> {
            setStatus("Black");
            onColor(0, false);
        });
        colorItems[0].setToggleGroup(mColorGroup);
        colorItems[0].setSelected(true);

        colorItems[1].setOnAction(actionEvent -> {
            setStatus("Red");
            onColor(1, false);
        });
        colorItems[1].setToggleGroup(mColorGroup);

        colorItems[2].setOnAction(actionEvent -> {
            setStatus("Green");
            onColor(2, false);
        });
        colorItems[2].setToggleGroup(mColorGroup);

        colorItems[3].setOnAction(actionEvent -> {
            setStatus("Blue");
            onColor(3, false);
        });
        colorItems[3].setToggleGroup(mColorGroup);

        colorMenu.getItems().addAll(colorItems);

        colorMenu.setOnShowing(event -> onColorShowing());

        return colorMenu;
    }

    private void onColorShowing() {
        mColorGroup.selectToggle(mColorGroup.getToggles().get(mColorind));
    }

    private Menu buildHelp() {
        Menu helpMenu = new Menu("_Help");

        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());

        helpMenu.getItems().add(aboutMenuItem);
        return helpMenu;
    }

    private MenuBar buildMenus() {
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(buildFile(), buildWidth(), buildColor(), buildHelp());
        return menuBar;
    }

    private void onWidth(int val, boolean cycle) {
        if (cycle) {
            if (mWidthind >= mWidthMap.length - 1)
                mWidthind = 0;
            else
                mWidthind++;
        } else
            mWidthind = val;
        mWidthCur = mWidthMap[mWidthind];
    }

    private void onColor(int val, boolean cycle) {
        if (cycle) {
            if (mColorind >= mColorMap.length - 1)
                mColorind = 0;
            else
                mColorind++;
        } else
            mColorind = val;
        mColorCur = mColorMap[mColorind];
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Alexander Toenniessen, CSCD 370 Lab 5, Fall 2017");
        alert.showAndWait();
    }

    private void clearCanvas(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private Point2D getPoint(MouseEvent event) {
        return new Point2D(event.getX(), event.getY());
    }

    private Canvas blankCanvas(int w, int h, Paint color) {
        Canvas newCanvas = new Canvas(w, h);
        newCanvas.getGraphicsContext2D().setFill(color);
        newCanvas.getGraphicsContext2D().fillRect(0, 0, newCanvas.getWidth(), newCanvas.getHeight());
        return newCanvas;
    }

    private void drawLine() {
        mTempCanvas.getGraphicsContext2D().strokeLine(mStart.getX(), mStart.getY(), mEnd.getX(), mEnd.getY());
    }

    public enum ToolBarPs {
        LEFT, TOP, RIGHT
    }
}