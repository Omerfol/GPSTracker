package com.GPSTracker.gpstracker.ControllerTest;


import com.GPSTracker.gpstracker.Controllers.ArduinoController;
import com.GPSTracker.gpstracker.Models.*;
import com.GPSTracker.gpstracker.Repositories.DeviceRepository;
import com.GPSTracker.gpstracker.Services.ConnectionService;
import com.GPSTracker.gpstracker.Services.LocationService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;



@Slf4j
@SpringBootTest(classes = IntegrationTestConfiguration.class)
@RunWith(SpringRunner.class)
@Testcontainers
@Transactional
public class ArduinoDataSaveAndReadTest {

    @Autowired
    private ArduinoController arduinoController;
    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private DeviceRepository deviceRepository;


    @Test
    public void dataInputAndReadTest2()  {
        //given
        deviceRepository.save(new Device("device1","devmail@wp.pl",30,100,30));
        Device device = deviceRepository.getById(1);
        int arduinoTestTime = 1679819822;
        Boolean status = true;
        Boolean alarm = false;
        Timestamp ts = new Timestamp(arduinoTestTime * 1000L);
        String lon = "15.51";
        String lat = "91.15";
        Arduino arduino = new Arduino(status, lon, lat, alarm, arduinoTestTime);



        //When
        arduinoController.saveDataFromArduino(1,arduino);
        Optional<Connection> savedConnectionStatus = connectionService.findById(1);
        Optional<Location> savedLocation = locationService.findById(1);

        //Then
        Optional<Location> expectedLocation = Optional.of(new Location(1, ts, lon, lat, alarm, device));
        Optional<Connection> expectedConnectionStatus = Optional.of(new Connection(1,ts, status,false, device, AlarmStatus.Connected));
        assertEquals(expectedConnectionStatus, savedConnectionStatus);
        assertEquals(expectedLocation, savedLocation);

    }


}
