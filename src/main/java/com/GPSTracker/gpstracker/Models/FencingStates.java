package com.GPSTracker.gpstracker.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

@Entity
@Table(name = "fencing_states")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FencingStates {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    @Column(name = "id")
    Integer id;

    @Column(name = "uid",unique = true)
    Integer uid;

    @Column(name = "fencing_status")
    Boolean fencingStatus;

    @Column(name = "fencing_lon")
    String lon;

    @Column(name = "fencing_lat")
    String lat;

    @Column(name = "last_saved_time")
    Timestamp lastSavedTime;

    @Column(name = "fencing_alarm")
    @Enumerated(EnumType.STRING)
    AlarmStatus alarmStatus;

    public FencingStates(boolean b, AlarmStatus alarmStatus) {
        this.fencingStatus = b;
        this.alarmStatus = alarmStatus;
    }
}
