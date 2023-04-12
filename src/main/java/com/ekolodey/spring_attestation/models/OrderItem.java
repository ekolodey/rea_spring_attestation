package com.ekolodey.spring_attestation.models;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Product product;

    private float price;

    private int count;


    public OrderItem(Product product, Order order, int count, float price) {
        this.product = product;
        this.order = order;
        this.count = count;
        this.price = price;
    }

    public OrderItem() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
