package sample.controllersClasses;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logics.Task;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    static Stage primaryStage;
    static List<Task> tasks = new ArrayList<>();
    static Task selectedTask = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/sample/viewsFXMLs/mainView.fxml"));
        primaryStage.setTitle("Task Manger");
        primaryStage.setScene(new Scene(root, 1250, 680));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
