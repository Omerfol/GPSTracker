package com.GPSTracker.gpstracker.Services;

import com.GPSTracker.gpstracker.Models.Connection;
import com.GPSTracker.gpstracker.Repositories.ConnectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class ConnectionService {
    @Autowired
    private ConnectionRepository connectionsRepository;


    public Optional<Connection> findById(Integer id) {
        return connectionsRepository.findById(id);
    }

    public Connection saveConnections(Connection connection) {
        return connectionsRepository.save(connection);
    }

    public List<Connection> fetchConnectionsList() {
        return (List<Connection>) connectionsRepository.findAll();
    }

    public Connection updateConnections(Connection connection, Integer id) {
        Connection connectionDB = connectionsRepository.findById(id).get();
        if (Objects.nonNull(connection.getDateAndTime())
                && !"".equalsIgnoreCase(
                String.valueOf(connection.getDateAndTime()))) {
            connectionDB.setDateAndTime(
                    connection.getDateAndTime());
        }
        return connectionsRepository.save(connectionDB);
    }

    public void deleteConnections(Integer id) {
        connectionsRepository.deleteById(id);
    }
}