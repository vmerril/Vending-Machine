package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;


import java.util.*;

public class Menu {
	private PrintWriter output;
	private Scanner input;


	private Map<String, ArrayList<String>> menu = new HashMap<>();

	private String currentMenu;
	private List<String> currentOptions = new ArrayList<>();

	private Cart myCart;
	private Inventory myInventory;
	private VendLog myLog;



	private final String MAIN_MENU = "What would you like to do?\n";
	private final String PURCHASE_MENU = "Make a purchase: \n";
	private final String PRODUCT_MENU = "Please Select a product to purchase:\n";
	private final String MONEY_MENU = "Please input cash:\n";
	private final int ADMINISTRATIVE_OVERRIDE_PASSWORD = 789456123;
	private final String INVENTORY_PATH = "vendingmachine.csv";


	public Menu(InputStream input, OutputStream output) {
		this.output = new PrintWriter(output);
		this.input = new Scanner(input);

		myCart = new Cart();
		myInventory = new Inventory(INVENTORY_PATH);
		myLog = new VendLog();

		menuSetup();
		currentMenu = MAIN_MENU;
		currentOptions = menu.get(MAIN_MENU);



	}




	public void displayMenu(){


		System.out.println(currentMenu);
		for(String string : currentOptions){
			System.out.println(string);
		}
		if(currentMenu != MAIN_MENU)
			System.out.printf("Current available funds: %.2f%n", myCart.getCurrentFunds());

		makeChoice(input.nextLine());
		System.out.println("\n\n*******************\n\n\n");

	}

	//Strips menu numbers from current menu options and compares those to userInput,
	//then sends to menuChooser to perform logic
	public void makeChoice(String userInput){
		List<String> menuOptions = new ArrayList<>();
		String currentChoice;
		for(String menuOption : menu.get(currentMenu)){
			menuOptions.add(menuOption.split(" |[)]")[0].toLowerCase());
		}
		currentChoice = userInput.split(" |[)]")[0];
		if(menuOptions.contains(currentChoice.toLowerCase()) || (currentMenu == MAIN_MENU && currentChoice.equals("**"))){
			try{
				menuChooser(currentChoice.toLowerCase());
			}catch (InterruptedException e){

			}
		} else
			System.out.println("Please choose one of the available options. Thank you.");

	}

	//performs actual logic of sending user to different menu
	//changes currentMenu and currentOptions
	public void menuChooser(String userInput) throws InterruptedException {

		switch (currentMenu){
			case MAIN_MENU -> {
				if(userInput.equals("1")){
					System.out.println(inventoryString());

				} else if(userInput.equals("2")){
					changeMenu(PURCHASE_MENU);

				} else if(userInput.equals("3")){
					System.out.println("Only authorized personnel can turn off this vending machine. " +
							"If believe that there has been an error, please contact an employee.");
					if(Integer.parseInt(input.nextLine()) == ADMINISTRATIVE_OVERRIDE_PASSWORD){
						System.exit(0);
					}else{
						System.out.println("Please contact an employee");
					}
				} else if(userInput.equals("**")){
					System.out.println("Please enter your employee password");
					if(Integer.parseInt(input.nextLine()) == ADMINISTRATIVE_OVERRIDE_PASSWORD){
						System.out.println("Printing Sales report... but like later tho.");
					}else{
						System.out.println("You are unauthorized to view this page");
					}
				}

			}
			case PURCHASE_MENU -> {

				if(userInput.equals("1")){
					changeMenu(MONEY_MENU);

				} else if(userInput.equals("2")){
					changeMenu(PRODUCT_MENU);

				}
				else if(userInput.equals("3")){
					//actually finish the transaction
					double beforeChange = myCart.getCurrentFunds();
					int[] change = myCart.makeChange();

					System.out.printf("%d %s, %d %s, and %d %s fall out of the change return slot.%n",
							change[0], change[0] == 1 ? "quarter" : "quarters",
							change[1], change[1] == 1 ? "dime" : "dimes",
							change[2], change[2] == 1 ? "nickel" : "nickels");

					System.out.println("Thank you for shopping at the Port-A-Diner!\n\n\n\n");
					changeMenu(MAIN_MENU);
					try {
						VendLog.log(String.format("GIVE CHANGE: $%.2f $%.2f", beforeChange, myCart.getCurrentFunds()));
					} catch (Exception e) {
						throw new VendLogException(e.getMessage());
					}
				}



			}
			case PRODUCT_MENU -> {
				if(userInput.equalsIgnoreCase("cancel")){
					changeMenu(PURCHASE_MENU);
					break;
				}
				Purchasable selectedItem = null;
				for(Purchasable item : myInventory.getItemList()){

					if(userInput.equalsIgnoreCase(item.getSlot())){
						selectedItem = item;
					}
				}
				if(selectedItem == null){
					System.out.printf("%s is not a valid selection. Please choose from the listed options.", userInput.toUpperCase());
					input.nextLine();
				}
				else if(myCart.getCurrentFunds() >= selectedItem.getPrice()){
					if(myInventory.removeFromInventory(userInput)) {
						double beforeVend = myCart.getCurrentFunds();
						myCart.vend(selectedItem);
						try {
							VendLog.log(String.format("%s %s: $%.2f $%.2f", selectedItem.getName(), selectedItem.getSlot(), beforeVend, myCart.getCurrentFunds()));
						} catch (Exception e) {
							throw new VendLogException(e.getMessage());
						}
						Thread.sleep(1500);
					}else{
						System.out.println("This product is SOLD OUT.");
						Thread.sleep(1500);
					}
				}
				else{
					System.out.println("Insufficient funds.");
					Thread.sleep(1500);
				}


				currentOptions = new ArrayList<>(Arrays.asList(inventoryString().split("\n")));
				currentOptions.add("Cancel");
				menu.put(PRODUCT_MENU, (ArrayList<String>) currentOptions);


			}
			case MONEY_MENU -> {
				double inputAmount = 0;

				switch (userInput){

					case "1" ->{
						inputAmount = 20.00;
					}case "2" ->{
						inputAmount = 10.00;
					}case "3" ->{
						inputAmount = 5.00;
					}case "4" ->{
						inputAmount = 2.00;
					}case "5" ->{
						inputAmount = 1.00;
					}case "6" ->{
						inputAmount = .25;
					}case "7" ->{
						inputAmount = .10;
					}case "8" ->{
						inputAmount = .05;
					}case "9" ->{
						System.out.println("The penny clinks through the machine and lands in the change return slot. Who puts a penny in a vending machine?");
					}case "10" ->{

						changeMenu(PURCHASE_MENU);
					}

				}


				myCart.addFunds(inputAmount);
				if(inputAmount > 0) {
					try {
						VendLog.log(String.format("FEED MONEY: $%.2f $%.2f", inputAmount, myCart.getCurrentFunds()));
					} catch (Exception e) {
						throw new VendLogException(e.getMessage());
					}
				}


			}
		}
	}

