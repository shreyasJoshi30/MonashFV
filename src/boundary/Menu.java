package src.boundary;

import entity.MFVFileIO;
import entity.ProductList;

import java.math.BigDecimal;
import java.util.*;

public class Menu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

        ProductList pl = new ProductList();
        pl.addProduct("aaa", new ArrayList<String>(), "Fruit", "Here", Arrays.asList(1, 3),"Loose", new BigDecimal("4.99"));
        pl.addProduct("bbb", new ArrayList<String>(), "Fruit", "Here", Arrays.asList(1, 3),"Loose", new BigDecimal("4.99"));

        MFVFileIO fio = new MFVFileIO();
        String filename = "savefile";
        fio.writeObjectToFile(pl, filename);

        List<UUID> a = pl.searchProducts("aaa");
        System.out.println(pl.size());
        pl.removeProduct(a.get(0));
        System.out.println(pl.size());

        pl = (ProductList)fio.readObjectFromFile(filename);
        System.out.println(pl.size());

    }

}
