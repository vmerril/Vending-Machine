package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {
	private PrintWriter output;
	private Scanner input;
	private String currentOutput = "Welcome to the Vendotron 9000. Please make your selection:\n" +
			                       "\n" +
			                       "1) Display Vending Machine Items\n" +
			                       "2) Purchase\n" +
			                       "3) Exit";

	public Menu(InputStream input, OutputStream output) {
		this.output = new PrintWriter(output);
		this.input = new Scanner(input);
	}
	public void displayMenu(){
		String currentChoice;
		System.out.println(currentOutput);
		currentChoice = input.nextLine();
		System.out.printf("You've selected %s. Thank you for your time.%n", currentChoice);

	}
}
