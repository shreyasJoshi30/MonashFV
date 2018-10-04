package MFVTest;

import controller.Inventory;
import entity.Item;
import entity.MFVConstants;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    @Test
    void randomTest() {
        Inventory i = TestCases.makeInventory();
        int j = 1;
    }

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
        List<UUID> found = i.findProductStock(TestCases.product1.getProductId());
        assertEquals(2, found.size());
        for (UUID tmp : found) {
            assertEquals(TestCases.product1.getName(), i.getItem(tmp).getName());
        }
    }

    @Test
    void reduceItemQty() {
        Inventory i2 = TestCases.makeInventory();
        String filename = "testInventoryFile";
        Inventory.writeInventoryToFile(i2, filename);
        Inventory i = Inventory.readInventoryFromFile(filename);
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

    @Test
    void inventoryIO() {
        Inventory i = TestCases.makeInventory();
        String filename = "testInventoryFile";
        Inventory.writeInventoryToFile(i, filename);
        Inventory i2 = Inventory.readInventoryFromFile(filename);
        List<UUID> expectedProducts = i.findItemsByState(MFVConstants.STOCKED);
        List<UUID> actualProducts = i2.findItemsByState(MFVConstants.STOCKED);
        assertTrue(actualProducts.containsAll(expectedProducts) && expectedProducts.containsAll(actualProducts));
        expectedProducts = i.findItemsByState(MFVConstants.SOLD);
        actualProducts = i2.findItemsByState(MFVConstants.SOLD);
        assertTrue(actualProducts.containsAll(expectedProducts) && expectedProducts.containsAll(actualProducts));
    }

    @Test
    void reduceQty() {
        Inventory i2 = TestCases.makeInventory();
        Item item = new Item(TestCases.product1, 10, Calendar.getInstance());
        i2.addItem(item);
        String filename = "testInventoryFile";
        Inventory.writeInventoryToFile(i2, filename);
        Inventory i = Inventory.readInventoryFromFile(filename);
        assertThrows(IllegalArgumentException.class, () -> {i.reduceQty(TestCases.product1.getProductId(), 100);});
        double expected = (i.getItem(TestCases.item1.getItemId()).getQty() + i.getItem(item.getItemId()).getQty()) - 1;
        i.reduceQty(TestCases.product1.getProductId(), 1);
        double actual = (i.getItem(TestCases.item1.getItemId()).getQty() + i.getItem(item.getItemId()).getQty());
        assertEquals(expected, actual);
        List<Pair<UUID, Double>> tmp = i.reduceQty(TestCases.product1.getProductId(), 60);
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


}