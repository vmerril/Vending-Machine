package com.techelevator.view;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.*;

public class MenuTest{


    Menu myMenu = new Menu(System.in, System.out);



    @Test
    public void menuSetupTester() {
        myMenu.menuSetup();
        Assert.assertTrue("Main menu should be present", myMenu.getMenu().containsKey(myMenu.getMAIN_MENU()));
        Assert.assertEquals("Main menu options should be 1) Display Vending Machine Items, 2) Purchase, 3) Exit",
                new ArrayList<>(Arrays.asList("1) Display Vending Machine Items", "2) Purchase", "3) Exit")),
                myMenu.getMenu().get(myMenu.getMAIN_MENU()));
        Assert.assertTrue("Purchase menu should be present", myMenu.getMenu().containsKey(myMenu.getPURCHASE_MENU()));
        Assert.assertEquals("Purchase menu options should be 1) Feed Money, 2) Select Product, 3) Finish Transaction",
                new ArrayList<>(Arrays.asList("1) Feed Money", "2) Select Product", "3) Finish Transaction")) ,
                myMenu.getMenu().get(myMenu.getPURCHASE_MENU()));
        Assert.assertTrue("Product menu should be present", myMenu.getMenu().containsKey(myMenu.getPRODUCT_MENU()));
        List<String> inventoryList = new ArrayList<>(Arrays.asList(myMenu.inventoryString().split("\n")));
        inventoryList.add("Cancel");
        Assert.assertEquals("Product menu options should match vendingmachine.csv, plus a 'Cancel' at the end.",
                inventoryList, myMenu.getMenu().get(myMenu.getPRODUCT_MENU()));
        Assert.assertTrue("Money menu should be present", myMenu.getMenu().containsKey(myMenu.getMONEY_MENU()));
        Assert.assertEquals("Money menu options should be 1) $20.00, 2) $10.00, 3) $5.00, 4) $2.00, 5) $1.00, 6) $.25, 7) $.10, 8) $.05, 9) .01, 10) I'm done.",
                new ArrayList<>(Arrays.asList("1) $20.00", "2) $10.00", "3) $5.00", "4) $2.00", "5) $1.00", "6) $.25", "7) $.10", "8) $.05", "9) .01", "10) I'm done.")) ,
                myMenu.getMenu().get(myMenu.getMONEY_MENU()));
    }


    @Test
    public void menuChooserMainMenu() {
        try {
            myMenu.setCurrentMenu(myMenu.getMAIN_MENU());
            myMenu.setCurrentOptions(myMenu.getMenu().get(myMenu.getMAIN_MENU()));
            myMenu.menuChooser("2");
            Assert.assertEquals("Menu should be purchase menu", myMenu.getPURCHASE_MENU(), myMenu.getCurrentMenu());
            Assert.assertEquals("Menu items should match purchase menu items.", myMenu.getMenu().get(myMenu.getPURCHASE_MENU()), myMenu.getCurrentOptions());

            myMenu.setCurrentMenu(myMenu.getMAIN_MENU());
            myMenu.setCurrentOptions(myMenu.getMenu().get(myMenu.getMAIN_MENU()));
            myMenu.menuChooser("2--");
            Assert.assertEquals("Menu should be main menu", myMenu.getMAIN_MENU(), myMenu.getCurrentMenu());
            Assert.assertEquals("Menu items should match main menu items.", myMenu.getMenu().get(myMenu.getMAIN_MENU()), myMenu.getCurrentOptions());




        } catch (InterruptedException e){
            System.err.println("This is an interrupted exception error.");
        }
    }
    @Test
    public void menuChooserPurchaseMenu() {
        try {


            myMenu.setCurrentMenu(myMenu.getPURCHASE_MENU());
            myMenu.setCurrentOptions(myMenu.getMenu().get(myMenu.getPURCHASE_MENU()));

            myMenu.menuChooser("1");
            Assert.assertEquals("Menu should be moneyMenu", myMenu.getMONEY_MENU(), myMenu.getCurrentMenu());
            Assert.assertEquals("Menu items should match money menu items", myMenu.getMenu().get(myMenu.getMONEY_MENU()), myMenu.getCurrentOptions());

            myMenu.setCurrentMenu(myMenu.getPURCHASE_MENU());
            myMenu.setCurrentOptions(myMenu.getMenu().get(myMenu.getPURCHASE_MENU()));

            myMenu.menuChooser("2");
            Assert.assertEquals("Menu should be product menu", myMenu.getPRODUCT_MENU(), myMenu.getCurrentMenu());
            Assert.assertEquals("Menu items should match product menu items.", myMenu.getMenu().get(myMenu.getPRODUCT_MENU()), myMenu.getCurrentOptions());

            myMenu.setCurrentMenu(myMenu.getPURCHASE_MENU());
            myMenu.setCurrentOptions(myMenu.getMenu().get(myMenu.getPURCHASE_MENU()));
            myMenu.menuChooser("2--");
            Assert.assertEquals("Menu should be purchase menu", myMenu.getPURCHASE_MENU(), myMenu.getCurrentMenu());
            Assert.assertEquals("Menu items should match purchase menu items.", myMenu.getMenu().get(myMenu.getPURCHASE_MENU()), myMenu.getCurrentOptions());

            myMenu.setCurrentMenu(myMenu.getPURCHASE_MENU());
            myMenu.setCurrentOptions(myMenu.getMenu().get(myMenu.getPURCHASE_MENU()));
            myMenu.getMyCart().addFunds(.25);
            myMenu.menuChooser("3");
            Assert.assertEquals("Current funds should be zero", 0, myMenu.getMyCart().getCurrentFunds(), .001);



            myMenu.setCurrentMenu(myMenu.getMAIN_MENU());
            myMenu.setCurrentOptions(myMenu.getMenu().get(myMenu.getMAIN_MENU()));




        } catch (InterruptedException e){
            System.err.println("This is an interrupted exception error.");
        }
    }
    @Test
    public void menuChooserProductMenu() {

        try {
            myMenu.menuSetup();

            myMenu.setCurrentMenu(myMenu.getPRODUCT_MENU());
            myMenu.setCurrentOptions(myMenu.getMenu().get(myMenu.getPRODUCT_MENU()));



            myMenu.getMyCart().addFunds(5.00);


            Assert.assertEquals("A1 quantity should equal 5.", 5, myMenu.getMyInventory().getItemList().get(0).getQuantity());
            myMenu.menuChooser("A1");
            Assert.assertEquals("A1 quantity should equal 5.", 4, myMenu.getMyInventory().getItemList().get(0).getQuantity());
            myMenu.getMyCart().resetFunds();


        } catch (InterruptedException e){
            System.err.println("This is an interrupted exception error.");
        }
    }
    @Test
    public void menuChooserMoneyMenu() {
        try {
            myMenu.setCurrentMenu(myMenu.getMONEY_MENU());
            myMenu.setCurrentOptions(myMenu.getMenu().get(myMenu.getMAIN_MENU()));
            myMenu.menuChooser("1");
            Assert.assertEquals("Money should equal 20.", 20.00, myMenu.getMyCart().getCurrentFunds(), .0001);
        } catch (InterruptedException e){
            System.err.println("This is an interrupted exception error.");
        }
    }


