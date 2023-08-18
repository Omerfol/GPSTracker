package com.GPSTracker.gpstracker.Models;

public enum AlarmStatus {
    noAlarm("0"),
    fencingAlarm("1"),
    gpsAlarm("2"),
    heartbeatAlarm("3"),
    noFencingReceived("4"),
    Connected("10"),
    fencingOn("20"),
    fencingOff("21");


    private final String value;

    AlarmStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
