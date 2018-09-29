package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Implements a Product Profile that stores information on each type of product that is sold.
 * Examples of Product profile include watermelons, royal gala apples, pink lady apples and lebanese cucumbers.
 * Different sales modes also have different profiles, e.g loose potatoes and bags of potatoes have different profiles.
 *
 * W0\/E|\| L|_|
 */
public class ProductProfile implements Serializable {
    private final UUID PRODUCT_ID;
    private String name;
    private List<String> altNames;
    private String category;
    private String source;
    private List<Integer> shelfLife;
    private String salesMode;
    private BigDecimal price;

    private static final List<String> SALES_MODE_TYPES = Arrays.asList(MFVConstants.LOOSE, MFVConstants.BATCH);
    private static final List<String> CATEGORY_TYPES = Arrays.asList(MFVConstants.FRUIT, MFVConstants.VEG);

    /**
     * Constructor for a Product Profile.
     * @param name Name of a product.
     * @param altNames List of alternative names of product.
     * @param category Category of product, 'F' or 'V' for 'Fruit' and 'Vegetable' respectively.
     * @param source Source of product.
     * @param shelfLife Shelf life of product.
     * @param salesMode Sales mode of product. Can be "Loose" or "Batch".
     * @param price Price of product.
     */
    public ProductProfile(String name, List<String> altNames, String category, String source,
                          List<Integer> shelfLife, String salesMode, BigDecimal price){
        this.PRODUCT_ID = UUID.randomUUID();
        setName(name);
        setAltNames(altNames);
        setCategory(category);
        setSource(source);
        setShelfLife(shelfLife);
        setSalesMode(salesMode);
        setPrice(price);
    }

    /**
     * Constructor for a Product Profile. Used by subclasses to replicate values.
     * @param product A product profile whose values should be copied to the new product being created.
     */
    public ProductProfile(ProductProfile product){
        this.PRODUCT_ID = product.getProductId();
        setName(product.getName());
        setAltNames(product.getAltNames());
        setCategory(product.getCategory());
        setSource(product.getSource());
        setShelfLife(product.getShelfLife());
        setSalesMode(product.getSalesMode());
        setPrice(product.getPrice());
    }

    /**
     * Getter for the protect ID, a unique identifier for the Product Profile. This will never be null.
     * @return ID of the product profile.
     */
    public UUID getProductId() {
        return PRODUCT_ID;
    }

    /**
     * Getter for Name of product.
     * @return Name of product.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for Name of product.
     * @param name The name that should be used for the product profile.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the alternative names of the product. These are other names that an be used to refer to the product.
     * @return A List containing each of the alternative names as separate entries. Will be empty if there are none.
     */
    public List<String> getAltNames() {
        return altNames;
    }

    /**
     * Setter for the alternative names of the product.
     * @param altNames A List containing the alternative names of the product profile.
     */
    public void setAltNames(List<String> altNames) {
        this.altNames = altNames;
    }

    /**
     * Getter for category. Will be either 'F' or 'V.
     * @return Category of the product.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Setter for category.
     * @param category Category of the product. Must be either the character 'F' or 'V' for fruit and vegetable respectively.
     * @exception InvalidParameterException Thrown when the category specified is not a valid value.
     */
    public void setCategory(String category) {
        if (CATEGORY_TYPES.contains(category)) {
            this.category = category;
        } else {
            throw new InvalidParameterException("Invalid parameter value for 'category'. Please refer to docs for valid values");
        }
    }

    /**
     * Getter for the source of the product, i.e place of origin.
     * @return Origin of product.
     */
    public String getSource() {
        return source;
    }

    /**
     * Setter for the source of the product, i.e place of origin.
     * @param source Origin of product.
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Getter for sales mode. This is how the product is sold. There are two options. "Loose" for items that are
     * weighed and "Batch" for items that come in pre-packaged weights.
     * @return Sales mode of product.
     */
    public String getSalesMode() {
        return salesMode;
    }

    /**
     * Setter for sales mode. This is how the product is sold. There are two options. "Loose" for items that are
     * weighed and "Batch" for items that come in pre-packaged weights.
     * @param salesMode Sales mode of product.
     * @exception InvalidParameterException Thrown when the sales mode specified is not a valid value.
     */
    public void setSalesMode(String salesMode) {
        if (SALES_MODE_TYPES.contains(salesMode)) {
            this.salesMode = salesMode;
        } else {
            throw new InvalidParameterException("Invalid parameter value for 'salesMode'. Please refer to docs for valid values");
        }
    }

    /**
     * Getter for shelf life of product.
     * @return A list of two numbers. The first is the lower bound and the second is the upper bound of the shelf life.
     */
    public List<Integer> getShelfLife() {
        return shelfLife;
    }

    /**
     * Setter for shelf life of product.
     * @param shelfLife A list of two numbers. The first is the lower bound and the second is the upper bound of the shelf life.
     * @exception InvalidParameterException Thrown when the shelf life contains a number less than 0 or is not two numbers.
     */
    public void setShelfLife(List<Integer> shelfLife) {
        if (shelfLife.size() != 2) {
            throw new InvalidParameterException("shelfLife must contain exactly two numbers");
        }
        for (Integer i : shelfLife) {
            if (i < 0) {throw new InvalidParameterException("shelfLife cannot contain negative numbers");}
        }
        this.shelfLife = shelfLife;
    }

    /**
     * Returs a list of all the possible names of the product.
     * @return A List containing all the names.
     */
    public List<String> getAllNames() {
        List<String> names = new ArrayList<String>();
        names.add(this.getName());
        names.addAll(this.getAltNames());
        return names;
    }

    /**
     * Getter for price of the product.
     * @return Price of product.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Setter for price of the product.
     * @param price Price of product.
     * @exception InvalidParameterException Thrown when price is less than or equal to 0.
     */
    public void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) > 0) {
            this.price = price;
        } else {
            throw new InvalidParameterException("Price must be greater than 0. ");
        }

    }

}

