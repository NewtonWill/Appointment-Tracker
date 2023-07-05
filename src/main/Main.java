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

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login.fxml")));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 500,208));
        stage.show();
    }

    public static void main(String[] args) throws SQLException {

        new Session(Locale.getDefault().getLanguage(), ZoneId.of(TimeZone.getDefault().getID()), null);

        //ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        System.out.println("Local Timezone: " + Session.getLocalZoneId());
        //String SysLang = Locale.getDefault().getLanguage();
        System.out.println("Local Lang ID: " + Session.getLocalLanguage());



        //opens database connection
        JDBC.openConnection();

        try {
            customersQuery.delete(666);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //loads Database to memory
        System.out.println("Countries loaded: " + countriesQuery.loadToMemory());
        System.out.println("Divisions loaded: " + divisionsQuery.loadToMemory());
        System.out.println("Customers loaded: " + customersQuery.loadToMemory());
        System.out.println("Appointments loaded: " + appointmentsQuery.loadToMemory());
        System.out.println("Contacts loaded: " + contactsQuery.loadToMemory());

        //Launches tests
        //TestScripts.loginTest();

        TestScripts.customerAddTest();

        //set range of appointment filtering years
        ObservableList<Integer> yearsSet = FXCollections.observableArrayList();
        for(Integer i = 2000; i <= 2030; i++){
            yearsSet.add(i);
        }
        Session.setAllYears(yearsSet);


        //launches first screen
        launch(args);


        customersQuery.delete(666);
        //program close
        JDBC.closeConnection();

        System.exit(0);
    }

    public static LocalDate getFirstWeekDate(Month month, int year){
        LocalDate dayOne = LocalDate.of(year, month, 1);
        while(dayOne.getDayOfWeek() != DayOfWeek.SUNDAY){
            dayOne = dayOne.minusDays(1);
        }
        return dayOne;
    }

    public static void fifteenMinuteCheck(){

        if(!Session.isFirstTimeMainForm()){
            return;
        }

        LocalDateTime nowPlusFifteen = LocalDateTime.now().plusMinutes(15);

        for(Appointment appointmentX : Session.getAllAppointments()){
            if (appointmentX.getStartDT().isAfter(LocalDateTime.now()) && appointmentX.getStartDT().isBefore(nowPlusFifteen)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming Appointment");
                alert.setContentText("Appointment ID#" + appointmentX.getAppointmentId() + ": " + appointmentX.getTitle() + " starts within the next 15 minutes");
                Optional<ButtonType> result = alert.showAndWait();
                Session.setFirstTimeMainForm(false);
                return;
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No Upcoming Appointment");
        alert.setContentText("There are no appointments starting within the next 15 minutes");
        Optional<ButtonType> result = alert.showAndWait();
        Session.setFirstTimeMainForm(false);
    }

    public static boolean customerDataCheck(String name, String address, Division division, String phone, String postalCode){
        return name != null && !name.isBlank() && address != null && !address.isBlank() && phone != null && !phone.isBlank() &&
                postalCode != null && !postalCode.isBlank() && division != null;
    }

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

    public static boolean appointmentFullCheck(ZonedDateTime start, ZonedDateTime end, Integer customerId, Integer appointmentId){
        if (end.isBefore(start) || end.equals(start)){
            System.out.println("Error: Appointment cannot end before or at start");
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

                ZonedDateTime startX = appointmentX.getStartDT().atZone(ZoneId.of("UTC")).withZoneSameInstant(Session.getLocalZoneId());
                ZonedDateTime endX = appointmentX.getStartDT().atZone(ZoneId.of("UTC")).withZoneSameInstant(Session.getLocalZoneId());

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Appointment overlap");
                alert.setContentText("Appointments with the same customer cannot be overlapping");

                if((start.isAfter(startX) || start.isEqual(startX)) && start.isBefore(endX)){
                    System.out.println("Scenario 1: Start is in window");
                    System.out.println("StartX: " + startX + " EndX: " + endX);
                    System.out.println("Start: " + start + " End: " + end);

                    return false;
                }

                if(end.isAfter(startX) && (end.isBefore(endX) || end.isEqual(endX))){
                    System.out.println("Scenario 2: End is in the window");
                    System.out.println("StartX: " + startX + " EndX: " + endX);
                    System.out.println("Start: " + start + " End: " + end);
                    Optional<ButtonType> result = alert.showAndWait();
                    return false;
                }

                if((start.isBefore(startX) || start.isEqual(startX)) && (end.isAfter(endX) || end.isEqual(endX))){
                    System.out.println("Scenario 3: Start and end are both outside of window");
                    System.out.println("StartX: " + startX + " EndX: " + endX);
                    System.out.println("Start: " + start + " End: " + end);
                    Optional<ButtonType> result = alert.showAndWait();
                    return false;
                }

            }
        }
        return true;
    }

    public static void lambdas(){
        // value returning lambda expression
        //GeneralInterface square = n -> n * n;
        //System.out.println(square.calculateSquare(5));

        // void lambda expression with one parameter
        //GeneralInterface message = s -> System.out.println("hello again " + s);
        //message.displayMessage("Malcolm!");

        // void Lambda Expression with one parameter

        // Multiple parameter Lambda Expression
        //GeneralInterface sum = (n1, n2) -> n1 + n2;
        //System.out.println(sum.calculateSum(5, 0));

        // no parameter Lambda Expression
        //GeneralInterface message = () -> System.out.println("hello World! ");
        //message.displayMessage();

        // multiple statement Lambda Expression
        /*GeneralInterface square = n -> {
            int result = n * n;
            return result;
        };
        System.out.println(square.calculateSquare(6));*/

        // using local variable in lambda expression
         /*final int num = 50;

        GeneralInterface square = n -> n * n;
        System.out.println(square.calculateSquare(num));*/
    }

    public static void dateAndTime(){
        //ZoneId.getAvailableZoneIds().stream().forEach(System.out::println);

        //ZoneId.getAvailableZoneIds().stream().filter(c -> c.contains("Europe")).forEach(System.out::println);

        LocalDate parisDate = LocalDate.of(2019, 10, 26);
        LocalTime parisTime = LocalTime.of(1, 0);
        ZoneId parisZoneId = ZoneId.of("Europe/Paris");
        ZonedDateTime parisZDT = ZonedDateTime.of(parisDate, parisTime, parisZoneId);
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

        Instant parisToGMTInstant = parisZDT.toInstant(); //convert to utc/gmt
        ZonedDateTime parisToLocalZDT = parisZDT.withZoneSameInstant(localZoneId); //convert paris to local time
        ZonedDateTime gmtToLocalZDT = parisToGMTInstant.atZone(localZoneId); //convert utc to local

        //System.out.println("Local: " + ZonedDateTime.now());
        System.out.println("Paris: " + parisZDT);
        System.out.println("Paris->GMT: " + parisToGMTInstant);
        System.out.println("GMT->Local: " + gmtToLocalZDT);
        System.out.println("GMT->LocalDate: " + gmtToLocalZDT.toLocalDate());
        System.out.println("GMT->LocalDate: " + gmtToLocalZDT.toLocalTime());
        String date = String.valueOf(gmtToLocalZDT.toLocalDate());
        String time = String.valueOf(gmtToLocalZDT.toLocalTime());
        String dateTime = date + " " + time;
        System.out.println(dateTime);
    }
}