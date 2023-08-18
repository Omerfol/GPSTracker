package com.GPSTracker.gpstracker.Services;

import com.GPSTracker.gpstracker.Functions.CheckAlarmStatus;
import com.GPSTracker.gpstracker.Models.*;
import com.GPSTracker.gpstracker.Repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class SaveService {
    @Autowired
    LocationService locationService;
    @Autowired
    private CheckAlarmStatus readButton;
    @Autowired
    ConnectionService connectionService;
    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    CheckAlarmStatus checkAlarmStatus;
    public Map<Integer, Boolean> firstSave = new HashMap<>();

    public void saveConAndLocObjectToDB(Integer uid, Location location, Connection connection, Arduino arduino) {
        readButton.readArduinoStatus(uid, connection.getAlarmStatus(), location.getLon(), location.getLat());
        locationService.saveLocation(location);
        if (firstSave.get(uid) == null) {
            Device device = deviceRepository.getById(uid);
            connectionService.saveConnections(new Connection(connection.getDateAndTime(),connection.getGpsStatus(), connection.getAlarmStatus() , device ,AlarmStatus.Connected));
            firstSave.put(uid,true);
        }
    }


}
