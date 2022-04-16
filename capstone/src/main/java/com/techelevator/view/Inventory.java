package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Inventory {
    private List<List<String>> inventoryList;
    private List<Purchasable> itemList;
    private File inputFile;

    public Inventory(String path){
        inputFile = new File(path);
        inventoryList = new ArrayList<>();
        itemList = new ArrayList<>();
        try(Scanner inputScanner = new Scanner(inputFile)){

            while(inputScanner.hasNextLine()){

                String currentLine = inputScanner.nextLine();
                String[] tempArray = currentLine.split("\\|");
                switch (tempArray[3]){
                    case "Chip" -> {
                        itemList.add(new Chip(tempArray[0], tempArray[1], Double.parseDouble(tempArray[2]), 5));
                    }
                    case "Gum" -> {
                        itemList.add(new Gum(tempArray[0], tempArray[1], Double.parseDouble(tempArray[2]), 5));
                    }
                    case "Drink" ->{
                        itemList.add(new Drink(tempArray[0], tempArray[1], Double.parseDouble(tempArray[2]), 5));
                    }
                    case "Candy" ->{
                        itemList.add(new Candy(tempArray[0], tempArray[1], Double.parseDouble(tempArray[2]), 5));
                    }

                }


            }

        }catch(FileNotFoundException e){
            System.err.println("Requested inventory file not found.");
        }

    }

    public List<Purchasable> getItemList() {
        return itemList;
    }
    public boolean removeFromInventory(String inputSlot){
        for(Purchasable item : itemList){
            if(item.getSlot().equalsIgnoreCase(inputSlot)){
                if(item.getQuantity() > 0){
                    item.removeItem();
                    return true;
                }
                else
                    return false;

            }
        }
        return false;
    }
}
