package com.GPSTracker.gpstracker.Services;

import com.GPSTracker.gpstracker.Models.AlarmStatus;
import com.GPSTracker.gpstracker.Models.Connection;
import com.GPSTracker.gpstracker.Models.Device;
import com.GPSTracker.gpstracker.Models.HeartbeatStates;
import com.GPSTracker.gpstracker.Repositories.DeviceRepository;
import com.GPSTracker.gpstracker.Repositories.HeartbeatStatesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@EnableScheduling
public class HeartbeatStatesServices {

    @Autowired
    HeartbeatStatesRepository heartbeatStatesRepository;
    @Autowired
    EmailSendService emailSendService;
    @Autowired
    ConnectionService connectionService;
    @Autowired
    DeviceRepository deviceRepository;

    Map<Integer, Boolean> firstHB = new HashMap<>();

    public synchronized void updateState(Integer uuid) {
        heartbeatStatesRepository.saveOrUpdate(uuid, new Timestamp(System.currentTimeMillis()));
    }

    @Scheduled(fixedRate = 5_000, initialDelay = 10_000)
    public void checkOutdated() {
        List<HeartbeatStates> states = heartbeatStatesRepository.findAll();
        states.forEach(state -> {
            Device device = deviceRepository.getById(state.getUid());
            if ((System.currentTimeMillis() - state.getLastSavedTime().getTime()) <= 35_000) {
                log.info("Received heartbeat for uid: {}", state.getUid());
                firstHB.put(state.getUid(), null);
            } else {
                if (firstHB.get(state.getUid()) == null) {
                    emailSendService.sendEmailByAlarmStatus(state.getUid(), AlarmStatus.heartbeatAlarm);
                    connectionService.saveConnections(new Connection(heartbeatStatesRepository.getLastSavedTime(state.getUid()), false,false, device, AlarmStatus.heartbeatAlarm));
                    log.info("No received heartbeat for uid: {}", state.getUid());
                    firstHB.put(state.getUid(), false);
                }
            }
        });
    }
}
