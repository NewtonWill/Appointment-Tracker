package model;

import java.time.ZoneId;

public class Session {

    private static String localLanguage;
    private static ZoneId localZoneId;

    public Session(String localLanguage, ZoneId localZoneId){
        Session.localLanguage = localLanguage;
        Session.localZoneId = localZoneId;
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


}
