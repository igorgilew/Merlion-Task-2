package sample.controllersClasses;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
import logics.*;

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
    private CheckBox cbTitleSort;

    @FXML
    private RadioButton rbComplited;

    @FXML
    private ToggleGroup toggleGroup2;

    @FXML
    private RadioButton rbNotComplited;

    @FXML
    private ScrollPane scrollSort;

    @FXML
    private AnchorPane scrollAnchor;

    @FXML
    private RadioButton rbOverdueSort;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private RadioButton rbSoonSort;

    @FXML
    private RadioButton rbTagSort;

    @FXML
    private TextField tfTagForSort;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUseSort;

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
    private ListView<SubTask> lvSubTask;

    @FXML
    private Button btnSubTuskDown;

    @FXML
    private Button btnSubTuskUp;

    @FXML
    private AnchorPane centerAnchorPane;

    @FXML
    private ListView<Task> lvTasks;

    @FXML
    private TextField tfSubTaskTitle;

    @FXML
    private TextArea taSubTaskDescr;

    @FXML
    private Label labelSubTaskStatus;

    static ObservableList<Task> lvTasksContent = FXCollections.observableArrayList(Main.tasks);
    

    static ObservableList<SubTask> lvSubTaskContent = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        scrollSort.setVisible(false);
        dpDescrDeadline.setEditable(false);
        dpDescrDeadline.setOnMouseClicked(e -> dpDescrDeadline.hide());

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
            newWindow.setX(Main.primaryStage.getX() + 200);
            newWindow.setY(Main.primaryStage.getY() + 100);

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
            newWindow.setX(Main.primaryStage.getX() + 200);
            newWindow.setY(Main.primaryStage.getY() + 100);

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
            newWindow.initOwner(Main.primaryStage);

            // Set position of second window, related to primary window.
            newWindow.setX(Main.primaryStage.getX() + 200);
            newWindow.setY(Main.primaryStage.getY() + 100);
            newWindow.show();
            newWindow.setOnHidden(event1 -> {
                updateDescrPanel(Main.selectedTask);
                lvTasks.getSelectionModel().select(Main.selectedTask);
            });
        });
        btnUpdatesubTask.setOnAction(event -> {

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/viewsFXMLs/updateSubTask.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene secondScene = new Scene(root, 660, 560);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Update SubTask");
            newWindow.setScene(secondScene);
            newWindow.initOwner(Main.primaryStage);

            // Set position of second window, related to primary window.
            newWindow.setX(Main.primaryStage.getX() + 200);
            newWindow.setY(Main.primaryStage.getY() + 100);
            newWindow.show();
            newWindow.setOnHidden(event1 -> {
                updateDescrSubTask(Main.selectedSubTask);
                SubTask old = Main.selectedSubTask;
                lvSubTaskContent.clear();
                lvSubTaskContent.addAll(Main.selectedTask.subTasks);
                lvSubTask.getSelectionModel().select(old);
            });
        });

        btnUseSort.setOnAction(event->{
            if(cbTitleSort.isSelected() && !rbComplited.isSelected() &&!rbNotComplited.isSelected()){
                List<Task> tasksSorted = sortOnTitleGrow(Main.tasks);
                if(tasksSorted.size()>0){
                    lvTasks.setItems(FXCollections.observableArrayList(tasksSorted));
                }
            }
            if (rbComplited.isSelected() && !cbTitleSort.isSelected()) {
                List<Task> tasksSorted = sortOnStatus(TaskStatus.Complete);
                if(tasksSorted.size()>0){
                    lvTasks.setItems(FXCollections.observableArrayList(tasksSorted));
                }
                else{
                    showAlert();
                }
            }
            if(cbTitleSort.isSelected() && rbComplited.isSelected()){
                List<Task> tasksSorted = sortOnStatus(TaskStatus.Complete);
                tasksSorted = sortOnTitleGrow(tasksSorted);
                if(tasksSorted.size()>0){
                    lvTasks.setItems(FXCollections.observableArrayList(tasksSorted));
                }
                else
                {
                    showAlert();
                }
            }
            if(rbNotComplited.isSelected()  && !rbOverdueSort.isSelected() && !rbSoonSort.isSelected() && !rbTagSort.isSelected()){
                List<Task> tasksSorted = sortOnStatus(TaskStatus.InProgress);
                if(cbTitleSort.isSelected())
                    tasksSorted = sortOnTitleGrow(tasksSorted);
                if(tasksSorted.size()>0){
                    lvTasks.setItems(FXCollections.observableArrayList(tasksSorted));
                }
                else{
                    showAlert();
                }
            }

            if (rbNotComplited.isSelected() && rbOverdueSort.isSelected()) {
                List<Task> tasksSorted = sortOnOverdue();
                if(cbTitleSort.isSelected())
                    tasksSorted = sortOnTitleGrow(tasksSorted);

                if(tasksSorted.size()>0){
                    lvTasks.setItems(FXCollections.observableArrayList(tasksSorted));
                }
                else
                {
                    showAlert();
                }
            }
            if (rbNotComplited.isSelected() && rbSoonSort.isSelected()) {
                List<Task> tasksSorted = sortOnSoon();
                if(cbTitleSort.isSelected()) {
                    tasksSorted = sortOnDeadlineAndTitle(tasksSorted);
                }
                else{
                    tasksSorted = sortOnDeadlineOnly(tasksSorted);
                }

                if(tasksSorted.size()>0){
                    lvTasks.setItems(FXCollections.observableArrayList(tasksSorted));
                }
                else
                {
                    showAlert();
                }
            }
            if (rbNotComplited.isSelected() && rbTagSort.isSelected()) {

                List<Task> tasksSorted = sortOnTag();
                if(cbTitleSort.isSelected()) {
                    tasksSorted = sortOnTitleGrow(tasksSorted);
                }

                if(tasksSorted.size()>0){
                    lvTasks.setItems(FXCollections.observableArrayList(tasksSorted));
                }
                else
                {
                    showAlert();
                }
            }
        });
        btnReset.setOnAction(event -> {
            lvTasks.setItems(lvTasksContent);
        });
        rbNotComplited.setOnAction(event -> {
             scrollSort.setVisible(true);
        });
        rbComplited.setOnAction(event -> {
            scrollSort.setVisible(false);
        });
    }

    private List<Task> sortOnTitleGrow(List<Task> tasks) {
        List<Task> tasksSorted = new ArrayList<>(tasks);
        Comparator<Task> taskComparator = new TaskTitleComparator();
        tasksSorted.sort(taskComparator);
        return  tasksSorted;
    }
    private List<Task> sortOnStatus(TaskStatus status){
        List<Task> tasksFiltered = Main.tasks;
        return tasksFiltered.stream().filter(x->x.getTaskStatus() == status)
                .collect(Collectors.toList());
    }
    private List<Task> sortOnOverdue(){
        List<Task> tasksFiltered = Main.tasks;
        return tasksFiltered.stream().filter(x->(x.getDeadline().isBefore(LocalDate.now())) && x.getTaskStatus() == TaskStatus.InProgress)
                .collect(Collectors.toList());
    }

    private List<Task> sortOnSoon() {
        List<Task> tasksFiltered = Main.tasks;
        return tasksFiltered.stream().filter(x->(x.getDeadline().isBefore(LocalDate.now().plusWeeks(1))) && x.getDeadline().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
    }

    private List<Task> sortOnDeadlineAndTitle(List<Task> tasksSorted) {
        Comparator<Task> taskComparator = new TaskDeadlineComparator().thenComparing(new TaskTitleComparator());
        tasksSorted.sort(taskComparator);
        return tasksSorted;
    }
    private List<Task> sortOnDeadlineOnly(List<Task> tasksSorted) {
        Comparator<Task> taskComparator = new TaskDeadlineComparator();
        tasksSorted.sort(taskComparator);
        return tasksSorted;
    }

    private List<Task> sortOnTag() {
        List<Task> tasksFiltered = Main.tasks;
        String toCompare;
//        if(tfTagForSort.getText().equals("")) return tasksFiltered.stream().filter(x->x.getTags().equals(tfTagForSort.getText()))
//                .collect(Collectors.toList());
//        else {
//
//            return tasksFiltered.stream().filter(x->x.getTags().equals(tfTagForSort.getText()))
//                    .collect(Collectors.toList());
//        }
        return tasksFiltered.stream().filter(x->x.getTags().equals(tfTagForSort.getText()))
                .collect(Collectors.toList());



    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Сортировка");
        alert.setHeaderText("Результат: нет элементов удовлетворяющих выбранным критериям");
//        alert.setContentText("Нет элементов удовлетворяющих выбранным критериям");

        alert.showAndWait();
    }
    private void updateDescrPanel(Task task) {
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