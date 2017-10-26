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
import javafx.stage.Stage;

public class Main extends Application {
    private Label mStatus;
    private Canvas mTempCanvas, mPermCanvas;
    private Point2D mStart, mEnd;
    private int mWidthind = 0;
    private int mColorind = 0;
    private double mWidthCur = 1.0;
    private Color mColorCur = Color.BLACK;
    private ToggleGroup mWidthGroup = new ToggleGroup();
    private ToggleGroup mColorGroup = new ToggleGroup();
    private ToolBarPs mToolBarPos = ToolBarPs.LEFT;
    private BorderPane mRoot = new BorderPane();
    private ToolBar mToolBar = buildToolBar();
    private MenuBar mTopMenu = buildMenus();
    private static double[] mWidthMap = new double[]{
            1.0, 4.0, 8.0
    };
    private static final Color[] mColorMap = new Color[]{
            Color.BLACK, Color.RED, Color.GREEN, Color.BLUE
    };

    @Override
    public void start(Stage primaryStage) {
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

        mStatus = new Label("Everything is Copacetic");
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
            if(mTempCanvas.contains(mEnd))
                drawLine(mTempCanvas, Color.BLACK);
        });
        mPermCanvas.setOnMouseReleased((event) -> {
            setStatus("Mouse released");
            mEnd = getPoint(event);
            clearCanvas(mTempCanvas);
            if(mPermCanvas.contains(mEnd))
                drawLine(mPermCanvas, mColorCur);
        });

        primaryStage.setTitle("AToenniessen_Lab4");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    private void setStatus(String s){
        mStatus.setText(s);
    }
    private ToolBar buildToolBar(){
        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        Button one = new Button(); Button two = new Button(); Button three = new Button();
        Button four = new Button(); Button five = new Button(); Button six = new Button();

        one.setTooltip(new Tooltip("New"));
        one.setGraphic(new ImageView("ToolBar_Images/New.png"));
        one.setOnAction(actionEvent -> clearCanvas(mPermCanvas));

        toolBar.getItems().add(one);

        two.setTooltip(new Tooltip("Open"));
        two.setGraphic(new ImageView("ToolBar_Images/Open.png"));
        two.setOnAction(actionEvent -> setStatus("Open file"));

        toolBar.getItems().add(two);

        three.setTooltip(new Tooltip("Save"));
        three.setGraphic(new ImageView("ToolBar_Images/Save.png"));
        three.setOnAction(actionEvent -> setStatus("Save"));

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
    private void onToolBarMoved(){
        switch (mToolBarPos){
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
        switch(mToolBarPos){
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
    private Menu buildFile(){
        Menu fileMenu = new Menu("_File");

        MenuItem[] fileItems = new MenuItem[]{
                new MenuItem("_New"), new MenuItem("_Open"),
                new MenuItem("_Save"), new MenuItem("_Save As"),
                new SeparatorMenuItem(), new MenuItem("_Exit")
        };
        fileItems[0].setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        fileItems[0].setOnAction(actionEvent -> clearCanvas(mPermCanvas));

        fileItems[1].setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        fileItems[1].setOnAction(actionEvent -> setStatus("Open File"));

        fileItems[2].setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        fileItems[2].setOnAction(actionEvent -> setStatus("Save File"));

        fileItems[3].setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        fileItems[3].setOnAction(actionEvent -> setStatus("Save File As"));

        fileItems[4].setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        fileItems[4].setOnAction(actionEvent -> Platform.exit());

        fileMenu.getItems().addAll(fileItems);
        return fileMenu;
    }
    private Menu buildWidth(){
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
    private void onWidthShowing(){
        mWidthGroup.selectToggle(mWidthGroup.getToggles().get(mWidthind));
    }
    private Menu buildColor(){
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
    private void onColorShowing(){
        mColorGroup.selectToggle(mColorGroup.getToggles().get(mColorind));
    }
    private Menu buildHelp(){
        Menu helpMenu = new Menu("_Help");

        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());

        helpMenu.getItems().add(aboutMenuItem);
        return helpMenu;
    }
    private MenuBar buildMenus(){
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(buildFile(), buildWidth(), buildColor(), buildHelp());
        return menuBar;
    }
    private void onWidth(int val, boolean cycle){
        if(cycle){
            if(mWidthind >= mWidthMap.length - 1)
                mWidthind = 0;
            else
                mWidthind++;
        }
        else
            mWidthind = val;
        mWidthCur = mWidthMap[mWidthind];
    }
    private void onColor(int val, boolean cycle){
        if(cycle){
            if(mColorind >= mColorMap.length - 1)
                mColorind = 0;
            else
                mColorind++;
        }
        else
            mColorind = val;
        mColorCur = mColorMap[mColorind];
    }
    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Alexander Toenniessen, CSCD 370 Lab 4, Fall 2017");
        alert.showAndWait();
    }
    private void drawLine(Canvas canvas, Paint color){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(color);
        gc.setLineWidth(mWidthCur);
        gc.strokeLine(mStart.getX(), mStart.getY(), mEnd.getX(), mEnd.getY());
    }
    private void clearCanvas(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
    private Point2D getPoint(MouseEvent event){
        return new Point2D(event.getX(), event.getY());
    }
    private Canvas blankCanvas(int w, int h, Paint color){
        Canvas newCanvas = new Canvas(w, h);
        newCanvas.getGraphicsContext2D().setFill(color);
        newCanvas.getGraphicsContext2D().fillRect(0, 0, newCanvas.getWidth(), newCanvas.getHeight());
        return newCanvas;
    }
    public enum ToolBarPs{
        LEFT, TOP, RIGHT
    }
}