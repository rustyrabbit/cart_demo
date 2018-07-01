package com.fitz.ecommerce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConsoleUtils {

	final static String CART_FORMATTER = "%1$-40s %2$10s %3$10s %4$10s";
	final static String CATALOG_FORMATTER = "%1$-8s %2$-40s %3$-10s %4$-10s %5$-5s";

	final static String WELCOME = "Welcome %s!\r\nProducts we offer:\r\n";
	final static String BYE = "Bye for now!";

	final static String SELECT_PRODUCT = "To select, please enter the product id, "
			+ "then <return>, to add to cart.\r\n" + "Enter (h) for a list of available commands."
			+ "Enter <q> to exit.";

	final static String SELECT_QUANTITY = "Please enter the quantity you'd like to purchase, or q to cancel.";

	final static String PROMPT_CONFIRM = "You selected %s."
			+ "Continue shopping <s>, checkout now <c>, or cancel transaction <q>?";
	final static String PROMPT_RESELECT = "I could not find your response %s in the catalog. Please try again.\r\n";
	final static String PROMPT_INVALID_QUANTITY = "We are unable to fulfill order with quantities less than one. Please re-enter the quantity you wish to purchase.\r\n";

	final static String CONFIRM_ADD = "Added %s %s to your cart.";
	final static String CONFIRM_REMOVE = "Removed %s %s from your cart.";
	final static String PROMPT_CHECKOUT = "Your total is %s. Please select one of the payment options:\n"
			+ "<v> Visa\n<p>Paypal";
	final static String PROMPT_CONTINUE_SHOPPING = "Continue shopping <s>, checkout now <c>, or quit <q>?";
	final static String PROMPT_CANCEL = "Cancel transaction? Enter <1> OK, <2> to continue shopping.";

	final static String COMMANDS = "<1> OK\n<q>Cancel, remove from cart\n<3>View cart\n<4>Edit cart<5>"
			+ "<6>Cancel order\n<h>Help, show commands.";

	static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

	static List<String> consoleCodes = new ArrayList<String>();

	public static void displayWelcome(String customerName) {
		System.out.println(String.format(WELCOME, customerName));
	}

	public static void displayBye() {
		System.out.println(BYE);
		System.exit(0);
	}

	public static void displayConfirmAddProduct(String product, int quantity) {

		System.out.println(String.format(CONFIRM_ADD, quantity, product));

	}

	public static void displayConfirmRemoveProduct(String product, int quantity) {

		// TODO: Implement

	}

	public static void displayCatalog(Map<Integer, Product> catalog) {

		String header = String.format(CATALOG_FORMATTER, "ID", "Product", "Unit", "Quantity", "Cost");
		System.out.println(header);

		for (Map.Entry<Integer, Product> entry : catalog.entrySet()) {

			Integer productId = entry.getKey();
			String productName = entry.getValue().getProductName();
			String unit = entry.getValue().getUnit().toString();
			Integer quantity = entry.getValue().getQuantityPerProduct();
			BigDecimal unitCost = entry.getValue().getCost();

			System.out.println(String.format(CATALOG_FORMATTER, productId, productName, unit, quantity, unitCost));
		}

		System.out.println("\r\n");

	}

	public static int displaySelectPrompt() {

		System.out.println(SELECT_PRODUCT);

		String response = readConsole();

		int selection = parseIntFromConsole(response);

		if (selection == -1) {

			displaySelectPrompt();

		}

		return selection;

	}

	public static int displayQuantityPrompt() {

		System.out.println(SELECT_QUANTITY);

		String response = readConsole();

		int quantity = parseIntFromConsole(response);

		if (quantity == -1) {

			displayQuantityPrompt();

		}

		return quantity;

	}

	public static void displayQuantityReselectPrompt() {
		System.out.println(PROMPT_INVALID_QUANTITY);
	}

	public static void displayReselect(int response) {

		System.out.println(String.format(PROMPT_RESELECT, response));
		return;

	}

	public static void displayConfirm(String product) {

		System.out.println(String.format(PROMPT_CONFIRM, product));

	}

	private static String readConsole() {

		String s = null;

		try {
			s = bufferedReader.readLine();
		} catch (IOException e) {

			System.out.println("Could not parse console input.");
			e.printStackTrace();

		}

		return s;

	}

	public static String parseCodeFromConsole(String s) {

		consoleCodes.clear();
		consoleCodes.add("c");
		consoleCodes.add("s");
		consoleCodes.add("q");

		if (consoleCodes.contains(s)) {
			return s;
		}

		else {

			System.out.println("Code not found: " + s);

			return "-1";
		}

	}

	public static int parseIntFromConsole(String s) {

		int i = -1;

		// Positive integers with optional leading zeroes
		String pattern = "^([0-9]+\\d*)$|^0$";

		if (!s.matches(pattern)) {

			System.out.println("Invalid quantity: " + s);
			return i;

		}

		try {

			i = Integer.parseInt(s);

		}

		catch (Exception e) {

			System.out.println("Could not parse int from console entry.");

		}

		return i;

	}

	public static String displayContinueShoppingPrompt() {

		System.out.println(PROMPT_CONTINUE_SHOPPING);
		String response = readConsole();
		response = parseCodeFromConsole(response);

		return response;
	}

}