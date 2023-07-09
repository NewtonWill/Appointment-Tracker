package helper;

import model.Appointment;
import model.Customer;
import model.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Database helper class for appointment objects
 */
public abstract class appointmentsQuery {

    /**
     * Loads appointment records from database into java objects
     * @return the number of records were loaded
     */
    public static int loadToMemory() throws SQLException {
    String sql = "SELECT * FROM appointments";
    PreparedStatement ps = JDBC.connection.prepareStatement(sql);
    ResultSet rs = ps.executeQuery();
    int rowsAffected = 0;
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
        //System.out.println("Appointment added: " + title);
        rowsAffected = rowsAffected + 1;
        }
    return rowsAffected;
    }

    /**
     * Creates new appointment record in database
     * @param newAppointment the appointment object inserted
     * @return the number of records that were inserted
     */
    public static int insert(Appointment newAppointment) throws SQLException {

        String sql = "INSERT INTO APPOINTMENTS (Appointment_ID, Title, Description, Location, " +
                "Type, Start, End, Customer_ID, User_ID, Contact_ID, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, newAppointment.getAppointmentId());
        ps.setString(2, newAppointment.getTitle());
        ps.setString(3, newAppointment.getDescription());
        ps.setString(4, newAppointment.getLocation());
        ps.setString(5, newAppointment.getType());
        ps.setTimestamp(6, Timestamp.valueOf(newAppointment.getStartDT()));
        ps.setTimestamp(7, Timestamp.valueOf(newAppointment.getEndDT()));
        ps.setInt(8, newAppointment.getCustomer_ID());
        ps.setInt(9, newAppointment.getUser_ID());
        ps.setInt(10, newAppointment.getContactID());
        ps.setTimestamp(11, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(12, "User " + Session.getLocalUserId());
        ps.setTimestamp(13, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(14, "User " + Session.getLocalUserId());
        return ps.executeUpdate();
    }

    /**
     * Updates appointment record in database
     * @param newAppointment the appointment object updated
     * @return the number of records that were affected
     */
    public static int update(Appointment newAppointment) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, " +
                "End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?,Last_Update = ?, Last_Updated_By = ? " +
                "WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, newAppointment.getTitle());
        ps.setString(2, newAppointment.getDescription());
        ps.setString(3, newAppointment.getLocation());
        ps.setString(4, newAppointment.getType());
        ps.setTimestamp(5, Timestamp.valueOf(newAppointment.getStartDT()));
        ps.setTimestamp(6, Timestamp.valueOf(newAppointment.getEndDT()));
        ps.setInt(7, newAppointment.getCustomer_ID());
        ps.setInt(8, newAppointment.getUser_ID());
        ps.setInt(9, newAppointment.getContactID());
        ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(11, "User " + Session.getLocalUserId());
        ps.setInt(12, newAppointment.getAppointmentId());
        return ps.executeUpdate();
    }

    /**
     * Deletes appointment record from database
     * @param AppointmentId the Appointment ID to search for
     * @return the number of records that were deleted
     */
    public static int delete(int AppointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, AppointmentId);
        return ps.executeUpdate();
    }

    /**
     * Deletes appointment record from database
     * @param newAppointment the appointment object to delete
     * @return the number of records that were deleted
     */
    public static int delete(Appointment newAppointment) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, newAppointment.getAppointmentId());
        return ps.executeUpdate();
    }
}
