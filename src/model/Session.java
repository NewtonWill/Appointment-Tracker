package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.ZoneId;
import java.util.Objects;

public class Session {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    private static String localLanguage;
    private static ZoneId localZoneId;
    private static Integer localUserId;

    public Session(String localLanguage, ZoneId localZoneId, Integer localUserId){
        Session.localLanguage = localLanguage;
        Session.localZoneId = localZoneId;
        Session.localUserId = localUserId;
    }

    /**
     * Method adds customer to allCustomers
     * @param newCustomer the Customer to add to allCustomers
     */
    public static void addCustomer (Customer newCustomer){
        allCustomers.add(newCustomer);
    }

    /**
     * Searches all Customers to find a match with ID
     * @param CustomerId the Customer ID to search for
     * @return the Customer that matches the ID parameter
     */
    public static Customer lookupCustomer (int CustomerId){
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
            return false; //runtime used these lines to fix error when lookupCustomer() returned null
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

    public static String getLocalLanguage() {
        return localLanguage;
    }

    public void setLocalLanguage() {
        localLanguage = localLanguage;
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
}