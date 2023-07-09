package controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller of the reports choice view
 */
public class reportsController {

    Stage stage;
    Parent scene;

    /**
     * Method returns user to main form
     */
    @FXML
    void onActionBackButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     * Method takes user to month/type report form
     */
    @FXML
    void onActionReport1(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/monthTypeReport.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method takes user to schedule report form
     */
    @FXML
    void onActionReport2(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/scheduleReport.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method takes user to length report form
     */
    @FXML
    void onActionReport3(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/lengthReport.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}