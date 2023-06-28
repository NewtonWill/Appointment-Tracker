package main;

import helper.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Appointment;
import model.Division;
import model.Session;

import java.sql.SQLException;
import java.time.*;
import java.util.Locale;
import java.util.Objects;
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


        //launches first screen
        launch(args);


        customersQuery.delete(666);
        //program close
        JDBC.closeConnection();

        System.exit(0);
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
        for(Appointment appointmentX : Session.getAllAppointments()){
            if(Objects.equals(appointmentX.getCustomer_ID(), customerId) && !Objects.equals(appointmentX.getAppointmentId(), appointmentId)){
                //above checks if the rotating appointmentX is for the same customer, and if so will make sure it is not the same appointment

                ZonedDateTime startX = appointmentX.getStartDT().atZone(ZoneId.of("UTC"));
                ZonedDateTime endX = appointmentX.getStartDT().atZone(ZoneId.of("UTC"));

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
                    return false;
                }

                if((start.isBefore(startX) || start.isEqual(startX)) && (end.isAfter(endX) || end.isEqual(endX))){
                    System.out.println("Scenario 3: Start and end are both outside of window");
                    System.out.println("StartX: " + startX + " EndX: " + endX);
                    System.out.println("Start: " + start + " End: " + end);
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