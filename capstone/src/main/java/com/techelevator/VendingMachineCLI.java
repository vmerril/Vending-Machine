package com.techelevator;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private Menu myMenu;

	public VendingMachineCLI(Menu myMenu) {
		this.myMenu = myMenu;
	}

	public void run() {
		while (true) {
			myMenu.displayMenu();

		}
	}

	public static void main(String[] args) {
		Menu myMenu = new Menu(System.in, System.out);
		VendingMachineCLI myMachine = new VendingMachineCLI(myMenu);
		myMachine.run();
	}
}
