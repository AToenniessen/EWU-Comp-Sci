package AshMan;

import AshMan.GameElements.Direction;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private Label mStatus;
    private Maze game;



    @Override
    public void start(Stage primaryStage) {
        game = new Maze(new Canvas(520,520), new Canvas(520, 520), 0);

        BorderPane root = new BorderPane();
        StackPane pane = new StackPane(game.mBoardCanvas, game.mGameCanvas);

        root.setCenter(pane);

        root.setTop(buildMenus());

        mStatus = new Label("Everything is Copacetic");
        ToolBar toolBar = new ToolBar(mStatus);
        root.setBottom(toolBar);

        Scene scene = new Scene(root);

        scene.setOnKeyPressed(event ->{
            Direction direction = Direction.stop;
            switch (event.getCode()){
                case UP:
                    direction = Direction.down;
                    break;
                case DOWN:
                    direction = Direction.up;
                    break;
                case LEFT:
                    direction = Direction.left;
                    break;
                case RIGHT:
                    direction = Direction.right;
                    break;
            }
            game.mElements.get(0).setDirection(direction);
        });

        primaryStage.setTitle("TicTacToe Grid");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(true);
    }
    public static void main(String[] args) {
        launch(args);
    }

    private void setStatus(String s) {
        mStatus.setText(s);
    }

    private void winAlert(){        //Finish win/loose prompts
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulation!");
        alert.setHeaderText("You Beat The Level");
        alert.showAndWait();
    }

    private MenuBar buildMenus() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("_File");
        MenuItem quitMenuItem = new MenuItem("_Quit");
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        quitMenuItem.setOnAction(actionEvent -> Platform.exit());
        fileMenu.getItems().add(quitMenuItem);

        Menu gameMenu = new Menu("_Game");
        MenuItem newMenuItem = new MenuItem("_New");
        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        newMenuItem.setOnAction(actionEvent -> {
            game.reset();
        });
        MenuItem pauseMenuItem = new MenuItem("_Pause");
        pauseMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
        pauseMenuItem.setDisable(true);
        MenuItem goMenuItem = new MenuItem("_Go");
        goMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));
        pauseMenuItem.setOnAction(actionEvent -> {
            game.pause();
            pauseMenuItem.setDisable(true);
            goMenuItem.setDisable(false);
        });
        goMenuItem.setOnAction(actionEvent -> {
            game.go();
            goMenuItem.setDisable(true);
            pauseMenuItem.setDisable(false);
        });

        gameMenu.getItems().addAll(newMenuItem, goMenuItem, pauseMenuItem);

        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);

        menuBar.getMenus().addAll(fileMenu, gameMenu, helpMenu);
        return menuBar;
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Alexander Toenniessen, CSCD 370 AshMan Project, Fall 2017");
        alert.showAndWait();
    }
}
