package com.GPSTracker.gpstracker.Mapper;

import com.GPSTracker.gpstracker.Models.*;
import com.GPSTracker.gpstracker.Repositories.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Slf4j
public class ConAndLocMapperImpl {
    @Autowired
    DeviceRepository deviceRepository;

    public Location arduinoDataToLocationObject(Arduino arduino, Integer uid) {
        Device device = deviceRepository.getById(uid);
        long arduinoTime = arduino.getArduino_time() * 1000L;
        Timestamp ts = new Timestamp(arduinoTime);
        Location location = Location.builder()
                .dateAndTime(ts)
                .lon(arduino.getLon())
                .lat(arduino.getLat())
                .device(device)
                .alarmStatus(arduino.getAlarm())
                .build();
        log.info("arduinoDataToLocationObject {}",String.valueOf(location));
        return location;
    }

    public Connection arduinoDataToConnectionObject(Arduino arduino, Integer uid) {
        Device device = deviceRepository.getById(uid);
        long arduinoTime = arduino.getArduino_time() * 1000L;
        Timestamp ts = new Timestamp(arduinoTime);
        Connection connection = Connection.builder()
                .dateAndTime(ts)
                .gpsStatus(arduino.getStatus())
                .alarmStatus(arduino.getAlarm())
                .device(device)
                .alarmStatusFromFencing(AlarmStatus.Connected).build();
        log.info("arduinoDataToConnectionObject {}", String.valueOf(connection));
        return connection;

    }
}
