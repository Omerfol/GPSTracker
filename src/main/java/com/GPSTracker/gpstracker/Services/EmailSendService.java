package com.GPSTracker.gpstracker.Services;

import com.GPSTracker.gpstracker.Models.AlarmStatus;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Data
@Service
public class EmailSendService {

    @Autowired
    DeviceService deviceService;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    ConnectionService connectionService;


    String mailTo;
    String subject;
    String text;

    public SimpleMailMessage setUpEmailDetails(Integer uid){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String mailTo = String.valueOf(deviceService.getMailByUid(uid));
        mailMessage.setTo(mailTo);
        mailMessage.setSubject("Your GPS Tracker status has changed!");
        mailMessage.setText("Hello, the current status of your device is: ");
        if (mailTo == null) {
            throw new IllegalArgumentException("Empty address e-mail field.");
        }
        return mailMessage;
    }
    @Async
    public void sendEmailByButton(Integer uid, String lon, String lat) {
        SimpleMailMessage mailMessage =  setUpEmailDetails(uid);
        String mailTo = String.valueOf(deviceService.getMailByUid(uid));
        mailMessage.setSubject("Your GPS Tracker status has changed!");
        mailMessage.setText(setUpEmailDetails(1).getText()+
                            "send location by button.\n" +
                            "Your location is:\n" +
                            "Latitude: " + lat +
                            "\nLongitude: " + lon);
        javaMailSender.send(mailMessage);
        log.info("Sending e-mail to: " + mailTo);
    }

    @Async
    public void sendEmailByAlarmStatus(Integer uid, AlarmStatus alarmStatus) {
        SimpleMailMessage mailMessage =  setUpEmailDetails(uid);
        log.info("uid statusowe to: " + uid);
        alarmStatus.getValue();
        if (alarmStatus == AlarmStatus.fencingAlarm) {
            mailMessage.setText(text + "Actual alarm status: fencing alarm");
        } else if (alarmStatus == AlarmStatus.gpsAlarm) {
            mailMessage.setText("Actual alarm status: GPS alarm");
        } else if (alarmStatus == AlarmStatus.heartbeatAlarm) {
            mailMessage.setText("Actual alarm status: heartbeat alarm");
        } else if (alarmStatus == AlarmStatus.noFencingReceived) {
            mailMessage.setText("Actual alarm status: noFencingReceived alarm");
        } else {
            log.info("Something goes wrong!!!");
        }
        javaMailSender.send(mailMessage);
    }
}