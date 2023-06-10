package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
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
    private Label loginUIElement;

    @FXML
    private Button okBtn;

    @FXML
    private TextField passwordTxt;

    @FXML
    private Label passwordUIElement;

    @FXML
    private Label timeZoneLabel;

    @FXML
    private TextField usernameTxt;

    @FXML
    private Label usernameUIElement;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeZoneLabel.setText(String.valueOf(Session.getLocalZoneId()));
        if (!String.valueOf(Session.getLocalLanguage()).equals("en")) {
            System.out.println("english not detected");
            if (String.valueOf(Session.getLocalLanguage()).equals("fr")){
                System.out.println("Translating to french...");
                usernameUIElement.setText("nom d'utilisateur");
                passwordUIElement.setText("mot de passe");
                loginUIElement.setText("connectez-vous");
                okBtn.setText("d'accord");
                cancelBtn.setText("Annuler");
                //fixme fix the ui spacing if there is time
            }
            else {
                System.out.println("Language not supported, english set by default");
                Alert languageAlert = new Alert(Alert.AlertType.WARNING);
                languageAlert.setTitle("Language unsupported");
                languageAlert.setContentText("System language is not supported. Program defaulting to english.");
                languageAlert.show();
            }
        }

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
