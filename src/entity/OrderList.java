package entity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import javafx.util.Pair;

public class OrderList {

	private LinkedHashMap<UUID, Order> orders;
	private LinkedHashMap<UUID, Order> ordersFromFile;
	private Order order;

	/**
	 * Constructor for the OrderList. Initializes the order list
	 */
	public OrderList() {
		orders = new LinkedHashMap<UUID, Order>();
		ordersFromFile = new LinkedHashMap<UUID, Order>();
		order = new Order();
	}

	/**
	 * Creates a order object after the user checks out
	 * 
	 * @param totalCartCost
	 * 
	 * @return UUID- unique orderID for new order
	 */
	public UUID makeOrder(String customers, List<Pair<UUID, Double>> items, String deliveryMethod, String destAddress,
			String paymentMethod, String paymentDetails, Boolean paymentConfirmed, BigDecimal totalCartCost) {

		//Order order = new Order();
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

		writeOrderToFile();

		return order.getOrderID();
	}

	public void confirmPayment(UUID orderId) {
	    orders.get(orderId).setPaymentConfirmed(true);
    }

	public List<Order> getOrders(Calendar earliestDate, Calendar latestDate) {

		List<Order> orders = new ArrayList<Order>();
		for (Map.Entry<UUID, Order> entry : ordersFromFile.entrySet()) {
			Order o = entry.getValue();
			// now work with key and value...

			if (o.getOrderDate().after(earliestDate) && o.getOrderDate().before(latestDate)) {
				orders.add(o);
			}
		}
		return orders;
	}

	public List<Order> getOrdersByDeliveryMethod(Calendar earliestDate, Calendar latestDate, String deliveryMethod) {

		List<Order> orders = new ArrayList<Order>();
		for (Map.Entry<UUID, Order> entry : ordersFromFile.entrySet()) {
			Order o = entry.getValue();
			// now work with key and value...

			if (o.getOrderDate().after(earliestDate) && o.getOrderDate().before(latestDate)
					&& o.getDeliveryMethod().equals(deliveryMethod)) {
				orders.add(o);
			}
		}
		return orders;
	}

	public Order getOrderByUUID(UUID orderId) {

		Order order = new Order();
		for (Map.Entry<UUID, Order> entry : ordersFromFile.entrySet()) {
			Order o = entry.getValue();
			// now work with key and value...

			if (o.getOrderID() == orderId) {
				order = o;
				break;
			}
		}

		return order;
	}

	public String getOrderReceipt(UUID orderId) {
		
		Order order = getOrderByUUID(orderId);
		StringBuilder receipt = new StringBuilder();
		receipt.append(order.getOrderID().toString()+"\n");
		receipt.append(order.getOrderCost().toString()+"\n");
		return receipt.toString();
		
	}

	public String getOrderInvoice(UUID orderId) {
		
		Order order = getOrderByUUID(orderId);
		StringBuilder receipt = new StringBuilder();
		receipt.append(order.getOrderID().toString()+"\n");
		receipt.append(order.getOrderCost().toString()+"\n");
		return receipt.toString();
	}

	public void writeOrderToFile() {
		try {
			FileOutputStream fout = new FileOutputStream("orders.out");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(orders);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void readOrderFromFile() {
		try {
			FileInputStream fin = new FileInputStream("file.out");
			ObjectInputStream ois = new ObjectInputStream(fin);
			ordersFromFile = (LinkedHashMap<UUID, Order>) ois.readObject();
		} catch (Exception e) {
			e.getMessage();
		}
	}

}