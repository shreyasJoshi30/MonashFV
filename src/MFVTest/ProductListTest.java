package MFVTest;

import controller.ProductList;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductListTest {

    @Test
    void getProduct() {
        ProductList p = new ProductList();
        assertEquals(null, p.getProduct(UUID.randomUUID()));
        System.out.println("Success");
    }

    private void searchForProductTest(ProductList products, String searchTerm) {
        System.out.println("--- Searching for: " + searchTerm);
        List<String> hits = new ArrayList<String>();
        List<UUID> tmp = products.searchProducts(searchTerm);
        for (UUID id : tmp) {
            hits.add(products.getProduct(id).getName());
        }
        System.out.println("Found:" );
        for (String s : hits) {
            System.out.println(s);
        }
    }

    @Test
    void searchProducts() {
        ProductList p = TestCases.makeProductList();
        searchForProductTest(p, "ipe");
        searchForProductTest(p, "ie");
        searchForProductTest(p, "ssage");
        searchForProductTest(p, "SAUSAGE");
        searchForProductTest(p, "pitato");
        searchForProductTest(p, "uice");
        searchForProductTest(p, "hole");
        searchForProductTest(p, "KrAB");
        searchForProductTest(p, "gooogosdgjois ssage");
        searchForProductTest(p, "gooogosdgjois  ssage");
        searchForProductTest(p, "A2");
    }

    @Test
    void productListIO() {
        ProductList pl = TestCases.makeProductList();
        String filename = "testProductFile";
        ProductList.writeProductListToFile(pl, filename);
        ProductList pl2 = ProductList.readProductListFromFile(filename);
        List<Pair<UUID, String>> expectedProducts = pl.getAllProducts();
        List<Pair<UUID, String>> actualProducts = pl2.getAllProducts();
        assertTrue(actualProducts.containsAll(expectedProducts) && expectedProducts.containsAll(actualProducts));
    }

    @Test
    void getAllProducts() {
        ProductList p = TestCases.makeProductList();
        List<Pair<UUID, String>> allProducts = p.getAllProducts();
        System.out.println("All products are: ");
        for (Pair<UUID, String> tmp : allProducts) {
            System.out.println(tmp.getValue());
        }
    }
}