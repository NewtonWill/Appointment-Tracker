package controller;

import helper.customersQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.Country;
import model.Customer;
import model.Division;
import model.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class newCustomerController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField addressTxt;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<Country> countryCombo;

    @FXML
    private TextField customerIdTxt;

    @FXML
    private TextField customerNameTxt;

    @FXML
    private ComboBox<Division> divisionCombo;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField postalCodeTxt;

    @FXML
    private Button saveBtn;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionCountry(ActionEvent event) {
        divisionCombo.setItems(Session.filterDivisions(countryCombo.getSelectionModel().getSelectedItem().getCountryId()));
        divisionCombo.setDisable(false);
    }

    @FXML
    void onActionSaveCustomer(ActionEvent event) throws SQLException, IOException {

        if(!Main.customerDataCheck(customerNameTxt.getText(), addressTxt.getText(), divisionCombo.getSelectionModel().getSelectedItem(),
                phoneTxt.getText(), postalCodeTxt.getText())) {
            System.out.println("value check fail");
            return;
        }
        Customer newCustomer = new Customer(Integer.parseInt(customerIdTxt.getText()), customerNameTxt.getText(),
                addressTxt.getText(), postalCodeTxt.getText(), phoneTxt.getText(),
                divisionCombo.getSelectionModel().getSelectedItem().getDivisionId());

        Session.addCustomer(newCustomer); //adding new customer to java object list
        customersQuery.insert(newCustomer); //adding new customer to database

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        countryCombo.setItems(Session.getAllCountries());
        divisionCombo.setDisable(true);
        customerIdTxt.setText(String.valueOf(Session.getNextCustomerId()));
    }

}
