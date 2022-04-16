package com.techelevator.view;

public class Candy implements Purchasable{
    private String slot;
    private String name;
    private double price;
    private int quantity;
    private String type;


    public Candy (String slot, String name, double price, int quantity) {
        this.slot = slot;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.type = "Candy";
    }

    public String getType() {
        return type;
    }

    public void removeItem(){
        this.quantity -= 1;

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
