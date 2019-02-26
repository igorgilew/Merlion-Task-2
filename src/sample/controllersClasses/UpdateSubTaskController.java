package sample.controllersClasses;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import logics.SubTask;
import logics.Task;
import logics.TaskStatus;

public class UpdateSubTaskController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfSubTaskTitle;

    @FXML
    private TextArea taSubTaskDescription;

    @FXML
    private Button btnSubTaskUpdate;

    @FXML
    private ChoiceBox<String> cbSubTaskStatus;

    @FXML
    void initialize() {
        cbSubTaskStatus.setItems(FXCollections.observableArrayList("Не выполнена", "Выполнена"));
        tfSubTaskTitle.setText(Main.selectedSubTask.getTitle());
        taSubTaskDescription.setText(Main.selectedSubTask.getDescription());
        if(Main.selectedSubTask.getTaskStatus() == TaskStatus.Complete)cbSubTaskStatus.getSelectionModel().select("Выполнена");
        else cbSubTaskStatus.getSelectionModel().select("Не выполнена");
        btnSubTaskUpdate.setOnAction(event -> {
            Main.selectedSubTask.setTitle(tfSubTaskTitle.getText());
            Main.selectedSubTask.setDescription(taSubTaskDescription.getText());
            Main.selectedSubTask.setTaskStatus(cbSubTaskStatus.getSelectionModel().getSelectedItem().equals("Выполнена")?
                    TaskStatus.Complete : TaskStatus.InProgress);
//            //update lvSubTask
//           MainViewController.lvSubTaskContent.set(MainViewController.lvSubTaskContent.indexOf(oldSubTask), Main.selectedSubTask);

           ((Node)(event.getSource())).getScene().getWindow().hide();
        });
    }
}
