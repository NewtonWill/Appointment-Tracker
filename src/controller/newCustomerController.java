package controller;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class newCustomerController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField addressTxt;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<?> countryCombo;

    @FXML
    private TextField customerIdTxt;

    @FXML
    private TextField customerNameTxt;

    @FXML
    private ComboBox<?> divisionCombo;

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


    }

    @FXML
    void onActionSaveCustomer(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
