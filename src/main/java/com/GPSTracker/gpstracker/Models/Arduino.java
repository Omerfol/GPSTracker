package com.GPSTracker.gpstracker.Models;

import lombok.Value;



@Value
public class Arduino {

    Boolean status;
    String lon;
    String lat;
    Boolean alarm;
    Integer arduino_time;

}
