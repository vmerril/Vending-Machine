package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Menu {
	private PrintWriter output;
	private Scanner input;
	private Map<String, ArrayList<String>> menu = new HashMap<>();

	private String currentMenu;
	private List<String> currentOptions;

	private Cart myCart;


	private final String MAIN_MENU = "What would you like to do?\n";
	private final String PURCHASE_MENU = "Make a purchase: \n";
	private final String PRODUCT_MENU = "Please Select a product to purchase:\n";
	private final String MONEY_MENU = "Please input cash:\n";
	private final int ADMINISTRATIVE_OVERRIDE_PASSWORD = 789456123;


	public Menu(InputStream input, OutputStream output) {
		this.output = new PrintWriter(output);
		this.input = new Scanner(input);

		menuSetup();
		currentMenu = MAIN_MENU;
		currentOptions = menu.get(MAIN_MENU);

		myCart = new Cart();

	}


	public void displayMenu(){
		System.out.println(currentMenu);
		for(String string : currentOptions){
			System.out.println(string);
		}
		if(currentMenu != MAIN_MENU)
			System.out.printf("Current available funds: %.2f%n", myCart.getCurrentFunds());

		makeChoice(input.nextLine());

	}
	private void makeChoice(String userInput){
		List<String> menuOptions = new ArrayList<>();
		String currentChoice;
		for(String menuOption : menu.get(currentMenu)){
			menuOptions.add(menuOption.split(" |[)]")[0]);
		}
		currentChoice = userInput.split(" |[)]")[0];
		if(menuOptions.contains(currentChoice)){
			menuChooser(currentChoice);
		} else
			System.out.println("Please choose one of the available options. Thank you.");


	}


	private void displayInventory(){
		System.out.println("Displaying inventory");
	}



	private void changeMenu(String inputMenu){
		currentMenu = inputMenu;
		currentOptions = menu.get(inputMenu);
	}
	private void menuSetup(){
		menu.put(MAIN_MENU, new ArrayList<>(Arrays.asList("1) Display Vending Machine Items", "2) Purchase", "3) Exit")) );
		menu.put(PURCHASE_MENU, new ArrayList<>(Arrays.asList("1) Feed Money", "2) Select Product", "3) Finish Transaction")) );
		menu.put(PRODUCT_MENU, new ArrayList<>(Arrays.asList("1) First Item", "2) Second Item", "3) Third Item")) );
		menu.put(MONEY_MENU, new ArrayList<>(Arrays.asList("1) $20.00", "2) $10.00", "3) $5.00", "4) $2.00", "5) $1.00", "6) $.25", "7) $.10", "8) $.05", "9) .01", "10) I'm done.")) );



	}
	private void menuChooser(String userInput){

		switch (currentMenu){
			case MAIN_MENU -> {
				if(userInput.equals("1")){
					displayInventory();

				} else if(userInput.equals("2")){
					changeMenu(PURCHASE_MENU);

				} else{
					System.out.println("Only authorized personnel can turn off this vending machine. " +
							"If believe that there has been an error, please contact an employee.");
					if(Integer.parseInt(input.nextLine()) == ADMINISTRATIVE_OVERRIDE_PASSWORD){
						System.exit(0);
					}else{
						System.out.println("Please contact an employee");
					}
				}

			}
			case PURCHASE_MENU -> {
				if(userInput.equals("1")){
					changeMenu(MONEY_MENU);

				} else if(userInput.equals("2")){
					changeMenu(PRODUCT_MENU);

				}
				else{
					//actually finish the transaction
					int[] change = myCart.makeChange();
					System.out.printf("%d %s, %d %s, and %d %s fall out of the change return slot.%n",
							change[0], change[0] == 1 ? "quarter" : "quarters",
							change[1], change[1] == 1 ? "dime" : "dimes",
							change[2], change[2] == 1 ? "nickel" : "nickels");

					System.out.println("Thank you for shopping at the Port-A-Diner!\n\n\n\n");
					System.out.println("Welcome to the Port-A-Diner!");
					changeMenu(MAIN_MENU);
				}


			}
			case PRODUCT_MENU -> {
				System.out.println("This is the product menu");
				changeMenu(PURCHASE_MENU);
			}
			case MONEY_MENU -> {
				switch (userInput){
					case "1" ->{
						myCart.addFunds(20.00);
					}case "2" ->{
						myCart.addFunds(10.00);
					}case "3" ->{
						myCart.addFunds(5.00);
					}case "4" ->{
						myCart.addFunds(2.00);
					}case "5" ->{
						myCart.addFunds(1.00);
					}case "6" ->{
						myCart.addFunds(.25);
					}case "7" ->{
						myCart.addFunds(.10);
					}case "8" ->{
						myCart.addFunds(.05);
					}case "9" ->{
						System.out.println("The penny clinks through the machine and lands in the change return slot. Who puts a penny in a vending machine?");
					}case "10" ->{
						changeMenu(PURCHASE_MENU);
					}
				}


			}
		}
	}
}
