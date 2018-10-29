//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package View;

import Logger.StageHolder;
import Objects.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ReadAll {
    private User searchFields;
    private View view;
    private boolean isDone;
    private ListView listView;
    @FXML
    public TextField username;
    @FXML
    public TextField password;
    @FXML
    public TextField firstname;
    @FXML
    public TextField lastname;
    @FXML
    public TextField city;
    @FXML
    public TextField birthdate;

    public ReadAll() {
    }

    public void handleSearch() {
        this.searchFields = new User(this.username.getText(), this.password.getText(), this.firstname.getText(), this.lastname.getText(), this.city.getText(), this.birthdate.getText());
        this.showSearchResults(this.view.searchInDataBase(this.searchFields));
        //this.showSearchResults(this.view.getAllDataBase());
    }

    public void handleBack() {
        this.isDone = true;
        StageHolder.getInstance().getStage().close();
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public User getSearchFields() {
        return this.searchFields;
    }

    public void showSearchResults(ObservableList searchResults) {
        if (searchResults != null) {
            this.listView.setItems(searchResults);
        }
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public void setView(View view) {
        this.view = view;
    }
}
