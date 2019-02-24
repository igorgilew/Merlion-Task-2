package sample.controllersClasses;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import logics.SubTask;

public class CreateSubTaskController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfSubTaskTitle;

    @FXML
    private TextArea taSubTaskDescription;

    @FXML
    private Button btnSubTaskCreate;

    @FXML
    void initialize() {
        btnSubTaskCreate.setOnAction(event -> {
            SubTask subTask = createSubTask();
            Main.selectedTask.subTasks.add(subTask);
            MainViewController.lvSubTaskContent.add(subTask);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        });
    }

    private SubTask createSubTask() {
        return new SubTask(tfSubTaskTitle.getText(), taSubTaskDescription.getText());
    }
}
