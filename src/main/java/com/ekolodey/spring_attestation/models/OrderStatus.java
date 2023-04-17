package com.ekolodey.spring_attestation.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class OrderStatus {
    @Id
    private Integer id;

    private String name;

    public OrderStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public OrderStatus() {

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
