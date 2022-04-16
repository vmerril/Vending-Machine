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

        if(currentFunds<.05){
            currentFunds = 0;
        }
        return new int[]{numQuarters, numDimes,numNickels};
    }

    public void addFunds(double funds){
        currentFunds += funds;
    }
    public double getCurrentFunds(){
        return currentFunds;
    }

    public void vend(Purchasable item) {
        this.currentFunds -= item.getPrice();
        System.out.printf("Vending %s. Thank you for your purchase.%n", item.getName());
        switch (item.getType()){
            case ("Chip") -> System.out.println("Crunch Crunch, Yum!");
            case ("Candy") -> System.out.println("Munch Munch, Yum!");
            case ("Drink") -> System.out.println("Glug Glug, Yum!");
            case ("Gum") -> System.out.println("Chew Chew, Yum!");
        }
    }
    public void resetFunds(){
        currentFunds = 0;
    }
    public List<Purchasable> getMyCart() {
        return myCart;
    }

}
