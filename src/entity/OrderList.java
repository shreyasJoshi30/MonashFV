package entity;

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
	 * Creates a order object after the user checks out
	 * 
	 * @param totalCartCost
	 * 
	 * @return UUID- unique orderID for new order
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

	public void confirmPayment(UUID orderId) {
	    orders.get(orderId).setPaymentConfirmed(true);
    }

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

	public void writeOrderToFile(String filename) {
		try {
			FileOutputStream fout = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(orders);
		} catch (Exception e) {
			e.getMessage();
		}
	}

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