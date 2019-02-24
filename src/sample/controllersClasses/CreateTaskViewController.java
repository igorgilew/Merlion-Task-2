package sample.controllersClasses;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import logics.Task;

public class CreateTaskViewController {

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
    private Button btnTaskCreate;


    @FXML
    void initialize() {
        btnTaskCreate.setOnAction(event->{

            Task task = createTask();
            Main.tasks.add(task);
            MainViewController.lvTasksContent.add(task);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        });
    }
    private Task createTask()
    {
        return new Task(taTaskTitle.getText(), tfTaskDescription.getText(), dpTaskDeadline.getValue(), tfTaskTags.getText());
    }
}

