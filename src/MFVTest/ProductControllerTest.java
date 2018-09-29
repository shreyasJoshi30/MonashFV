package MFVTest;

import controller.ProductController;
import entity.MFVFileIO;
import entity.ProductList;
import entity.ProductProfile;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    @Test
    void productListIO() {
        ProductList pl = TestCases.makeProductList();
        ProductController pc = new ProductController();
        String filename = "testProductFile";
        pc.writeProductListToFile(pl, filename);
        ProductList pl2 = pc.readProductListFromFile(filename);
        List<Pair<UUID, String>> expectedProducts = pl.getAllProducts();
        List<Pair<UUID, String>> actualProducts = pl2.getAllProducts();
        assertTrue(expectedProducts.containsAll(expectedProducts) && expectedProducts.containsAll(actualProducts));
    }

    @Test
    void getAllProducts() {
        ProductList pl = TestCases.makeProductList();
        ProductController pc = new ProductController();
        List<Pair<UUID, String>> allProducts = pc.getAllProducts(pl);
        System.out.println("All products are: ");
        for (Pair<UUID, String> tmp : allProducts) {
            System.out.println(tmp.getValue());
        }
    }
}