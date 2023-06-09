package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.Session;

import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class login implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button okBtn;

    @FXML
    private TextField passwordTxt;

    @FXML
    private TextField usernameTxt;

    @FXML
    private Label timeZoneLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        timeZoneLabel.setText(String.valueOf(Session.getLocalZoneId()));

        //String sysdef = Locale.getDefault().toString();
        //System.out.println(sysdef);
    }

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
