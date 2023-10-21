package ru.datamanagment.roadTransportCopany.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "created_at_date", nullable = false)
    private LocalDate createdAt;



    public Payment(Trip trip, Integer amount, LocalDate createdAt) {
        this.trip = trip;
        this.amount = amount;
        this.createdAt = createdAt;
    }
}