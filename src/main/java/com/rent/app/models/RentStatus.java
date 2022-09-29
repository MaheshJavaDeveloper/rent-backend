package com.rent.app.models;

public enum RentStatus {
    GENERATED("Generated"),SENT("Sent"),PAID("Paid"),UNPAID("UnPaid");

    String value;

    RentStatus(String value) {
        this.value =value;
    }
}
