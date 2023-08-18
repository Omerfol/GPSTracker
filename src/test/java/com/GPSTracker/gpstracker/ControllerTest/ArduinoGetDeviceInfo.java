package com.GPSTracker.gpstracker.ControllerTest;

import com.GPSTracker.gpstracker.Controllers.ArduinoController;
import com.GPSTracker.gpstracker.Models.DeviceInfo;
import com.GPSTracker.gpstracker.Models.Device;
import com.GPSTracker.gpstracker.Repositories.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;


import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@Import(IntegrationTestConfiguration.class)
@SpringBootTest(classes = IntegrationTestConfiguration.class)
@Testcontainers
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ArduinoGetDeviceInfo {

    @Autowired
    ArduinoController arduinoController;
    @Autowired
    DeviceRepository deviceRepository;

    @Test
    public void sendInfoToDevice(){
        //given
        Device device = new Device("Device1","devmail@wp.pl",30,100,30);
        deviceRepository.save(device);

        //when
        DeviceInfo d1 = arduinoController.sendInformationToArduinoAboutDevice(1);
        //then
        DeviceInfo expectedInfo = new DeviceInfo(30, d1.getTime(),30,100);
        assertEquals(expectedInfo,d1);
    }
}

