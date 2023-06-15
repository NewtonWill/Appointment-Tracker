package model;

import java.time.ZoneId;

public class Session {

    private static String localLanguage;
    private static ZoneId localZoneId;
    private static Integer localUserId;

    public Session(String localLanguage, ZoneId localZoneId, Integer localUserId){
        Session.localLanguage = localLanguage;
        Session.localZoneId = localZoneId;
        Session.localUserId = localUserId;
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