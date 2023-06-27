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

public class modifyCustomerController implements Initializable {

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
    void onActionSaveCustomer(ActionEvent event) throws SQLException, IOException {
        if(!Main.customerDataCheck(customerNameTxt.getText(), addressTxt.getText(), divisionCombo.getSelectionModel().getSelectedItem(),
                phoneTxt.getText(), postalCodeTxt.getText())) {
            System.out.println("value check fail");
            return;
        }



        int currentIndex = Session.getAllCustomers().indexOf(Session.lookupCustomer(Integer.parseInt(customerIdTxt.getText())));

        Customer newCustomer = new Customer(Integer.parseInt(customerIdTxt.getText()), customerNameTxt.getText(),
                addressTxt.getText(), postalCodeTxt.getText(), phoneTxt.getText(),
                divisionCombo.getSelectionModel().getSelectedItem().getDivisionId());

        Session.updateCustomer(currentIndex, newCustomer); //adding new customer to java object list

        if(customersQuery.update(newCustomer) == 1){
            System.out.println("DB update success");
        }
        else {
            System.out.println("DB update Error");
        }

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionCountry(ActionEvent event) {

        divisionCombo.setItems(Session.filterDivisions(countryCombo.getSelectionModel().getSelectedItem().getCountryId()));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        countryCombo.setItems(Session.getAllCountries());
        //divisionCombo.setItems(Session.filterDivisions(countryCombo.getSelectionModel().getSelectedItem().getCountryId()));

    }

    public void sendCustomer(Customer customer){
        countryCombo.setValue(Session.lookupCountry(Session.lookupDivision(customer.getDivisionId()).getCountryId()));
        divisionCombo.setValue(Session.lookupDivision(customer.getDivisionId()));

        addressTxt.setText(customer.getAddress());
        customerIdTxt.setText(String.valueOf(customer.getCustomerId()));
        customerNameTxt.setText(customer.getName());
        phoneTxt.setText(customer.getPhone());
        postalCodeTxt.setText(customer.getPostalCode());

        divisionCombo.setItems(Session.filterDivisions(countryCombo.getSelectionModel().getSelectedItem().getCountryId()));

    }

}
