package controller;

import helper.appointmentsQuery;
import helper.customersQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.Session;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class mainFormController implements Initializable{

    Stage stage;
    Parent scene;

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

    /**
     * Method brings user to the new appointment form
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/newAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionAddCust(ActionEvent event) throws IOException{
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/newCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws SQLException {
        Appointment selectedAppt = appointmentTableView.getSelectionModel().getSelectedItem();
        if(selectedAppt == null){
            Alert noAppointmentAlert = new Alert(Alert.AlertType.ERROR);
            noAppointmentAlert.setTitle("Appointment Not Deleted");
            noAppointmentAlert.setContentText("No Appointment selected to delete");
            noAppointmentAlert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Appointment?");
        alert.setContentText("Press okay to confirm deletion of Appointment");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK){
            int DLines = appointmentsQuery.delete(selectedAppt);
            if (DLines == 1) {
                System.out.println("DB Delete success");
            } else {
                System.out.println("ERROR: Unexpected deletion of " + DLines + "lines");
            }
            if(Session.deleteAppointment(selectedAppt)){
                System.out.println("Java object delete success");
            }
        }
    }

    @FXML
    void onActionDeleteCust(ActionEvent event) throws SQLException {
        Customer selectedCust = customerTableView.getSelectionModel().getSelectedItem();
        if(selectedCust == null){
            Alert noCustomerAlert = new Alert(Alert.AlertType.ERROR);
            noCustomerAlert.setTitle("Customer Not Deleted");
            noCustomerAlert.setContentText("No Customer selected to delete");
            noCustomerAlert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Customer?");
        alert.setContentText("Press okay to confirm deletion of Customer");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK){
            int DLines = customersQuery.delete(selectedCust);
            if (DLines == 1) {
                System.out.println("DB Delete success");
            } else {
                System.out.println("ERROR: Unexpected deletion of " + DLines + "lines");
            }
            if(Session.deleteCustomer(selectedCust)){
                System.out.println("Java object delete success");
            }
        }
    }

    @FXML
    void onActionExitProgram(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onActionLeftBtn(ActionEvent event) {

    }

    @FXML
    void onActionModifyAppointment(ActionEvent event) throws IOException {
        if (appointmentTableView.getSelectionModel().getSelectedItem() == null){
            System.out.println("Error: No appointment selected");
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/modifyAppointment.fxml"));
        loader.load();

        modifyAppointmentController MAppointmentController = loader.getController();
        MAppointmentController.sendAppointment(appointmentTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionModifyCust(ActionEvent event) throws IOException {
        if (customerTableView.getSelectionModel().getSelectedItem() == null){
            System.out.println("Error: No customer selected");
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/modifyCustomer.fxml"));
        loader.load();

        modifyCustomerController MCustomerController = loader.getController();
        MCustomerController.sendCustomer(customerTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

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
