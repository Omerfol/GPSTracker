package com.GPSTracker.gpstracker.Functions;

import com.GPSTracker.gpstracker.Models.*;
import com.GPSTracker.gpstracker.Repositories.DeviceRepository;
import com.GPSTracker.gpstracker.Services.ConnectionService;
import com.GPSTracker.gpstracker.Services.EmailSendService;
import com.GPSTracker.gpstracker.Services.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class CheckAlarmStatus {
    @Autowired
    ConnectionService connectionService;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    LocationService locationService;


    public Map<Integer, Boolean> firstMail = new HashMap<>();
    Timestamp arduinoTime = new Timestamp(System.currentTimeMillis());

    @Autowired
    EmailSendService emailSendService;

    public void readArduinoStatus(Integer uid, Boolean alarm, String lon, String lat) {
        if (alarm) {
            Device device = deviceRepository.getById(uid);
            log.info("Alarm activated");
            locationService.saveLocation(new Location(new Timestamp(System.currentTimeMillis()),lon,lat,alarm,device));
            emailSendService.sendEmailByButton(uid, lon, lat);
        }
    }



    public void readFencingStatus(Integer uid, FencingStates fencing) {
        Device device = deviceRepository.getById(uid);
        AlarmStatus alarmStatus = fencing.getAlarmStatus();
        if ((alarmStatus == AlarmStatus.fencingAlarm && fencing.getFencingStatus().equals(true)) || (alarmStatus == AlarmStatus.gpsAlarm && fencing.getFencingStatus().equals(true))) {
            log.info("Fencing alarm activated");
            if (alarmStatus == AlarmStatus.fencingAlarm) {
                connectionService.saveConnections(new Connection(arduinoTime, true,true, device, AlarmStatus.fencingAlarm));
                if (firstMail.get(uid) == null) {
                    emailSendService.sendEmailByAlarmStatus(uid, alarmStatus);
                    firstMail.put(uid,true);
                }
            } else {
                connectionService.saveConnections(new Connection(arduinoTime, true,true, device, AlarmStatus.gpsAlarm));
                if (firstMail.get(uid) == null) {
                    emailSendService.sendEmailByAlarmStatus(uid, alarmStatus);
                    firstMail.put(uid,true);
                }
            }
        }
    }

    public void resetFencingMailStatus(Integer uid) {
        firstMail.put(uid,null);
    }
}

