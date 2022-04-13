package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Menu {
	private PrintWriter output;
	private Scanner input;
	private String currentMenu;
	private Map<String, ArrayList<String>> menu = new HashMap<>();
	private List<String> currentOptions = new ArrayList<>();


	private final String MAIN_MENU = "What would you like to do?\n";
	private final String PURCHASE_MENU = "Make a purchase: \n";
	private final String PRODUCT_MENU = "Please Select a product to purchase:\n";
	private final String MONEY_MENU = "Please input cash:\n";


	public Menu(InputStream input, OutputStream output) {
		this.output = new PrintWriter(output);
		this.input = new Scanner(input);
		menuSetup();
		currentOptions = menu.get(MAIN_MENU);
		currentMenu = MAIN_MENU;

	}


	public void displayMenu(){
		String currentChoice;
		System.out.println(currentMenu);
		for(String string : currentOptions){
			System.out.println(string);
		}
		currentChoice = input.nextLine();
		makeChoice(currentChoice);

	}
	private void makeChoice(String userInput){
		List<String> menuOptions = new ArrayList<>();
		String currentChoice;
		for(String menuOption : menu.get(currentMenu)){
			menuOptions.add(menuOption.split(" |[)]")[0]);
		}
		currentChoice = userInput.split(" |[)]")[0];
		if(menuOptions.contains(currentChoice)){
			menuOptions(currentChoice);
		} else
			System.out.println("Invalid choice");


	}

	private void menuOptions(String userInput){
		System.out.println(userInput);
		switch (currentMenu){
			case MAIN_MENU -> {
				if(userInput.equals("1")){
					displayInventory();

				} else if(userInput.equals("2")){
					changeMenu(PURCHASE_MENU);

				} else{
					System.exit(0);
				}

			}
			case PURCHASE_MENU -> {
				if(userInput.equals("1")){
					changeMenu(MONEY_MENU);

				} else if(userInput.equals("2")){
					changeMenu(PRODUCT_MENU);

				}
				else{
					System.out.println("Transaction complete");
					changeMenu(MAIN_MENU);
				}


			}
			case PRODUCT_MENU -> {
				System.out.println("This is the product menu");
				changeMenu(PURCHASE_MENU);
			}
			case MONEY_MENU -> {


			}
		}
	}

	private void displayInventory(){
		System.out.println("Displaying inventory");
	}

	public void changeMenu(String inputMenu){
		currentMenu = inputMenu;
		currentOptions = menu.get(inputMenu);
	}
	private void menuSetup(){
		menu.put(MAIN_MENU, new ArrayList<>(Arrays.asList("1) Display Vending Machine Items", "2) Purchase", "3) Exit")) );
		menu.put(PURCHASE_MENU, new ArrayList<>(Arrays.asList("1) Feed Money", "2) Select Product", "3) Finish Transaction")) );
		menu.put(PRODUCT_MENU, new ArrayList<>(Arrays.asList("1) First Item", "2) Second Item", "3) Third Item")) );
		menu.put(MONEY_MENU, new ArrayList<>(Arrays.asList("1) $20.00", "2) $10.00", "3) $5.00", "4) $2.00", "5) $1.00", "6) $.25", "7) $.10", "8) $.5")) );



	}
}
