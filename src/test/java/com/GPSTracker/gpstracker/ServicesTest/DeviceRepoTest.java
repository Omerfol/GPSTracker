package com.GPSTracker.gpstracker.ServicesTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.GPSTracker.gpstracker.ControllerTest.IntegrationTestConfiguration;
import com.GPSTracker.gpstracker.Models.Device;
import com.GPSTracker.gpstracker.Repositories.DeviceRepository;
import com.GPSTracker.gpstracker.Services.DeviceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = IntegrationTestConfiguration.class)
public class DeviceRepoTest {
    @Mock
    private DeviceRepository deviceRepository;


    @InjectMocks
    private DeviceService deviceService;

    public void addDevice() {
        Device dev = new Device("Device1","devmail@wp.pl",30,15,30);
        deviceService.saveDevice(dev);

        verify(deviceRepository, times(1)).save(dev);
        verifyNoMoreInteractions(deviceRepository);
    }



    @Test
    public void FindDeviceById_NotFound() {
        Integer deviceUid = 9999;
        String expectedResponse = "Not found";

        String result = (String) deviceService.findById(deviceUid);
        assertEquals(expectedResponse, result);
    }


    public void findDeviceById() {
        Integer deviceUid = 1;
        Device expected = new Device("Device1","devmail@gmail.com",30,15,30);
        when(deviceRepository.findById(deviceUid)).thenReturn(Optional.of(expected));
        Optional<Device> actualDevice = (Optional<Device>) deviceService.findById(deviceUid);

        assertEquals(Optional.of(expected), actualDevice);
    }

}
