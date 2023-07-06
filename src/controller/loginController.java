package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Session;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

public class loginController implements Initializable {

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

    /**
     * Method checks that log in is valid, logs userID, and launches main menu
     */
    @FXML
    void onActionLogIn(ActionEvent event) throws SQLException, IOException {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        Session.setLocalUserId(helper.usersQuery.checkMatch(username, password));
        if (Session.getLocalUserId() != null){
            System.out.println("Login successful");
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/mainForm.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            Alert loginError = new Alert(Alert.AlertType.WARNING);
            if(String.valueOf(Session.getLocalLanguage()).equals("fr")){
                loginError.setTitle("Erreur de connexion");
                loginError.setContentText("Le nom d'utilisateur ou le mot de passe était incorrect");
            }
            else{
                loginError.setTitle("Login Error");
                loginError.setContentText("Username or password was incorrect");
            }
            loginError.showAndWait();
            System.out.println("Login unsuccessful");
        }
    }

    @FXML
    void onActionCancelBtn(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onActionTextEnter(ActionEvent event) throws SQLException, IOException {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        Session.setLocalUserId(helper.usersQuery.checkMatch(username, password));
        if (Session.getLocalUserId() != null){
            trackLogin(true);
            System.out.println("Login successful");
            stage = (Stage)((TextField)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/mainForm.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            trackLogin(false);
            Alert loginError = new Alert(Alert.AlertType.WARNING);
            if(String.valueOf(Session.getLocalLanguage()).equals("fr")){
                loginError.setTitle("Erreur de connexion");
                loginError.setContentText("Le nom d'utilisateur ou le mot de passe était incorrect");
            }
            else{
                loginError.setTitle("Login Error");
                loginError.setContentText("Username or password was incorrect");
            }
            loginError.showAndWait();
            System.out.println("Login unsuccessful");
        }
    }

    public static void trackLogin(boolean successful) throws IOException{
        String filename = "src/login_activity.txt", item;
        FileWriter fWriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fWriter);
        StringBuilder sb = new StringBuilder("Login attempt at " + LocalDateTime.now(Session.getLocalZoneId()) + " (user system time) || Login success: " + successful);
        outputFile.println(sb);
        outputFile.close();
    }

}
