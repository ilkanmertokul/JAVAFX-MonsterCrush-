package Game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private GameManager game;

    @Override
    public void start(Stage stage) throws Exception {
        game = new GameManager(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
