package com.company;

public class Products{

    private int id; //id товара
    private String name;  //Название товара
    private float price;  //Цена товара

    public boolean isOnSales() {
        return onSales;
    }

    public void setOnSales(boolean onSales) {
        this.onSales = onSales;
    }

    private boolean onSales;  //Акционный ли товар

    public Products(int id, String name, float price, boolean onSales) { //конструктор товаров
        this.id = id;
        this.name = name;
        this.price = price;
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

    public float getPrice() {
        return price;
    }

    public void  setPrice(float price){
        this.price = price;
    }





}
