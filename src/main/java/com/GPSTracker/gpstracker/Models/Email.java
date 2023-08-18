package com.GPSTracker.gpstracker.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    int id;

    @Column(name = "to", length = 50)
    String mailTo;

    @Column(name = "subject", length = 50)
    String subject;

    @Column(name = "message", length = 200)
    String message;
}