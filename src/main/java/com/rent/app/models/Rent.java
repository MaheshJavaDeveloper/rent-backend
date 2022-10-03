package com.rent.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rent")
@Data
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long houseNumber;

    @Column(unique=true)
    private String invoiceNumber;

    private Date billDate;

    private Integer rentAmount;

    private Integer currentMeterReading;

    private Integer previousMeterReading;

    private Integer consumedMeterReading;

    private Integer electricCharges;

    private Integer waterCharge;

    private Integer pendingPayment;

    private Integer otherCharges;

    private Integer totalRent;

    private Long houseOwnerId;

    @Enumerated(EnumType.STRING)
    private RentStatus rentStatus;

    @OneToMany
    @JoinColumn(name = "rent_id")
    private Set<Payment> payments = new HashSet<>();

}