	public void changeMenu(String inputMenu){
		currentMenu = inputMenu;
		currentOptions = menu.get(inputMenu);
	}
	public void menuSetup(){
		menu.put(MAIN_MENU, new ArrayList<>(Arrays.asList("1) Display Vending Machine Items", "2) Purchase", "3) Exit")) );
		menu.put(PURCHASE_MENU, new ArrayList<>(Arrays.asList("1) Feed Money", "2) Select Product", "3) Finish Transaction")) );
		menu.put(PRODUCT_MENU, new ArrayList<>(Arrays.asList(inventoryString().split("\n"))));
		menu.get(PRODUCT_MENU).add("Cancel");
		menu.put(MONEY_MENU, new ArrayList<>(Arrays.asList("1) $20.00", "2) $10.00", "3) $5.00", "4) $2.00", "5) $1.00", "6) $.25", "7) $.10", "8) $.05", "9) .01", "10) I'm done.")) );



	}
	public String inventoryString(){
		String inventoryString = "";
		for(Purchasable item : myInventory.getItemList()){
			inventoryString += String.format("%s | %s | %.2f | %s | Quantity: %d%n",item.getSlot(), item.getName(), item.getPrice(), item.getType(), item.getQuantity());
		}
		return inventoryString;
	}









//getters and setters that I don't need but have to be included to accommodate unit testing
	public Map<String, ArrayList<String>> getMenu() {
		return menu;
	}
	public void setMenu(Map<String, ArrayList<String>> menu) {
		this.menu = menu;
	}
	public String getCurrentMenu() {
		return currentMenu;
	}
	public void setCurrentMenu(String currentMenu) {
		this.currentMenu = currentMenu;
	}
	public List<String> getCurrentOptions() {
		return currentOptions;
	}
	public void setCurrentOptions(List<String> currentOptions) {
		this.currentOptions = currentOptions;
	}
	public Cart getMyCart() {
		return myCart;
	}
	public void setMyCart(Cart myCart) {
		this.myCart = myCart;
	}
	public Inventory getMyInventory() {
		return myInventory;
	}
	public void setMyInventory(Inventory myInventory) {
		this.myInventory = myInventory;
	}
	public VendLog getMyLog() {
		return myLog;
	}
	public void setMyLog(VendLog myLog) {
		this.myLog = myLog;
	}
	public String getMAIN_MENU() {
		return MAIN_MENU;
	}
	public String getPURCHASE_MENU() {
		return PURCHASE_MENU;
	}
	public String getPRODUCT_MENU() {
		return PRODUCT_MENU;
	}
	public String getMONEY_MENU() {
		return MONEY_MENU;
	}
	public int getADMINISTRATIVE_OVERRIDE_PASSWORD() {
		return ADMINISTRATIVE_OVERRIDE_PASSWORD;
	}
	public String getINVENTORY_PATH() {
		return INVENTORY_PATH;
	}
}
