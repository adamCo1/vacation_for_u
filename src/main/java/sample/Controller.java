//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sample;

import Logger.Logger;
import Model.ISQLModel;
import Model.Model;
import Objects.User;
import View.IView;
import java.sql.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class Controller {

    private IView view;
    private ISQLModel model;
    private Stage primaryStage;

    public Controller() {
    }

    public void createUsersTable() {
        this.model.createUsersTable();
    }

    public void handleSubmitSignIn(User submit) {
        if (submit != null) {
            try {
                this.model.insert(submit.getUsername(), submit.getPassword(), submit.getFirstname(), submit.getLastname(), submit.getCity(), (Date)null);
            } catch (NullPointerException var3) {
                Logger.getInstance().log("NULL RECORD");
            }

        }
    }

    public List<User> searchAllRecordsByFields(User fields) {
        return fields == null ? null : this.model.searchRecordsByFields(fields);
    }

    public ObservableList searchInDataBase(User user) {
        return ((Model)this.model).searchRecordsByFields(user);
    }

    public ObservableList getAllDataBase() {
        return ((Model)this.model).selectAllDataBase();
    }

    public void setAll() {
        this.view.setController(this);
        this.model.setController(this);
    }

    public void setView(IView view) {
        this.view = view;

    }

    public void setModel(ISQLModel model) {
        this.model = model;
    }

    public void deleteUser(String username){
        this.model.deleteUsers(username);
    }

}
