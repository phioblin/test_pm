package com.priceminister.account;

public class IllegalAmountException extends Exception {

    private Double amount;

    public IllegalAmountException(Double illegalAmount) {
        this.amount = illegalAmount;
    }

    public String toString() {
        return "Illegal amount:" + amount;
    }
}
