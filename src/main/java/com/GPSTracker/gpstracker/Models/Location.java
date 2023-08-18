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
@Table(name = "location")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )

    @Column(name = "id_loc")
    int id;

    @Column(name = "date_and_time")
    Timestamp dateAndTime;

    @Column(name = "lon")
    String lon;

    @Column(name = "lat")
    String lat;

    @Column(name = "alarm")
    Boolean alarmStatus;

    @ManyToOne
    @JoinColumn(name = "uid_loc", referencedColumnName = "uid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Device device;

    public Location(Timestamp timestamp, String lon, String lat, Boolean alarm, Device device) {
        this.dateAndTime = timestamp;
        this.lon = lon;
        this.lat = lat;
        this.alarmStatus = alarm;
        this.device = device;
    }
}
