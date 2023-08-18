package com.GPSTracker.gpstracker.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;


@Value
@Data
@Getter
@Setter
public class DeviceInfo {
    Integer interval;
    Long time;
    Integer heartbeat_rate;
    Integer range;

}
