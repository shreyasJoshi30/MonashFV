package boundary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
System.out.println("Urmi");
		System.out.println("Hello! Welcome to Monash Fruit and Veggie Store");
		String token1 = "";
	    try {
			Scanner inFile1 = new Scanner(new File("user.txt"));
			//Scanner inFile12 = new Scanner(new File("user1.txt")).useDelimiter(",\\s*");
			List<String> temps = new ArrayList<String>();
			
			
			
			while (inFile1.hasNext()) {
			      // find next line
			      token1 = inFile1.next();
			      temps.add(token1);
			    }
			    inFile1.close();
			    //inFile12.close();
			    String[] tempsArray = temps.toArray(new String[0]);
			    
			    for (String s : tempsArray) {
			        System.out.println(s);
			      }
			    
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    
		
		
	}

}
