package entity;

import java.util.List;
import java.util.UUID;

import javafx.util.Pair;


public class Order {
	
	private UUID orderID;
	private String customer;
	private String deliveryMethod;
	private String destAddress;
	private String paymentMethod;
	private String paymentDetails;
	private boolean paymentConfirmed;
	private List<Pair<UUID, Double>> items;
	
	
	public UUID getOrderID() {
		return orderID;
	}
	public void setOrderID(UUID orderID) {
		this.orderID = orderID;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	public String getDestAddress() {
		return destAddress;
	}
	public void setDestAddress(String destAddress) {
		this.destAddress = destAddress;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentDetails() {
		return paymentDetails;
	}
	public void setPaymentDetails(String paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	public boolean isPaymentConfirmed() {
		return paymentConfirmed;
	}
	public void setPaymentConfirmed(boolean paymentConfirmed) {
		this.paymentConfirmed = paymentConfirmed;
	}
	public List<Pair<UUID, Double>> getItems() {
		return items;
	}
	public void setItems(List<Pair<UUID, Double>> items2) {
		this.items = items2;
	}
	

	
	
	

}
