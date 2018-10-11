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


	public boolean addProduct(UUID productId, double qty) {
		for (int i = 0; i < this.items.size(); i++) {
			if (items.get(i).getKey().equals(productId)) {
				return false;
			}
		}
		items.add(new Pair<UUID, Double>(productId, qty));
		return true;
	}

	public void editProduct(UUID productId, double qty) {
		for (int i = 0; i < this.items.size(); i++) {
			if (items.get(i).getKey().equals(productId)) {
				items.set(i, new Pair<UUID, Double>(productId, qty));
				break;
			}
		}
	}

	public void removeProduct(UUID productId) {
		for (int i = 0; i < this.items.size(); i++) {
			if (items.get(i).getKey().equals(productId)) {
				items.remove(i);
				break;
			}
		}
	}

	public List<Pair<UUID, Double>> getProducts() {
		return this.items;
	}

}