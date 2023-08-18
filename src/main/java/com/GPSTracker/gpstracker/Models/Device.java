package com.GPSTracker.gpstracker.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.transaction.annotation.Transactional;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Transactional
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    @Column(name = "uid")
    Integer uid;

    @Column(name = "name")
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "`interval`")
    Integer interval;

    @Column(name = "`range`")
    Integer range;

    @Column(name = "heartbeat_rate")
    Integer heartbeat_rate;


    public Device(String name, String email, Integer interval, Integer range, Integer heartbeat_rate) {
        this.name = name;
        this.email = email;
        this.interval = interval;
        this.range = range;
        this.heartbeat_rate = heartbeat_rate;
    }

}
