package model;

import helper.customersQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.util.Objects;


/**
 * Class designed to house all objects in memory and current user system information
 */
public class Session {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    private static ObservableList <Division> filteredDivisions = FXCollections.observableArrayList();
    private static ObservableList <Appointment> filteredAppointments = FXCollections.observableArrayList();

    public static ObservableList<Month> getAllMonths() {
        return allMonths;
    }

    public static void setAllMonths(ObservableList<Month> allMonths) {
        Session.allMonths = allMonths;
    }

    public static ObservableList<Integer> getAllYears() {
        return allYears;
    }

    public static void setAllYears(ObservableList<Integer> allYears) {
        Session.allYears = allYears;
    }

    private static ObservableList <Month> allMonths = FXCollections.observableArrayList(Month.values());
    private static ObservableList <Integer> allYears = FXCollections.observableArrayList();

    public static LocalDate getCurrentSunday() {
        return currentSunday;
    }

    public static void setCurrentSunday(LocalDate currentSunday) {
        Session.currentSunday = currentSunday;
    }

    private static LocalDate currentSunday;

    public static boolean isFirstTimeMainForm() {
        return firstTimeMainForm;
    }

    public static void setFirstTimeMainForm(boolean firstTimeMainForm) {
        Session.firstTimeMainForm = firstTimeMainForm;
    }

    private static boolean firstTimeMainForm = true;


    private static Integer nextCustomerId = 1;
    private static Integer nextAppointmentId = 1;

    private static String localLanguage;
    private static ZoneId localZoneId;
    private static Integer localUserId;

    public Session(String localLanguage, ZoneId localZoneId, Integer localUserId){
        Session.localLanguage = localLanguage;
        Session.localZoneId = localZoneId;
        Session.localUserId = localUserId;
    }

    public static ObservableList<Appointment> appointmentsForContact(Contact contact){
        if(!(getFilteredAppointments().isEmpty()))
            getFilteredAppointments().clear();

        for(Appointment appointmentX : getAllAppointments()){
            if(appointmentX.getContactID().equals(contact.getContactId())){
                getFilteredAppointments().add(appointmentX);
            }
        }
        return getFilteredAppointments();
    }

    /**
     * Method generates a list of appointments that correspond to specified week
     * @param firstDay the day to index by
     * @return the list of appointments
     */
    public static ObservableList<Appointment> appointmentGetWeek (LocalDate firstDay){

        if(!(getFilteredAppointments().isEmpty()))
            getFilteredAppointments().clear();

        LocalDate lastDay = firstDay.plusDays(6);

        for(Appointment appointmentX : getAllAppointments()){

            LocalDate appDate = appointmentX.getStartDT().toLocalDate();

            if(appDate.isEqual(firstDay) || appDate.isEqual(lastDay) || (appDate.isAfter(firstDay) && appDate.isBefore(lastDay))){
                getFilteredAppointments().add(appointmentX);
            }
        }

        if(getFilteredAppointments().isEmpty()) {
            System.out.println("No matching appointments found");
            /*Alert noMatchAlert = new Alert(Alert.AlertType.WARNING);
            noMatchAlert.setTitle("Appointment Search Error");
            noMatchAlert.setContentText("No matching Appointment(s) found");
            noMatchAlert.show();*/
        }
        return getFilteredAppointments();
    }


    /**
     * Method generates a list of appointments that correspond to specified month
     * @param month the month to index by
     * @return the list of appointments
     */
    public static ObservableList<Appointment> appointmentGetMonth (Month month, Integer year){

        if(!(getFilteredAppointments().isEmpty()))
            getFilteredAppointments().clear();

        for(Appointment appointmentX : getAllAppointments()){
            if(appointmentX.getStartDT().getYear() == year && appointmentX.getStartDT().getMonth().equals(month)){
                getFilteredAppointments().add(appointmentX);
            }
        }

        if(getFilteredAppointments().isEmpty()) {
            System.out.println("No matching appointments found");
            /*Alert noMatchAlert = new Alert(Alert.AlertType.WARNING);
            noMatchAlert.setTitle("Appointment Search Error");
            noMatchAlert.setContentText("No matching Appointment(s) found");
            noMatchAlert.show();*/
        }
        return getFilteredAppointments();
    }

