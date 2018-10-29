//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package View;

import Logger.StageHolder;
import Objects.User;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.EventListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Controller;

public class View implements IView , EventListener {
    private Controller controller;
    private Stage primaryStage;
    private List<User> searchResults;

    public View() {
    }

    public void setCurrentStage(Stage stage) {
        this.primaryStage = stage;
    }

    //region SCENES

    public void signInScene() {
        FXMLLoader loader = new FXMLLoader();

        try {
            Parent root = (Parent)loader.load(this.getClass().getClassLoader().getResource("logInFXML.fxml").openStream());
            Scene scene = new Scene(root, 500.0D, 500.0D);
            scene.getStylesheets().add(this.getClass().getClassLoader().getResource("Forms.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            this.primaryStage.hide();
            StageHolder.getInstance().holdStage(stage);
            stage.showAndWait();
            this.primaryStage.show();
            LoginSceneController sceneController = (LoginSceneController)loader.getController();
            this.controller.handleSubmitSignIn(sceneController.getToSubmit());
        } catch (IOException var6) {
            var6.getCause();
            var6.printStackTrace();
        }

    }

    public void handleDelete(){
        FXMLLoader loader = new FXMLLoader();

        try{
            Parent root = loader.load(getClass().getClassLoader().getResource("Delete.fxml").openStream());
            Scene scene = new Scene(root,400,270);
            scene.getStylesheets().add(getClass().getClassLoader().getResource("Forms.css").toExternalForm());
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(scene);

            DeleteController deleteController = loader.getController();
            StageHolder.getInstance().holdStage(stage);
            stage.showAndWait();
            String username = deleteController.getUsername();
            controller.deleteUser(username);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handleRead() {
        FXMLLoader loader = new FXMLLoader();

        try {
            Parent root = (Parent)loader.load(this.getClass().getClassLoader().getResource("readAllController.fxml").openStream());
            SplitPane splitPane = (SplitPane)root.getChildrenUnmodifiable().get(0);
            AnchorPane upperPane = (AnchorPane)splitPane.getItems().get(0);
            ListView listView = (ListView)upperPane.getChildren().get(0);
            Scene scene = new Scene(root, 600.0D, 600.0D);
            scene.getStylesheets().add(this.getClass().getClassLoader().getResource("Forms.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(scene);
            ReadAll readAll = (ReadAll)loader.getController();
            readAll.setListView(listView);
            readAll.setView(this);
            StageHolder.getInstance().holdStage(stage);
            stage.showAndWait();

            this.primaryStage.show();
        } catch (IOException var9) {
            var9.printStackTrace();
        }

    }

    //endregion

    public ObservableList searchInDataBase(User user) {
        return this.controller.searchInDataBase(user);
    }

    public ObservableList getAllDataBase() {
        return this.controller.getAllDataBase();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}
