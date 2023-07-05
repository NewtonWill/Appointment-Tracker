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
import main.Main;
import model.Appointment;
import model.Customer;
import model.Session;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
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
    private ComboBox<Month> monthSelect;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private Button rightBtn;

    @FXML
    private RadioButton weekRadio;

    @FXML
    private ComboBox<Integer> yearSelect;

    @FXML
    private RadioButton allRadio;

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
    void onActionReportsButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/reports.fxml"));
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
        alert.setTitle("Appointment ID " + selectedAppt.getAppointmentId());
        alert.setContentText("Press okay to confirm deletion of Appointment " + selectedAppt.getAppointmentId() + ", " + selectedAppt.getType());
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

        for(Appointment appointmentX : Session.getAllAppointments()){
            if(selectedCust.getCustomerId().equals(appointmentX.getCustomer_ID())){
                Alert noAppointmentAlert = new Alert(Alert.AlertType.ERROR);
                noAppointmentAlert.setTitle("Customer Not Deleted");
                noAppointmentAlert.setContentText("Customer record cannot be deleted until all associated appointments are deleted");
                noAppointmentAlert.showAndWait();
                return;
            }
        }

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
    void onActionAllRadio(ActionEvent event){
        monthRadio.setSelected(false);
        weekRadio.setSelected(false);

        appointmentTableView.setItems(Session.getAllAppointments());

        leftBtn.setDisable(true);
        rightBtn.setDisable(true);
        monthSelect.setDisable(true);
        yearSelect.setDisable(true);

        dateDisplay.setText("All appointments");
    }

    @FXML
    void onActionMonthRadio(ActionEvent event) {
        allRadio.setSelected(false);
        weekRadio.setSelected(false);

        appointmentTableView.setItems(Session.appointmentGetMonth(monthSelect.getSelectionModel().getSelectedItem(), yearSelect.getSelectionModel().getSelectedItem()));

        leftBtn.setDisable(false);
        rightBtn.setDisable(false);
        monthSelect.setDisable(false);
        yearSelect.setDisable(false);

        dateDisplay.setText(monthSelect.getSelectionModel().getSelectedItem().toString());
    }

    @FXML
    void onActionWeekRadio(ActionEvent event) {
        allRadio.setSelected(false);
        monthRadio.setSelected(false);

        Session.setCurrentSunday(Main.getFirstWeekDate(monthSelect.getSelectionModel().getSelectedItem(), yearSelect.getValue()));
        appointmentTableView.setItems(Session.appointmentGetWeek(Session.getCurrentSunday()));

        dateDisplay.setText("Week starting with Sunday " + Session.getCurrentSunday());

        leftBtn.setDisable(false);
        rightBtn.setDisable(false);
        monthSelect.setDisable(false);
        yearSelect.setDisable(false);
    }



    @FXML
    void onActionMonthSelect(ActionEvent event) {
        if(monthRadio.isSelected()){
            appointmentTableView.setItems(Session.appointmentGetMonth(monthSelect.getSelectionModel().getSelectedItem(), yearSelect.getSelectionModel().getSelectedItem()));
        }
        else if(weekRadio.isSelected()) {
            Session.setCurrentSunday(Main.getFirstWeekDate(monthSelect.getSelectionModel().getSelectedItem(), yearSelect.getValue()));
            appointmentTableView.setItems(Session.appointmentGetWeek(Session.getCurrentSunday()));
            dateDisplay.setText("Week starting with Sunday " + Session.getCurrentSunday());
        }
    }

    @FXML
    void onActionYearSelect(ActionEvent event) {
        if(monthRadio.isSelected()){
            appointmentTableView.setItems(Session.appointmentGetMonth(monthSelect.getSelectionModel().getSelectedItem(), yearSelect.getSelectionModel().getSelectedItem()));
        }
        else if(weekRadio.isSelected()) {
            Session.setCurrentSunday(Main.getFirstWeekDate(monthSelect.getSelectionModel().getSelectedItem(), yearSelect.getValue()));
            appointmentTableView.setItems(Session.appointmentGetWeek(Session.getCurrentSunday()));
            dateDisplay.setText("Week starting with Sunday " + Session.getCurrentSunday());
        }
    }


    @FXML
    void onActionRightBtn(ActionEvent event) {

        if(weekRadio.isSelected()){
            if(!Session.getCurrentSunday().plusWeeks(1).getMonth().equals(monthSelect.getSelectionModel().getSelectedItem())){
                System.out.println("End of month reached");
                return;
            }

            Session.setCurrentSunday(Session.getCurrentSunday().plusWeeks(1));
            appointmentTableView.setItems(Session.appointmentGetWeek(Session.getCurrentSunday()));

            dateDisplay.setText("Week starting with Sunday " + Session.getCurrentSunday());
        }

        if(monthRadio.isSelected()){
            monthSelect.setValue(monthSelect.getValue().plus(1));
            dateDisplay.setText(monthSelect.getSelectionModel().getSelectedItem().toString());
        }

    }

    @FXML
    void onActionLeftBtn(ActionEvent event) {

        if(weekRadio.isSelected()){
            if(!Session.getCurrentSunday().getMonth().equals(monthSelect.getSelectionModel().getSelectedItem())){
                //Sunday is already a member of previous month
                System.out.println("Beginning of month reached: Scenario 1");
                return;
            }

            /*LocalDate saturday = Session.getCurrentSunday().plusDays(6);
            if(!saturday.getMonth().equals(monthSelect.getSelectionModel().getSelectedItem())){
                //saturday is member of previous month
                System.out.println("Beginning of month reached: Scenario 2");
                return;
            }*/

            Session.setCurrentSunday(Session.getCurrentSunday().minusWeeks(1));
            appointmentTableView.setItems(Session.appointmentGetWeek(Session.getCurrentSunday()));

            dateDisplay.setText("Week starting with Sunday " + Session.getCurrentSunday());
        }

        if(monthRadio.isSelected()){
            monthSelect.setValue(monthSelect.getValue().minus(1));
            dateDisplay.setText(monthSelect.getSelectionModel().getSelectedItem().toString());
        }

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
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("startLocalZone"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("endLocalZone"));
        apptCIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        apptUIDCol.setCellValueFactory(new PropertyValueFactory<>("User_ID"));

        allRadio.setSelected(true);

        monthSelect.setItems(Session.getAllMonths());
        monthSelect.getSelectionModel().selectFirst();

        yearSelect.setItems(Session.getAllYears());
        yearSelect.getSelectionModel().selectFirst();

        Session.setCurrentSunday(Main.getFirstWeekDate(monthSelect.getSelectionModel().getSelectedItem(), yearSelect.getValue()));

        leftBtn.setDisable(true);
        rightBtn.setDisable(true);
        monthSelect.setDisable(true);
        yearSelect.setDisable(true);

        dateDisplay.setText("All appointments");

        Main.fifteenMinuteCheck();
    }

}
