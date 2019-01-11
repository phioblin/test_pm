/*
 * =============================================================================
 *
 *   PRICE MINISTER APPLICATION
 *   Copyright (c) 2000 Babelstore.
 *   All Rights Reserved.
 *
 *   $Source$
 *   $Revision$
 *   $Date$
 *   $Author$
 *
 * =============================================================================
 */
package com.priceminister.account.implementation;

import com.priceminister.account.*;


public class CustomerAccountRule implements AccountRule {

    // the minimum acceptable value for the account balance
    private Double minimumAccountBalance;

    public CustomerAccountRule(Double minimumAccountBalance) {
        this.minimumAccountBalance = minimumAccountBalance;
    }

    public boolean withdrawPermitted(Double resultingAccountBalance) {
        return resultingAccountBalance >= minimumAccountBalance;
    }

    public Double getMinimumAccountBalance() {
        return minimumAccountBalance;
    }

}
