package controller;

import entity.Item;
import entity.MFVConstants;
import entity.ProductProfile;
import javafx.util.Pair;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.*;

/**
 * Implement the Inventory which contains Items.
 * It also provides functions to manage the items.
 *
 * @author W0\/E|\| L|_|
 */
public class Inventory implements Serializable {

    private LinkedHashMap<UUID, Item> items;

    /**
     * Constructor for the Inventory. Initializes the item list
     */
    public Inventory() {
        items = new LinkedHashMap<UUID, Item>();
    }

    /**
     * Adds an item to the inventory. The expiry date will be the current date with the max shelf life of the product added.
     * @param product The product profile that the item is an instance of.
     * @param qty Quantity of the product. The unit depends on the sales mode of the product.
     * @exception InvalidParameterException Thrown when qty is less than 0.
     */
    public void addItem(ProductProfile product, double qty) {
        if (qty < 0) {
            throw new InvalidParameterException("Quantity must be greater than or equal to 0.");
        }
        Calendar expiryDate = Calendar.getInstance();
        expiryDate.add(Calendar.DATE, Collections.max(product.getShelfLife()));
        Item item = new Item(product, qty, expiryDate);
        this.items.put(item.getItemId(), item);
    }

    /**
     * Adds an item to the inventory. Used when item is already created.
     * @param item Item to be added.
     */
    public void addItem(Item item) {
        this.items.put(item.getItemId(), item);
    }

    /**
     * Gets an item from the inventory.
     * @param itemId ID of the item to obtain.
     * @return The desired item. Returns null if ID is not found.
     */
    public Item getItem(UUID itemId) {
        return this.items.get(itemId);
    }

    /**
     * Remove an item from the inventory. Does nothing if ID is not found.
     * @param itemId ID of item to remove.
     */
    public void removeItem(UUID itemId) {
        this.items.remove(itemId);
    }

    /**
     * Reduces the quantity of an item in the inventory. This works on a single item (NOT a product).
     * @param itemId ID of the item to work on.
     * @param amount Quantity to remove from inventory.
     * @exception IllegalArgumentException Thrown when amount is less than or equal to 0 or it's NaN or infinite.
     * This is also thrown if if the item is a batch item and the amount is not an integer amount.
     * @exception IllegalArgumentException Thrown when the amount to be reduced is greater than the quantity of the item, i.e not enough stock.
     */
    private void reduceItemQty(UUID itemId, double amount) {
        double currentQty = this.getItem(itemId).getQty();
        if (amount <= 0 || Double.isInfinite(amount) || Double.isNaN(amount)) {
            throw new InvalidParameterException("Invalid amount. Must be greater than 0.");
        }
        if (this.getItem(itemId).getSalesMode().equals(MFVConstants.BATCH) && amount != Math.floor(amount)) {
            throw new InvalidParameterException("Invalid amount. Amount must be an integer for batch items.");
        }
        if (amount > currentQty) {
            throw new IllegalArgumentException("Not enough stock. Desired quantity is greater than available stock for item.");
        } else if (amount == currentQty) {
            this.getItem(itemId).setQty(0);
            this.getItem(itemId).setState(MFVConstants.SOLD);
        } else {
            this.getItem(itemId).setQty(currentQty - amount);
        }
    }

    /**
     * Finds all stocked items that are instance of a certain Product. Can be used when determining how much of a
     * product is available to purchase.
     * @param productId ID of product to search for.
     * @return A list of item ID's that match the searched product ID.
     */
    public List<UUID> findProductStock(UUID productId) {
        List<UUID> stocked = this.findItemsByState(MFVConstants.STOCKED);
        List<UUID> found = new ArrayList<UUID>();
        for (UUID itemId : stocked) {
            if (this.getItem(itemId).getProductId().equals(productId)) {
                found.add(itemId);
            }
        }
        return found;
    }

    /**
     * Find all items that are in a certain state.
     * @param itemType The string associated with the state. Should be found in a constants file.
     * @return A list of ID's for items in the chosen state.
     */
    public List<UUID> findItemsByState(String itemType) {
        List<UUID> found = new ArrayList<UUID>();
        for(UUID itemId : this.items.keySet()){
            Item tmp = this.items.get(itemId);
            if (itemType.equals(tmp.getState())){
                found.add(itemId);
            }
        }
        return found;
    }

