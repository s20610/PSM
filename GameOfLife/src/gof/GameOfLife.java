package gof;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class GameOfLife extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gof.fxml")));

        primaryStage.setTitle("Game Of Life s20610");
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
