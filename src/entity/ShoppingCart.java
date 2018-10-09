package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import javafx.util.Pair;

public class ShoppingCart {

	private UUID CART_ID;
	private UUID customerID;
	List<Pair<UUID, Double>> items = new ArrayList<Pair<UUID,Double>>();

	public UUID getCART_ID() {
		return CART_ID;
	}

	public void setCART_ID(UUID cART_ID) {
		CART_ID = cART_ID;
	}

	public UUID getCustomerID() {
		return customerID;
	}

	public void setCustomerID(UUID customerID) {
		this.customerID = customerID;
	}

	public List<Pair<UUID, Double>> getItems() {
		return items;
	}

	public void setItems(List<Pair<UUID, Double>> items) {
		this.items = items;
	}

	public void addProduct(UUID productId, double qty) {
		items.add(new Pair<UUID, Double>(productId, qty));
	}

	public void editProduct(UUID productId, double qty) {
		ListIterator<Pair<UUID, Double>> litr = items.listIterator();

		while (litr.hasNext()) {
			Pair<UUID, Double> element = litr.next();
			if (element.getKey() == productId) {
				items.set(litr.previousIndex(), new Pair<UUID, Double>(productId, qty));
			}
		}
	}

	public void removeProduct(UUID productId) {
		ListIterator<Pair<UUID, Double>> litr = items.listIterator();

		while (litr.hasNext()) {
			Pair<UUID, Double> element = litr.next();
			if (element.getKey() == productId) {
				items.remove(litr.previousIndex());
			}
		}
	}

	public List<Pair<UUID, Double>> getProducts() {
		return items;
	}

}