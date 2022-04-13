package com.techelevator.view;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Purchasable> myCart = new ArrayList<>();
    private double currentFunds;


    public Cart(){
        currentFunds = 0;
    }

    public int[] makeChange(){
        DecimalFormat rounder = new DecimalFormat(".##");
        rounder.setRoundingMode(RoundingMode.CEILING);
        int numQuarters = 0;
        int numNickels = 0;
        int numDimes = 0;

        numQuarters = (int)(currentFunds/.25) ;
        currentFunds = Double.parseDouble(rounder.format(currentFunds % .25));
        numDimes = (int)(currentFunds/.10);
        currentFunds = Double.parseDouble(rounder.format(currentFunds % .10));
        numNickels = (int)(currentFunds/.05);
        currentFunds = Double.parseDouble(rounder.format(currentFunds % .05));


        return new int[]{numQuarters, numDimes,numNickels};
    }

    public void addFunds(double funds){
        currentFunds += funds;
    }
    public double getCurrentFunds(){
        return currentFunds;
    }

    public void addToCart(Purchasable item) {  }
    public List<Purchasable> getMyCart() {
        return myCart;
    }

}
