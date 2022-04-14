package com.techelevator.view;

public class Chip implements Purchasable{
    private String slot;
    private String name;
    private double price;
    private int quantity;

    public Chip (String slot, String name, double price, int quantity) {
        this.slot = slot;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    public int minusPurchase(){
        quantity = quantity -1;
        return quantity;
    }

    public String getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
