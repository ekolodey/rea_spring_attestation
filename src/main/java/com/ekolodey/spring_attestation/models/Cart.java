package com.ekolodey.spring_attestation.models;

import jakarta.persistence.*;

@Entity
@Table(name = "product_cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "person_id")
    private int personId;


    @Column(name="count")
    private int count;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Cart(int personId, Product product, int count) {
        this.personId = personId;
        this.product=product;
        this.count = count;
    }

    public Cart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getTotalPrice(){
        return this.getProduct().getPrice() * this.getCount();
    }
}
