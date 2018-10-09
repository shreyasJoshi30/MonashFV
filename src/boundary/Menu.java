package boundary;

import java.io.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.UUID;

import controller.ProductList;
import controller.ShoppingController;
import javafx.util.Pair;

public class Menu {
	
	
	public static void main(String[] args)
    {
        String filename = "members.txt";
        try
        {
        	//Dummy Inputs
        	
        	
        	String customers="shreyas";
        	List<Pair<UUID, Double>> items = new ArrayList<Pair<UUID, Double>>();
        	List<Pair<UUID, String>> allProducts = new ArrayList<Pair<UUID, String>>();
			  String deliveryMethod="card";
			  String destAddress ="monash";
			  String paymentMethod= "card";
			  String paymentDetails="8520852085208520-607";
			  Boolean paymentConfirmed=false;
			  /*items.add(new Pair<UUID, Double>(UUID.randomUUID(), 1.5));
			  items.add(new Pair<UUID, Double>(UUID.randomUUID(), 1.9));*/
			  
			  allProducts.add(new Pair<UUID, String>(UUID.randomUUID(), "MANGO"));
			  allProducts.add(new Pair<UUID, String>(UUID.randomUUID(), "APPLE"));
			  
			  //-------------------------
        	Scanner sc = new Scanner(System.in);
        	ShoppingController s= new ShoppingController();
        	//Browsing Products
        	
        	ProductList prodList = new ProductList();
        	//List<Pair<UUID, String>> allProducts = prodC.getAllProducts(prodList);  UNCOMMENT THIS!!!
        	
        	ListIterator<Pair<UUID,String>> itrAllProds = allProducts.listIterator();
  	      
  	      while(itrAllProds.hasNext()) {
  	    	Pair<UUID,String> element = itrAllProds.next();
  	         System.out.println(itrAllProds.previousIndex() + " " + element.getValue());
  	      }
  	      
  	      System.out.println("Enter product number to add to cart");
      	int prodIndex = sc.nextInt();  	
        	
        	
        	System.out.println("Enter quantity to add product\n");
    		double qty = sc.nextDouble();
    	
    		
    		s.addProduct(allProducts.get(prodIndex).getKey(), qty);
    		System.out.println("Product added\n");
    		
    		System.out.println("Here are your cart products\n");
    		List<Pair<UUID,Double>> cartItems = s.getCartProducts();
    		Iterator<Pair<UUID,Double>> itr = cartItems.iterator();
    	      
    	      while(itr.hasNext()) {
    	         Object element = itr.next();
    	         System.out.print(element + " ");
    	      }
        	
        	
        	
        	System.out.println("press one to chekout");
        	int n = sc.nextInt();
        	if(n==1) {
        		
        		s.checkout(customers,cartItems,deliveryMethod,destAddress,paymentMethod,paymentDetails,paymentConfirmed);
        	}
        	
            FileReader inputFile = new FileReader(filename);
            try 
            {
                Scanner fileReader = new Scanner(inputFile);
                int memberIndex = 0;
                while (fileReader.hasNextLine())
                {
                    String line = fileReader.nextLine();                     
                    String lineArray[] = line.split(",");
                    System.out.println(lineArray[0]);
                    System.out.println(lineArray[1]);
                    //addAccount(lineArray[0]);
                    //getTeams().get(teamIndex).setRanking(Integer.valueOf(lineArray[1]));
                    memberIndex++;
                }
            }
            finally
            {
                inputFile.close();
            }
        }
        catch(FileNotFoundException exception)
        {
            System.out.println(filename + " not found");
        }        
        catch(IOException exception)
        {            
            System.out.println("Unexpected I/O error");
        }
    }

}
