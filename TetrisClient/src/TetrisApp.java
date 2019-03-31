import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class TetrisApp extends Application {

    public static final int TILE_SIZE = 40;
    public static final int GRID_WIDTH = 15;
    public static final int GRID_HEIGHT = 20;

    @FXML
    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("tetris.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        Socket socket = new Socket("localhost",8090);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();




    }


    public static void main(String[] args) {
        launch(args);

    }
}
