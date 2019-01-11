package com.priceminister.account;

import com.priceminister.account.implementation.CustomerAccountRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit Test for {@link CustomerAccountRule}
 */
public class CustomerAccountRuleTest {

    private AccountRule rule;
    private Double minimumAccountBalance = -20.0;

    @Before
    public void setUp() {
        rule = new CustomerAccountRule(minimumAccountBalance);
    }

    /**
     * Tests the rule with a permitted withdraw
     */
    @Test
    public void testWithdrawPermitted () {
        Double authorizedBalance = 10.82;
        boolean permitted = rule.withdrawPermitted(authorizedBalance);
        Assert.assertTrue(permitted);
    }

    /**
     * Tests the rule with an unpermitted withdraw
     */
    @Test
    public void testWithdrawNotPermitted () {
        Double lowerAccountBalanceThanPermitted = -30.0;
        boolean permitted = rule.withdrawPermitted(lowerAccountBalanceThanPermitted);
        Assert.assertFalse(permitted);
    }
}
