package controller;

import entity.MFVConstants;
import entity.MFVFileIO;
import entity.ProductProfile;
import javafx.util.Pair;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * Implements the Product List which stores all of the products the store manages.
 * It also provides functions to manage it.
 *
 * W0\/E|\| L|_|
 */
public class ProductList implements Serializable {

    private LinkedHashMap<UUID, ProductProfile> products;
    private static final int MIN_EDIT_DISTANCE = 1;
    private static final double EDIT_LENGTH_PERCENTAGE = 0.30;

    /**
     * Constructor for the Product List. This initializes the list.
     */
    public ProductList(){
        products = new LinkedHashMap<UUID, ProductProfile>();
    }

    /**
     * Adds a product to the list with the provided details.
     * @param name Name of a product.
     * @param altNames List of alternative names of product.
     * @param category Category of product, Refer to ProductProfile documentation for options.
     * @param source Source of product.
     * @param shelfLife Shelf life of product.
     * @param salesMode Sales mode of product. Refer to ProductProfile documentation for options.
     * @param price Price of product.
     */
    public void addProduct(String name, List<String> altNames, String category, String source,
                           List<Integer> shelfLife, String salesMode, BigDecimal price){
        ProductProfile product = new ProductProfile(name, altNames, category, source, shelfLife, salesMode, price);
        this.products.put(product.getProductId(), product);
    }

    /**
     * Edit the details of a product.
     * @param productId ID of the product profile to obtain.
     * @param name Name of a product.
     * @param altNames List of alternative names of product.
     * @param category Category of product, Refer to ProductProfile documentation for options.
     * @param source Source of product.
     * @param shelfLife Shelf life of product.
     * @param salesMode Sales mode of product. Refer to ProductProfile documentation for options.
     * @param price Price of product.
     */
    public void editProduct(UUID productId, String name, List<String> altNames, String category,
                            String source, List<Integer> shelfLife, String salesMode, BigDecimal price){
        this.getProduct(productId).setName(name);
        this.getProduct(productId).setAltNames(altNames);
        this.getProduct(productId).setCategory(category);
        this.getProduct(productId).setSource(source);
        this.getProduct(productId).setShelfLife(shelfLife);
        this.getProduct(productId).setSalesMode(salesMode);
        this.getProduct(productId).setPrice(price);
    }

    /**
     * Adds a product to the list with the provided details. This is for when the ProductProfile object has been created already.
     * @param product The ProductProfile object to add.
     */
    public void addProduct(ProductProfile product){
        this.products.put(product.getProductId(), product);
    }

    /**
     * Gets a product profile from the list.
     * @param productId ID of the product profile to obtain.
     * @return The desired product profile. Returns null if key is not found.
     */
    public ProductProfile getProduct(UUID productId) {
        return this.products.get(productId);
    }

    /**
     * Removes a Product Profile from the Product List.
     * @param productId The ID of the Product Profile to be removed. Does nothing if key is not found.
     */
    public void removeProduct(UUID productId){
        this.products.remove(productId);
    }

    /**
     * Searches the Product List for a given name.
     * @param name The name to search.
     * @return A List containing the ID's of possible matches. Will be empty if nothing matches.
     */
    public List<UUID> searchProducts(String name) {
        List<UUID> found = new ArrayList<UUID>();
        for(UUID productId : this.products.keySet()){
            if (nameListMatch(name, this.products.get(productId).getAllNames())) {
                found.add(productId);
            }
        }
        return found;
    }



    /**
     * This searches a list of names for a match to the target name.
     * @param name Target name to search.
     * @param listNames List of names to search.
     * @return Whether there is a match for the target name.
     */
    private static boolean nameListMatch(String name, List<String> listNames) {
        for (String tmp : listNames) {
            if (namesSimilar(tmp, name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tells whether two names are referring to the same product. This is occurs when there is a word in the search input
     * and the product name that is similar. The exception is if it is a phrase like "(Bag)" which describes how it's
     * sold and these words will be ignored.
     * @param productName Name of the product (as stored in the ProductProfile)
     * @param userInput User search input
     * @return Whether the two provided names are similar.
     */
    private static boolean namesSimilar(String productName, String userInput) {
        String[] s1 = productName.toUpperCase().split("\\s+");
        String[] s2 = userInput.toUpperCase().split("\\s+");
        for (String word1 : s1) {
            for (String word2 : s2) {
                if (!word1.startsWith(MFVConstants.PRODUCT_SEARCH_IGNORE_CHARACTER)) {
                    if (stringSimilarity(word1, word2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Uses edit distance to determine if two strings are similar.
     * @return True if they are similar.
     */
    private static boolean stringSimilarity(String n1, String n2) {
        int minLen = Math.min(n1.length(), n2.length());
        int minEdit = Math.max(MIN_EDIT_DISTANCE, (int)Math.ceil(minLen * EDIT_LENGTH_PERCENTAGE)); //Calculate min edit distance to be similar. Is larger for longer strings.
        if (editDistance(n1, n2) <= minEdit) {
            return true;
        }
        return false;
    }

    /**
     * Calculates edit distance
     * @author gabhi/gist:11243437
     */
    private static int editDistance(String s1, String s2){
        int edits[][]=new int[s1.length()+1][s2.length()+1];
        for(int i=0;i<=s1.length();i++)
            edits[i][0]=i;
        for(int j=1;j<=s2.length();j++)
            edits[0][j]=j;
        for(int i=1;i<=s1.length();i++){
            for(int j=1;j<=s2.length();j++){
                int u=(s1.charAt(i-1)==s2.charAt(j-1)?0:1);
                edits[i][j]=Math.min(
                        edits[i-1][j]+1,
                        Math.min(
                                edits[i][j-1]+1,
                                edits[i-1][j-1]+u
                        )
                );
            }
        }
        return edits[s1.length()][s2.length()];
    }

    /**
     * Return the number of product profiles stored.
     * @return Number of product profiles.
     */
    public int size() {
        return this.products.size();
    }

    /**
     * Get the name and ID of all products.
     * @return A list of the ID and name of all products. This will be sorted in alphabetical order.
     */
    public List<Pair<UUID, String>> getAllProducts() {
        List<Pair<UUID, String>> allProducts = new ArrayList<Pair<UUID, String>>();
        for(UUID productId : this.products.keySet()){
            ProductProfile tmp = this.products.get(productId);
            Pair<UUID, String> p = new Pair<UUID, String>(tmp.getProductId(), tmp.getName());
            allProducts.add(p);
        }
        allProducts.sort(Comparator.comparing(Pair::getValue, (p1, p2) -> p1.compareTo(p2)));
        return allProducts;
    }

    /**
     * Writes a product list to a file. Used in conjunction with 'readProductListFromFile'.
     * @param products Product List object to write.
     * @param filename Name of file to write to.
     */
    public static void writeProductListToFile(ProductList products, String filename) {
        MFVFileIO fio = new MFVFileIO();
        fio.writeObjectToFile(products, filename);
    }

    /**
     * Reads a product list from a file. Used in conjunction with 'writeProductListToFile'. File must be written by that function to succeed.
     * @param filename Name of file to read from.
     * @return Stored Product List object.
     */
    public static ProductList readProductListFromFile(String filename) {
        MFVFileIO fio = new MFVFileIO();
        return (ProductList)fio.readObjectFromFile(filename);
    }

}