    @Test
    public void inventoryString() {

        myMenu.menuSetup();

        Assert.assertEquals("Inventory String should match inventory string",
                "A1 | Potato Crisps | 3.05 | Chip | Quantity: 5\r\n" +
                        "A2 | Stackers | 1.45 | Chip | Quantity: 5\r\n" +
                        "A3 | Grain Waves | 2.75 | Chip | Quantity: 5\r\n" +
                        "A4 | Cloud Popcorn | 3.65 | Chip | Quantity: 5\r\n" +
                        "B1 | Moonpie | 1.80 | Candy | Quantity: 5\r\n" +
                        "B2 | Cowtales | 1.50 | Candy | Quantity: 5\r\n" +
                        "B3 | Wonka Bar | 1.50 | Candy | Quantity: 5\r\n" +
                        "B4 | Crunchie | 1.75 | Candy | Quantity: 5\r\n" +
                        "C1 | Cola | 1.25 | Drink | Quantity: 5\r\n" +
                        "C2 | Dr. Salt | 1.50 | Drink | Quantity: 5\r\n" +
                        "C3 | Mountain Melter | 1.50 | Drink | Quantity: 5\r\n" +
                        "C4 | Heavy | 1.50 | Drink | Quantity: 5\r\n" +
                        "D1 | U-Chews | 0.85 | Gum | Quantity: 5\r\n" +
                        "D2 | Little League Chew | 0.95 | Gum | Quantity: 5\r\n" +
                        "D3 | Chiclets | 0.75 | Gum | Quantity: 5\r\n" +
                        "D4 | Triplemint | 0.75 | Gum | Quantity: 5\r\n", myMenu.inventoryString());

        myMenu.getMyInventory().removeFromInventory("A1");
        Assert.assertEquals("Inventory String should match inventory string",
                "A1 | Potato Crisps | 3.05 | Chip | Quantity: 4\r\n" +
                        "A2 | Stackers | 1.45 | Chip | Quantity: 5\r\n" +
                        "A3 | Grain Waves | 2.75 | Chip | Quantity: 5\r\n" +
                        "A4 | Cloud Popcorn | 3.65 | Chip | Quantity: 5\r\n" +
                        "B1 | Moonpie | 1.80 | Candy | Quantity: 5\r\n" +
                        "B2 | Cowtales | 1.50 | Candy | Quantity: 5\r\n" +
                        "B3 | Wonka Bar | 1.50 | Candy | Quantity: 5\r\n" +
                        "B4 | Crunchie | 1.75 | Candy | Quantity: 5\r\n" +
                        "C1 | Cola | 1.25 | Drink | Quantity: 5\r\n" +
                        "C2 | Dr. Salt | 1.50 | Drink | Quantity: 5\r\n" +
                        "C3 | Mountain Melter | 1.50 | Drink | Quantity: 5\r\n" +
                        "C4 | Heavy | 1.50 | Drink | Quantity: 5\r\n" +
                        "D1 | U-Chews | 0.85 | Gum | Quantity: 5\r\n" +
                        "D2 | Little League Chew | 0.95 | Gum | Quantity: 5\r\n" +
                        "D3 | Chiclets | 0.75 | Gum | Quantity: 5\r\n" +
                        "D4 | Triplemint | 0.75 | Gum | Quantity: 5\r\n", myMenu.inventoryString());

    }
}