    /**
     * Method generates and returns a unique Customer ID
     * @return the next generated Customer ID
     */
    public static Integer getNextCustomerId() {
        do{
            nextCustomerId++;
        }
        while (!(lookupCustomer(nextCustomerId) == null));
        return nextCustomerId;
    }

    /**
     * Method generates and returns a unique Appointment ID
     * @return the next generated Appointment ID
     */
    public static Integer getNextAppointmentId() {
        do{
            nextAppointmentId++;
        }
        while (!(lookupAppointment(nextAppointmentId) == null));
        return nextAppointmentId;
    }

    public static ObservableList<Division> filterDivisions(Integer countryId){
        if(!(getFilteredDivisions().isEmpty()))
            getFilteredDivisions().clear();

        for(Division divisionx : getAllDivisions()){
            if(divisionx.getCountryId() == countryId){
                getFilteredDivisions().add(divisionx);
            }
        }
        return getFilteredDivisions();
    }

    /**
     * Method adds customer to allCustomers
     * @param newCustomer the Customer to add to allCustomers
     */
    public static void addCustomer(Customer newCustomer) throws SQLException {
        allCustomers.add(newCustomer);
    }

    /**
     * Searches all Customers to find a match with ID
     * @param CustomerId the Customer ID to search for
     * @return the Customer that matches the ID parameter
     */
    public static Customer lookupCustomer(int CustomerId){
        for(Customer Customer : getAllCustomers()){
            if(Customer.getCustomerId() == CustomerId) {
                return Customer;
            }
        }
        return null;
    }

    /**
     * @return all Customers
     */
    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    /**
     * Method replaces Customer at index with Customer parameter
     * @param index the index of the Customer to replace
     * @param selectedCustomer the Customer to place at the specified index
     */
    public static void updateCustomer (int index, Customer selectedCustomer){

        getAllCustomers().set(index, selectedCustomer);
    }

    /**
     * Method verifies selection, checks for associated Customers, then deletes the Customer
     * @param selectedCustomer the Customer to delete
     * @return true if the deletion is successful
     */
    public static boolean deleteCustomer (Customer selectedCustomer){

        if (selectedCustomer == null) {
            System.out.println("Selected Customer is null. Delete aborted");
            return false;
        }

        for(Customer CustomerX : getAllCustomers()){
            if(Objects.equals(CustomerX.getCustomerId(), selectedCustomer.getCustomerId())){
                System.out.println("Customer ID " + selectedCustomer.getCustomerId() + " Deleted");
                return getAllCustomers().remove(CustomerX);
            }
        }
        System.out.println("Customer ID " + selectedCustomer.getCustomerId() + " Not Found");
        return false;
    }





    /**
     * Method adds Contact to allContacts
     * @param newContact the Contact to add to allContacts
     */
    public static void addContact (Contact newContact){
        allContacts.add(newContact);
    }

    /**
     * Searches all Contacts to find a match with ID
     * @param ContactId the Contact ID to search for
     * @return the Contact that matches the ID parameter
     */
    public static Contact lookupContact (int ContactId){
        for(Contact Contact : getAllContacts()){
            if(Contact.getContactId() == ContactId) {
                return Contact;
            }
        }
        return null;
    }

    /**
     * @return all Contacts
     */
    public static ObservableList<Contact> getAllContacts() {
        return allContacts;
    }

    /**
     * Method replaces Contact at index with Contact parameter
     * @param index the index of the Contact to replace
     * @param selectedContact the Contact to place at the specified index
     */
    public static void updateContact (int index, Contact selectedContact){

        getAllContacts().set(index, selectedContact);
    }

    /**
     * Method verifies selection, checks for associated Contacts, then deletes the Contact
     * @param selectedContact the Contact to delete
     * @return true if the deletion is successful
     */
    public static boolean deleteContact (Contact selectedContact){

        if (selectedContact == null) {
            System.out.println("Selected Contact is null. Delete aborted");
            return false;
        }

        for(Contact ContactX : getAllContacts()){
            if(Objects.equals(ContactX.getContactId(), selectedContact.getContactId())){
                System.out.println("Contact ID " + selectedContact.getContactId() + " Deleted");
                return getAllContacts().remove(ContactX);
            }
        }
        System.out.println("Contact ID " + selectedContact.getContactId() + " Not Found");
        return false;
    }




