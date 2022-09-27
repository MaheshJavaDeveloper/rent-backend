package com.rent.app.models;

import lombok.Data;

import javax.persistence.*;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "current_tenant_id", referencedColumnName = "id")
    private Tenant currentTenant;

}
