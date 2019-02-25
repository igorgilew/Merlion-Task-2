package sample.controllersClasses;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logics.SubTask;
import logics.Task;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    static Stage primaryStage;
    static List<Task> tasks = new ArrayList<>();
    static Task selectedTask = null;
    static SubTask selectedSubTask = null;
    private final String file = "session.dat";

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage primaryStage) throws Exception{
        //load old session if exist
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file)))
        {
            tasks=((ArrayList<Task>)ois.readObject());
            System.out.println("Session has been loaded");
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }

        Main.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/sample/viewsFXMLs/mainView.fxml"));
        primaryStage.setTitle("Task Manger");
        primaryStage.setScene(new Scene(root, 1250, 680));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        //save current session
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file)))
        {
            oos.writeObject(tasks);
            System.out.println("Session has been saved");
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
        super.stop();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
