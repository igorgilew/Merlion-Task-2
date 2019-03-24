package sample.controllersClasses;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

    @FXML
    private RadioButton rbAdvanced;

    @FXML
    private ComboBox<String> cbAdvanced;

    @FXML
    private TextField tfAdvancedTag;

    @FXML
    private TextField tfAdvancedSubstring;

    static ObservableList<Task> lvTasksContent = FXCollections.observableArrayList(Main.tasks);
    

    static ObservableList<SubTask> lvSubTaskContent = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        cbAdvanced.getItems().addAll(
                "Задачи на ближайший месяц, " +
                        "помеченные выбираемым тегом и содержащие в тексте описания указываемую подстроку",
                "Незавершённые задачи, в которых по крайней мере половина подзадач завершена",
                "Просроченные задачи, отмеченные по крайней мере одним " +
                        "из трёх самых часто используемых тегов среди всех задач",
                "Три задачи с ближайшим крайним сроком, отмеченные выбираемым тегом",
                "Четыре задачи с самым отдалённым крайним сроком, не отмеченные никаким тегом"
        );
        cbAdvanced.getSelectionModel().select(0);

        scrollSort.setVisible(false);

        dpDescrDeadline.setEditable(false);
        dpDescrDeadline.setOnMouseClicked(e -> dpDescrDeadline.hide());

        lvTasks.setItems(lvTasksContent);
        lvTasks.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        lvSubTask.setItems(lvSubTaskContent);
        lvSubTask.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        lvTasks.getItems().addListener((InvalidationListener) observable -> {
            //notFP
            if(lvTasks.getItems().isEmpty()){
                btnAlterTask.setDisable(true);
                btnDeleteTask.setDisable(true);
                btnAddSubTask.setDisable(true);
                btnDeleteSubTask.setDisable(true);
                btnUpdatesubTask.setDisable(true);
            }
            else{
                btnAddSubTask.setDisable(false);
                btnAlterTask.setDisable(false);
                btnDeleteTask.setDisable(false);
            }
        });
        lvSubTask.getItems().addListener((InvalidationListener) observable -> {
            if(lvSubTask.getItems().isEmpty()){
                btnDeleteSubTask.setDisable(true);
                btnUpdatesubTask.setDisable(true);
            }
        });

        lvTasks.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Optional<Task> newTask = Optional.ofNullable(newValue);
            newTask.ifPresent(x->{
                Main.selectedTask = x;
                updateDescrPanel(x);
                lvSubTaskContent.clear();
                lvSubTaskContent.addAll(x.subTasks);
                Main.selectedSubTask = null;
                clearDescrSubTask();
                if(lvSubTask.getSelectionModel().getSelectedIndex()<0){
                    btnDeleteSubTask.setDisable(true);
                    btnUpdatesubTask.setDisable(true);
                }
                else{
                    btnDeleteSubTask.setDisable(false);
                    btnUpdatesubTask.setDisable(false);
                }
            });
        });

        lvSubTask.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Optional<SubTask> newSubtask = Optional.ofNullable(newValue);
            newSubtask.ifPresent(x->
            {
                Main.selectedSubTask = x;
                updateDescrSubTask(x);
                btnDeleteSubTask.setDisable(false);
                btnUpdatesubTask.setDisable(false);
            });

        });

        
        btnCreateTask.setOnAction(event -> {
            lvTasks.setItems(lvTasksContent);
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
            //notFP
            if(lvSubTaskContent.isEmpty()){clearDescrSubTask(); Main.selectedSubTask = null;}
        });
        btnDeleteTask.setOnAction(event -> {
            Main.selectedTask.subTasks.clear();
            lvSubTaskContent.clear();
            clearDescrSubTask();
            Main.tasks.remove(Main.selectedTask);
            lvTasksContent.remove(Main.selectedTask);
            //notFP
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
                //notFP
                if(Main.selectedTask.getTaskStatus() == TaskStatus.Complete){
                    //FP
                    Main.selectedTask.subTasks.forEach(x->x.setTaskStatus(TaskStatus.Complete));
                }
                updateDescrPanel(Main.selectedTask);
                clearDescrSubTask();
                //NotFP - эта проверка нужна для того чтобы если задача завершится и у нее была выбрана подзадача,
                // то описание выбранной подзадачи обновилось (у подзадачи также меняется статус)
                if(Main.selectedSubTask != null){
                    updateDescrSubTask(Main.selectedSubTask);
                }
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
        //сортировки
        btnUseSort.setOnAction(event->{
            List<Task> tasksSorted = null;
            //not FP (Только по названию)
            if(cbTitleSort.isSelected() && !rbComplited.isSelected() &&!rbNotComplited.isSelected()){
                tasksSorted = sortOnTitleGrow(Main.tasks);
            }
            //not FP (Завершенные задачи)
            if (rbComplited.isSelected()) {
                tasksSorted = sortOnStatus(TaskStatus.Complete);
                //notFP
                if(cbTitleSort.isSelected()){
                    tasksSorted = sortOnTitleGrow(tasksSorted);
                }
            }
            //notFP(только незавершенные)
            if(rbNotComplited.isSelected()  && !rbOverdueSort.isSelected() && !rbSoonSort.isSelected() && !rbTagSort.isSelected()){
                tasksSorted = sortOnStatus(TaskStatus.InProgress);
                //notFP
                if(cbTitleSort.isSelected())
                    tasksSorted = sortOnTitleGrow(tasksSorted);
            }
            //notFP(незавершенные просроченные)
            if (rbNotComplited.isSelected() && rbOverdueSort.isSelected()) {
                tasksSorted = sortOnOverdue();
                //notFP
                if(cbTitleSort.isSelected())
                    tasksSorted = sortOnTitleGrow(tasksSorted);
            }
            //notFP(незавершенные со сроком в ближайшую неделю)
            if (rbNotComplited.isSelected() && rbSoonSort.isSelected()) {
                tasksSorted = sortOnSoon();
                //notFP
                if(cbTitleSort.isSelected()) {
                    tasksSorted = sortOnDeadlineAndTitle(tasksSorted);
                } else{
                    tasksSorted = sortOnDeadlineOnly(tasksSorted);
                }
            }
            //notFP(незавершенные по тегу)
            if (rbNotComplited.isSelected() && rbTagSort.isSelected()) {
                tasksSorted = sortOnTag();
                //notFP
                if(cbTitleSort.isSelected()) {
                    tasksSorted = sortOnTitleGrow(tasksSorted);
                }
            }
            if(rbAdvanced.isSelected()){
                switch (cbAdvanced.getSelectionModel().getSelectedIndex()){
                    case 0:{
                        tasksSorted=sortAdvanced1();
                    }break;
                    case 1:{
                        tasksSorted=sortAdvanced2();
                    }break;
                    case 2:{
                        tasksSorted=sortAdvanced3();
                    }break;
                    case 3:{

                    }break;
                    case 4:{

                    }break;
                }
            }


            //notFP (Не придумал адекватного способа убрать проверку на null)
            if(tasksSorted != null && tasksSorted.size()>0){
                lvTasks.setItems(FXCollections.observableArrayList(tasksSorted));
            } else {
                showAlert();
            }
        });
        btnReset.setOnAction(event -> {
            lvTasks.setItems(lvTasksContent);
        });

        toggleGroup2.selectedToggleProperty().addListener((changed, oldValue, newValue)-> {
            //get selected rb
            if(((RadioButton) newValue).equals(rbNotComplited)){
                scrollSort.setVisible(true);
                rbAdvanced.setLayoutY(342);
                cbAdvanced.setLayoutY(370);
            }
            else{
                scrollSort.setVisible(false);
                rbAdvanced.setLayoutY(175);
                cbAdvanced.setLayoutY(208);
            }
            if (((RadioButton) newValue).equals(rbAdvanced)) {
                cbAdvanced.setVisible(true);
                cbAdvanced.fireEvent(new ActionEvent());
            }
            else {
                tfAdvancedSubstring.setVisible(false);
                tfAdvancedTag.setVisible(false);
                cbAdvanced.setVisible(false);
            }
        });

        cbAdvanced.setOnAction(event -> {
            tfAdvancedTag.setVisible(false);
            tfAdvancedSubstring.setVisible(false);
            if(cbAdvanced.getSelectionModel().getSelectedIndex() == 0){
                tfAdvancedSubstring.setVisible(true);
                tfAdvancedTag.setVisible(true);
            }
            if(cbAdvanced.getSelectionModel().getSelectedIndex() == 3){
                tfAdvancedTag.setVisible(true);
            }
        });
    }

    private List<Task> sortOnTitleGrow(List<Task> tasks) {
        List<Task> tasksSorted = new ArrayList<>(tasks);
        Comparator<Task> taskComparator = new TaskTitleComparator();
        //FP
        tasksSorted.sort(taskComparator);
        return  tasksSorted;
    }

    private List<Task> sortOnStatus(TaskStatus status){
        List<Task> tasksFiltered = new ArrayList<>(Main.tasks);
        //FP
        return tasksFiltered.stream().filter(x->x.getTaskStatus() == status)
                .collect(Collectors.toList());
    }

    private List<Task> sortOnOverdue(){
        List<Task> tasksFiltered = sortOnStatus(TaskStatus.InProgress);
        //FP
        tasksFiltered= tasksFiltered.stream().filter(x->x.getDeadline().isBefore(LocalDate.now())).collect(Collectors.toList());
        return tasksFiltered;
    }

    private List<Task> sortOnSoon() {
        List<Task> tasksFiltered = sortOnStatus(TaskStatus.InProgress);
        //FP
        return tasksFiltered.stream().filter(x->(x.getDeadline().isBefore(LocalDate.now().plusWeeks(1))) && x.getDeadline().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
    }

    private List<Task> sortOnDeadlineAndTitle(List<Task> tasksSorted) {
        Comparator<Task> taskComparator = new TaskDeadlineComparator().thenComparing(new TaskTitleComparator());
        //FP
        tasksSorted.sort(taskComparator);
        return tasksSorted;
    }

    private List<Task> sortOnDeadlineOnly(List<Task> tasksSorted) {
        Comparator<Task> taskComparator = new TaskDeadlineComparator();
        //FP
        tasksSorted.sort(taskComparator);
        return tasksSorted;
    }

    private List<Task> sortOnTag() {
        List<Task> tasksFiltered = sortOnStatus(TaskStatus.InProgress);
        //FP
        return tasksFiltered.stream().filter(x->x.getSplittedTags().contains(tfTagForSort.getText()))
                .collect(Collectors.toList());
    }

    private List<Task> sortAdvanced1() {
        List<Task> tasksFiltered = new ArrayList<>(Main.tasks);
        return tasksFiltered.stream().filter(x->x.getSplittedTags().contains(tfAdvancedTag.getText()) &&
                x.getDescription().contains(tfAdvancedSubstring.getText()) &&
                x.getDeadline().isAfter(LocalDate.now()) && x.getDeadline().isBefore(LocalDate.now().plusMonths(1))).
                collect(Collectors.toList());
    }

    private List<Task> sortAdvanced2() {
        List<Task> tasksFiltered = sortOnStatus(TaskStatus.InProgress);
        return tasksFiltered.stream().filter(x -> x.subTasks.stream().filter(
                y -> y.getTaskStatus() == TaskStatus.Complete).count() >=
                (x.subTasks.size() %2 == 0? x.subTasks.size()/2: x.subTasks.size()/2+1))
                .collect(Collectors.toList());
    }
    private List<Task> sortAdvanced3() {
        List<Task> tasksFiltered = sortOnOverdue();
        Map<String, Integer> dict = new HashMap<>();
        tasksFiltered.forEach(task-> task.getSplittedTags().forEach(tag->{
            dict.merge(tag, 1, (a, b) -> a + b);
        }));
        List<String> threePopularTags = new ArrayList<>();
        dict.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(3)
                .forEach(x->{ threePopularTags.add(x.getKey());});
        //threePopularTags.forEach(System.out::println);
        return tasksFiltered.stream().filter(task -> task.getSplittedTags().stream()
                .anyMatch(threePopularTags::contains)).collect(Collectors.toList());
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Сортировка");
        alert.setHeaderText("Результат: нет элементов удовлетворяющих выбранным критериям");
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
    private void clearDescrSubTask() {
        tfSubTaskTitle.setText("");
        taSubTaskDescr.setText("");
        labelSubTaskStatus.setText("");
    }
    private void clearDescrTask() {
        tfDescrTitle.setText("");
        taDescrDescr.setText("");
        dpDescrDeadline.setValue(LocalDate.now());
        tfDescrTags.setText("");
        labelTaskStatus.setText("");
    }
}