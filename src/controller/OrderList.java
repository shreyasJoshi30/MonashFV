package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import controller.ProductList;
import entity.Order;
import javafx.util.Pair;

public class OrderList implements Serializable {

	private LinkedHashMap<UUID, Order> orders;

	/**
	 * Constructor for the OrderList. Initializes the order list
	 */
	public OrderList() {
		orders = new LinkedHashMap<UUID, Order>();
	}
	/**
	 * This method creates  a order
	 * @param customers - the logged in customer
	 * @param items the list of items
	 * @param deliveryMethod the delivery method to process the order
	 * @param destAddress the address of delivery
	 * @param paymentMethod payment method to differentiate between cash/card
	 * @param paymentDetails cardno-cvv concatenated string
	 * @param paymentConfirmed flag to validate payment
	 * @param totalCartCost the total cart cost of the  all the products
	 * @return returns the order Object
	 */
	public Order makeOrder(String customers, List<Pair<UUID, Double>> items, String deliveryMethod, String destAddress,
			String paymentMethod, String paymentDetails, Boolean paymentConfirmed, BigDecimal totalCartCost) {
	    Order order = new Order();
		UUID orderId = UUID.randomUUID();
		order.setOrderID(orderId);
		order.setCustomer(customers);
		order.setItems(items);
		order.setDeliveryMethod(deliveryMethod);
		order.setDestAddress(destAddress);
		order.setPaymentMethod(paymentMethod);
		order.setPaymentDetails(paymentDetails);
		order.setPaymentConfirmed(paymentConfirmed);
		order.setOrderDate(Calendar.getInstance());
		order.setOrderCost(totalCartCost);
		// Adding order to orderList
		this.orders.put(orderId, order);
		return order;
	}

	/**
	 * This method verifies the payment of the order
	 * @param orderId the generated order ID 
	 */
	public void confirmPayment(UUID orderId) {
	    orders.get(orderId).setPaymentConfirmed(true);
    }

	/**
	 * Returns the list of orders between the given date range
	 * @param earliestDate startdate
	 * @param latestDate enddate
	 * @return list<Order>
	 */
	public List<Order> getOrders(Calendar earliestDate, Calendar latestDate) {
		List<Order> ordersL = new ArrayList<Order>();
		for (Map.Entry<UUID, Order> entry : this.orders.entrySet()) {
			Order o = entry.getValue();
			if (o.getOrderDate().after(earliestDate) && o.getOrderDate().before(latestDate)) {
				ordersL.add(o);
			}
		}
		return ordersL;
	}

	/**
	 * method returns the list of order based on deliverymethod and range of dates
	 * @param earliestDate startdate
	 * @param latestDate end Date
	 * @param deliveryMethod  pickup/delivery
	 * @return
	 */
	public List<Order> getOrdersByDeliveryMethod(Calendar earliestDate, Calendar latestDate, String deliveryMethod) {
		List<Order> ordersL = new ArrayList<Order>();
		for (Map.Entry<UUID, Order> entry : this.orders.entrySet()) {
			Order o = entry.getValue();

			if (o.getOrderDate().after(earliestDate) && o.getOrderDate().before(latestDate)
					&& o.getDeliveryMethod().equals(deliveryMethod)) {
				ordersL.add(o);
			}
		}
		return ordersL;
	}

	/**
	 * This method returns the Order object from a given orderID
	 * @param orderId the unique orderId
	 * @return returns the order object
	 */
	public Order getOrderByUUID(UUID orderId) {
		Order order = new Order();
		for (Map.Entry<UUID, Order> entry : this.orders.entrySet()) {
			Order o = entry.getValue();
			if (o.getOrderID().equals(orderId)) {
				order = o;
				break;
			}
		}

		return order;
	}

	/**
	 * This method prints the receipt after the order is processed
	 * @param orderId the unique order id
	 * @param productList the list of all products
	 * @return returns the generated receipt in string
	 */
	public String getOrderReceipt(UUID orderId, ProductList productList) {
		
		List<Pair<UUID,Double>> itemsInOrder= new ArrayList<>();
		ProductList prod = new ProductList();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		
		Order order = getOrderByUUID(orderId);
		StringBuilder receipt = new StringBuilder();
		receipt.append("---------------------------------------------"+"\n");
		receipt.append(" OrderID: "+order.getOrderID()+"\n");
		receipt.append(" Order Cost: "+order.getOrderCost()+"\n");
		receipt.append(" Address: "+ order.getDestAddress()+"\n");
		receipt.append(" Order Date: "+ sdf.format(order.getOrderDate().getTime())+"\n");
		receipt.append("---------------ITEMS-------------------------"+"\n");
		itemsInOrder=order.getItems();
		for(Pair<UUID,Double> i:itemsInOrder ) {
			receipt.append(" "+productList.getProduct(i.getKey()).getName() +
                    ": " + i.getValue().toString() + "\n");
		}
		receipt.append("---------------------------------------------"+"\n");
		receipt.append("THANK YOU for shopping with us. Hope you had a pleasant experience"+"\n");
		receipt.append("---------------------------------------------"+"\n");
		return receipt.toString();
		
	}
	
	/**
	 * This method writes the orders into a file
	 * @param filename
	 */
	public void writeOrderToFile(String filename) {
		try {
			FileOutputStream fout = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(orders);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * This method reads the orders from the file
	 * @param filename
	 */
	public void readOrderFromFile(String filename) {
		try {
			FileInputStream fin = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fin);
			this.orders = (LinkedHashMap<UUID, Order>) ois.readObject();
		} catch (Exception e) {
			e.getMessage();
		}

	}

}