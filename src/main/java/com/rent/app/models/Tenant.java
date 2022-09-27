package com.rent.app.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tenant")
@Data
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    private String status;

    private String houseNumber;

}
