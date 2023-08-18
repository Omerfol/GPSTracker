package com.GPSTracker.gpstracker.Services;

import com.GPSTracker.gpstracker.Models.DeviceInfo;
import com.GPSTracker.gpstracker.Models.Device;
import com.GPSTracker.gpstracker.Repositories.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;


    public Device saveDevice(Device device) {
        return deviceRepository.save(device);
    }


    public List<Device> findAll() {
        return (List<Device>) deviceRepository.findAll();
    }

    public Object findById(Integer uid) {
        if (deviceRepository.findById(uid).isEmpty()) {
            return "Not found";
        } else {
            return Optional.ofNullable(deviceRepository.getById(uid));
        }
    }

    public String getMailByUid(Integer emailWithUid) {
        Optional<Device> device = deviceRepository.findById(emailWithUid);
        return device.get().getEmail();
    }

    Map<Integer, Boolean> firstHB = new HashMap<>();

    public DeviceInfo getDeviceInfo(Integer uid) {
        return deviceRepository.findById(uid).map(
                        dev -> {
                            DeviceInfo di = new DeviceInfo(dev.getInterval(), System.currentTimeMillis(), dev.getHeartbeat_rate(), dev.getRange());
                            return di;
                        }
                )
                .orElseThrow(() -> new RuntimeException("Empty database"));
    }


    public Device updateDevice(Integer uid, Device device) {
        return deviceRepository.findById(uid)
                .map(dev -> {
                    dev.setUid(device.getUid());
                    dev.setName(device.getName());
                    dev.setEmail(device.getEmail());
                    dev.setInterval(device.getInterval());
                    dev.setRange(device.getRange());
                    dev.setHeartbeat_rate(device.getHeartbeat_rate());
                    return deviceRepository.save(dev);
                })
                .orElseGet (() -> {
                    device.setUid(uid);
                    return deviceRepository.save(device);
                });
    }


    public void deleteDevice(Integer uid) {
        deviceRepository.deleteById(uid);
    }

}
