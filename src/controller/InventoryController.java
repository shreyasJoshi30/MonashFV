package controller;

import entity.*;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.*;

/**
 * This class provides functions to manage the inventory.
 *
 * @author W0\/E|\| L|_|
 */
public class InventoryController {

    /**
     * Contructor for InventoryController.
     */
    public InventoryController(){}

    /**
     * Add an item to inventory.
     * @param inventory The Inventory object to add item to.
     * @param product The product profile that the item is an instance of.
     * @param qty Quantity of the product. The unit depends on the sales mode of the product.
     */
    public void addItem(Inventory inventory, ProductProfile product, double qty) {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, Collections.max(product.getShelfLife()));
        inventory.addItem(product, qty, today);
    }

    /**
     * Edit an item in the inventory.
     * @param inventory The Inventory object to add item to.
     * @param price Price of the item.
     * @param itemId ID of the item to edit.
     * @param qty Quantity of the product. The unit depends on the sales mode of the product.
     * @param expiryDate Expiry date of the product.
     */
    public void editItem(Inventory inventory, UUID itemId, BigDecimal price, double qty, Calendar expiryDate) {
        inventory.getItem(itemId).setPrice(price);
        inventory.getItem(itemId).setQty(qty);
        inventory.getItem(itemId).setExpiryDate(expiryDate);
    }

    /**
     * Checks if there is enough quantity in the inventory to purchase a product.
     * @param inventory The Inventory object to work on.
     * @param productId ID of the product to work on.
     * @param amount Quantity to remove from inventory.
     * @return True if there is enough.
     */
    public boolean enoughQty(Inventory inventory, UUID productId, double amount) {
        List<UUID> found = inventory.findStockedProduct(productId);
        double totalQty = 0;
        for (UUID id : found) { totalQty += inventory.getItem(id).getQty(); }
        return (totalQty > amount);
    }

    /**
     * Reduces the quantity of a product in the inventory. This can apply over multiple items in the inventory.
     * The customer will be purchasing products while the inventory may contains multiple items which are instances of said product.
     * @param inventory The Inventory object to work on.
     * @param productId ID of the product to work on.
     * @param amount Quantity to remove from inventory.
     * @return A list of pairs containing the ID and amount subtracted from each item.
     * @exception IllegalArgumentException Thrown when the amount to be reduced is greater than the quantity of the item, i.e not enough stock.
     */
    public List<Pair<UUID, Double>> reduceQty(Inventory inventory, UUID productId, double amount) {
        List<UUID> found = inventory.findStockedProduct(productId);
        double totalQty = 0;
        for (UUID id : found) { totalQty += inventory.getItem(id).getQty(); }
        if (amount > totalQty){
            throw new IllegalArgumentException("Not enough stock. Desired quantity is greater than available stock for item.");
        }
        List<Pair<UUID, Double>> reducedItems = new ArrayList<Pair<UUID, Double>>();
        double currentAmount = amount;
        //Change below if a specific method of choosing items (e.g closest to expiring) is desired
        for (UUID tmp : found) {
            double currentQty = inventory.getItem(tmp).getQty();
            if (currentQty >= currentAmount) {
                inventory.reduceItemQty(tmp, currentAmount);
                reducedItems.add(new Pair<UUID, Double>(tmp, currentAmount));
                break;
            } else {
                inventory.reduceItemQty(tmp, currentQty);
                reducedItems.add(new Pair<UUID, Double>(tmp, currentQty));
                currentAmount -= currentQty;
            }
        }
        return reducedItems;
    }

    /**
     * Set the state of an item in the inventory.
     * @param inventory The Inventory object to work on.
     * @param itemId ID of the item to work on.
     * @param state The string associated with the new state of the item. Values can be found in a constants file.
     */
    public void setItemState(Inventory inventory, UUID itemId, String state) {
        inventory.getItem(itemId).setState(state);
        inventory.getItem(itemId).setEndOfLifeDate(Calendar.getInstance());
    }

    /**
     * Get an item from the inventory.
     * @param inventory The Inventory object to get from.
     * @param itemId ID of the item to obtain.
     * @return The desired item. Returns null if item ID not found.
     */
    public Item getItem(Inventory inventory, UUID itemId) {
        return inventory.getItem(itemId);
    }

    /**
     * Remove an item from the inventory. Does nothing  if item ID not found.
     * @param inventory The Inventory object to remove from.
     * @param itemId ID of the item to obtain.
     */
    public void removeItem(Inventory inventory, UUID itemId) {
        inventory.removeItem(itemId);
    }

    /**
     * Writes inventory to a file.
     * @param inventory Inventory object to write.
     * @param filename Name of file to write to.
     */
    public void writeInventoryToFile(Inventory inventory, String filename) {
        MFVFileIO fio = new MFVFileIO();
        fio.writeObjectToFile(inventory, filename);
    }

    /**
     * Reads an inventory from a file.
     * @param filename Name of file to read from.
     * @return Stored Inventory object.
     */
    public Inventory readInventoryFromFile(String filename) {
        MFVFileIO fio = new MFVFileIO();
        return (Inventory)fio.readObjectFromFile(filename);
    }

}
