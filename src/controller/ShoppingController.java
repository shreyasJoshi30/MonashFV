package controller;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import controller.Inventory;
import entity.Item;
import entity.OrderList;
import entity.PaymentSystem;
import entity.ShoppingCart;
import javafx.util.Pair;

public class ShoppingController {

	ShoppingCart cart = new ShoppingCart();
	PaymentSystem ps = new PaymentSystem();
	UUID cartid = cart.getCART_ID();

	public boolean addProduct(UUID productId, double qty) {
		return cart.addProduct(productId, qty);
	}

	public void editProduct(UUID productId, double qty) {
		cart.editProduct(productId, qty);
	}

	public void removeProduct(UUID productId) {
		cart.removeProduct(productId);
	}

	public List<Pair<UUID, Double>> getCartProducts() {
		return cart.getProducts();
	}

	public UUID checkout(OrderList orderList, Inventory inventory, String customers, List<Pair<UUID, Double>> items,
							String deliveryMethod, String destAddress, String paymentMethod, String paymentDetails) {
		// validate stock + calculate cost
		BigDecimal totalCartCost = BigDecimal.ZERO;
		for (Pair<UUID, Double> x : items) {
			totalCartCost.add(inventory.getItem(x.getKey()).getPrice().multiply(BigDecimal.valueOf(x.getValue())));
			if (inventory.enoughQty(x.getKey(), x.getValue())) {return null;}
		}

		UUID orderId = orderList.makeOrder(customers, items, deliveryMethod, destAddress, paymentMethod, paymentDetails, false, totalCartCost);

        boolean isPaymentDone = PaymentSystem.payByCreditCard(paymentDetails.split("-")[0], paymentDetails.split("-")[1], totalCartCost);
		//set confirm payment in order object

		if (isPaymentDone) {
			for (Pair<UUID, Double> x : items) {
				inventory.reduceQty (x.getKey(), x.getValue());
			}
		} else {
			return null;
		}
		return orderId;
	}

	// Should be removed. Does not make sense
	public void editOrder(String customer, List<Pair<UUID, Double>> items, String deliveryMethod, String destAddress,
			String paymentMethod, String paymentDetails, boolean paymentConfirmed) {

	}
	

	public void clearCart() {
		List<Pair<UUID, Double>> emptyList = Collections.emptyList();
		cart.setItems(emptyList);
	}

	
}