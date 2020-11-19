package com.company;

import java.math.BigDecimal;

public class Product{

    private int id;
    private String name;
    private BigDecimal price;
    private boolean onSales;

    public Product(int id, String name, BigDecimal price, boolean onSales) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.onSales = onSales;
    }

    public boolean isOnSales() {
        return onSales;
    }

    public void setOnSales(boolean onSales) {
        this.onSales = onSales;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void  setPrice(BigDecimal price){
        this.price = price;
    }

}
