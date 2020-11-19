package com.company;

//Модель скидочной карты

public class Cards{

    private int cardNumber; //Номер скидочной карты
    private int discount; //Размер скидки на карте

    public Cards(int cardNumber, int discount) {
        this.cardNumber = cardNumber;
        this.discount = discount;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public int getDiscount() {
        return discount;
    }
}
