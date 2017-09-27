package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private Label mStatus;
    private Canvas mTempCanvas;

    @Override
    public void start(Stage primaryStage) {
        blankCanvas();
        ScrollPane pane = new ScrollPane();
        pane.setContent(mTempCanvas);
        BorderPane root = new BorderPane();
        root.setCenter(pane);

        root.setTop(buildMenus());

        mStatus = new Label("Everything is Copacetic");
        ToolBar toolBar = new ToolBar(mStatus);
        root.setBottom(toolBar);

        Scene scene = new Scene(root, 400, 400);

        mTempCanvas.setOnMousePressed((event) -> setStatus("Mouse Pressed"));
        mTempCanvas.setOnMouseDragged((event) -> setStatus("Mouse Dragged"));
        mTempCanvas.setOnMouseReleased((event) -> setStatus("Mouse Released"));

        primaryStage.setTitle("AToenniessen_Lab2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void blankCanvas(){
        mTempCanvas = new Canvas(400,400);
        mTempCanvas.getGraphicsContext2D().setFill(Color.WHITE);
        mTempCanvas.getGraphicsContext2D().fillRect(0, 0, mTempCanvas.getWidth(), mTempCanvas.getHeight());
    }


    public static void main(String[] args) {
        launch(args);
    }
    public void setStatus(String s){
        mStatus.setText(s);
    }
    private MenuBar buildMenus(){
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("_File");
        MenuItem quitMenuItem = new MenuItem("_Quit");
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        quitMenuItem.setOnAction(actionEvent -> Platform.exit());
        fileMenu.getItems().add(quitMenuItem);

        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);

        menuBar.getMenus().addAll(fileMenu,helpMenu);
        return menuBar;
    }
    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Alexander Toenniessen, CSCD 370 Lab 2, Fall 2017");
        alert.showAndWait();
    }
}
