package controller;

import java.math.BigDecimal;
import java.util.*;

import entity.MFVConstants;
import entity.Order;
import entity.PaymentSystem;
import entity.ShoppingCart;
import javafx.util.Pair;

public class ShoppingController {

	ShoppingCart cart = new ShoppingCart();
	PaymentSystem ps = new PaymentSystem();
	UUID cartid = cart.getCART_ID();

	/**
	 * This method adds the given product into cart
	 * @param productId the product ID which is to be added
	 * @param qty the quantity of the product
	 * @return returns true if the item is added in cart or else false
	 */
	public boolean addProduct(UUID productId, double qty) {
		return cart.addProduct(productId, qty);
	}
	/**
	 * This method updates the cart with the given quantity
	 * @param productId
	 * @param qty
	 */
	public void editProduct(UUID productId, double qty) {
		cart.editProduct(productId, qty);
	}

	/**
	 * This method removes the item from the cart
	 * @param productId the product ID to remove  from cart
	 */
	public void removeProduct(UUID productId) {
		cart.removeProduct(productId);
	}
	
	/**
	 * This method gets all the products present in the cart
	 * @return the list of products present in the cart
	 */
	public List<Pair<UUID, Double>> getCartProducts() {
		return cart.getProducts();
	}
	
	/**
	 * Method provides the checkout functionality. It incudes creating order, processing payment and generating receipt
	 * @param orderList - the list of orders
	 * @param inventory - the object of inventory
	 * @param customers - current user logged in
	 * @param items 	-the List of items in shopping cart
	 * @param deliveryMethod - delivery method pickup/deliver
	 * @param destAddress - address to be delivered
	 * @param paymentMethod - payment method -card/cash
	 * @param paymentDetails - payment details = cardno-cvv
	 * @param productList - the list of products
	 * @return
	 */
	public Order checkout(OrderList orderList, Inventory inventory, String customers, List<Pair<UUID, Double>> items,
			String deliveryMethod, String destAddress, String paymentMethod, String paymentDetails, ProductList productList) {
		Order order = new Order();
		// validate stock + calculate cost
		BigDecimal totalCartCost = BigDecimal.ZERO;
		for (Pair<UUID, Double> x : items) {
			totalCartCost=totalCartCost.add((productList.getProduct(x.getKey()).getPrice()).multiply(BigDecimal.valueOf(x.getValue())));
			if (!inventory.enoughQty(x.getKey(), x.getValue())) {
				order.setOrderStatusMsg(MFVConstants.NOT_ENOUGH_STOCK+" for "+productList.getProduct(x.getKey()).getName());
				return order;
			}
		}
		//make order
		order = orderList.makeOrder(customers, items, deliveryMethod, destAddress, paymentMethod, paymentDetails, false, totalCartCost);
		if (paymentMethod.equals(MFVConstants.paymentMethod.CARD)){
			//make payment if card is used
            boolean isPaymentDone = PaymentSystem.payByCreditCard(paymentDetails.split("-")[0],
                    paymentDetails.split("-")[1], totalCartCost);
            orderList.confirmPayment(order.getOrderID());
            if (isPaymentDone) { //reduce inventory if payment went good
                order.setOrderStatusMsg(MFVConstants.PAYMENT_SUCCESSFUL);
                for (Pair<UUID, Double> x : items) {
                    inventory.reduceQty(x.getKey(), x.getValue());
                }
            } else {
                order.setOrderStatusMsg(MFVConstants.PAYMENT_UNSUCCESSFUL);
            }
		} else {
            order.setOrderStatusMsg(MFVConstants.PAYMENT_PENDING);
        }
		return order;
	}

	/**
	 * This method clears the shopping cart
	 */
	public void clearCart() {
		List<Pair<UUID, Double>> emptyList = new ArrayList<Pair<UUID, Double>>();
		cart.setItems(emptyList);
	}

	
}