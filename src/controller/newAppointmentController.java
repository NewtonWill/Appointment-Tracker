package controller;

import helper.appointmentsQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;

public class newAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField locationTxt;

    @FXML
    private TextField aptIdTxt;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<Contact> contactCombo;

    @FXML
    private ComboBox<Customer> customerCombo;

    @FXML
    private ComboBox<Integer> startHour;

    @FXML
    private ComboBox<Integer> startMinute;

    @FXML
    private ComboBox<Integer> endHour;

    @FXML
    private ComboBox<Integer> endMinute;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField typeTxt;

    @FXML
    private Label zoneLabel1;

    @FXML
    private Label zoneLabel2;

    @FXML
    void onActionCancelBtn(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionSaveBtn(ActionEvent event) throws SQLException, IOException {

        if(customerCombo.getSelectionModel().getSelectedItem() == null && contactCombo.getSelectionModel().getSelectedItem() == null){
            System.out.println("Customer and Contact combo boxes must be selected");
            return;
        }

        if(titleTxt.getText().isBlank() || titleTxt.getText() == null){
            System.out.println("Title Text error");
            return;
        }

        if((descriptionTxt.getText().isBlank() || descriptionTxt.getText() == null)){
            System.out.println("Description Text error");
            return;
        }

        if((locationTxt.getText().isBlank() || locationTxt.getText() == null)){
            System.out.println("Location Text error");
            return;
        }

        if((typeTxt.getText().isBlank() || typeTxt.getText() == null)){
            System.out.println("Type Text error");
            return;
        }

        ZonedDateTime startZDT = Main.appointmentValidTimeCheck(startDatePicker.getValue(), startHour.getSelectionModel().getSelectedItem(), startMinute.getSelectionModel().getSelectedItem());
        ZonedDateTime endZDT = Main.appointmentValidTimeCheck(endDatePicker.getValue(), endHour.getSelectionModel().getSelectedItem(), endMinute.getSelectionModel().getSelectedItem());

        if(startZDT == null || endZDT == null){
            System.out.println("Invalid dates, appointment not saved");
            return;
        }

        if(!Main.appointmentFullCheck(startZDT, endZDT, customerCombo.getSelectionModel().getSelectedItem().getCustomerId(), Integer.parseInt(aptIdTxt.getText()))){
            //System.out.println("Appointment Error detected, Appointment not saved");
            return;
        }

        System.out.println("Appointment okay!");

        startZDT = startZDT.withZoneSameInstant(ZoneId.of("UTC"));
        endZDT = endZDT.withZoneSameInstant(ZoneId.of("UTC"));

        Appointment newAppointment = new Appointment(Integer.parseInt(aptIdTxt.getText()), titleTxt.getText(), descriptionTxt.getText(), locationTxt.getText(),
                contactCombo.getSelectionModel().getSelectedItem().getContactId(), typeTxt.getText(), startZDT.toLocalDateTime(), endZDT.toLocalDateTime(),
                customerCombo.getSelectionModel().getSelectedItem().getCustomerId(), Session.getLocalUserId());

        Session.addAppointment(newAppointment);
        appointmentsQuery.insert(newAppointment);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();


    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        aptIdTxt.setText(String.valueOf(Session.getNextAppointmentId()));
        contactCombo.setItems(Session.getAllContacts());
        customerCombo.setItems(Session.getAllCustomers());

        zoneLabel1.setText(Session.getLocalZoneId().toString());
        zoneLabel2.setText(Session.getLocalZoneId().toString());

        for(Integer i = 0; i <= 23; i++){
            startHour.getItems().add(i);
            endHour.getItems().add(i);
        }

        for(Integer i = 0; i < 60; i++){
            startMinute.getItems().add(i);
            endMinute.getItems().add(i);
        }

        startHour.getSelectionModel().selectFirst();
        startMinute.getSelectionModel().selectFirst();
        endHour.getSelectionModel().selectFirst();
        endMinute.getSelectionModel().selectFirst();

    }

}
