package controller;

import entity.MFVConstants;
import entity.Order;
import entity.OrderList;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * This class contains functions that can be used to generate reports.
 *
 * @author W0\/E|\| L|_|
 */
public class ReporterController {

	/**
	 * Finds all stocked items in the inventory, i.e ones that can be bought.
	 * 
	 * @param inventory The inventory object to get items from.
	 * @return A list of ID's that meet the specified conditions.
	 */
	public List<UUID> getStockedItems(Inventory inventory) {
		return inventory.findItemsByState(MFVConstants.STOCKED);
	}

	/**
	 * Finds all donated items within specified dates.
	 * 
	 * @param inventory    inventory The inventory object to get items from.
	 * @param earliestDate Earliest item for which product life ended to include.
	 * @param latestDate   Latest item for which product life ended to include.
	 * @return A list of ID's that meet the specified conditions.
	 */
	public List<UUID> getDonatedItems(Inventory inventory, Calendar earliestDate, Calendar latestDate) {
		return inventory.findItemsByStateAndDate(earliestDate, latestDate, MFVConstants.CHARITY);
	}

	/**
	 * Finds all discarded items within specified dates.
	 * 
	 * @param inventory    inventory The inventory object to get items from.
	 * @param earliestDate Earliest item for which product life ended to include.
	 * @param latestDate   Latest item for which product life ended to include.
	 * @return A list of ID's that meet the specified conditions.
	 */
	public List<UUID> getDiscardedItems(Inventory inventory, Calendar earliestDate, Calendar latestDate) {
		return inventory.findItemsByStateAndDate(earliestDate, latestDate, MFVConstants.DISCARDED);
	}

	public String getSalesReport(Calendar earliestDate, Calendar latestDate) {
		OrderList orderList = new OrderList();
		List<Order> orders = orderList.getOrders(earliestDate, latestDate);
		StringBuilder salesReport = new StringBuilder();
		for (Order o : orders) {
			//add order data here
			salesReport.append(o.getOrderID());
			salesReport.append(o.getOrderCost());
		}
		return salesReport.toString();
	}

	public String getDeliveries(Calendar earliestDate, Calendar latestDate, String deliveryMethod) {
		OrderList orderList = new OrderList();
		List<Order> orders = orderList.getOrdersByDeliveryMethod(earliestDate, latestDate, deliveryMethod);
		StringBuilder deliveryReport = new StringBuilder();
		for (Order o : orders) {
			deliveryReport.append(o.getOrderID());
			deliveryReport.append(o.getOrderCost());
		}
		return deliveryReport.toString();
	}
}