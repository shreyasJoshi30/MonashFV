package MFVTest;

import com.sun.xml.internal.ws.developer.Serialization;
import entity.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class TestCases {
    public static final String[] randomStrings = {"toe", "dog dumping its phone on ma lawn", ""};
    public static final String[] salesModesStrings = {MFVConstants.LOOSE, MFVConstants.BATCH};
    public static final String[] categoryStrings = {MFVConstants.FRUIT, MFVConstants.VEG};
    public static final String[] itemStateStrings = {MFVConstants.STOCKED, MFVConstants.SOLD, MFVConstants.DISCARDED, MFVConstants.CHARITY};
    public static ProductProfile product1 = new ProductProfile("Sausage Pie", Arrays.asList("a2", "a3"), MFVConstants.FRUIT,
            "Here", Arrays.asList(1, 3), MFVConstants.LOOSE, new BigDecimal("4.99"));
    public static ProductProfile product2 = new ProductProfile("Squeezed Crab", new ArrayList<String>(), MFVConstants.VEG,
            "Here", Arrays.asList(3, 5), MFVConstants.BATCH, new BigDecimal("4.99"));
    public static ProductProfile product3 = new ProductProfile("Sausage Pie (Whole)", new ArrayList<String>(), MFVConstants.VEG,
            "Here", Arrays.asList(3, 5), MFVConstants.BATCH, new BigDecimal("4.99"));
    public static ProductProfile product4 = new ProductProfile("Throwing Apples", Arrays.asList("adf2", "afdfd3"), MFVConstants.VEG,
            "Here", Arrays.asList(1, 3), MFVConstants.LOOSE, new BigDecimal("3.99"));
    public static ProductProfile product5 = new ProductProfile("Potato Juice", Arrays.asList("abc2", "asda3"), MFVConstants.VEG,
            "Here", Arrays.asList(1, 3), MFVConstants.LOOSE, new BigDecimal("3.99"));
    public static Item item1 = new Item(product1, 56.78, Calendar.getInstance());
    public static Item item2 = new Item(product2, 5, Calendar.getInstance());
    public static Item item3 = new Item(product3, 10, Calendar.getInstance());
    public static Item item4 = new Item(product4, 9.21, Calendar.getInstance());
    public static Item item5 = new Item(product5, 14.70, Calendar.getInstance());

    public static ProductList makeProductList(){
        ProductList p = new ProductList();
        p.addProduct(TestCases.product1);
        p.addProduct(TestCases.product2);
        p.addProduct(TestCases.product3);
        p.addProduct(TestCases.product4);
        p.addProduct(TestCases.product5);
        return p;
    }

    public static Inventory makeInventory(){
        Inventory i = new Inventory();
        i.addItem(TestCases.item1);
        i.addItem(TestCases.item2);
        i.addItem(TestCases.item3);
        i.addItem(TestCases.item4);
        i.addItem(TestCases.item5);
        Item item = new Item(TestCases.product1, 14.70, Calendar.getInstance());
        item.setState(MFVConstants.SOLD);
        item.setEndOfLifeDate(Calendar.getInstance());
        i.addItem(item);
        return i;
    }

}
