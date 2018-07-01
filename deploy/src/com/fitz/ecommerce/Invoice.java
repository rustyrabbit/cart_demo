package com.fitz.ecommerce;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Invoice {

	private int customerId;

	final String INVOICE_FORMATTER = "%1$-40s %2$10s %3$10s %4$10s";

	private Map<Integer, Integer> cart;
	private Map<Integer, Product> catalog = new HashMap<Integer, Product>();

	public Invoice(int customerId, Map<Integer, Product> catalog) {

		this.customerId = customerId;
		this.catalog = catalog;
		this.cart = new HashMap<Integer, Integer>();

	}

	public void addProduct(Integer productId, Integer quantity) {

		Integer count = null;

		// If there aren't products of this kind in the cart initialize count to zero
		// Otherwise get the current quantity

		if (!cart.keySet().contains(productId)) {
			count = 0;
		} else {
			count = cart.get(productId);
		}

		count += quantity;
		cart.put(productId, count);

	}

	public void deleteProduct(Integer productId, Integer quantity) {

		if (quantity <= 0) {

			System.out.println(String.format("Sorry, you eneterd %s. Please enter a positive whole number", quantity));
			return;

		}

		Integer count = cart.get(productId);

		if (quantity >= cart.get(productId)) {

			// Remove all <product> from cart Yes, No , Cancel
			boolean response = false;
			if (response) {
				cart.put(productId, 0);
				return;
			}

			else {

				// Cancel transaction
				return;

			}

		}

		else {

			count -= quantity;
			this.cart.put(productId, count);
			return;

		}

	}

	public BigDecimal getCartTotal() {

		BigDecimal total = new BigDecimal(BigInteger.ZERO, 2);

		for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {

			Integer quantity = entry.getValue();
			Product p = new Product(entry.getKey());

			total.add(p.getCost().multiply(new BigDecimal(quantity)));

		}

		return total;

	}

	public Map<Integer, Integer> getCart() {
		return this.cart;
	}

	public void viewInvoice() {

		BigDecimal total = new BigDecimal(BigInteger.ZERO, 2);
		String header = String.format(INVOICE_FORMATTER, "Product", "Quantity", "Unit Cost", "Total");
		System.out.println(header);
		
		for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {

			Integer quantity = entry.getValue();
			Product p = catalog.get((entry.getKey()));
			BigDecimal cost = p.getCost();
			BigDecimal q = new BigDecimal(quantity);
			BigDecimal subTotal = cost.multiply(q);
			total = total.add(subTotal);

			System.out.println(String.format(INVOICE_FORMATTER, p.getProductName(), quantity, p.getCost().toString(), subTotal));

		}
		
		System.out.println(String.format(INVOICE_FORMATTER,"Grand Total", "", "", total.toString()));
		

	}

}