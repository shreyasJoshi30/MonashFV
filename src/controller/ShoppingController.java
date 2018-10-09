package controller;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import entity.Inventory;
import entity.Item;
import entity.OrderList;
import entity.PaymentSystem;
import entity.ShoppingCart;
import javafx.util.Pair;

public class ShoppingController {

	ShoppingCart cart = new ShoppingCart();
	PaymentSystem ps = new PaymentSystem();
	UUID cartid = cart.getCART_ID();

	public void addProduct(UUID productId, double qty) {
		cart.addProduct(productId, qty);
	}

	public void editProduct(UUID productId, double qty) {
		cart.editProduct(productId, qty);
	}

	public void removeProduct(UUID productId) {
		cart.removeProduct(productId);
	}

	public List<Pair<UUID, Double>> getCartProducts() {
		List<Pair<UUID, Double>> items = null;
		items = cart.getProducts();
		return items;
	}

	public boolean processPayment(UUID orderId) {
		boolean isPaymentSuccessful = false;

		return isPaymentSuccessful;
	}

	/**
	 * 
	 * @param customers
	 * @param items
	 * @param paymentConfirmed
	 * @param paymentDetails
	 * @param paymentMethod
	 * @param destAddress
	 * @param deliveryMethod
	 * @return boolean flag true:success ;false: failure
	 */
	public boolean checkout(String customers, List<Pair<UUID, Double>> items, String deliveryMethod, String destAddress,
			String paymentMethod, String paymentDetails, Boolean paymentConfirmed) {

		Boolean isCheckoutDone = false;
		Boolean isPaymentDone = false;
		PaymentSystem ps = new PaymentSystem();
		OrderList ol = new OrderList();
		InventoryController invc = new InventoryController();
		Inventory inv = new Inventory();

		String cardNo = paymentDetails.split("-")[0]; // Card no
		String cvv = paymentDetails.split("-")[1]; // Cvv

		UUID orderId = ol.makeOrder(customers, items, deliveryMethod, destAddress, paymentMethod, paymentDetails,
				paymentConfirmed);
		// validate stock

		ListIterator<Pair<UUID, Double>> litr = items.listIterator();
		BigDecimal totalCartCost = BigDecimal.ZERO;

		while (litr.hasNext()) {
			Pair<UUID, Double> element = litr.next();
			// Calculate price
			Item currentItem = inv.getItem(element.getKey());
			totalCartCost = totalCartCost.add(currentItem.getPrice());
		}
		if (orderId != null) {
			isPaymentDone = ps.payByCreditCard(cardNo, cvv, totalCartCost);
		}
		if (isPaymentDone) {
			isCheckoutDone = true;
			while (litr.hasNext()) {
				Pair<UUID, Double> element = litr.next();
				// Reduce
				invc.reduceQty(inv, element.getKey(), element.getValue());
			}
		}
		return isCheckoutDone;
	}

	// Should be removed. Does not make sense
	public void editOrder(String customer, List<Pair<UUID, Double>> items, String deliveryMethod, String destAddress,
			String paymentMethod, String paymentDetails, boolean paymentConfirmed) {

	}

	public List<String> getReceipt() {
		List<String> receipt = null;

		return receipt;
	}

	public List<String> getInvoice() {
		List<String> invoice = null;

		return invoice;
	}

	public double getDistanceFromStore(String address) {
		double distance = 0.0;

		return distance;
	}

	public void clearCart() {
		List<Pair<UUID, Double>> emptyList = Collections.emptyList();
		cart.setItems(emptyList);
	}

	public UUID makeOrder(String customer, List<Pair<UUID, Double>> items, String deliveryMethod, String destAddress,
			String paymentMethod, String paymentDetails, boolean paymentConfirmed) {
		UUID orderId = null;

		return orderId;
	}
}