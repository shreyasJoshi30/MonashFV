package controller;

import java.math.BigDecimal;
import java.util.*;

import controller.Inventory;
import entity.Item;
import entity.MFVConstants;
import entity.Order;
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

    /*public Pair<UUID, Integer> checkout(OrderList orderList, ProductList productList, Inventory inventory,
                                        String customers, List<Pair<UUID, Double>> items, String deliveryMethod,
                                        String destAddress, String paymentMethod, String paymentDetails) {
		// validate stock + calculate cost
		BigDecimal totalCartCost = BigDecimal.ZERO;
		for (Pair<UUID, Double> x : items) {
			totalCartCost.add(productList.getProduct(x.getKey()).getPrice().multiply(BigDecimal.valueOf(x.getValue())));
			if (!inventory.enoughQty(x.getKey(), x.getValue())) {
                return new Pair<UUID, Integer>(x.getKey(), Integer.valueOf(1));
			}
		}

		UUID orderId = orderList.makeOrder(customers, items, deliveryMethod, destAddress, paymentMethod, paymentDetails, false, totalCartCost);

        boolean isPaymentDone = PaymentSystem.payByCreditCard(paymentDetails.split("-")[0], paymentDetails.split("-")[1], totalCartCost);
		//set confirm payment in order object

		if (isPaymentDone) {
			for (Pair<UUID, Double> x : items) {
				inventory.reduceQty(x.getKey(), x.getValue());
			}
		} else {
            return new Pair<UUID, Integer>(null, Integer.valueOf(2));
		}
		return new Pair<UUID, Integer>(orderId, Integer.valueOf(0));
	}*/
	
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

		order = orderList.makeOrder(customers, items, deliveryMethod, destAddress, paymentMethod, paymentDetails, false, totalCartCost);
		if (paymentMethod.equals(MFVConstants.paymentMethod.CARD)){
            boolean isPaymentDone = PaymentSystem.payByCreditCard(paymentDetails.split("-")[0],
                    paymentDetails.split("-")[1], totalCartCost);
            orderList.confirmPayment(order.getOrderID());
            if (isPaymentDone) {
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

	public void clearCart() {
		List<Pair<UUID, Double>> emptyList = new ArrayList<Pair<UUID, Double>>();
		cart.setItems(emptyList);
	}

	
}