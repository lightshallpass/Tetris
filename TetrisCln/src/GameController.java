import game.Direction;
import game.Game;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class GameController {

    @FXML
    private Pane pane;

    @FXML
    private Canvas canvas;

    public static final int TILE_SIZE = 40;
    private int gridWidth = 15;
    private int gridHeight = 20;
    private Game game;
    private double time;
    private Stage stage;


    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize(){



    }


    public void init(){
        pane.setPrefSize(TILE_SIZE * gridWidth,TILE_SIZE * gridHeight);
        canvas.setHeight(TILE_SIZE * gridHeight);
        canvas.setWidth(TILE_SIZE * gridWidth);
        game = new Game(gridWidth,gridHeight,canvas.getGraphicsContext2D());
        pane.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE){
                game.makeMove(p->p.rotate(),p->p.rotateBack(),false);
            } else if (e.getCode() == KeyCode.LEFT){
                game.makeMove(p->p.move(Direction.LEFT),p-> p.move(Direction.RIGHT), false);
            }
            else if (e.getCode() == KeyCode.RIGHT){
                game.makeMove(p->p.move(Direction.RIGHT),p-> p.move(Direction.LEFT), false);
            }
            else if (e.getCode() == KeyCode.DOWN){
                game.makeMove(p->p.move(Direction.DOWN),p-> p.move(Direction.UP), true);
            }
            game.render();
        });
        game.spawn();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                time += 0.017;
                if(time >= 0.5){
                    game.update();
                    game.render();
                    time = 0;
                }
            }
        };
        timer.start();


    }
    public int getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }
}
