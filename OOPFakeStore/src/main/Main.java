package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import models.FetchHelper;
import models.Product;

public class Main {
	public String shorten(String s) {
		if (s.length() > 30) {
			return s.substring(0, 29);
		}
		return s;
	}

	public void viewProducts(List<Product> products) {
		for (Product p : products) {
			int id = p.getId();
			String title = shorten(p.getTitle());
			double price = p.getPrice();
			String description = shorten(p.getDescription());
			String category = shorten(p.getDescription());
			System.out.println("Id: " + id + "\nTitle: " + title + "\nPrice: " + price + "\nDescription: " + description
					+ "\nCategory: " + category + "\nRating rate: " + p.getRating().getRate() + "\nRating count: "
					+ p.getRating().getCount()
					+ "\n===========================================================================");
		}
	}

	public void addToCart(List<Product> products, List<Product> cart, Scanner input) {
		int choice = 0;
		while (choice != -1) {
			System.out.println("Input product id to add to cart (-1 to exit):");
			choice = input.nextInt();
			input.nextLine();
			if (choice > 0 && choice <= products.size()) {
				Product p = products.get(choice - 1);
				cart.add(p);
				System.out.println(p.getTitle() + " has been added to cart");
			}
		}
	}

	public void orderProducts(List<Product> cart, Scanner input) {
		double amount = 0;
		String choice = "";
		ArrayList<String> orderCommands = new ArrayList<String>();
		for (Product p : cart) {
			System.out.println(p.getTitle());
			amount += p.getPrice();
		}
		System.out.println("Total order: " + amount);
		while (!choice.equals("bank") && !choice.equals("credit") && !choice.equals("paypal")) {
			System.out.println("Payment method? (bank || credit || paypal)");
			choice = input.nextLine();
		}
		orderCommands.add(choice);
		choice = "";
		while (!choice.equals("Y") && !choice.equals("N")) {
			System.out.println("Use Discount? (Y/N)");
			choice = input.nextLine();
		}

		if (choice.equals("Y")) {
			orderCommands.add("discount");
		}
		choice = "";
		while (!choice.equals("Y") && !choice.equals("N")) {
			System.out.println("Use giftwrap on order? (Y/N)");
			choice = input.nextLine();
		}

		if (choice.equals("Y")) {
			orderCommands.add("giftwrap");
		}
		choice = "";
		while (!choice.equals("Y") && !choice.equals("N")) {
			System.out.println("Use FastShipping? (Y/N)");
			choice = input.nextLine();
		}

		if (choice.equals("Y")) {
			orderCommands.add("fastshipping");
		}

		if (orderCommands.contains("fastshipping")) {
			amount = amount + (amount * 1 / 100);
			System.out.println("Product is sent via FastShipping. New amount is " + amount);
		}
		if (orderCommands.contains("giftwrap")) {
			amount = amount + 2;
			System.out.println("Product wrapped in gift wrapping. New amount is " + amount);
		}
		if (orderCommands.contains("discount")) {
			amount = amount - (amount * 10 / 100);
			System.out.println("DISCOUNT: Applied 10% discount. Final amount: " + amount);
		}
		if(orderCommands.contains("bank")){
			System.out.println("Paid " + amount + " using Bank Transfer.");
		}else if (orderCommands.contains("credit")) {
			System.out.println("Paid " + amount + " using Credit Card.");
		}else {
			System.out.println("Paid " + amount + " using Paypal.");
		}

	}

	public Main() {
		// TODO Auto-generated constructor stub
		
		FetchHelper helper = new FetchHelper();
		List<Product> response = helper.fetch();
		List<Product> products = response.stream().map(p -> new Product(p.getId(), p.getTitle(), p.getPrice(),
				p.getDescription(), p.getCategory(), p.getImage(), p.getRating())).collect(Collectors.toList());
		List<Product> cart = new ArrayList<>();
		int choice = 0;
		Scanner input = new Scanner(System.in);
		while (choice != 5) {
			System.out.print(
					"Welcome to FakeStore\n1. View products\n2. Add to cart\n3. View cart\n4. Order products\n>");
			choice = input.nextInt();
			input.nextLine();
			if (choice == 1) {
				viewProducts(products);
			} else if (choice == 2) {
				addToCart(products, cart, input);
			} else if (choice == 3) {
				viewProducts(cart);
			} else if (choice == 4) {
				orderProducts(cart, input);
			}
		}
		input.close();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}

}
