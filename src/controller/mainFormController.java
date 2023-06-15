package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class mainFormController {

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
    private TableView<?> partTableView;

    @FXML
    private TableView<?> productTableView;

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

}
