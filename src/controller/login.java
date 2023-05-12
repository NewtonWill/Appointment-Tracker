package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class login {

    @FXML
    private Button cancelBtn;

    @FXML
    private Button okBtn;

    @FXML
    private TextField passwordTxt;

    @FXML
    private TextField usernameTxt;

    @FXML
    void onActionLogIn(ActionEvent event) throws SQLException {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        if (helper.usersQuery.checkMatch(username, password)){
            System.out.println("Login successful");
        }
    }

    @FXML
    void onActionSavePart(ActionEvent event) {

    }

}
