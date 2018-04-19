package Calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Calculator program implements an application that
 * can solve simple math problems.
 *
 * @author Ryhmä3
 * *@version 1.0
 * @since 2018-04-19
 */


public class Main extends Application {
    private static Stage window;

    /**
     * start method that loads FXML file and launches the userinterface.
     *
     * @param primaryStage is the Stage that is used by the application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("UserInterface.fxml"));
        primaryStage.setTitle("Calculator");
        primaryStage.setOnCloseRequest(e -> Main.closeMe());
        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(root, 600, 300));
        primaryStage.show();
    }

    /**
     * closeMe method is used to exit application
     */
    public static void closeMe() {
        window.close();
    }


    public static void main(String[] args) {
        launch(args);
    }
}