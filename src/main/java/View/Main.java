package View;

/**
 * Initialize everything needed to start the app
 */

import Model.Model;
import View.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controller;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getClassLoader().getResource("sample.fxml").openStream());
        View view = loader.getController();
        primaryStage.setTitle("Vacation4U");
        primaryStage.setScene(new Scene(root, 600, 500));
        Controller controller = new Controller();

        controller.setView(view);
        controller.setModel(new Model());
        controller.setAll();
        controller.createUsersTable();
        view.setCurrentStage(primaryStage);
        //view.setController(controller);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }

}



