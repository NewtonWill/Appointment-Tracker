package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class modifyAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField LocationTxt;

    @FXML
    private TextField aptIdTxt;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<Contact> contactCombo;

    @FXML
    private ComboBox<Customer> customerCombo;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private TextField endTimeTxt;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField startTimeTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField typeTxt;

    @FXML
    void onActionCancelBtn(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionSaveBtn(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        contactCombo.setItems(Session.getAllContacts());
        customerCombo.setItems(Session.getAllCustomers());

    }

    public void sendAppointment(Appointment appointment){

        contactCombo.setValue(Session.lookupContact(appointment.getContactID()));
        customerCombo.setValue(Session.lookupCustomer(appointment.getCustomer_ID()));

        LocationTxt.setText(appointment.getLocation());
        aptIdTxt.setText(String.valueOf(appointment.getAppointmentId()));
        descriptionTxt.setText(appointment.getDescription());
        titleTxt.setText(appointment.getTitle());
        typeTxt.setText(appointment.getType());
    }

}
