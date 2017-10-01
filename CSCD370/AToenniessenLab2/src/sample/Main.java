package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Main extends Application {
    private Label mStatus;
    private Canvas mTempCanvas, mPermCanvas;
    private Point2D mStart, mEnd;

    @Override
    public void start(Stage primaryStage) {
        mTempCanvas = blankCanvas(400, 400, Color.WHITE);
        mPermCanvas = blankCanvas(400, 400, Color.TRANSPARENT);
        StackPane tPane = new StackPane();
        tPane.getChildren().addAll(mTempCanvas, mPermCanvas);
        ScrollPane pane = new ScrollPane();
        pane.setContent(tPane);
        BorderPane root = new BorderPane();
        root.setCenter(pane);

        root.setTop(buildMenus());

        mStatus = new Label("Everything is Copacetic");
        ToolBar toolBar = new ToolBar(mStatus);
        root.setBottom(toolBar);

        Scene scene = new Scene(root, 400, 400);

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
                drawLine(mPermCanvas, Color.RED);
        });

        primaryStage.setTitle("AToenniessen_Lab2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void drawLine(Canvas canvas, Paint color){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(color);
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


    public static void main(String[] args) {
        launch(args);
    }
    public void setStatus(String s){
        mStatus.setText(s);
    }
    private MenuBar buildMenus(){
        MenuBar menuBar = new MenuBar();

        //Create File Menu
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

        //Create Width Menu
        Menu widthMenu = new Menu("_Width");

        RadioMenuItem[] widthItems = new RadioMenuItem[]{
                new RadioMenuItem("_1 Pixel"), new RadioMenuItem("_4 Pixels"),
                new RadioMenuItem("_8 Pixels")
        };
        widthItems[0].setOnAction(actionEvent -> setStatus("Pixel Width 1"));

        widthItems[1].setOnAction(actionEvent -> setStatus("Pixel Width 4"));

        widthItems[2].setOnAction(actionEvent -> setStatus("Pixel Width 8"));

        widthMenu.getItems().addAll(widthItems);

        //Create Color Menu
        Menu colorMenu = new Menu("_Color");

        RadioMenuItem[] colorItems = new RadioMenuItem[]{
                new RadioMenuItem("_Black"), new RadioMenuItem("_Red"),
                new RadioMenuItem("_Green"), new RadioMenuItem("_Blue")
        };
        colorItems[0].setOnAction(actionEvent -> setStatus("Black"));

        colorItems[1].setOnAction(actionEvent -> setStatus("Red"));

        colorItems[2].setOnAction(actionEvent -> setStatus("Green"));

        colorItems[3].setOnAction(actionEvent -> setStatus("Blue"));

        colorMenu.getItems().addAll(colorItems);

        //Create Help Menu
        Menu helpMenu = new Menu("_Help");

        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());

        helpMenu.getItems().add(aboutMenuItem);

        menuBar.getMenus().addAll(fileMenu, widthMenu, colorMenu, helpMenu);
        return menuBar;
    }
    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Alexander Toenniessen, CSCD 370 Lab 2, Fall 2017");
        alert.showAndWait();
    }
}
