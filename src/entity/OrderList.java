package entity;

import java.util.List;
import java.util.UUID;

import javafx.util.Pair;

public class OrderList {
	
	/**
	 * Creates a order object after the user checks out
	 * @return UUID- unique orderID for new order
	 */
	public UUID makeOrder(String customers,List<Pair<UUID, Double>> items,
						  String deliveryMethod,String destAddress,String paymentMethod,
						  String paymentDetails,Boolean paymentConfirmed) {
		
		Order order = new Order();
		UUID orderId= UUID.randomUUID();
		order.setOrderID(orderId);
		order.setCustomer(customers);
		order.setItems(items);
		order.setDeliveryMethod(deliveryMethod);
		order.setDestAddress(destAddress);
		order.setPaymentMethod(paymentMethod);
		order.setPaymentDetails(paymentDetails);
		order.setPaymentConfirmed(paymentConfirmed);
		
		
		return order.getOrderID();
		
	}

}


 