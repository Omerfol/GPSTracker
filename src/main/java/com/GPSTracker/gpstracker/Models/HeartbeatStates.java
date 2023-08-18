package com.GPSTracker.gpstracker.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

@Entity
@Table(name = "heartbeat_states")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeartbeatStates {

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

    @Column(name = "last_saved_time")
    Timestamp lastSavedTime;


}
