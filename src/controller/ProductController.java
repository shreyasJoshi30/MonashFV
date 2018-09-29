package controller;

import entity.MFVFileIO;
import entity.ProductList;
import entity.ProductProfile;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.util.*;

/**
 * This class provides functions to manage products.
 *
 * @author W0\/E|\| L|_|
 */
public class ProductController {

    /**
     * Contructor for ProductController.
     */
    public ProductController(){}

    /**
     * Add a product with the provided details.
     * @param products The ProductList object to add product to.
     * @param name Name of a product.
     * @param altNames List of alternative names of product.
     * @param category Category of product, Refer to ProductProfile documentation for options.
     * @param source Source of product.
     * @param shelfLife Shelf life of product.
     * @param salesMode Sales mode of product. Refer to ProductProfile documentation for options.
     * @param price Price of product.
     */
    public void addProduct(ProductList products, String name, List<String> altNames, String category,
                           String source, List<Integer> shelfLife, String salesMode, BigDecimal price){
        products.addProduct(name, altNames, category, source, shelfLife, salesMode, price);
    }

    /**
     * Edit the details of a product.
     * @param products The ProductList object work on.
     * @param productId ID of the product profile to obtain.
     * @param name Name of a product.
     * @param altNames List of alternative names of product.
     * @param category Category of product, Refer to ProductProfile documentation for options.
     * @param source Source of product.
     * @param shelfLife Shelf life of product.
     * @param salesMode Sales mode of product. Refer to ProductProfile documentation for options.
     * @param price Price of product.
     */
    public void editProduct(ProductList products, UUID productId, String name, List<String> altNames, String category,
                           String source, List<Integer> shelfLife, String salesMode, BigDecimal price){
        products.getProduct(productId).setName(name);
        products.getProduct(productId).setAltNames(altNames);
        products.getProduct(productId).setCategory(category);
        products.getProduct(productId).setSource(source);
        products.getProduct(productId).setShelfLife(shelfLife);
        products.getProduct(productId).setSalesMode(salesMode);
        products.getProduct(productId).setPrice(price);
    }

    /**
     * Gets a product profile.
     * @param products The ProductList object to work on.
     * @param productId ID of the product profile to obtain.
     * @return The desired product profile. Returns null if product ID is not found.
     */
    public ProductProfile getProduct(ProductList products, UUID productId) {
        return products.getProduct(productId);
    }

    /**
     * Removes a Product Profile. Does nothing if product ID is not found.
     * @param products The ProductList object to work on.
     * @param productId The ID of the Product Profile to be removed.
     */
    public void removeProduct(ProductList products, UUID productId){
        products.removeProduct(productId);
    }

    /**
     * Search for a product.
     * @param products The ProductList object to work on.
     * @param name The name to search.
     * @return A List containing the ID's of possible matches.
     */
    public List<UUID> searchProduct(ProductList products, String name) {
        return products.searchProducts(name);
    }


    /**
     * Writes a product list to a file.
     * @param products Product List object to write.
     * @param filename Name of file to write to.
     */
    public void writeProductListToFile(ProductList products, String filename) {
        MFVFileIO fio = new MFVFileIO();
        fio.writeObjectToFile(products, filename);
    }

    /**
     * Reads a product list from a file.
     * @param filename Name of file to read from.
     * @return Stored Product List object.
     */
    public ProductList readProductListFromFile(String filename) {
        MFVFileIO fio = new MFVFileIO();
        return (ProductList)fio.readObjectFromFile(filename);
    }

    /**
     * Get the name and ID of all products.
     * @param products Product List object to write.
     * @return A list of the ID and name of all products in alphabetical order.
     */
    public List<Pair<UUID, String>> getAllProducts(ProductList products) {
        List<Pair<UUID, String>> allProducts = products.getAllProducts();
        allProducts.sort(Comparator.comparing(Pair::getValue, (p1, p2) -> p1.compareTo(p2)));
        //Collections.sort(allProducts, Comparator.comparing((p1, p2) -> p);
        return allProducts;
    }

}