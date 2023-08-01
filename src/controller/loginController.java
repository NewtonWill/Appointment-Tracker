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
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller of the login view
 */
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

    /**
     * Method detects user language and displays proper ui language
     */
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
            }
            else {
                System.out.println("Language not supported, english set by default");
                Alert languageAlert = new Alert(Alert.AlertType.WARNING);
                languageAlert.setTitle("Language unsupported");
                languageAlert.setContentText("System language is not supported. Program defaulting to english.");
                languageAlert.show();
            }
        }
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

    /**
     * Method exits program
     */
    @FXML
    void onActionCancelBtn(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Method checks if username and password matches and launches main form if successful
     */
    @FXML
    void onActionTextEnter(ActionEvent event) throws SQLException, IOException {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        Session.setLocalUserId(helper.usersQuery.checkMatch(username, password));
        if (Session.getLocalUserId() != null){
            trackLogin(true, username);
            System.out.println("Login successful");
            stage = (Stage)((TextField)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/mainForm.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            trackLogin(false, username);
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

    /**
     * Method records current date, time, and the success of a login attempt
     */
    public static void trackLogin(boolean successful, String username) throws IOException{
        String filename = "src/login_activity.txt", item;
        FileWriter fWriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fWriter);
        outputFile.println("Login attempt at " + ZonedDateTime.now() + " || Attempt username: " + username + " || Login success: " + successful);
        outputFile.close();
    }

}
