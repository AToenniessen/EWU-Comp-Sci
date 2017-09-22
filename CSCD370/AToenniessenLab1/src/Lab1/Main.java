package Lab1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application implements ChangeListener<String> {
    Label mStatus = new Label("Everything is Copacetic");
    ImageView mImageView = new ImageView();

    @Override
    public void start(Stage primaryStage) {

        setmImageView("logo.png");          //Sets image onto image view
        setStatus("First Album, 1979");

        ListView<String> list = new ListView<>();

        list.getSelectionModel().selectedItemProperty().addListener(this);

        list.setItems(FXCollections.observableArrayList("First Album", "Cindy", "Fred", "Kate", "Keith", "Matt", "Ricky"));

        list.setPrefWidth(getStringWidth(list.getItems().get(0)) * 1.3);

        BorderPane root = new BorderPane();
        root.setCenter(mImageView);


        root.setLeft(list);

        root.setTop(buildMenus());

        ToolBar toolBar = new ToolBar(mStatus);
        root.setBottom(toolBar);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Lab 1");
        primaryStage.setScene(scene);
        primaryStage.show();
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
        alert.setHeaderText("Alexander J Toenniessen, CSCD 370 Lab 1, Fall 2017");
        alert.showAndWait();
    }
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        switch (newValue){
            case "First Album":
                setmImageView("logo.png");
                setStatus("First Album, 1979");
                break;
            case "Cindy":
                setmImageView("cindy.png");
                setStatus("Cindy Wilson (Percussion since 1976)");
                break;
            case "Fred":
                setmImageView("fred.png");
                setStatus("Fred Schneider (Vocals, Cowbell, since 1976)");
                break;
            case "Kate":
                setmImageView("kate.png");
                setStatus("Kate Pierson (Organ since 1976)");
                break;
            case "Keith":
                setmImageView("keith.png");
                setStatus("Keith Strickland (Drums, Guitar, since 1976)");
                break;
            case "Matt":
                setmImageView("matt.png");
                setStatus("Matt Flynn (Touring, Drums, prior to 2004)");
                break;
            case "Ricky":
                setmImageView("rickey.png");
                setStatus("Ricky Wilson, deceased (Bass, 1976 - 1986)");
                break;
        }
    }
    private void setmImageView(String s){
        mImageView.setImage(new Image("images/" + s));
    }
    private double getStringWidth(String s){
        final Text text = new Text(s);
        return text.getLayoutBounds().getWidth();
    }
}
