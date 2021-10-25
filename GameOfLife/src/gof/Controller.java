package gof;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private final int    DEFAULT_SIZE = 15;
    private final double DEFAULT_PROB = 0.3;

    @FXML
    private FlowPane base;
    @FXML
    private Button runButton, stopButton, clearButton;
    @FXML
    private HBox rootBox;

    private Board board;

    private JavaFXDisplayDriver display;

    private Timeline loop = null;

    private int windowWidth = 750;
    private int cellSizePx = 30;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createBoard(DEFAULT_SIZE, DEFAULT_PROB);
        attachResizeListener();
    }

    @FXML
    private void onRun(Event evt) {
        toggleButtons(false);

        loop = new Timeline(new KeyFrame(Duration.millis(300), e -> {
            board.update();
            display.displayBoard(board);
        }));

        loop.setCycleCount(100);
        loop.play();
    }

    @FXML
    private void onStop(Event evt) {
        toggleButtons(true);
        loop.stop();
    }

    @FXML
    private void onClear(Event evt) {
        createBoard(DEFAULT_SIZE, 0);
    }

    private void toggleButtons(boolean enable) {
        runButton.setDisable(!enable);
        clearButton.setDisable(!enable);
        stopButton.setDisable(enable);
    }

    private void createBoard(int size, double prob) {
        board = new Board(size, size, prob);
        createDisplay();
    }

    private void createDisplay() {
        display = new JavaFXDisplayDriver(board.getSize(), cellSizePx, board);

        base.getChildren().clear();
        base.getChildren().add(new Group(display.getPane()));
    }

    private void attachResizeListener() {
        ChangeListener<Number> sizeListener = (observable, oldValue, newValue) -> {
            int newWidth = newValue.intValue();
            if (newWidth > 250 && Math.abs(newWidth - windowWidth) >= 50) {
                windowWidth = newWidth;
                cellSizePx = newWidth / 25;
                createDisplay();
            }
        };
        rootBox.widthProperty().addListener(sizeListener);
    }
}
