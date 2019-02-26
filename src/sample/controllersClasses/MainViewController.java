package sample.controllersClasses;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logics.SubTask;
import logics.Task;
import logics.TaskStatus;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Observable;
import java.util.ResourceBundle;

import static sample.controllersClasses.Main.primaryStage;

public class MainViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane rootGridPane;

    @FXML
    private GridPane leftGridPane;

    @FXML
    private AnchorPane leftBottomAnchorPane;

    @FXML
    private AnchorPane leftCenterAnchorPane;

    @FXML
    private Button btnCreateTask;

    @FXML
    private Button btnAlterTask;

    @FXML
    private Button btnDeleteTask;

    @FXML
    private Button btnAddSubTask;

    @FXML
    private Button btnUpdatesubTask;

    @FXML
    private Button btnDeleteSubTask;

    @FXML
    private AnchorPane leftTopAnchorPane;

    @FXML
    private Label lbName;

    @FXML
    private Label lbSurname;

    @FXML
    private GridPane rightGridPane;

    @FXML
    private AnchorPane rightTopAnchorPane;

    @FXML
    private TextField tfDescrTitle;

    @FXML
    private TextArea taDescrDescr;

    @FXML
    private DatePicker dpDescrDeadline;

    @FXML
    private TextField tfDescrTags;

    @FXML
    private Label labelTaskStatus;

    @FXML
    private AnchorPane rightBottomAnchorPane;

    @FXML
    private AnchorPane centerAnchorPane;

    @FXML
    private TextField tfSubTaskTitle;

    @FXML
    private TextArea taSubTaskDescr;

    @FXML
    private Label labelSubTaskStatus;

    @FXML
    private Button btnSubTuskDown;

    @FXML
    private Button btnSubTuskUp;

    @FXML
    private ListView<Task> lvTasks;

    static ObservableList<Task> lvTasksContent = FXCollections.observableArrayList(Main.tasks);

    @FXML
    private ListView<SubTask> lvSubTask;

    static ObservableList<SubTask> lvSubTaskContent = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        lvTasks.setItems(lvTasksContent);
        lvTasks.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        lvSubTask.setItems(lvSubTaskContent);
        lvSubTask.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        lvTasks.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue<? extends Task> observable, Task oldValue, Task newValue) {
                Main.selectedTask = newValue;
                updateDescrPanel(newValue);

                lvSubTaskContent.clear();
                lvSubTaskContent.addAll(newValue.subTasks);
                Main.selectedSubTask = null;
                clearDescrSubTask();
            }
        });

        lvSubTask.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SubTask>() {
            @Override
            public void changed(ObservableValue<? extends SubTask> observable, SubTask oldValue, SubTask newValue) {
                Main.selectedSubTask = newValue;
                updateDescrSubTask(newValue);
            }
        });



        btnCreateTask.setOnAction(event -> {

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/viewsFXMLs/createTaskView.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene secondScene = new Scene(root, 660, 560);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Create Task");
            newWindow.setScene(secondScene);

            // Set position of second window, related to primary window.
            newWindow.setX(primaryStage.getX() + 200);
            newWindow.setY(primaryStage.getY() + 100);

            newWindow.show();
        });
        btnAddSubTask.setOnAction(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/viewsFXMLs/createSubTaskView.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene secondScene = new Scene(root, 660, 400);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Create SubTask");
            newWindow.setScene(secondScene);

            // Set position of second window, related to primary window.
            newWindow.setX(primaryStage.getX() + 200);
            newWindow.setY(primaryStage.getY() + 100);

            newWindow.show();
        });
        btnDeleteSubTask.setOnAction(event -> {
            Main.selectedTask.subTasks.remove(Main.selectedSubTask);
            lvSubTaskContent.remove(Main.selectedSubTask);
            if(lvSubTaskContent.isEmpty()){clearDescrSubTask(); Main.selectedSubTask = null;}
        });
        btnDeleteTask.setOnAction(event -> {
            Main.selectedTask.subTasks.clear();
            lvSubTaskContent.clear();
            clearDescrSubTask();
            Main.tasks.remove(Main.selectedTask);
            lvTasksContent.remove(Main.selectedTask);
            if(lvTasksContent.isEmpty()){clearDescrTask(); Main.selectedTask = null;}
        });
        btnSubTuskDown.setOnAction(event -> {
            try
            {
                int curSubTaskIndx = lvSubTaskContent.indexOf(Main.selectedSubTask);
                Collections.swap(lvSubTaskContent, curSubTaskIndx, curSubTaskIndx+1);
                Collections.swap(Main.selectedTask.subTasks, curSubTaskIndx, curSubTaskIndx+1);
                lvSubTask.getSelectionModel().select(curSubTaskIndx+1);
                lvSubTask.getFocusModel().focus(curSubTaskIndx+1);
                lvSubTask.scrollTo(curSubTaskIndx+1);
            }
            catch (IndexOutOfBoundsException e){
                System.out.println(e.getMessage());
            }

        });
        btnSubTuskUp.setOnAction(event -> {
            try{
                int curSubTaskIndx = lvSubTaskContent.indexOf(Main.selectedSubTask);
                Collections.swap(lvSubTaskContent, curSubTaskIndx, curSubTaskIndx-1);
                Collections.swap(Main.selectedTask.subTasks, curSubTaskIndx, curSubTaskIndx-1);
                lvSubTask.getSelectionModel().select(curSubTaskIndx-1);
                lvSubTask.getFocusModel().focus(curSubTaskIndx-1);
                lvSubTask.scrollTo(curSubTaskIndx-1);
            }
            catch (IndexOutOfBoundsException e){
                System.out.println(e.getMessage());
            }

        });

        btnAlterTask.setOnAction(event -> {

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/viewsFXMLs/updateTaskView.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene secondScene = new Scene(root, 660, 560);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Update Task");
            newWindow.setScene(secondScene);
            newWindow.initOwner(primaryStage);

            // Set position of second window, related to primary window.
            newWindow.setX(primaryStage.getX() + 200);
            newWindow.setY(primaryStage.getY() + 100);
            newWindow.show();
            newWindow.setOnHidden(event1 -> {
                updateDescrPanel(Main.selectedTask);
                lvTasks.getSelectionModel().select(Main.selectedTask);
            });
        });
    }

    public void updateDescrPanel(Task task) {
        tfDescrTitle.setText(task.getTitle());
        taDescrDescr.setText(task.getDescription());
        dpDescrDeadline.setValue(task.getDeadline());
        tfDescrTags.setText(task.getTags());
        labelTaskStatus.setText(task.getTaskStatus() == TaskStatus.Complete? "выполнено":"не выполнено");
    }

    private void updateDescrSubTask(SubTask subTask) {
        tfSubTaskTitle.setText(subTask.getTitle());
        taSubTaskDescr.setText(subTask.getDescription());
        labelSubTaskStatus.setText(subTask.getTaskStatus() == TaskStatus.Complete? "выполнено":"не выполнено");
    }

    private void clearDescrSubTask()
    {
        tfSubTaskTitle.setText("");
        taSubTaskDescr.setText("");
        labelSubTaskStatus.setText("");
    }

    private void clearDescrTask() {
        tfDescrTitle.setText("");
        taDescrDescr.setText("");
        dpDescrDeadline.setValue(null);
        tfDescrTags.setText("");
        labelTaskStatus.setText("");
    }
}