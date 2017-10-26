
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Arrays;

public class Main extends Application {
    private Label mStatus;
    private String mBottomSegVal = "1233";

    @Override
    public void start(Stage primaryStage) {
        SevenSegment[] segments = new SevenSegment[5];
        for (int i = 0; i < segments.length; i++) {
            segments[i] = new SevenSegment(i);
        }
        Button btn = new Button("Increment");
        btn.setOnAction((ActionEvent event) -> {
            increment(segments);
        });
        StackPane mid = new StackPane(btn);
        StackPane.setAlignment(btn, Pos.CENTER);
        VBox v = new VBox(segments[0]);
        HBox h = new HBox(segments[1], segments[2], segments[3], segments[4]);
        GridPane holder = new GridPane();
        holder.setAlignment(Pos.CENTER);
        holder.add(v, 0, 0);
        holder.add(mid, 0, 1);
        holder.add(h, 0, 2);
        BorderPane root = new BorderPane();
        root.setCenter(holder);

        root.setTop(buildMenus());

        mStatus = new Label("Everything is Copacetic");
        ToolBar toolBar = new ToolBar(mStatus);
        root.setBottom(toolBar);

        Scene scene = new Scene(root, 500, 700);

        primaryStage.setTitle("Seven Segment Display");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    private void increment(SevenSegment[] seg){
        int increment = seg[0].getmValue() + 1;
        seg[0].setmValue(increment);
        seg[0].draw();
        if(increment > 10){
            Integer t = Integer.parseInt(mBottomSegVal);
            t++;
            mBottomSegVal = t.toString();
            incrementBottom(seg);
        }

    }
    private void incrementBottom(SevenSegment[] seg){
        int[] nums = Arrays.stream(mBottomSegVal.split("")).mapToInt(Integer::parseInt).toArray();
        seg[1].setmValue(nums[0]);
        seg[2].setmValue(nums[1]);
        seg[3].setmValue(nums[2]);
        seg[4].setmValue(nums[3]);
        seg[1].draw();
        seg[2].draw();
        seg[3].draw();
        seg[4].draw();
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
        alert.setHeaderText("Alexander Toenniessen, CSCD 370 Lab 3, Fall 2017");
        alert.showAndWait();
    }
}
