package com.rent.app.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rent")
@Data
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long houseNumber;

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

    private RentStatus rentStatus;

}
