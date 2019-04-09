import game.Game;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
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
