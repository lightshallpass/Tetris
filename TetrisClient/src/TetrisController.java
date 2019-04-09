import game.Direction;
import game.GameController;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.Socket;

public class TetrisController {


    private Scene scene;

    private double time;
    private GameController game;
    @FXML
    private Pane pane;
    @FXML
    private Canvas canvas;
    @FXML
    public void initialize(){

    }


    public void init(){
        Socket socket = null;
        try {
            socket = new Socket("localhost",8090);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pane.setPrefSize(15*40,20*40);
        game = new GameController(socket,canvas.getGraphicsContext2D(),20,15);
        canvas.setWidth(15 * 40);
        canvas.setHeight(20 * 40);
        scene.setOnKeyPressed(e ->{
            if (e.getCode() == KeyCode.SPACE) {
                game.makeMove(p -> p.rotate(), p -> p.rotateBack(), false);
            } else if (e.getCode() == KeyCode.LEFT) {
                game.makeMove(p -> p.move(Direction.LEFT), p -> p.move(Direction.RIGHT), false);
            } else if (e.getCode() == KeyCode.RIGHT) {
                game.makeMove(p -> p.move(Direction.RIGHT), p -> p.move(Direction.LEFT), false);
            } else if (e.getCode() == KeyCode.DOWN) {
                game.makeMove(p -> p.move(Direction.DOWN), p -> p.move(Direction.UP), true);
            }

        });
        game.spawn();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                time += 0.017;

                if (time >= 1.5) {
                    game.update();
                    game.render();
                    time = 0;
                }
            }
        };
        timer.start();
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }


    public Canvas getCanvas(){
        return canvas;
    }



}
