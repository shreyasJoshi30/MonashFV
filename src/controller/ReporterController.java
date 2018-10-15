package controller;

import entity.MFVConstants;
import entity.Order;
import entity.OrderList;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.String.format;

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

	public String getSalesReport(OrderList orderList, ProductList productList, Calendar earliestDate, Calendar latestDate) {
		List<Order> orders = orderList.getOrders(earliestDate, latestDate);
		BigDecimal totalSales = BigDecimal.ZERO;
        HashMap<UUID, Double> productSales = new HashMap<UUID, Double>();
		for (Order o : orders) {
			if (o.isPaymentConfirmed()) {
				//add order data here
                for (Pair<UUID, Double> p : o.getItems()) {
                    if(productSales.containsKey(p.getKey())) {
                        double tmp = productSales.get(p.getKey()) + p.getValue();
                        productSales.put(p.getKey(), tmp);
                    } else {
                        productSales.put(p.getKey(), p.getValue());
                    }
                }
				totalSales = totalSales.add(o.getOrderCost());
			}
		}
		String report = "";
        report += format("%1$-"+30+"s", "Name") + format("%1$-"+10+"s", "Quantity") + "\n";
        for (Map.Entry<UUID, Double> x : productSales.entrySet()) {
            report += format("%1$-"+30+"s", productList.getProduct(x.getKey()).getName()) +
                    format("%1$-"+10+"s", x.getValue()) + "\n";
        }
        report += "Total Revenue: $" + totalSales.toString() + "\n";
		return report;
	}

	public String getDeliveries(OrderList orderList, ProductList productList,
                                Calendar earliestDate, Calendar latestDate, String deliveryMethod) {
		List<Order> orders = orderList.getOrdersByDeliveryMethod(earliestDate, latestDate, deliveryMethod);
		String report = "";
		for (Order o : orders) {
		    report += orderList.getOrderReceipt(o.getOrderID(), productList) + "\n";
		}
		return report;
	}

	public String getOrderReport(OrderList orderList, Calendar earliestDate, Calendar latestDate) {
		List<Order> orders = orderList.getOrders(earliestDate, latestDate);
		StringBuilder salesReport = new StringBuilder();
		salesReport.append("---------------------------------ORDER REPORT-------------------------------------\n");
		salesReport.append(" OrderID \t\t\t\t Order Cost \t Address \t Order Date \n");
		int totalOrders = 0;
		BigDecimal totalSales = new BigDecimal("0");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");

		for (Order o : orders) {
			if (o.isPaymentConfirmed()) {
				totalOrders++;
				totalSales = totalSales.add(o.getOrderCost());

				// add order data here
				salesReport.append(o.getOrderID() + " \t\t" + o.getOrderCost() + " \t " + o.getDestAddress() + " \t "
						+ sdf.format(o.getOrderDate().getTime()) + "\n");
			}
		}
		salesReport.append("-----------------------------------------------------------------\n");
		salesReport.append("Total Orders: " + totalOrders + "\n");
		salesReport.append("Total Sales: " + totalSales + "\n");
		salesReport.append("-----------------------------------------------------------------\n");

		return salesReport.toString();
	}
}