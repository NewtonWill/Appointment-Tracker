package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Customer;
import model.Session;


import java.net.URL;
import java.util.ResourceBundle;

public class mainFormController implements Initializable{

    @FXML
    private Button addAppointmentBtn;

    @FXML
    private Button addCustBtn;

    @FXML
    private TableColumn<?, ?> apptAIDCol;

    @FXML
    private TableColumn<?, ?> apptCIDCol;

    @FXML
    private TableColumn<?, ?> apptContactCol;

    @FXML
    private TableColumn<?, ?> apptDescriptionCol;

    @FXML
    private TableColumn<?, ?> apptEndCol;

    @FXML
    private TableColumn<?, ?> apptLocationCol;

    @FXML
    private TableColumn<?, ?> apptStartCol;

    @FXML
    private TableColumn<?, ?> apptTitleCol;

    @FXML
    private TableColumn<?, ?> apptTypeCol;

    @FXML
    private TableColumn<?, ?> apptUIDCol;

    @FXML
    private TableColumn<?, ?> custAddressCol;

    @FXML
    private TableColumn<?, ?> custCIDCol;

    @FXML
    private TableColumn<?, ?> custCountryCol;

    @FXML
    private TableColumn<?, ?> custDivisionCol;

    @FXML
    private TableColumn<?, ?> custNameCol;

    @FXML
    private TableColumn<?, ?> custPhoneCol;

    @FXML
    private TableColumn<?, ?> custZipCol;

    @FXML
    private Label dateDisplay;

    @FXML
    private Button deleteAppointmentBtn;

    @FXML
    private Button deleteCustBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button leftBtn;

    @FXML
    private Button modifyAppointmentBtn;

    @FXML
    private Button modifyCustBtn;

    @FXML
    private RadioButton monthRadio;

    @FXML
    private ComboBox<?> monthSelect;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private Button rightBtn;

    @FXML
    private RadioButton weekRadio;

    @FXML
    private ComboBox<?> yearSelect1;

    @FXML
    void onActionAddAppointment(ActionEvent event) {

    }

    @FXML
    void onActionAddCust(ActionEvent event) {

    }

    @FXML
    void onActionDeleteAppointment(ActionEvent event) {

    }

    @FXML
    void onActionDeleteCust(ActionEvent event) {

    }

    @FXML
    void onActionExitProgram(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    void onActionLeftBtn(ActionEvent event) {

    }

    @FXML
    void onActionModifyAppointment(ActionEvent event) {

    }

    @FXML
    void onActionModifyCust(ActionEvent event) {

    }

    @FXML
    void onActionMonthRadio(ActionEvent event) {

    }

    @FXML
    void onActionMonthSelect(ActionEvent event) {

    }

    @FXML
    void onActionRightBtn(ActionEvent event) {

    }

    @FXML
    void onActionWeekRadio(ActionEvent event) {

    }

    @FXML
    void onActionYearSelect(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        customerTableView.setItems(Session.getAllCustomers());

        custCIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        custCountryCol.setCellValueFactory(new PropertyValueFactory<>("CountryName"));
        custDivisionCol.setCellValueFactory(new PropertyValueFactory<>("DivisionName"));
        custZipCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));


        appointmentTableView.setItems(Session.getAllAppointments());

        apptAIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("StartDT"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("EndDT"));
        apptCIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        apptUIDCol.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
    }

}
