package com.priceminister.account;


import com.priceminister.account.implementation.CustomerAccount;
import com.priceminister.account.implementation.CustomerAccountRule;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;


/**
 * Please create the business code, starting from the unit tests below.
 * Implement the first test, the develop the code that makes it pass.
 * Then focus on the second test, and so on.
 * 
 * We want to see how you "think code", and how you organize and structure a simple application.
 * 
 * When you are done, please zip the whole project (incl. source-code) and send it to recrutement-dev@priceminister.com
 * 
 */
public class CustomerAccountTest {
    
    private Account customerAccount;
    private AccountRule rule;

    @Before
    public void setUp() {
        customerAccount = new CustomerAccount();
        rule = new CustomerAccountRule(-20.0);
    }
    
    /**
     * Tests that an empty account always has a balance of 0.0, not a NULL.
     */
    @Test
    public void testAccountWithoutMoneyHasZeroBalance() {
        Double zeroBalance = 0.0;
        assertEquals(zeroBalance, customerAccount.getBalance());
    }
    
    /**
     * Add a positive amount to the account and checks that the new balance is as expected.
     */
    @Test
    public void testAddPositiveAmount() throws IllegalAmountException {
        Double positiveAmount = 10.32;
        customerAccount.add(positiveAmount);
        assertEquals(positiveAmount, customerAccount.getBalance());
    }

    /**
     * Add a negative amount to the account and checks that IllegalAmountException exception is thrown.
     */
    @Test(expected=IllegalAmountException.class)
    public void testAddNegativeAmount() throws IllegalAmountException {
        Double negativeAmount = -10.32;
        customerAccount.add(negativeAmount);
    }

    /**
     * Adds a null value to the account and checks that IllegalAmountException exception is thrown.
     */
    @Test(expected=IllegalAmountException.class)
    public void testAddNullAmount() throws IllegalAmountException {
        customerAccount.add(null);
    }
    
    /**
     * Tests that an illegal withdrawal throws the expected exception.
     */
    @Test(expected=IllegalBalanceException.class)
    public void testWithdrawAndReportBalanceIllegalBalance() throws IllegalAmountException, IllegalBalanceException {
        Double illegalWithDraw = 35.0;
        customerAccount.withdrawAndReportBalance(illegalWithDraw, rule);

    }

    /**
     * Tests that a withdrawal of null value throws the expected exception.
     */
    @Test(expected=IllegalAmountException.class)
    public void testNullWithdraw() throws IllegalAmountException, IllegalBalanceException {
        customerAccount.withdrawAndReportBalance(null, rule);

    }

    /**
     * Tests that a legal withdraw returns the new balance and update balance
     * @throws IllegalBalanceException
     * @throws IllegalAmountException
     */
    @Test
    public void testWithdrawAndReportBalance () throws IllegalBalanceException, IllegalAmountException {
        Double legalWithDraw = 15.0;
        Double expectedNewBalance = -15.0;
        Double newBalance = customerAccount.withdrawAndReportBalance(legalWithDraw, rule);
        assertEquals(expectedNewBalance, newBalance);
        assertEquals(expectedNewBalance, customerAccount.getBalance());
    }

    /**
     * Tests assume that add and withdraw operations works well in a multi-thread context,
     * @throws InterruptedException
     */
    @Test
    public void testInMultiThread() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);

        for(int i = 0; i < 1000; i++){
            service.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        customerAccount.add(10.0);
                        customerAccount.withdrawAndReportBalance(10.0, rule);
                    } catch (IllegalBalanceException | IllegalAmountException e){
                        //should never happen
                        e.printStackTrace();
                    }
                }
            });
        }
        service.awaitTermination(100, TimeUnit.MILLISECONDS);

        //TODO replace by Lambda
//        IntStream.range(0, 1000)
//                .forEach((int count) -> service.submit(customerAccount.test(count)));
//        service.awaitTermination(1000, TimeUnit.MILLISECONDS);

        Double expectedBalance=0.0;
        assertEquals(expectedBalance, customerAccount.getBalance());

    }

}
