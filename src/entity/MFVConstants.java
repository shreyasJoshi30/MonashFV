package entity;

import java.util.Arrays;
import java.util.List;

/**
 * This class stores constants used throughout the application.
 *
 * @author W0\/E|\| L|_|
 */
public class MFVConstants {
    /**
     * A sales mode for products. Refers to any mode that can be purchased in any amount, e.g any that is bought in kg's.
     */
    public static final String LOOSE = "Loose";

    /**
     * A sales mode for products. Refers to any mode that comes in integral amounts, e.g bags of potatoes, bunches of aspragus, etc.
     */
    public static final String BATCH = "Batch";

    /**
     * A type of product. A culinary fruit.
     */
    public static final String FRUIT = "Fruit";

    /**
     * A type of product. A culinary vegetable.
     */
    public static final String VEG = "Veg";

    /**
     * An end of life outcome for a product. Product has been donated to charity.
     */
    public static final String CHARITY = "Charity";

    /**
     * An end of life outcome for a product. Product has been disposed of.
     */
    public static final String DISCARDED = "Discarded";

    /**
     * An end of life outcome for a product. Product has been completely sold off.
     */
    public static final String SOLD = "Sold";

    /**
     * An end of life outcome for a product. Product is in stock and available to purchase.
     */
    public static final String STOCKED = "Stocked";

    /**
     * Brackets "()" are used in product names to describe them. These should be excluded from searches.
     */
    public static final String PRODUCT_SEARCH_IGNORE_CHARACTER = "(";

    /**
     * Descriptors used in the name of product which are sold in batches.
     */
    public static final List<String> BATCH_PRODUCT_DESCRIPTORS = Arrays.asList("(Bag)", "(Bunch)", "(Each)");

    public static final String PICK_UP = "Pick-up";

    public static final String DELIVERY = "Delivery";
    
    
    public static  interface deliveryMethod{
    	
    	public static final String PICKUP = "Pickup";
    	public static final String DELIVERY = "Delivery";
    }

	public static interface paymentMethod {

		public static final String CARD = "Card";
		public static final String CASH = "Cash";
	}

	public static final String PAYMENT_SUCCESSFUL = "Payment Successful";

	public static final String PAYMENT_UNSUCCESSFUL = "Payment Unsuccessful";
	public static final String NOT_ENOUGH_STOCK = "Not Enough Stock";

}
