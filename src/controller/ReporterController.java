package controller;

import entity.MFVConstants;
import entity.Order;
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
	 * returns the sales of all the products 
	 * @param orderList -The list of all orders
	 * @param earliestDate - start date
	 * @param latestDate - end date
	 * @return String which contains the sales report
	 */
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
	/**
	 * Returns the report about total deliveries made
	 * @param orderList -The list of all orders
	 * @param productList- The list of all products
	 * @param earliestDate - start date
	 * @param latestDate - end date
	 * @return String which contains the delivery report
	 */
	public String getDeliveries(OrderList orderList, ProductList productList,
                                Calendar earliestDate, Calendar latestDate, String deliveryMethod) {
		List<Order> orders = orderList.getOrdersByDeliveryMethod(earliestDate, latestDate, deliveryMethod);
		String report = "";
		for (Order o : orders) {
		    report += orderList.getOrderReceipt(o.getOrderID(), productList) + "\n";
		}
		return report;
	}
	
	/**
	 *  Returns the string which comprises of all the orders within the given date range
	 * @param orderList -The list of all orders
	 * @param earliestDate - start date
	 * @param latestDate - end date
	 * @return String which contains the report
	 */
	public String getOrderReport(OrderList orderList, Calendar earliestDate, Calendar latestDate) {
		List<Order> orders = orderList.getOrders(earliestDate, latestDate);
		StringBuilder salesReport = new StringBuilder();
		salesReport.append("---------------------------------ORDER REPORT-------------------------------------\n");
		String header=format("%1$-"+40+"s", "ORDER ID") + format("%1$-"+15+"s", "Order Cost") 
		+ format("%1$-"+30+"s", "Address")+ format("%1$-"+30+"s", "Order Date")+"\n";
		salesReport.append(header);
		int totalOrders = 0;
		BigDecimal totalSales = new BigDecimal("0");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");

		for (Order o : orders) {
			if (o.isPaymentConfirmed()) {
				totalOrders++;
				totalSales = totalSales.add(o.getOrderCost());

				// add order data here
				salesReport.append(format("%1$-"+40+"s", o.getOrderID())+
						format("%1$-"+15+"s", o.getOrderCost())+
						format("%1$-"+30+"s", o.getDestAddress())+
						format("%1$-"+30+"s", sdf.format(o.getOrderDate().getTime()))+ "\n");
			}
		}
		salesReport.append("-----------------------------------------------------------------\n");
		salesReport.append("Total Orders: " + totalOrders + "\n");
		salesReport.append("Total Sales: $" + totalSales + "\n");
		salesReport.append("-----------------------------------------------------------------\n");

		return salesReport.toString();
	}
}