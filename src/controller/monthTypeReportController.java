package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Appointment;
import model.Session;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.Objects;
import java.util.ResourceBundle;

public class monthTypeReportController implements Initializable {

    @FXML
    private Label apr;

    @FXML
    private Label aug;

    @FXML
    private Label debriefingLabel;

    @FXML
    private Label dec;

    @FXML
    private Label feb;

    @FXML
    private Label jan;

    @FXML
    private Label jul;

    @FXML
    private Label jun;

    @FXML
    private Label mar;

    @FXML
    private Label may;

    @FXML
    private Label nov;

    @FXML
    private Label oct;

    @FXML
    private Label otherLabel;

    @FXML
    private Label planningLabel;

    @FXML
    private Label sep;

    Stage stage;
    Parent scene;

    @FXML
    void onActionBackButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        int planning = 0;
        int debrief = 0;
        int other = 0;

        int JAN = 0;
        int FEB = 0;
        int MAR = 0;
        int APR = 0;
        int MAY = 0;
        int JUN = 0;
        int JUL = 0;
        int AUG = 0;
        int SEP = 0;
        int OCT = 0;
        int NOV = 0;
        int DEC = 0;

        for (Appointment appointmentX: Session.getAllAppointments()){
            Month month = appointmentX.getStartDT().getMonth();
            String type = appointmentX.getType();
            if (Objects.equals(type, "Planning Session")){
                planning = planning + 1;
            }
            else if (Objects.equals(type, "De-Briefing")){
                debrief = debrief + 1;
            }
            else {
                other = other + 1;
            }

            if (month == Month.JANUARY){
                JAN = JAN + 1;
            }
            if (month == Month.FEBRUARY){
                FEB = FEB + 1;
            }
            if (month == Month.MARCH){
                MAR = MAR + 1;
            }
            if (month == Month.APRIL){
                APR = APR + 1;
            }
            if (month == Month.MAY){
                MAY = MAY + 1;
            }
            if (month == Month.JUNE){
                JUN = JUN + 1;
            }
            if (month == Month.JULY){
                JUL = JUL + 1;
            }
            if (month == Month.AUGUST){
                AUG = AUG + 1;
            }
            if (month == Month.SEPTEMBER){
                SEP = SEP + 1;
            }
            if (month == Month.OCTOBER){
                OCT = OCT + 1;
            }
            if (month == Month.NOVEMBER){
                NOV = NOV + 1;
            }
            if (month == Month.DECEMBER){
                DEC = DEC + 1;
            }
        }

        planningLabel.setText(String.valueOf(planning));
        debriefingLabel.setText(String.valueOf(debrief));
        otherLabel.setText(String.valueOf(other));

        jan.setText(String.valueOf(JAN));
        feb.setText(String.valueOf(FEB));
        mar.setText(String.valueOf(MAR));
        apr.setText(String.valueOf(APR));
        may.setText(String.valueOf(MAY));
        jun.setText(String.valueOf(JUN));
        jul.setText(String.valueOf(JUL));
        aug.setText(String.valueOf(AUG));
        sep.setText(String.valueOf(SEP));
        oct.setText(String.valueOf(OCT));
        nov.setText(String.valueOf(NOV));
        dec.setText(String.valueOf(DEC));
    }

}
