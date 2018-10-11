package entity;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Implements an Item which is an instance of a Product Profile.
 *
 * @author W0\/E|\| L|_|
 */
public class Item extends ProductProfile implements Serializable {

    private final UUID ITEM_ID;
    private double qty;
    private Calendar expiryDate;
    private Calendar endOfLifeDate;
    private String state;
    private static final List<String> ITEM_STATES = Arrays.asList(MFVConstants.CHARITY, MFVConstants.DISCARDED,
            MFVConstants.SOLD, MFVConstants.STOCKED);

    /**
     * Constructor for Item.
     * @param product The product profile that the item is an instance of.
     * @param qty Quantity of the product. The unit depends on the sales mode of the product.
     * @param expiryDate Expiry date of product.
     */
    public Item(ProductProfile product, double qty, Calendar expiryDate) {
        super(product);
        this.ITEM_ID = UUID.randomUUID();
        this.setQty(qty);
        this.setExpiryDate(expiryDate);
        this.setState(MFVConstants.STOCKED);
    }

    /**
     * Alternative constructor for Item. Can be used if for some reason item should not be stocked.
     * @param product The product profile that the item is an instance of.
     * @param qty Quantity of the product. The unit depends on the sales mode of the product.
     * @param expiryDate Expiry date of product.
     * @param endDate End of life date of the product.
     * @param state State of the product.
     */
    public Item(ProductProfile product, double qty, Calendar expiryDate, Calendar endDate, String state) {
        super(product);
        this.ITEM_ID = UUID.randomUUID();
        this.setQty(qty);
        this.setExpiryDate(expiryDate);
        this.setEndOfLifeDate(endDate);
        this.setState(state);
    }

    /**
     * Getter for Item ID.
     * @return Item Id.
     */
    public UUID getItemId() {
        return ITEM_ID;
    }

    /**
     * Getter for quantity of item.
     * @return Quantity of item.
     */
    public double getQty() {
        return qty;
    }

    /**
     * Setter for quantity of item.
     * @param qty Quantity of item.
     * @exception InvalidParameterException Thrown when qty is less than 0 or if it is a non-integer value for batch products.
     */
    public void setQty(double qty) {
        if (qty >= 0) {
            if (this.getSalesMode().equals(MFVConstants.BATCH) && qty != Math.floor(qty)) {
                throw new InvalidParameterException("Invalid quantity. Quantity must be an integer for batch items.");
            }
            this.qty = qty;
        } else {
            throw new InvalidParameterException("Quantity must be greater than or equal to 0.");
        }
    }

    /**
     * Getter for expiry date.
     * @return Expiry date.
     */
    public Calendar getExpiryDate() {
        return expiryDate;
    }

    /**
     * Setter for expiry date.
     * @param expiryDate Expiry date of product.
     */
    public void setExpiryDate(Calendar expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Getter for the end of life date of item.
     * @return End of life date.
     */
    public Calendar getEndOfLifeDate() {
        return endOfLifeDate;
    }

    /**
     * Setter for the end of life date of item.
     * @param endOfLifeDate Date on which item reached the end of it's life.
     */
    public void setEndOfLifeDate(Calendar endOfLifeDate) {
        this.endOfLifeDate = endOfLifeDate;
    }

    /**
     * Getter for end of life outcome. This value can only take a certain number of predefined values.
     * @return End of life outcome.
     */
    public String getState() {
        return state;
    }

    /**
     * Setter for end of life outcome. This value can only take a certain number of predefined values.
     * @param state String denoting the state of the item.
     */
    public void setState(String state) {
        if (ITEM_STATES.contains(state)) {
            this.state = state;
        } else {
            throw new InvalidParameterException("Invalid parameter value for 'state'. Please refer to docs for valid values");
        }
    }
}
