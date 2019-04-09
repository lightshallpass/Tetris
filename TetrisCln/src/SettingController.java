import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class SettingController {

    @FXML
    private Button start;

    @FXML
    private TextField height;

    @FXML
    private TextField width;

    @FXML
    private Label error;




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
                if (wit != 0 && hei != 0){
                    openNextScene(wit,hei);
                }

            }
        });

    }


    private void openNextScene(int width, int height){

    }

}
