package sample.controllersClasses;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import logics.Task;
import logics.TaskStatus;

import static sample.controllersClasses.Main.primaryStage;

public class UpdateTaskViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField taTaskTitle;

    @FXML
    private TextArea tfTaskDescription;

    @FXML
    private DatePicker dpTaskDeadline;

    @FXML
    private TextField tfTaskTags;

    @FXML
    private Button btnTaskUpdate;

    @FXML
    private ChoiceBox<String> cbTaskStatus;

    @FXML
    void initialize() {
        cbTaskStatus.setItems(FXCollections.observableArrayList("Не выполнена", "Выполнена"));
        taTaskTitle.setText(Main.selectedTask.getTitle());
        tfTaskDescription.setText(Main.selectedTask.getDescription());
        tfTaskTags.setText(Main.selectedTask.getTags());
        dpTaskDeadline.setValue(Main.selectedTask.getDeadline());
        if(Main.selectedTask.getTaskStatus() == TaskStatus.Complete)cbTaskStatus.getSelectionModel().select("Выполнена");
        else cbTaskStatus.getSelectionModel().select("Не выполнена");
        btnTaskUpdate.setOnAction(event -> {
            //Main.tasks.remove(Main.selectedTask);
            //изменять лист вью
            Task oldTask = Main.selectedTask;
            Main.selectedTask.setTitle(taTaskTitle.getText());
            Main.selectedTask.setDescription(tfTaskDescription.getText());
            Main.selectedTask.setTaskStatus(cbTaskStatus.getSelectionModel().getSelectedItem().equals("Выполнена")?
                    TaskStatus.Complete : TaskStatus.InProgress);
            Main.selectedTask.setDeadline(dpTaskDeadline.getValue());
            Main.selectedTask.setTags(tfTaskTags.getText());

            MainViewController.lvTasksContent.set(MainViewController.lvTasksContent.indexOf(oldTask), Main.selectedTask);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        });
    }
}