    /**
     * Find all items that are in a certain state and that entered that state within a certain time limit.
     * Date limits are inclusive.
     * @param earliestDate Earliest item for which product life ended to include.
     * @param latestDate Latest item for which product life ended to include.
     * @param itemType The string associated with the state. Should be found in a constants file.
     * @return A list of ID's for items in the chosen state and where the end of life date is between the limits.
     */
    public List<UUID> findItemsByStateAndDate(Calendar earliestDate, Calendar latestDate, String itemType) {
        List<UUID> found = new ArrayList<UUID>();
        for(UUID itemId : this.items.keySet()) {
            Item tmp = this.getItem(itemId);
            if (!(tmp.getEndOfLifeDate() == null)) {
                if (itemType.equals(tmp.getState())) {
                    if (tmp.getEndOfLifeDate().after(earliestDate) && tmp.getEndOfLifeDate().before(latestDate)) {
                        found.add(itemId);
                    }
                }
            }
        }
        return found;
    }

    /**
     * Checks if there is enough quantity in the inventory to purchase a product.
     * @param productId ID of the product to work on.
     * @param amount Quantity to check for in the inventory.
     * @return True if there is enough.
     */
    public boolean enoughQty(UUID productId, double amount) {
        List<UUID> found = this.findProductStock(productId);
        double totalQty = 0;
        for (UUID id : found) { totalQty += this.getItem(id).getQty(); }
        return (totalQty >= amount);
    }

    /**
     * Reduces the quantity of a product in the inventory. This can apply over multiple items in the inventory.
     * The customer will be purchasing products while the inventory may contains multiple items which are instances of said product.
     * @param productId ID of the product to work on.
     * @param amount Quantity to remove from inventory.
     * @return A list of pairs containing the ID and amount subtracted from each item.
     * @exception IllegalArgumentException Thrown when the amount to be reduced is greater than the quantity of the item, i.e not enough stock.
     */
    public List<Pair<UUID, Double>> reduceQty(UUID productId, double amount) {
        List<UUID> found = this.findProductStock(productId);
        double totalQty = 0;
        for (UUID id : found) { totalQty += this.getItem(id).getQty(); }
        if (amount > totalQty){
            throw new IllegalArgumentException("Not enough stock. Desired quantity is greater than available stock for item.");
        }
        List<Pair<UUID, Double>> reducedItems = new ArrayList<Pair<UUID, Double>>();
        double currentAmount = amount;
        //Change below if a specific method of choosing items (e.g closest to expiring) is desired. Can sort foun
        sortItemPurchaseOrder(found);
        for (UUID tmp : found) {
            double currentQty = this.getItem(tmp).getQty();
            if (currentQty >= currentAmount) {
                this.reduceItemQty(tmp, currentAmount);
                reducedItems.add(new Pair<UUID, Double>(tmp, currentAmount));
                break;
            } else {
                this.reduceItemQty(tmp, currentQty);
                reducedItems.add(new Pair<UUID, Double>(tmp, currentQty));
                currentAmount -= currentQty;
            }
        }
        return reducedItems;
    }

    /**
     * Modifies the list of items based on what order they should be purchased in.
     * @param foundItems List of items that all refer to the same product.
     */
    private void sortItemPurchaseOrder(List<UUID> foundItems) { }

    /**
     * Set the state of an item in the inventory. Also changes the end of life date of said item to the current date.
     * @param itemId ID of the item to work on.
     * @param state The string associated with the new state of the item. Values can be found in a constants file.
     */
    public void setItemState(UUID itemId, String state) {
        this.getItem(itemId).setState(state);
        this.getItem(itemId).setEndOfLifeDate(Calendar.getInstance());
    }

    /**
     * Writes inventory to a file. Used in conjunction with 'readInventoryFromFile'.
     * @param filename Name of file to write to.
     */
    public void writeInventoryToFile(String filename) {
        MFVFileIO fio = new MFVFileIO();
        fio.writeObjectToFile(this.items, filename);
    }

    /**
     * Reads an inventory from a file. Used in conjunction with 'writeInventoryToFile'. File must be written by that function to succeed.
     * @param filename Name of file to read from.
     * @return Stored Inventory object.
     */
    public void readInventoryFromFile(String filename) {
        MFVFileIO fio = new MFVFileIO();
        this.items = (LinkedHashMap<UUID, Item>)fio.readObjectFromFile(filename);
    }

}
