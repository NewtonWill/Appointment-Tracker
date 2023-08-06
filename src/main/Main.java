package main;

import helper.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.Appointment;
import model.Division;
import model.Session;

import java.sql.SQLException;
import java.time.*;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;

/**
 * The main class of the program
 * @author William Newton
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login.fxml")));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 500,208));
        stage.show();
    }

    /**
     * The main executable for the program. Houses test data and launches args
     * @param args the args to launch
     */
    public static void main(String[] args) throws SQLException {

        new Session(Locale.getDefault().getLanguage(), ZoneId.of(TimeZone.getDefault().getID()), null);

        //ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        System.out.println("Local Timezone: " + Session.getLocalZoneId());
        //String SysLang = Locale.getDefault().getLanguage();
        System.out.println("Local Lang ID: " + Session.getLocalLanguage());



        //opens database connection
        JDBC.openConnection();

        /*try {
            customersQuery.delete(666);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

        //loads Database to memory
        System.out.println("Countries loaded: " + countriesQuery.loadToMemory());
        System.out.println("Divisions loaded: " + divisionsQuery.loadToMemory());
        System.out.println("Customers loaded: " + customersQuery.loadToMemory());
        System.out.println("Appointments loaded: " + appointmentsQuery.loadToMemory());
        System.out.println("Contacts loaded: " + contactsQuery.loadToMemory());

        //Launches tests
        //TestScripts.loginTest();

        //TestScripts.customerAddTest();

        //set range of appointment filtering years
        ObservableList<Integer> yearsSet = FXCollections.observableArrayList();
        for(Integer i = 2000; i <= 2030; i++){
            yearsSet.add(i);
        }
        Session.setAllYears(yearsSet);


        //launches first screen
        launch(args);


        //customersQuery.delete(666);
        //program close
        JDBC.closeConnection();

        System.exit(0);
    }
    /**
     * Method designed to identify the first week of the month
     * @param month the specified month
     * @param year the specified year
     * @return the first day of the week
     */
    public static LocalDate getFirstWeekDate(Month month, int year){
        LocalDate dayOne = LocalDate.of(year, month, 1);
        while(dayOne.getDayOfWeek() != DayOfWeek.SUNDAY){
            dayOne = dayOne.minusDays(1);
        }
        return dayOne;
    }

    /**
     * Method to alert user where or not there is an upcoming appointment within the next 15 minutes
     */
    public static void fifteenMinuteCheck(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if(!Session.isFirstTimeMainForm()){
            return;
        }

        LocalDateTime nowPlusFifteen = LocalDateTime.now().plusMinutes(15);

        for(Appointment appointmentX : Session.getAllAppointments()){
            if (appointmentX.getStartDT().isAfter(LocalDateTime.now()) && appointmentX.getStartDT().isBefore(nowPlusFifteen)){
                alert.setTitle("Upcoming Appointment");
                alert.setContentText("Appointment ID#" + appointmentX.getAppointmentId() + ": " + appointmentX.getTitle() + " starts within the next 15 minutes");
                Optional<ButtonType> result = alert.showAndWait();
                Session.setFirstTimeMainForm(false);
                return;
            }
        }
        alert.setTitle("No Upcoming Appointment");
        alert.setContentText("There are no appointments starting within the next 15 minutes");
        Optional<ButtonType> result = alert.showAndWait();
        Session.setFirstTimeMainForm(false);
    }

    /**
     * Method checks that all user input text fields are valid.
     * Lambda Expression checks for nulls and blanks at the same time. Lambda expression improves code by increasing readability
     * through cutting down amount of visible "and" expressions via consolidation within the expression.
     * @param name the name to check
     * @param address the address to check
     * @param division the division to check
     * @param phone the phone to check
     * @param postalCode the postal code to check
     * @return boolean signifying validity of data
     */
    public static boolean customerDataCheck(String name, String address, Division division, String phone, String postalCode){
        dataCheckInterface checkItOut = s -> s != null && !s.isBlank(); //Lambda Expression checks for nulls and blanks at the same time. Lambda expression improves code by increasing readability
        return checkItOut.nullOrBlank(name) && checkItOut.nullOrBlank(address) && checkItOut.nullOrBlank(phone) &&
                checkItOut.nullOrBlank(postalCode) && division != null;
    }

    /**
     * Method checks that data is valid while also converting into ZonedDateTime
     * @param date the date
     * @param hour the hour
     * @param minute the minute
     * @return the validated ZonedDateTime using local user zoneId
     */
    public static ZonedDateTime appointmentValidTimeCheck(LocalDate date, Integer hour, Integer minute){
        if (date == null){
            System.out.println("Date invalid");
            return null;
        }
        if (hour == null || minute == null){
            System.out.println("Time invalid");
            return null;
        }
        LocalTime time = LocalTime.of(hour, minute);
        return ZonedDateTime.of(date, time, Session.getLocalZoneId());
    }

    /**
     * Method checks that appointment times are chronologically valid with no overlap for customer.
     * @param start the appointment start
     * @param end the appointment end
     * @param customerId the id of the customer involved
     * @param appointmentId the id of the appointment being set
     * @return boolean indicating the validity of the appointment
     */
    public static boolean appointmentFullCheck(ZonedDateTime start, ZonedDateTime end, Integer customerId, Integer appointmentId){
        if (end.isBefore(start) || end.equals(start)){
            System.out.println("Error: Appointment cannot end before or at start");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Appointment error");
            alert.setContentText("Appointments cannot end before or at start");
            return false;
        }

        ZonedDateTime opening = ZonedDateTime.of(start.toLocalDate(), LocalTime.of(8, 0), ZoneId.of("America/New_York")).withZoneSameInstant(Session.getLocalZoneId());
        ZonedDateTime closing = ZonedDateTime.of(start.toLocalDate(), LocalTime.of(22, 0), ZoneId.of("America/New_York")).withZoneSameInstant(Session.getLocalZoneId());
        if (opening.isAfter(start) || closing.isBefore(end)){
            if (opening.isAfter(start)){
                System.out.println("Open Time: " + opening);
                System.out.println("Start Time: " + start);
            }
            if (closing.isBefore(end)){
                System.out.println("Closing Time: " + closing);
                System.out.println("end Time: " + end);
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("INVALID TIME");
            alert.setContentText("Appointments must be set between the hours of 8 AM to 10 PM EST of a single day");
            Optional<ButtonType> result = alert.showAndWait();
            return false;
        }

        for(Appointment appointmentX : Session.getAllAppointments()){
            if(Objects.equals(appointmentX.getCustomer_ID(), customerId) && !Objects.equals(appointmentX.getAppointmentId(), appointmentId)){
                //above checks if the rotating appointmentX is for the same customer, and if so will make sure it is not the same appointment

                /*ZonedDateTime startX = appointmentX.getStartDT().atZone(ZoneId.of("UTC")).withZoneSameInstant(Session.getLocalZoneId());
                ZonedDateTime endX = appointmentX.getEndDT().atZone(ZoneId.of("UTC")).withZoneSameInstant(Session.getLocalZoneId());*/

                ZonedDateTime startX = appointmentX.getStartDT().atZone(Session.getLocalZoneId());
                ZonedDateTime endX = appointmentX.getEndDT().atZone(Session.getLocalZoneId());

                System.out.println();
                System.out.println("New Appointment Start: " + start);
                System.out.println("New appointment End:   " + end);
                System.out.println("Appointment " + appointmentX.getAppointmentId() + " Start:   " + startX);
                System.out.println("Appointment " + appointmentX.getAppointmentId() + " End:     " + endX);
                System.out.println("-----------------------------------------------------------------------");

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Appointment overlap");
                alert.setContentText("Appointments with the same customer cannot be overlapping");

                if((start.isAfter(startX) && start.isBefore(endX)) || start.isEqual(startX)){
                    System.out.println("Scenario 1: Start is in window");
                    /*System.out.println("StartX: " + startX + " EndX: " + endX);
                    System.out.println("Start: " + start + " End: " + end);*/
                    Optional<ButtonType> result = alert.showAndWait();
                    return false;
                }

                if(end.isAfter(startX) && (end.isBefore(endX) || end.isEqual(endX))){
                    System.out.println("Scenario 2: End is in the window");
                    /*System.out.println("StartX: " + startX + " EndX: " + endX);
                    System.out.println("Start: " + start + " End: " + end);*/
                    Optional<ButtonType> result = alert.showAndWait();
                    return false;
                }

                if((start.isBefore(startX) || start.isEqual(startX)) && (end.isAfter(endX) || end.isEqual(endX))){
                    System.out.println("Scenario 3: Start and end are both outside of window");
                    /*System.out.println("StartX: " + startX + " EndX: " + endX);
                    System.out.println("Start: " + start + " End: " + end);*/
                    Optional<ButtonType> result = alert.showAndWait();
                    return false;
                }

            }
        }
        return true;
    }
}