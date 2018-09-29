package MFVTest;

import controller.InventoryController;
import entity.Inventory;
import entity.Item;
import entity.MFVConstants;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InventoryControllerTest {

    @Test
    void reduceQty() {
        InventoryController ic = new InventoryController();
        Inventory i2 = TestCases.makeInventory();
        Item item = new Item(TestCases.product1, 10, Calendar.getInstance());
        i2.addItem(item);
        String filename = "testInventoryFile";
        ic.writeInventoryToFile(i2, filename);
        Inventory i = ic.readInventoryFromFile(filename);
        assertThrows(IllegalArgumentException.class, () -> {ic.reduceQty(i, TestCases.product1.getProductId(), 100);});
        double expected = (i.getItem(TestCases.item1.getItemId()).getQty() + i.getItem(item.getItemId()).getQty()) - 1;
        ic.reduceQty(i, TestCases.product1.getProductId(), 1);
        double actual = (i.getItem(TestCases.item1.getItemId()).getQty() + i.getItem(item.getItemId()).getQty());
        assertEquals(expected, actual);
        List<Pair<UUID, Double>> tmp = ic.reduceQty(i, TestCases.product1.getProductId(), 60);
        assertFalse(i.getItem(TestCases.item1.getItemId()).getState().equals(i.getItem(item.getItemId()).getState()));
        assertTrue(i.getItem(TestCases.item1.getItemId()).getState().equals(MFVConstants.SOLD) ||
                i.getItem(item.getItemId()).getState().equals(MFVConstants.SOLD));
        assertTrue(i.getItem(TestCases.item1.getItemId()).getQty() == 0 ||
                i.getItem(item.getItemId()).getQty() == 0);
        expected = 60;
        actual = 0;
        for (Pair<UUID, Double> p : tmp) {
            actual += p.getValue();
        }
        assertEquals(expected, actual);
        expected = 56.78 + 10 - 1 - 60;
        actual = 0;
        for (Pair<UUID, Double> p : tmp) {
            actual += i.getItem(p.getKey()).getQty();
        }
        assertEquals(expected, actual);
    }

    @Test
    void inventoryIO() {
        Inventory i = TestCases.makeInventory();
        InventoryController ic = new InventoryController();
        String filename = "testInventoryFile";
        ic.writeInventoryToFile(i, filename);
        Inventory i2 = ic.readInventoryFromFile(filename);
        List<UUID> expectedProducts = i.findItemsByState(MFVConstants.STOCKED);
        List<UUID> actualProducts = i2.findItemsByState(MFVConstants.STOCKED);
        assertTrue(expectedProducts.containsAll(expectedProducts) && expectedProducts.containsAll(actualProducts));
        expectedProducts = i.findItemsByState(MFVConstants.SOLD);
        actualProducts = i2.findItemsByState(MFVConstants.SOLD);
        assertTrue(expectedProducts.containsAll(expectedProducts) && expectedProducts.containsAll(actualProducts));
    }
}