import game.Direction;
import game.GameController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class TetrisApp extends Application {







    private GameController game;
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tetris.fxml"));
        Parent root = loader.load();
        TetrisController tc = loader.getController();
        Scene scene = new Scene(root);
        tc.setScene(scene);
        tc.init();
        primaryStage.setScene(scene);
        primaryStage.show();







    }



    public static void main(String[] args) {
        launch(args);

    }
}
