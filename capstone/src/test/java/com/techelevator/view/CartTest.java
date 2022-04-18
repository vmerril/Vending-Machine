package com.techelevator.view;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CartTest{
    Cart myCart = new Cart();

    @Test
    public void makeChange() {
        myCart.addFunds(.40);
        Assert.assertArrayEquals("Test should return one of each.", new int[]{1, 1, 1}, myCart.makeChange());

    }

    @Test
    public void addFundsGetCurrentFundsResetFunds() {
        myCart.addFunds(12.39);
        myCart.resetFunds();
        myCart.addFunds(40);
        Assert.assertEquals("Current Funds should equal 40", 40, myCart.getCurrentFunds(),.001);

    }


    @Test
    public void vend() {
        Candy candy = new Candy("Slot", "Name", 1.25, 2);
    }



    @Test
    public void getMyCart() {
    }
}