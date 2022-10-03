package com.rent.app.models;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "house")
@Data
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String meterNumber;

    private Integer overallMeterReading;

    private String status;

    private Integer advanceAmount;

    private Integer rentFixed;

    private Long houseOwnerId;

    private Integer pricePerUnit;

    @OneToMany
    @JoinColumn(name = "house_id")
    private Set<Rent> rents = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;



}
