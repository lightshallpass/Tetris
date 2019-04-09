import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class SettingController {

    @FXML
    private Button start;

    @FXML
    private TextField height;

    @FXML
    private TextField width;

    @FXML
    private Label error;

    private Stage stage;


    @FXML
    public void initialize(){

        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int wit = 0;
                int hei  = 0;
                try{
                    wit = Integer.parseInt(width.getText());
                    hei = Integer.parseInt(height.getText());
                } catch (Exception e){
                    error.setText("Uncorrect values");
                }
                if (wit > 0 && hei > 0 && wit <= 30 && hei <= 30){
                    openNextScene(wit,hei);
                }

            }
        });

    }


    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void openNextScene(int width, int height){

        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        try {
            Parent root = loader.load();
            GameController gc = loader.getController();
            gc.setStage(stage);
            gc.setGridWidth(width);
            gc.setGridHeight(height);
            gc.init();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
