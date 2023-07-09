package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller of the schedule report view
 */
public class scheduleReportController implements Initializable {

    @FXML
    private TableColumn<?, ?> apptIDCol;

    @FXML
    private Label contactLabel;

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
    private TableView<Appointment> appointmentTableView;

    @FXML
    private ComboBox<Contact> contactCombo;

    Stage stage;
    Parent scene;

    /**
     * Method returns user to reports choice form
     */
    @FXML
    void onActionBackButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Method sets the appointment list on the table according to selected contact
     */
    @FXML
    void onActionContactCombo(ActionEvent event){
        appointmentTableView.setItems(Session.appointmentsForContact(contactCombo.getSelectionModel().getSelectedItem()));
    }

    /**
     * Method populates contact combo box and sets appointment list accordingly
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        contactCombo.setItems(Session.getAllContacts());
        contactCombo.getSelectionModel().selectFirst();

        appointmentTableView.setItems(Session.appointmentsForContact(contactCombo.getSelectionModel().getSelectedItem()));

        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("StartLocalZone"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("EndLocalZone"));
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));

    }


    /**
     * Method changes the selected contact and sets appointment list according to the contact
     */
    @FXML
    void onActionLeft(ActionEvent event) {
        contactCombo.getSelectionModel().selectPrevious();
        appointmentTableView.setItems(Session.appointmentsForContact(contactCombo.getSelectionModel().getSelectedItem()));
    }

    /**
     * Method changes the selected contact and sets appointment list according to the contact
     */
    @FXML
    void onActionRight(ActionEvent event) {
        contactCombo.getSelectionModel().selectNext();
        appointmentTableView.setItems(Session.appointmentsForContact(contactCombo.getSelectionModel().getSelectedItem()));
    }
}