package helper;

import model.Appointment;
import model.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public abstract class appointmentsQuery {
    public static void loadToMemory() throws SQLException {
    String sql = "SELECT * FROM appointments";
    PreparedStatement ps = JDBC.connection.prepareStatement(sql);
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
        Integer AppointmentId = rs.getInt("Appointment_ID");
        String title = rs.getString("Title");
        String description = rs.getString("Description");
        String location = rs.getString("Location");
        String Type = rs.getString("Type");
        LocalDateTime startDT = rs.getTimestamp("Start").toLocalDateTime();
        LocalDateTime endDT = rs.getTimestamp("End").toLocalDateTime();
        Integer customerId = rs.getInt("Customer_ID");
        Integer userId = rs.getInt("User_ID");
        Integer contactId = rs.getInt("Contact_ID");

        Appointment newAppointment = new Appointment(AppointmentId, title,
                description, location, contactId, Type, startDT, endDT,customerId, userId);
        Session.addAppointment(newAppointment);
        System.out.println("Appointment added: " + title);
        }
    }
}
