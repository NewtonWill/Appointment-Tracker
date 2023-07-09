package model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


/**
 * Model class for appointment objects
 */
public class Appointment {

    private Integer appointmentId;
    private String Title;
    private String Description;
    private String Location;
    private Integer ContactID;
    private String Type;
    private LocalDateTime StartDT;
    private LocalDateTime EndDT;
    private Integer Customer_ID;
    private Integer User_ID;

    public Appointment(Integer appointmentId, String title, String description,
                       String location, Integer contactID, String type,
                       LocalDateTime startDT, LocalDateTime endDT, Integer customer_ID, Integer user_ID) {
        this.appointmentId = appointmentId;
        this.Title = title;
        this.Description = description;
        this.Location = location;
        this.ContactID = contactID;
        this.Type = type;
        this.StartDT = startDT;
        this.EndDT = endDT;
        this.Customer_ID = customer_ID;
        this.User_ID = user_ID;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public Integer getContactID() {
        return ContactID;
    }

    public void setContactID(Integer contactID) {
        ContactID = contactID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public LocalDateTime getStartDT() {
        return StartDT;
    }

    public void setStartDT(LocalDateTime startDT) {
        StartDT = startDT;
    }

    public LocalDateTime getEndDT() {
        return EndDT;
    }

    public void setEndDT(LocalDateTime endDT) {
        EndDT = endDT;
    }

    public Integer getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(Integer customer_ID) {
        Customer_ID = customer_ID;
    }

    public Integer getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(Integer user_ID) {
        User_ID = user_ID;
    }

    public String getEndLocalZone(){
        ZonedDateTime endZDT = ZonedDateTime.of(getEndDT(), ZoneId.of("UTC"));
        String endLocal = endZDT.withZoneSameInstant(Session.getLocalZoneId()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
        return endLocal;
    }
    public String getStartLocalZone(){
        ZonedDateTime startZDT = ZonedDateTime.of(getEndDT(), ZoneId.of("UTC"));
        String startLocal = startZDT.withZoneSameInstant(Session.getLocalZoneId()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
        return startLocal;
    }
}
