package com.GPSTracker.gpstracker.ControllerTest;

import com.GPSTracker.gpstracker.ConfigForEmail;
import com.GPSTracker.gpstracker.Models.*;
import com.GPSTracker.gpstracker.Repositories.DeviceRepository;
import com.GPSTracker.gpstracker.Repositories.FencingStatesRepository;
import com.GPSTracker.gpstracker.Services.ConnectionService;
import com.GPSTracker.gpstracker.Services.DeviceService;
import com.GPSTracker.gpstracker.Services.EmailSendService;
import com.GPSTracker.gpstracker.Services.FencingStatesServices;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = IntegrationTestConfiguration.class)
@ExtendWith(SpringExtension.class)
@Testcontainers
@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@Configuration
@ComponentScan
public class GetFencingDataTest {
    @Autowired
    FencingStatesRepository fencingStatesRepository;
    @Autowired
    FencingStatesServices fencingStatesServices;
    @Autowired
    DeviceService deviceService;
    @Autowired
    ConnectionService connectionService;
    @Autowired
    DeviceRepository deviceRepository;

    @MockBean
    JavaMailSender javaMailSender;

    @Autowired
    EmailSendService emailSendService;
    @Autowired
    ConfigForEmail configForEmail;
//
    @Autowired
    public GetFencingDataTest(ConfigForEmail configForEmail) {
        this.configForEmail = configForEmail;
    }


    @Test
    public void getFencingData_FencingEnableTest() {

        //given
        deviceRepository.save(new Device(1,"device1","devmail@wp.pl",30,100,30));
        Device device = deviceRepository.getById(1);
        FencingStates fencingStates = new FencingStates(1,1,true,"15","15",new Timestamp(System.currentTimeMillis()),AlarmStatus.fencingOn);
        fencingStatesRepository.save(fencingStates);

        //when

        fencingStatesServices.checkOutdated();
        //then
        Optional<Connection> expectedConnection = Optional.of(new Connection(1,fencingStatesRepository.getLastSavedTime(1), true,false, device, AlarmStatus.fencingOn));
        assertTrue(fencingStatesServices.firstSaveToDb.get(1));
        assertEquals(expectedConnection,connectionService.findById(1));
    }

    @Test
    public void getFencingData_FencingDisableTest() {

        //given
        deviceRepository.save(new Device(1,"device1","devmail@wp.pl",30,100,30));
        Device device = deviceRepository.getById(1);
        FencingStates fencingStates = new FencingStates(1,1,true,"15","15",new Timestamp(System.currentTimeMillis()),AlarmStatus.fencingOn);
        FencingStates fencingStates1 = new FencingStates(1,1,false,"15","15",new Timestamp(System.currentTimeMillis()),AlarmStatus.fencingOff);
        fencingStatesRepository.save(fencingStates);
        fencingStatesRepository.save(fencingStates1);
        //when
        fencingStatesServices.checkOutdated();
        //then
        Optional<Connection> expectedConnection = Optional.of(new Connection(1,fencingStatesRepository.getLastSavedTime(1), true,false, device, AlarmStatus.fencingOff));
        assertEquals(expectedConnection,connectionService.findById(1));
    }

    @Test
    public void getFencingData_FencingNoReceivedTest() {

        //given
        deviceRepository.save(new Device(1,"device1","devmail@wp.pl",30,100,30));
        Device device = deviceRepository.getById(1);
        deviceService.saveDevice(new Device(1,"Device1","devmail@wp.pl",30,100,30));
        deviceService.saveDevice(new Device(2,"Device2","devmail@wp.pl",30,100,30));
        FencingStates fencingStates = new FencingStates(1,1,true,"15","15",new Timestamp(System.currentTimeMillis()),AlarmStatus.fencingOn);
        FencingStates fencingStates1 = new FencingStates(1,1,true,"15","15",new Timestamp(System.currentTimeMillis()-40_000),AlarmStatus.fencingOff);

        fencingStatesRepository.save(fencingStates);
        fencingStatesRepository.save(fencingStates1);
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        //when
        fencingStatesServices.checkOutdated();

        //then
        Optional<Connection> expectedConnection = Optional.of(new Connection(1,fencingStatesRepository.getLastSavedTime(1), false,true, device, AlarmStatus.noFencingReceived));
        assertEquals(expectedConnection,connectionService.findById(1));
        verify(javaMailSender,times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void GetFencingEmailSendingTest() {
        // Given
        Arduino arduino = new Arduino(true, "15.61659", "51.13476", true, 1234567890);
        Device expectedDevice = new Device(1, "device1", "mail@gmail.com", 30, 100, 30);
        deviceService.saveDevice(expectedDevice);
        SimpleMailMessage expectedMailMessage = new SimpleMailMessage();
        String getMailFrom = expectedMailMessage.getFrom();
        log.info("Sending e-mail from: " + getMailFrom);
        expectedMailMessage.setTo(deviceService.getMailByUid(1));
        log.info(Arrays.toString(expectedMailMessage.getTo()));
        expectedMailMessage.setSubject("Your GPS Tracker status has changed!");
        emailSendService.setUpEmailDetails(1).getText();
        expectedMailMessage.setText(emailSendService.setUpEmailDetails(1).getText()+ "send location by button.\n" +
                "Your location is:\n" +
                "Latitude: " + arduino.getLat() +
                "\nLongitude: "  + arduino.getLon());

        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        // When
        emailSendService.sendEmailByButton(1, arduino.getLon(), arduino.getLat());

        // Then
        verify(javaMailSender, atLeastOnce()).send(eq(expectedMailMessage));
    }
}
