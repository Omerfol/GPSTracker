package com.GPSTracker.gpstracker.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;

@Entity
@Table(name = "connection")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Connection {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    @Column(name = "id_con")
    int id;

    @Column(name = "date_and_time")
    Timestamp dateAndTime;

    @Column(name = "gps_status")
    Boolean gpsStatus;

    @Column(name = "alarm_status")
    Boolean alarmStatus;

    @ManyToOne
    @JoinColumn(name = "uid_con", referencedColumnName = "uid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Device device;

    @Column(name = "alarm_status_from_fencing")
    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmStatusFromFencing;

    public Connection(Timestamp arduinoTime, Boolean status, Device device) {
        this.dateAndTime = arduinoTime;
        this.gpsStatus = status;
        this.device = device;
    }

    public Connection(int id, Timestamp dateAndTime, Boolean status, Device device) {
        this.id = id;
        this.dateAndTime = dateAndTime;
        this.gpsStatus = status;
        this.device = device;
    }

    public Connection(Timestamp dateAndTime, Boolean status, Device device, AlarmStatus alarmStatusFromFencing) {
        this.dateAndTime = dateAndTime;
        this.gpsStatus = status;
        this.device = device;
        this.alarmStatusFromFencing = alarmStatusFromFencing;
    }

    public Connection(Timestamp dateAndTime, Boolean status, Device device, Boolean alarmStatus) {
        this.dateAndTime = dateAndTime;
        this.gpsStatus = status;
        this.device = device;
        this.alarmStatus = alarmStatus;
    }

    public Connection(Timestamp lastSavedTime, boolean gpsStatus, boolean alarmStatus, Device device, AlarmStatus alarmStatusFromFencing) {
        this.dateAndTime = lastSavedTime;
        this.gpsStatus = gpsStatus;
        this.alarmStatus = alarmStatus;
        this.device = device;
        this.alarmStatusFromFencing = alarmStatusFromFencing;
    }
}
