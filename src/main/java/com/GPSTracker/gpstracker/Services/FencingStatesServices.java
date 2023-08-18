package com.GPSTracker.gpstracker.Services;

import com.GPSTracker.gpstracker.Functions.CheckAlarmStatus;
import com.GPSTracker.gpstracker.Models.AlarmStatus;
import com.GPSTracker.gpstracker.Models.Connection;
import com.GPSTracker.gpstracker.Models.Device;
import com.GPSTracker.gpstracker.Models.FencingStates;
import com.GPSTracker.gpstracker.Repositories.DeviceRepository;
import com.GPSTracker.gpstracker.Repositories.FencingStatesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FencingStatesServices {

    @Autowired
    FencingStatesRepository fencingStatesRepository;
    @Autowired
    EmailSendService emailSendService;
    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    ConnectionService connectionService;
    @Autowired
    CheckAlarmStatus checkAlarmStatus;
    public Map<Integer, Boolean> firstSaveToDb = new HashMap<>();
    public Map<Integer, Boolean> firstFS = new HashMap<>();

    public synchronized void updateState(Integer uuid, FencingStates fencingStates) {
        checkAlarmStatus.readFencingStatus(uuid,fencingStates);
        fencingStatesRepository.saveOrUpdate(uuid, fencingStates.getFencingStatus(), new Timestamp(System.currentTimeMillis()));
        firstFS.put(uuid,null);
    }


//

    @Scheduled(fixedRate = 1_000)
    public void checkOutdated() {
        List<FencingStates> states = fencingStatesRepository.findAll();
        states.forEach(state -> {
            Device device = deviceRepository.getById(state.getUid());
            if (!(((System.currentTimeMillis() - state.getLastSavedTime().getTime())) <= 18_000) && fencingStatesRepository.getFencingStatus(state.getUid())) {
                if (firstFS.get(state.getUid()) == null) {
                    log.info("No received fencing for uid: {}", state.getUid());
                    emailSendService.sendEmailByAlarmStatus(state.getUid(), AlarmStatus.noFencingReceived);
                    connectionService.saveConnections(new Connection(state.getLastSavedTime(), false,true, device, AlarmStatus.noFencingReceived));
                    firstFS.put(state.getUid(), true);
                    firstSaveToDb.put(state.getUid(),null);
                }
            } else if (!fencingStatesRepository.getFencingStatus(state.getUid())) {
                if (firstFS.get(state.getUid()) == null) {
                    log.info("Fencing disabled for uid: {}", state.getUid());
                    connectionService.saveConnections(new Connection(fencingStatesRepository.getLastSavedTime(state.getUid()), true,false, device, AlarmStatus.fencingOff));
                    firstFS.put(state.getUid(), false);
                    firstSaveToDb.put(state.getUid(),null);
                    checkAlarmStatus.resetFencingMailStatus(state.getUid());
                }
            } else if (fencingStatesRepository.getFencingStatus(state.getUid())) {
                log.info("Fencing received for uid: {}", state.getUid());
                if (firstSaveToDb.get(state.getUid()) == null) {
                    connectionService.saveConnections(new Connection(fencingStatesRepository.getLastSavedTime(state.getUid()), true,false, device, AlarmStatus.fencingOn));
                    firstSaveToDb.put(state.getUid(),true);
                }
            }
        });
    }
}

