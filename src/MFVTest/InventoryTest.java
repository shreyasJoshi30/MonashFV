package MFVTest;

import controller.InventoryController;
import entity.Inventory;
import entity.Item;
import entity.MFVConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    @Test
    void getItem() {
        Inventory i = new Inventory();
        assertEquals(null, i.getItem(UUID.randomUUID()));
        System.out.println("Success");
    }

    @Test
    void findItemsByState() {
        Inventory i = TestCases.makeInventory();
        assertTrue(i.findItemsByState("lksadfjiwe").size() == 0);
        assertTrue(i.findItemsByState(MFVConstants.STOCKED).size() == 5);
        System.out.println("Success");
    }

    @Test
    void findStockedProduct() {
        Inventory i = TestCases.makeInventory();
        Item item = new Item(TestCases.product1, 123, Calendar.getInstance());
        i.addItem(item);
        List<UUID> found = i.findStockedProduct(TestCases.product1.getProductId());
        assertEquals(2, found.size());
        for (UUID tmp : found) {
            assertEquals(TestCases.product1.getName(), i.getItem(tmp).getName());
        }
    }

    @Test
    void reduceItemQty() {
        Inventory i2 = TestCases.makeInventory();
        InventoryController ic = new InventoryController();
        String filename = "testInventoryFile";
        ic.writeInventoryToFile(i2, filename);
        Inventory i = ic.readInventoryFromFile(filename);
        UUID item1Id = TestCases.item1.getItemId();
        UUID item2Id = TestCases.item2.getItemId();
        Executable tmp = () -> {i.reduceItemQty(item1Id, -5);};
        assertThrows(InvalidParameterException.class, tmp);
        tmp = () -> {i.reduceItemQty(item1Id, Double.NaN);};
        assertThrows(InvalidParameterException.class, tmp);
        tmp = () -> {i.reduceItemQty(item1Id, Double.POSITIVE_INFINITY);};
        assertThrows(InvalidParameterException.class, tmp);
        tmp = () -> {i.reduceItemQty(item1Id, Double.NEGATIVE_INFINITY);};
        assertThrows(InvalidParameterException.class, tmp);
        tmp = () -> {i.reduceItemQty(item2Id, 2.178);};
        assertThrows(InvalidParameterException.class, tmp);
        tmp = () -> {i.reduceItemQty(item1Id, 123.345);};
        assertThrows(IllegalArgumentException.class, tmp);
        tmp = () -> {i.reduceItemQty(item2Id, 123456);};
        assertThrows(IllegalArgumentException.class, tmp);
        i.reduceItemQty(item1Id, 1);
        assertEquals(55.78, i.getItem(item1Id).getQty());
        i.reduceItemQty(item2Id, 1);
        assertEquals(4, i.getItem(item2Id).getQty());
        i.reduceItemQty(item1Id, 55.78);
        assertEquals(MFVConstants.SOLD, i.getItem(item1Id).getState());
        i.reduceItemQty(item2Id, 4);
        assertEquals(MFVConstants.SOLD, i.getItem(item2Id).getState());
    }

    @Test
    void findItemsByStateAndDate() {
        Inventory i = TestCases.makeInventory();
        Calendar t1 = Calendar.getInstance();
        t1.add(Calendar.DATE, 1);
        Calendar t2 = Calendar.getInstance();
        t2.add(Calendar.DATE, 2);
        Calendar y1 = Calendar.getInstance();
        y1.add(Calendar.DATE, -1);
        assertTrue(i.findItemsByStateAndDate(y1, t1, MFVConstants.SOLD).size() == 1);
        assertTrue(i.findItemsByStateAndDate(t1, t2, MFVConstants.SOLD).size() == 0);
        System.out.println("Success");
    }
}