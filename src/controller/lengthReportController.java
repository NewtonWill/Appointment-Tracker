package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.MINUTES;

public class lengthReportController implements Initializable {

    @FXML
    private TableColumn<?, ?> apptIDCol;

    @FXML
    private TableColumn<?, ?> custIDCol;

    @FXML
    private TableColumn<?, ?> descCol;

    @FXML
    private TableColumn<?, ?> endCol;

    @FXML
    private TableColumn<?, ?> startCol;

    @FXML
    private TableColumn<?, ?> titleCol;

    @FXML
    private TableColumn<?, ?> typeCol;

    @FXML
    private Label averageLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label longestLabel;

    @FXML
    private Label shortestLabel;

    @FXML
    private ComboBox<Contact> contactCombo;

    Stage stage;
    Parent scene;

    @FXML
    void onActionBackButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionContactCombo(ActionEvent event){
        setStats(contactCombo.getSelectionModel().getSelectedItem());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        contactCombo.setItems(Session.getAllContacts());
        contactCombo.getSelectionModel().selectFirst();

        setStats(contactCombo.getSelectionModel().getSelectedItem());
    }

    @FXML
    void onActionLeft(ActionEvent event) {
        contactCombo.getSelectionModel().selectPrevious();
        setStats(contactCombo.getSelectionModel().getSelectedItem());
    }

    @FXML
    void onActionRight(ActionEvent event) {
        contactCombo.getSelectionModel().selectNext();
        setStats(contactCombo.getSelectionModel().getSelectedItem());
    }

    void setStats(Contact contact){
        int appointmentsTotal = 0;

        Long minutesTotal = 0L;

        Appointment longest = null;
        Long minsLongest = null;

        Appointment shortest = null;
        Long minsShortest = null;


        for(Appointment appointmentX: Session.appointmentsForContact(contact)){
            appointmentsTotal = appointmentsTotal + 1;
            Long appMinutes = appointmentX.getStartDT().until(appointmentX.getEndDT(), MINUTES);
            System.out.println(appMinutes);
            minutesTotal = minutesTotal + appMinutes;
            if((minsLongest == null) || (appMinutes > minsLongest)){
                minsLongest = appMinutes;
                longest = appointmentX;
            }
            if((minsShortest == null) || (appMinutes < minsShortest)){
                minsShortest = appMinutes;
                shortest = appointmentX;
            }
        }

        if (appointmentsTotal == 0){
            numberLabel.setText(String.valueOf(appointmentsTotal));
            averageLabel.setText("N/A");
            longestLabel.setText("N/A");
            shortestLabel.setText("N/A");
            return;
        }

        double average = minutesTotal / appointmentsTotal;

        numberLabel.setText(String.valueOf(appointmentsTotal));
        averageLabel.setText(average + " minutes");
        longestLabel.setText("ID: " + longest.getAppointmentId() + " | " + minsLongest + " minutes");
        shortestLabel.setText("ID: " + shortest.getAppointmentId() + " | " + minsShortest + " minutes");
    }
}
