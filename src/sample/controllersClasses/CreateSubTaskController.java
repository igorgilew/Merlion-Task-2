package sample.controllersClasses;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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
            if(Main.selectedTask.subTasks.stream().anyMatch(x->x.getTitle().equals(subTask.getTitle()))){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
               //alert.setTitle("");
                alert.setHeaderText("Подзадача с таким названием уже существует");
//        alert.setContentText("Нет элементов удовлетворяющих выбранным критериям");
                alert.showAndWait();
            }
            else{
                Main.selectedTask.subTasks.add(subTask);
                MainViewController.lvSubTaskContent.add(subTask);
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }

        });
    }

    private SubTask createSubTask() {
        return new SubTask(tfSubTaskTitle.getText(), taSubTaskDescription.getText());
    }
}
