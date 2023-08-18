package com.GPSTracker.gpstracker.Services;

import com.GPSTracker.gpstracker.Models.Location;
import com.GPSTracker.gpstracker.Repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public Optional<Location> findById(Integer uid){return locationRepository.findById(uid);}
    public List<Location> fetchLocationList() {
        return (List<Location>) locationRepository.findAll();
    }
}