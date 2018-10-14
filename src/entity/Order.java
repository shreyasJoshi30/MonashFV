package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javafx.util.Pair;


public class Order implements Serializable{
	
	private UUID orderID;
	private String customer;
	private String deliveryMethod;
	private String destAddress;
	private String paymentMethod;
	private String paymentDetails;
	private boolean paymentConfirmed;
	private List<Pair<UUID, Double>> items;
	private Calendar orderDate;
	private BigDecimal orderCost;
	private String orderStatusMsg;
	
	
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
	public Calendar getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Calendar orderDate) {
		this.orderDate = orderDate;
	}
	public BigDecimal getOrderCost() {
		return orderCost;
	}
	public void setOrderCost(BigDecimal orderCost) {
		this.orderCost = orderCost;
	}
	public String getOrderStatusMsg() {
		return orderStatusMsg;
	}
	public void setOrderStatusMsg(String orderStatusMsg) {
		this.orderStatusMsg = orderStatusMsg;
	}
	
	
	

	
	
	

}
