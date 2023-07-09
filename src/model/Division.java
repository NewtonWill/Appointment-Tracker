package model;

/**
 * Model class for Division objects
 */
public class Division {

    private Integer divisionId;
    private String divisionName;
    private Integer countryId;
    private String countryName;

    public Division(Integer divisionId, String divisionName, Integer countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
        this.countryName = Session.lookupCountry(countryId).getCountryName();
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString(){
        return ("#" + Integer.toString(divisionId) + " - " + divisionName + " [" + countryName + "]");
    }
}
