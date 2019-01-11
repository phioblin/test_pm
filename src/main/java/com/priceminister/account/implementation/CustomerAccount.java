package com.priceminister.account.implementation;

import com.priceminister.account.*;


public class CustomerAccount implements Account {

    private Double balance = 0.0;

    //TODO synchronize
    public void add(Double addedAmount) throws IllegalAmountException {
        if(addedAmount == null || addedAmount <= 0.0) {
            throw new IllegalAmountException(addedAmount);
        }
        balance = balance + addedAmount;
    }

    public Double getBalance() {
        return balance;
    }

    //TODO synchronize
    public Double withdrawAndReportBalance(Double withdrawnAmount, AccountRule rule)
            throws IllegalBalanceException, IllegalAmountException {
        if(withdrawnAmount == null || withdrawnAmount <= 0.0) {
            throw new IllegalAmountException(withdrawnAmount);
        }

        Double newAmount = balance - withdrawnAmount;
        if(!rule.withdrawPermitted(newAmount)) {
            throw new IllegalBalanceException(newAmount);
        }
        balance = newAmount;
        return newAmount;
    }

}
