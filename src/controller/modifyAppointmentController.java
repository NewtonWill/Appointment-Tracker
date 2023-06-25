package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private ComboBox<?> contactCombo;

    @FXML
    private ComboBox<?> customerCombo;

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
    void onActionCancelBtn(ActionEvent event) {

    }

    @FXML
    void onActionSaveBtn(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
