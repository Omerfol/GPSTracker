package com.GPSTracker.gpstracker.ControllerTest;

import com.GPSTracker.gpstracker.Models.*;
import com.GPSTracker.gpstracker.Repositories.HeartbeatStatesRepository;
import com.GPSTracker.gpstracker.Services.ConnectionService;
import com.GPSTracker.gpstracker.Services.DeviceService;
import com.GPSTracker.gpstracker.Services.HeartbeatStatesServices;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@Slf4j
@SpringBootTest(classes = IntegrationTestConfiguration.class)
@ExtendWith(SpringExtension.class)
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class ArduinoHeartbeatTest {


    @Autowired
    HeartbeatStatesServices heartbeatStatesServices;

    @Autowired
    HeartbeatStatesRepository heartbeatStatesRepository;

    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private DeviceService deviceService;


    @MockBean
    JavaMailSender javaMailSender;




    @Test
    public void arduinoHeartbeat_NoReceivedHeartbeatTest() {
        //given
        Device device = new Device(2,"Device2","devmail@wp.pl",30,100,30);
        deviceService.saveDevice(new Device(1,"Device1","devmail@wp.pl",30,100,30));
        deviceService.saveDevice(device);

        HeartbeatStates heartbeatStates = new HeartbeatStates(1,1,new Timestamp(System.currentTimeMillis()));
        HeartbeatStates heartbeatStates2 = new HeartbeatStates(2,2,new Timestamp((System.currentTimeMillis()-36000)));
        heartbeatStatesRepository.save(heartbeatStates);
        heartbeatStatesRepository.save(heartbeatStates2);



        //when
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        heartbeatStatesServices.checkOutdated();

        //then
        Optional<Connection> expectedConnection = Optional.of(new Connection(1,heartbeatStatesRepository.getLastSavedTime(2), false,false, device, AlarmStatus.heartbeatAlarm));
        verify(javaMailSender,times(1)).send(any(SimpleMailMessage.class));
        assertEquals(expectedConnection,connectionService.findById(1));
    }
}