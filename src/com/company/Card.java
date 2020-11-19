package com.company;

import java.math.BigDecimal;

public class Card {

    private int cardNumber;
    private BigDecimal discount;

    public Card(int cardNumber, BigDecimal discount) {
        this.cardNumber = cardNumber;
        this.discount = discount;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public BigDecimal getDiscount() {
        return discount;
    }
}
