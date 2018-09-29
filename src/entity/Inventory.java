package entity;


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
     * Adds an item to the inventory.
     * @param product The product profile that the item is an instance of.
     * @param qty Quantity of the product. The unit depends on the sales mode of the product.
     * @param expiryDate Expiry date of product.
     */
    public void addItem(ProductProfile product, double qty, Calendar expiryDate) {
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
     * Reduces the quantity of an item in the inventory.
     * @param itemId ID of the item to work on.
     * @param amount Quantity to remove from inventory.
     * @exception IllegalArgumentException Thrown when amount is less than or equal to 0 or it's NaN or infinite.
     * This is also thrown if if the item is a batch item and the amount is not an integer amount.
     * @exception IllegalArgumentException Thrown when the amount to be reduced is greater than the quantity of the item, i.e not enough stock.
     */
    public void reduceItemQty(UUID itemId, double amount) {
        double currentQty = this.getItem(itemId).getQty();
        if (amount <= 0 || Double.isInfinite(amount) || Double.isNaN(amount)) {
            throw new InvalidParameterException("Invalid amount. Must be greater than 0.");
        }
        if (this.getItem(itemId).getSalesMode().equals(MFVConstants.BATCH) && amount != Math.floor(amount)) {
            throw new InvalidParameterException("Invalid amount. Amount must be an integer for batch items.");
        }
        if (amount > currentQty){
            throw new IllegalArgumentException("Not enough stock. Desired quantity is greater than available stock for item.");
        } else if (amount == currentQty) {
            this.getItem(itemId).setQty(0);
            this.getItem(itemId).setState(MFVConstants.SOLD);
        } else {
            this.getItem(itemId).setQty(currentQty - amount);
        }
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
     * Finds all stocked. items that are instance of a certain Product Profile. Used when determining how much of a
     * product is available to purchase.
     * @param productId ID of product to search for.
     * @return A list of item ID's that match the searched product ID.
     */
    public List<UUID> findStockedProduct(UUID productId) {
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

}
