package com.GPSTracker.gpstracker.ServicesTest;

import com.GPSTracker.gpstracker.Models.Connection;
import com.GPSTracker.gpstracker.Repositories.ConnectionRepository;
import com.GPSTracker.gpstracker.Services.ConnectionService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConnectionsTest {
    @Mock
    private ConnectionRepository connectionsRepository;
    @InjectMocks
    private ConnectionService connectionService;

    public ConnectionsTest() {
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveConnections() {
        Connection connection = new Connection();
        Mockito.when((Connection)this.connectionsRepository.save((Connection)ArgumentMatchers.any(Connection.class))).thenReturn(connection);
        Connection savedConnection = this.connectionService.saveConnections(connection);
        Assert.assertEquals(connection, savedConnection);
        ((ConnectionRepository)Mockito.verify(this.connectionsRepository, Mockito.times(1))).save(connection);
    }

    @Test
    public void testFetchConnectionsList() {
        List<Connection> connectionsList = new ArrayList();
        Mockito.when(this.connectionsRepository.findAll()).thenReturn(connectionsList);
        List<Connection> fetchedConnectionsList = this.connectionService.fetchConnectionsList();
        Assert.assertEquals(connectionsList, fetchedConnectionsList);
        ((ConnectionRepository)Mockito.verify(this.connectionsRepository, Mockito.times(1))).findAll();
    }

    @Test
    public void testUpdateConnections() {
        Integer id = 1;
        Connection existingConnection = new Connection();
        Mockito.when(this.connectionsRepository.findById(id)).thenReturn(Optional.of(existingConnection));
        Connection updatedConnection = new Connection();
        Mockito.when((Connection)this.connectionsRepository.save((Connection)ArgumentMatchers.any(Connection.class))).thenReturn(updatedConnection);
        Connection result = this.connectionService.updateConnections(new Connection(), id);
        Assert.assertEquals(updatedConnection, result);
        ((ConnectionRepository)Mockito.verify(this.connectionsRepository, Mockito.times(1))).findById(id);
        ((ConnectionRepository)Mockito.verify(this.connectionsRepository, Mockito.times(1))).save(existingConnection);
    }

    @Test
    public void testDeleteConnections() {
        Integer id = 1;
        this.connectionService.deleteConnections(id);
        ((ConnectionRepository)Mockito.verify(this.connectionsRepository, Mockito.times(1))).deleteById(id);
    }

}