    /**
     * Method adds Appointment to allAppointments
     * @param newAppointment the Appointment to add to allAppointments
     */
    public static void addAppointment (Appointment newAppointment){
        allAppointments.add(newAppointment);
    }

    /**
     * Searches all Appointments to find a match with ID
     * @param AppointmentId the Appointment ID to search for
     * @return the Appointment that matches the ID parameter
     */
    public static Appointment lookupAppointment (int AppointmentId){
        for(Appointment Appointment : getAllAppointments()){
            if(Appointment.getAppointmentId() == AppointmentId) {
                return Appointment;
            }
        }
        return null;
    }

    /**
     * @return all Appointments
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    /**
     * Method replaces Appointment at index with Appointment parameter
     * @param index the index of the Appointment to replace
     * @param selectedAppointment the Appointment to place at the specified index
     */
    public static void updateAppointment (int index, Appointment selectedAppointment){

        getAllAppointments().set(index, selectedAppointment);
    }

    /**
     * Method verifies selection, checks for associated Appointments, then deletes the Appointment
     * @param selectedAppointment the Appointment to delete
     * @return true if the deletion is successful
     */
    public static boolean deleteAppointment (Appointment selectedAppointment){

        if (selectedAppointment == null) {
            System.out.println("Selected Appointment is null. Delete aborted");
            return false;
        }

        for(Appointment AppointmentX : getAllAppointments()){
            if(Objects.equals(AppointmentX.getAppointmentId(), selectedAppointment.getAppointmentId())){
                System.out.println("Appointment ID " + selectedAppointment.getAppointmentId() + " Deleted");
                return getAllAppointments().remove(AppointmentX);
            }
        }
        System.out.println("Appointment ID " + selectedAppointment.getAppointmentId() + " Not Found");
        return false;
    }





    /**
     * Method adds Country to allCountries
     * @param newCountry the Country to add to allCountries
     */
    public static void addCountry (Country newCountry){
        allCountries.add(newCountry);
    }

    /**
     * Searches all Countries to find a match with ID
     * @param CountryId the Country ID to search for
     * @return the Country that matches the ID parameter
     */
    public static Country lookupCountry (int CountryId){
        for(Country Country : getAllCountries()){
            if(Country.getCountryId() == CountryId) {
                return Country;
            }
        }
        return null;
    }

    /**
     * @return all Countries
     */
    public static ObservableList<Country> getAllCountries() {
        return allCountries;
    }







    /**
     * Method adds Division to allDivisions
     * @param newDivision the Division to add to allDivisions
     */
    public static void addDivision(Division newDivision){
        allDivisions.add(newDivision);
    }

    /**
     * Searches all Divisions to find a match with ID
     * @param DivisionId the Division ID to search for
     * @return the Division that matches the ID parameter
     */
    public static Division lookupDivision(int DivisionId){
        for(Division Division : getAllDivisions()){
            if(Division.getDivisionId() == DivisionId) {
                return Division;
            }
        }
        return null;
    }

    /**
     * @return all Divisions
     */
    public static ObservableList<Division> getAllDivisions() {
        return allDivisions;
    }






    public static String getLocalLanguage() {
        return localLanguage;
    }

    public void setLocalLanguage() {
        Session.localLanguage = localLanguage;
    }

    public static ZoneId getLocalZoneId() {
        return localZoneId;
    }

    public void setLocalZoneId(ZoneId localZoneId) {
        Session.localZoneId = localZoneId;
    }

    public static Integer getLocalUserId() {
        return localUserId;
    }

    public static void setLocalUserId(Integer localUserId) {
        Session.localUserId = localUserId;
    }

    public static ObservableList<Division> getFilteredDivisions() {
        return filteredDivisions;
    }

    public static void setFilteredDivisions(ObservableList<Division> filteredDivisions) {
        Session.filteredDivisions = filteredDivisions;
    }

    public static ObservableList<Appointment> getFilteredAppointments() {
        return filteredAppointments;
    }

    public static void setFilteredAppointments(ObservableList<Appointment> filteredAppointments) {
        Session.filteredAppointments = filteredAppointments;
    }

}