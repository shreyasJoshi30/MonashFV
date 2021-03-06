package boundary;

import controller.*;
import entity.*;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.String.format;

public class Menu
{
    private String loginUsername;
    private UserList userList;
    private ReporterController reporterController;
    private Inventory inventory;
    private ShoppingController shoppingController; 
    private ProductList productList;
    private OrderList orderList;
    private static final int LIST_SIZE = 10;
    private final String inventoryFilename = "inventoryFile";
    private final String productListFilename = "productListFile";
    private final String orderListFilename = "orders.out";
    private boolean nextPageGotten;
    private String menuIndex;

    private void setMenuIndex(String x){
        this.menuIndex = x;
        this.nextPageGotten = true;
    }

    public static void main(String[] args){
        Menu m = new Menu();
        m.userInterface();
    }

    //Constructor for Menu
    public Menu()
    {
        this.loginUsername = "";
        this.userList = new UserList();
        this.reporterController = new ReporterController();
        this.inventory = new Inventory();
        try {
            this.inventory.readInventoryFromFile(inventoryFilename);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.productList = new ProductList();
        try {
            this.productList.readProductListFromFile(this.productListFilename);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.shoppingController = new ShoppingController();
        this.orderList = new OrderList();
        try {
            this.orderList.readOrderFromFile(this.orderListFilename);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.nextPageGotten = false;
        this.menuIndex = "!";
    }

    public void userInterface()
    {
        Scanner system = new Scanner(System.in);
        do 
        {
            switch (this.menuIndex)
            {
                case "!": home(); break;
                case "A": homeUser(); break;
                case "A1": homeUserLogin(); break;
                case "A2": homeUserMemberSignup(); break;
                case "A3": homeUserChangePassword(); break;
                case "A4": homeUserChangeEmail(); break;
                case "A5": homeUserChangePhoneNumber(); break;
                case "A6": homeUserCurrentUnregister(); break;
                case "A7": homeUserLogout(); break;
                case "A8": homeUserOwnerSignup(); break;
                case "A9": homeUserViewUserlist(); break;
                case "A10": homeUserUnregisterUser(); break;
                case "B": browseProducts(); break;
                case "B1": customerAddProduct(); break;
                case "B2": customerSearchProduct(); break;
                case "C": homeShoppingCart(); break;
                case "C1": homeShoppingCartChangeCart(); break;
                case "C2": homeShoppingCartCheckout(); break;
                case "D": homeManageProfile(); break;
                case "D1": homeManageProfileAddProfile(); break;
                case "D2": homeManageProfileViewProfile(); break;
                case "E": homeManageInventory(); break;
                case "E1": homeManageInventoryAddInventory(); break;
                case "E2": homeManageInventoryViewInventory(); break;
                case "F" : homeReports();break;
                case "F1" :homeSalesReport();break;
                case "F2" :homeDeliveryReport();break;
                case "F3" :homeOrderReport();break;
                case "X": break;
            }
            System.out.println("Press ! Back to homepage");
            if (this.nextPageGotten) {
                this.nextPageGotten = false;
            } else {
                this.menuIndex = system.nextLine().toUpperCase().trim();
            }
        }while (!this.menuIndex.equals("X"));
        closeProgram();
    }



    /*This function takes a list of strings and lets the user select one of them. The user can scroll and move through
    all the options. It returns and the index of the selected object. It returns -1 if the user chooses not to select anything.
     */
	private int selectionList(List<String> list, String pageName) {
	    //Initialise variables to keep track of the current selection.
        int groupCounter = 0;
        int groupMax = (int)Math.ceil((double)list.size() / LIST_SIZE);
        int screenCounter = 0;
        int currentScreenMax = Math.min(list.size(), LIST_SIZE);
        String move = "";
        Scanner system = new Scanner(System.in);
        int selection = -1;
        boolean selectionMade = false;
        while(!selectionMade){
            //display list
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println(pageName + "\n");
            for (int i = 0; i < LIST_SIZE; i++) {
                if (i < currentScreenMax) {
                    if (i == screenCounter) {System.out.print(">"); } else {System.out.print(" ");}
                    System.out.println(list.get((groupCounter * LIST_SIZE) + i));
                } else {
                    System.out.println("");
                }
            }
            System.out.println("Page: " + (groupCounter + 1) + "/" + (groupMax));
            System.out.println("Type w-a-s-d to navigate. q to quit. e to select.");
            //get input and parse it
            move = system.nextLine().toUpperCase().trim();
            switch (move) {
                case "W": screenCounter = (screenCounter - 1) % currentScreenMax; break;
                case "S": screenCounter = (screenCounter + 1) % currentScreenMax; break;
                case "A": groupCounter = (groupCounter - 1) % groupMax; break;
                case "D": groupCounter = (groupCounter + 1) % groupMax; break;
                case "Q": selection = -1; selectionMade = true; break;
                case "E": selection = groupCounter * LIST_SIZE + screenCounter; selectionMade = true; break;
            }
            // Crap to get counters working properly
            if (groupCounter < 0) {groupCounter += groupMax;}
            if(groupMax * LIST_SIZE == list.size()) {
                currentScreenMax = LIST_SIZE;
            } else if (groupCounter == (groupMax - 1))  {
                currentScreenMax = list.size() - (LIST_SIZE * (groupMax - 1));
            } else {
                currentScreenMax = Math.min(list.size(), LIST_SIZE);
            }
            screenCounter = Math.min(screenCounter, currentScreenMax - 1);
            if (screenCounter < 0) {screenCounter += currentScreenMax;}
        }
        return selection;
    }

    // checks input is a non negative number
    private boolean validateNonNegativeNumber(double x) {
        return (!Double.isNaN(x) && Double.isFinite(x) && x >= 0);
    }

    public void closeProgram()
    {
        try {
            this.inventory.writeInventoryToFile(this.inventoryFilename);
            this.productList.writeProductListToFile(this.productListFilename);
            this.orderList.writeOrderToFile(this.orderListFilename);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("See you next time !");
    }

    public void home()
    {
        System.out.println("");
        System.out.println("Welcome to Monash FV Store.");
        System.out.println("");
        System.out.println("Press A. Account");
        System.out.println("Press B. Browse our products");
        System.out.println("Press C. View your shopping");      
        if (userList.isOwnerLogin(loginUsername))
        {
            System.out.println("Press D. Manage product profiles");
            System.out.println("Press E. Manage inventories");
            System.out.println("Press F. View sales report");        
        }
        System.out.println("Press X: To Exit");
        System.out.println("");
    }

    //option A
    public void homeUser()
    {
        System.out.println("");
        if (!userList.isMemberLogin(loginUsername) && !userList.isOwnerLogin(loginUsername))
        {
            System.out.println("Press A1. Log in");
            System.out.println("Press A2. Create your account");
        }
        else
        {
            System.out.println("Press A3. Change password");
            System.out.println("Press A4. Change Email");
            System.out.println("Press A5. Change Phone Number");
            System.out.println("Press A6. Unregister your account");
            System.out.println("Press A7. Logout");   
            System.out.println("");
        }
        if (userList.isOwnerLogin(loginUsername))
        {
            System.out.println("Owner's authority");
            System.out.println("Press A8 Register owner account");
            System.out.println("Press A9 View Userlist");
            System.out.println("Press A10 Unregister user");
        }
        System.out.println("");
    }

    //option A1
    public void homeUserLogin()
    {
        if (!Util.checkNullEmptyStr(loginUsername))
        {
            System.out.println("This is Log in page");        
            Scanner system = new Scanner(System.in); 
            System.out.print("Please enter your username: ");        
            String enterUsername = system.nextLine().trim();
            System.out.print("Please enter your password: ");
            String enterPassword = system.nextLine().trim();
            if (userList.userLogin(enterUsername, enterPassword))
            {
                loginUsername = enterUsername;
                System.out.println("");
                System.out.println("Successful");
                this.setMenuIndex("!");
            }
            else
            {
                System.out.println("");
                System.out.println("Error! Please try again.");
                this.setMenuIndex("A1");

            }
        }    
        else 
        {
            System.out.println("");
            System.out.println("Error! You have logged in.");
            this.setMenuIndex("A");
        }
    }

    //option A2
    public void homeUserMemberSignup()
    {
        if (!Util.checkNullEmptyStr(loginUsername))
        {
            String username = userInputText("Please enter your username: ");
            String password = userInputText("Please enter your password: ");
            String firstName = userInputText("Please enter your first name: ");
            String lastName = userInputText("Please enter your last name: ");
            System.out.println("Please enter your date of birth: ");
            Calendar dob = inputDate(1918, 2100);
            String phoneNumber = inputDigits(10, "Please enter your phone number: ");
            String email = userInputText("Please enter your email: ");
            if (userList.registerMember(username, password, firstName, lastName, dob, 
                phoneNumber, email))
            {
                System.out.println("");
                System.out.println("Thank you for joining with us.");
                this.setMenuIndex("A");
            }
            else
            {
                System.out.println("");
                System.out.println("Error! Please try again.");
                this.setMenuIndex("A2");
            }
        }
        else 
        {
            System.out.println("");
            System.out.println("Error! You have logged in.");
            this.setMenuIndex("A");
        }
    }

    //Gets and validates strings that are input for the user functions, i.e A functions.
    private String userInputText(String command){
        System.out.print(command);
        Scanner system = new Scanner(System.in);
        boolean empty = true;
        boolean noComma = true;
        String name = system.nextLine().trim();            
        while(empty) {
            if (name.equals("")) {
                System.out.println("WHAT THAT!?!?!?!?!?");
                name = system.nextLine().trim();
            } else {
                empty = false;
            }
            for (int index = 0; index < name.length(); index++)
            {            	
            	if(name.charAt(index) == ',')
            	{
            		System.out.println("No ',' can be used. Try again!");
            		name = system.nextLine().trim();
            		index = 0;
            		empty = true;
            	}            		
            }
            noComma = false;
        }        
        return name;
    }

    //option A3
    public void homeUserChangePassword()
    {
        if (userList.isMemberLogin(loginUsername) || userList.isOwnerLogin(loginUsername))
        {
            Scanner system = new Scanner(System.in); 
            String newPassword = userInputText("Please enter your new password: ");
            if (userList.changePassword(loginUsername, newPassword))
            {
                System.out.println("");
                System.out.println("Your password has been changed.");
                this.setMenuIndex("A");
            }
            else
            {
                System.out.println("");
                System.out.println("Error! Please try again.");
                this.setMenuIndex("A3");
            }    
        }
        else 
        {
            System.out.println("");
            System.out.println("Invalid !");
            this.setMenuIndex("A");
        }
    }

    //option A4
    public void homeUserChangeEmail()
    {
        if (userList.isMemberLogin(loginUsername) || userList.isOwnerLogin(loginUsername))
        {
            Scanner system = new Scanner(System.in); 
            String newEmail = userInputText("Please enter your new email: ");
            if (userList.changeEmail(loginUsername, newEmail))
            {
                System.out.println("");
                System.out.println("Your email has been changed.");
                this.setMenuIndex("A");
            }
            else
            {
                System.out.println("");
                System.out.println("Error! Please try again.");
                this.setMenuIndex("A4");
            }   
        }
        else 
        {
            System.out.println("");
            System.out.println("Invalid !");
            this.setMenuIndex("A");
        }
    }

    //option A5
    public void homeUserChangePhoneNumber()
    {
        if (userList.isMemberLogin(loginUsername) || userList.isOwnerLogin(loginUsername))
        {
            Scanner system = new Scanner(System.in); 
            String newPhoneNumber = inputDigits(10, "Please enter your new phone number: ");
            if (userList.changePhoneNumber(loginUsername, newPhoneNumber))
            {
                System.out.println("");
                System.out.println("Your phone number has been changed.");
                this.setMenuIndex("A");
            }
            else
            {
                System.out.println("");
                System.out.println("Error! Please try again.");
                this.setMenuIndex("A5");
            }   
        }
        else 
        {
            System.out.println("");
            System.out.println("Invalid !");
            this.setMenuIndex("A");
        }
    }

    //option A6
    public void homeUserCurrentUnregister()
    {
        if (userList.isMemberLogin(loginUsername) || userList.isOwnerLogin(loginUsername))
        {
            if (userList.unregisterUser(loginUsername))
            {
                System.out.println("");
                System.out.println("Your account has been deleted.");
                this.setMenuIndex("A7");
            }
            else
            {
                System.out.println("");
                System.out.println("Error! Please try again.");
                this.setMenuIndex("A");
            }   
        }
        else 
        {
            System.out.println("");
            System.out.println("Invalid !");
            this.setMenuIndex("A");
        }
    }

    //option A7
    public void homeUserLogout()
    {
        if (userList.userLogout(loginUsername))
        {
            loginUsername = "";
            System.out.println("");
            System.out.println("Log out successfully.");
            this.setMenuIndex("A");
        }
        else
        {
            System.out.println("");
            System.out.println("Invalid !");
            this.setMenuIndex("A");
        }        
    }

    //option A8
    public void homeUserOwnerSignup()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
        	 String username = userInputText("Please enter your username: ");
             String password = userInputText("Please enter your password: ");
             String firstName = userInputText("Please enter your first name: ");
             String lastName = userInputText("Please enter your last name: ");
             System.out.println("Please enter your date of birth: ");
             Calendar dob = inputDate(1918, 2018);
             String phoneNumber = inputDigits(10, "Please enter your phone number: ");
             String email = userInputText("Please enter your email: ");
            if (userList.registerMember(username, password, firstName, lastName, dob, 
                phoneNumber, email))
            {
                System.out.println("");
                System.out.println("Another owner account has been created.");
                this.setMenuIndex("A");
            }
            else
            {
                System.out.println("");
                System.out.println("Error! Please try again.");
                homeUserOwnerSignup();
            }
        }
        else
        {
            System.out.println("");
            System.out.println("Invalid !");
            this.setMenuIndex("A");
        }   
    }

  //option A9
    public void homeUserViewUserlist()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            printUserProfile();
            System.out.println("");
            this.setMenuIndex("A");
        }
        else
        {
            System.out.println("");
            System.out.println("Invalid !");
            this.setMenuIndex("A");
        }   
    }
    
    public void printUserProfile()
    {
        ArrayList userProfile = userList.viewUserProfile();
        System.out.println("Username" + "\t" + "Password" + "\t" + "First Name" + "\t" + "Last Name" 
                            + "\t" + "Date of Birth" + "\t\t" + "Phone Number" + "\t\t" + "isOwner" + "\t\t" + "email");
        int totalLine = userProfile.size() / 8;
        for (int lineCount = 0; lineCount < totalLine; lineCount++)
        {
            int index = 0;
            index = lineCount * 8;
            int endOfLine = (lineCount + 1) * 8;
            while(index < endOfLine)                
            {
                System.out.print(userProfile.get(index) + "\t\t");
                index++;
            } 
            System.out.println("");
        }
    }

    //option A10
    public void homeUserUnregisterUser()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            System.out.println("User Profile:");
            System.out.println("");
            printUserProfile();
            System.out.println("");
            Scanner system = new Scanner(System.in); 
            System.out.print("Please enter the username of the account to be deleted: ");        
            String username = system.nextLine().trim();
            if (userList.unregisterUser(username))
            {
                System.out.println("");
                System.out.println(username + " account has been deleted.");
                this.setMenuIndex("A");
            }
            else
            {
                System.out.println("");
                System.out.println("Error! Please try again.");
                this.setMenuIndex("A");
            }        
        }
        else
        {
            System.out.println("");
            System.out.println("Invalid !");
            this.setMenuIndex("A");
        }   
    }

    //option B
    public void browseProducts()
    {
        System.out.println("Browse Products\n");
        System.out.println("Press B1. Browse products.");
        System.out.println("Press B2. Search for product.");
        System.out.println("Press C. View shopping cart / Checkout");
        System.out.println("");
    }

    //option B1
    public void customerAddProduct()
    {
        System.out.println("Add product\n");
        Scanner system = new Scanner(System.in);
        // Get all available products and let user select
        List<Pair<UUID, String>> products = this.productList.getAllProducts();
        List<String> foundNames = this.productsItemsToStringList(products);
        int index = this.selectionList(foundNames, "Browsing available products");
        if (index >= 0) {
            //If user selected a product, get quantity
            System.out.println(foundNames.get(index));
            double amount = this.getInputQty(products.get(index).getKey());
            //Add product to cart
            if (shoppingController.addProduct(products.get(index).getKey(), amount)) {
                System.out.println("Hey you wanted some " + foundNames.get(index) +
                        " so I added some " + foundNames.get(index) + " to ya shoppin cart.");
            } else {
                System.out.println("You alreadies gotz these prodoct buddi. Iz in ye cart already.");
            }
        }
        System.out.println("\nPress B1. Browse products.");
        System.out.println("Press B2. Search for product.");
        System.out.println("Press C. View shopping cart / Checkout");
    }

    //option B2
    public void customerSearchProduct()
    {
        Scanner system = new Scanner(System.in);
        System.out.println("Add product\n");
        System.out.println("Yo dawg type a product name ya want ta seaaaaaarch!");
        //Get search terms
        System.out.print("Search: ");
        String searchName =  system.nextLine().trim();
        List<UUID> found = this.productList.searchProducts(searchName);
        List<String> foundNames = new ArrayList<String>();
        for (UUID x:found) { foundNames.add(this.productList.getProduct(x).getName()); }
        //Show all found products
        if (found.size() > 0) {
            //Select a product from the found products, works same as in B1
            int index = this.selectionList(foundNames, "Browsing products for: " + searchName);
            if (index >= 0) {
                System.out.println(foundNames.get(index));
                double amount = this.getInputQty(found.get(index));
                if (shoppingController.addProduct(found.get(index), amount)) {
                    System.out.println("Hey you wanted some " + foundNames.get(index) +
                            " so I added some " + foundNames.get(index) + " to ya shoppin cart.");
                } else {
                    System.out.println("You alreadies gotz these prodoct buddi. Iz in ye cart already.");
                }
            }
        } else {
            System.out.println("Sorry we didn't find nuthin.");
        }
        System.out.println("\nPress B1. Browse products.");
        System.out.println("Press B2. Search for product.");
        System.out.println("Press C. View shopping cart / Checkout");
    }

    //Gets and validates a quantity from the user. Should be a non-negative integer.
    public double getInputQty(UUID productId) {
        Scanner system = new Scanner(System.in);
        boolean amountGotten = false;
        String amount = "";
        while (!amountGotten) {
            System.out.println("Type quantity: ");
            amount = system.nextLine().trim();
            try
            {
                double n = Double.parseDouble(amount);
                if (this.validateNonNegativeNumber(n) && n > 0) {
                    if (this.productList.getProduct(productId).getSalesMode().equals(MFVConstants.BATCH)
                            && n != Math.floor(n)) {
                        System.out.println("Yo gotta type a whole number for batch products like this one.");
                    } else {
                        amountGotten = true;
                    }
                } else {
                    System.out.println("Number gotta be above 0 ye filthy brass bowl.");
                }
            }
            catch(NumberFormatException e)
            {
                System.out.println("This ain't no number punk. Give me a number properly ya little porcupine faced numbskull.");
            }
        }
        return Double.valueOf(amount);
    }


    //option C
    public void homeShoppingCart()
    {
        //present shopping cart
        System.out.println("Shopping Cart\n");
        Scanner system = new Scanner(System.in);
        //Show items in cart
        List<Pair<UUID, Double>> cartItems = this.shoppingController.getCartProducts();
        List<String> tmp = cartItemsToStringList(cartItems);
        for (String s : tmp) { System.out.println(s); }
        System.out.println("");
        System.out.println("Press C1. Edit/Remove Shopping Cart product");
        System.out.println("Press C2. Checkout");
        System.out.println("Press B. back to Browse products");
        System.out.println("");
    }

    //A piece of crap designed to make the input work with selectionList.
    private List<String> cartItemsToStringList(List<Pair<UUID, Double>> cartItems) {
        List<String> list = new ArrayList<String>();
        for (Pair p : cartItems) {
            String s = (this.productList.getProduct((UUID)p.getKey()).getName() + ": " + p.getValue());
            if (!this.productList.getProduct((UUID)p.getKey()).getSalesMode().equals(MFVConstants.BATCH)) {
                s += "kg";
            }
            list.add(s);
        }
        return list;
    }

    //Anotere piece of crap designed to make the input work with selectionList.
    private List<String> productsItemsToStringList(List<Pair<UUID, String>> inputList) {
        List<String> list = new ArrayList<String>();
        for (Pair p : inputList) {
            list.add((String)p.getValue());
        }
        return list;
    }

    //option C1
    public void homeShoppingCartChangeCart()
    {
        Scanner system = new Scanner(System.in);
        System.out.println("Change Shopping Cart Item Quantity\n");
        //First pick the item to modify
        List<Pair<UUID, Double>> cartItems = this.shoppingController.getCartProducts();
        List<String> tmp = cartItemsToStringList(cartItems);
        int index = this.selectionList(tmp, "Select item");
        if (index >= 0) {
            while (true) {
                System.out.println("Type e to edit quantity and r to remove product from shopping cart.");
                String move = system.nextLine().toUpperCase().trim();
                if (move.equals("E")) {
                    System.out.println("Give me the new quantity.");
                    double amount = getInputQty(cartItems.get(index).getKey());
                    shoppingController.editProduct(cartItems.get(index).getKey(), amount);
                    break;
                } else if (move.equals("R")) {
                    shoppingController.removeProduct(cartItems.get(index).getKey());
                    break;
                }
            }
        }
        this.setMenuIndex("C");
    }

    //option C2
    public void homeShoppingCartCheckout() {
		//check current shopping cart empty or not
		if(userList.isMemberLogin(loginUsername) || userList.isOwnerLogin(loginUsername)){
			if(this.shoppingController.getCartProducts().size()<=0 ) {
				System.out.print("Your shopping cart is empty. Please add some products first.");
				this.setMenuIndex("B");
				return;
			}
			// Get order details
			String paymentMethod = "";
			String paymentDetails = "";
			String destAddress = "";
			String deliveryMethod = "";
			//Get payment and address details
			System.out.println("Please enter your payment information...");
			Scanner sc = new Scanner(System.in);
            System.out.println("What payment method do you want to use? y for credit card, anything else for cash.");
            String move = sc.nextLine().toUpperCase().trim();
            if (move.equals("Y")) {
				String name = this.inputText("Enter name on card: ");
				String cardNumber = inputDigits(16, "Enter card number: ");
				String CVV = inputDigits(3, "Enter CVV: ");
				paymentDetails = cardNumber + "-" + CVV;
				paymentMethod = MFVConstants.paymentMethod.CARD;
			} else {
				paymentMethod = MFVConstants.paymentMethod.CASH;
			}
			deliveryMethod = this.inputDeliveryMethod();
			if (deliveryMethod.equals(MFVConstants.DELIVERY)) {
				destAddress = this.inputText("Enter Shipping Address: ");
			}
            System.out.println("Do you want to proceed with payment (or order for cash payment)? y for yes, anything else to return to shopping.");
            move = sc.nextLine().toUpperCase().trim();
            if (move.equals("Y")) {
                //Process checkout
                Order order = shoppingController.checkout(this.orderList, this.inventory, this.loginUsername,
                        this.shoppingController.getCartProducts(), deliveryMethod, destAddress, paymentMethod,
                        paymentDetails, this.productList);
                System.out.println(order.getOrderStatusMsg());
                if (MFVConstants.PAYMENT_SUCCESSFUL.equals(order.getOrderStatusMsg())) { //this means success
                    shoppingController.clearCart();
                    System.out.println("\nYour order has been confirmed. Find your Order Receipt below:");
                    String receipt = orderList.getOrderReceipt(order.getOrderID(), this.productList);
                    System.out.println(receipt);
                } else if (MFVConstants.PAYMENT_PENDING.equals(order.getOrderStatusMsg())) { //this means success
                    System.out.println("We'll get your delivery ready so you get our money ready. Yo order is: ");
                    String receipt = orderList.getOrderReceipt(order.getOrderID(), this.productList);
                    System.out.println(receipt);
                }
            }
            try {
                this.inventory.writeInventoryToFile(this.inventoryFilename);
                this.orderList.writeOrderToFile(this.orderListFilename);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
		} else {
			System.out.print("Please log in before you checkout.");
			this.setMenuIndex("A");
		}
	}
    
    //Gets and validates a string that is a bunch of digits only
    private String inputDigits(int length, String command) {
        System.out.print(command);
        Scanner system = new Scanner(System.in);
        boolean gotten = false;
        String x = "";
        while(!gotten) {
            x = system.nextLine().trim();
            if (x.matches("[0-9]+") && x.length() == length) {
                gotten = true;
            } else {
                System.out.println("Soz that no good. You should trying again.");
            }
        }
        return x;
    }

    //Gets user input to choose a delivery method
    private String inputDeliveryMethod() {
        List<String> dm = Arrays.asList(MFVConstants.PICK_UP, MFVConstants.DELIVERY);
        int index;
        while (true) {
            index = this.selectionList(dm, "Select Delivery Method");
            if (index != -1){
                break;
            }
        }
        return dm.get(index);
    }

    //option D
    public void homeManageProfile()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            System.out.println("");
            Scanner system = new Scanner(System.in); 
            System.out.println("Press D1. Add profile");   
            System.out.println("Press D2. View/Edit/Remove profile");
            System.out.println("");
        }
        else
            homeUser();
    }

    //option D1
    public void homeManageProfileAddProfile()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            System.out.println("Let's adda a purodukkuta purofairu!");
            String name = this.inputProductName();
            List<String> altNames = this.inputProductAltNames();
            String category = inputProductCategory();
            String source = this.inputProductSource();
            List<Integer> shelfLife = this.inputProductShelfLife();
            String salesMode = this.inputProductSalesMode();
            if (salesMode.equals(MFVConstants.BATCH)) {
                int i = this.selectionList(MFVConstants.BATCH_PRODUCT_DESCRIPTORS, "Select Batch Type");
                name += " " + MFVConstants.BATCH_PRODUCT_DESCRIPTORS.get(i);
            }
            BigDecimal price = inputPrice();
            this.productList.addProduct(name, altNames, category, source, shelfLife, salesMode, price);
            try {
                this.productList.writeProductListToFile(this.productListFilename);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            this.setMenuIndex("D");
        }
        else
            homeUser();
    }

    //Gets and validates text from the user
    private String inputText(String command){
        System.out.print(command);
        Scanner system = new Scanner(System.in);
        boolean empty = true;
        String name = system.nextLine().trim();
        while(empty) {
            if (name.equals("")) {
                System.out.println("WHAT THAT!?!?!?!?!?");
                name = system.nextLine().trim();
            } else {
                empty = false;
            }
        }
        return name;
    }

    //Gets product name from user
    private String inputProductName(){
        return this.inputText("Enter product name: ");
    }

    //Gets list of alternative names from user
    private List<String> inputProductAltNames(){
        Scanner system = new Scanner(System.in);
        List<String> altNames = new ArrayList<String>();
        while (true) {
            System.out.println("Are there any alternatives names to enter? y for yes, n for no.");
            String move = system.nextLine().toUpperCase().trim();
            if (move.equals("Y")) {
                System.out.println("Give me the alternative name.");
                String tmp = system.nextLine().trim();
                altNames.add(tmp);
                break;
            } else if (move.equals("N")) {
                break;
            }
        }
        return altNames;
    }

    //Gets product source from user
    private String inputProductSource(){
        return this.inputText("What's the source");
    }

    //Gets product category from user
    private String inputProductCategory() {
        List<String> categories = Arrays.asList(MFVConstants.FRUIT, MFVConstants.VEG);
        int index;
        while (true) {
            index = this.selectionList(categories, "Select Product Category");
            if (index != -1){
                break;
            }
        }
        return categories.get(index);
    }

    //Gets the product shelf life from user
    private List<Integer> inputProductShelfLife(){
        System.out.println("Give me the lower bound for the shelf life in days.");
        int lower = inputPositiveInteger();
        System.out.println("Gimme that upper bound for zat shelf life in dayz.");
        int upper = -1;
        while (upper <= lower){
            upper = inputPositiveInteger();
        }
        return Arrays.asList(lower, upper);
    }

    private String inputProductSalesMode(){
        List<String> salesMode = Arrays.asList(MFVConstants.LOOSE, MFVConstants.BATCH);
        int index;
        while (true) {
            index = this.selectionList(salesMode, "Select Product Sales Mode");
            if (index != -1){
                break;
            }
        }
        return salesMode.get(index);
    }

    //Gets positive integer from user
    private int inputPositiveInteger() {
        Scanner system = new Scanner(System.in);
        boolean amountGotten = false;
        String amount = "";
        while (!amountGotten) {
            amount = system.nextLine().trim();
            try
            {
                int n = Integer.parseInt(amount);
                if (n > 0) {
                    amountGotten = true;
                } else {
                    System.out.println("That no positive integer. Do it properly fishsticks.");
                }
            }
            catch(NumberFormatException e)
            {
                System.out.println("That no number. Do it properly doofus.");
            }
        }
        return Integer.parseInt(amount);
    }

    //Gets a price in BigDecimal from user
    private BigDecimal inputPrice() {
        System.out.print("Enter price: ");
        Scanner system = new Scanner(System.in);
        String amount = "";
        boolean priceGotten = false;
        while(!priceGotten) {
            System.out.println("What ya price?!?!?!?!?!?");
            amount = system.nextLine().trim();
            try
            {
                double n = Double.parseDouble(amount);
                if (n > 0) {
                    priceGotten = true;
                } else {
                    System.out.println("How is that a price?");
                }
            }
            catch(NumberFormatException e)
            {
                System.out.println("Watchu talking bout?");
            }
        }
        return BigDecimal.valueOf(Double.parseDouble(amount));
    }


    private List<String> getProductInfo(UUID productId){
        List<String> info = new ArrayList<>();
        info.add("Name: " + this.productList.getProduct(productId).getName());
        info.add("Alternative Names: " + this.productList.getProduct(productId).getAltNames());
        info.add("Category: " + this.productList.getProduct(productId).getCategory());
        info.add("Source: " + this.productList.getProduct(productId).getSource());
        info.add("Shelf Life: " + this.productList.getProduct(productId).getShelfLife().get(0) + " to "
                + this.productList.getProduct(productId).getShelfLife().get(1) + " days");
        info.add("Sales Mode: " + this.productList.getProduct(productId).getSalesMode());
        info.add("Price: " + this.productList.getProduct(productId).getPrice());
        return info;
    }

    //option D2
    public void homeManageProfileViewProfile()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            Scanner system = new Scanner(System.in);
            //Get all products
            List<Pair<UUID, String >> products = this.productList.getAllProducts();
            List<String> tmp = this.productsItemsToStringList(products);
            int index = this.selectionList(tmp, "Select Product Profile"); //Display all products and let user select
            if (index >= 0) {
                while (true) {
                    //Print out the details of the product
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Product: \n");
                    List<String> info = getProductInfo(products.get(index).getKey());
                    for (String line : info) {
                        System.out.println(line);
                    }
                    System.out.println("Type e to edit product, r to remove product and q to quit.");
                    String move = system.nextLine().toUpperCase().trim();
                    if (move.equals("E")) {
                        int editIndex = this.selectionList(info, "Select Product property to edit");
                        ProductProfile curr = this.productList.getProduct(products.get(index).getKey());
                        switch (editIndex) { //Values for switch statement are hard coded. Order gotten from getProductInfo function
                            case 0:
                                String name = this.inputProductName();
                                curr.setName(name);
                                break;
                            case 1:
                                List<String> altNames = this.inputProductAltNames();
                                curr.setAltNames(altNames);
                                break;
                            case 2:
                                String category = inputProductCategory();
                                curr.setCategory(category);
                                break;
                            case 3:
                                String source = this.inputProductSource();
                                curr.setSource(source);
                                break;
                            case 4:
                                List<Integer> shelfLife = this.inputProductShelfLife();
                                curr.setShelfLife(shelfLife);
                                break;
                            case 5:
                                String oldSM = curr.getSalesMode();
                                String newSM = this.inputProductSalesMode();
                                curr.setSalesMode(newSM);
                                if (oldSM.equals(MFVConstants.BATCH) && newSM.equals(MFVConstants.LOOSE)) {
                                    curr.setName(curr.getName().replace("([a-zA-Z]*)", ""));
                                } else if (oldSM.equals(MFVConstants.LOOSE) && newSM.equals(MFVConstants.BATCH)) {
                                    int i = this.selectionList(MFVConstants.BATCH_PRODUCT_DESCRIPTORS, "Select Batch Type");
                                    curr.setName(curr.getName() + " " + MFVConstants.BATCH_PRODUCT_DESCRIPTORS.get(i));
                                }
                                break;
                            case 6:
                                BigDecimal price = inputPrice();
                                curr.setPrice(price);
                                break;
                        }
                    } else if (move.equals("R")) {
                        this.productList.removeProduct(products.get(index).getKey());
                        break;
                    } else if (move.equals("Q")) {
                        break;
                    }
                }
                try {
                    this.productList.writeProductListToFile(this.productListFilename);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            this.setMenuIndex("D");
        }
        else
            homeUser();
    }



    //option E
    public void homeManageInventory()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            System.out.println("");
            System.out.println("Press E1. Add Item to Inventory");
            System.out.println("Press E2. View/Edit/Remove Item in Inventory");
            System.out.println("");
        }
        else
            homeUser();
    }

    //option E1
    public void homeManageInventoryAddInventory()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            //First let user select the product profile of the item to add, then get quantity
            List<Pair<UUID, String >> allProducts = this.productList.getAllProducts();
            List<String> allProductsB = this.productsItemsToStringList(allProducts);
            int index = this.selectionList(allProductsB, "Select the product type of the item to add");
            UUID productId = allProducts.get(index).getKey();
            double qty = this.getInputQty(productId);
            this.inventory.addItem(this.productList.getProduct(productId), qty);
            try {
                this.inventory.readInventoryFromFile(inventoryFilename);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            this.setMenuIndex("E");
        }
        else
            homeUser();
    }

    //function designed to let input work with selectionList
    private List<String> createItemsInfoList(List<UUID> itemsId) {
        List<String> items = new ArrayList<>();
        for (UUID id : itemsId ) {
            Item tmp = this.inventory.getItem(id);
            String n = "Name: " + format("%1$-"+30+"s", tmp.getName());
            String q = " Qty: " + format("%1$-"+7+"s", tmp.getQty());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
            String d = " Expiry date: " + String.format("%1$-"+15+"s", sdf.format(tmp.getExpiryDate().getTime()));
            items.add(n + q + d);
        }
        return items;
    }

    //Get a list of string which contain the information of an item.
    private List<String> getItemInfoStrings(UUID itemId) {
        List<String> info = new ArrayList<>();
        Item item = this.inventory.getItem(itemId);
        info.add("Name: " + item.getName());
        String tmp = "Qty: " + item.getQty();
        if (item.getSalesMode().equals(MFVConstants.LOOSE)) { tmp += "kg"; }
        info.add(tmp);
        info.add("Price: " + item.getPrice());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
        info.add("Expiry date: " + sdf.format(item.getExpiryDate().getTime()));
        return info;
    }

    //Get a date from the user and validate to ensure it is after yearMin and before yearMax inclusive.
    public Calendar inputDate(int yearMin, int yearMax) {
        Scanner system = new Scanner(System.in);
        Calendar date = Calendar.getInstance();
        int y, m, d;
        String tmp;
        boolean dateGotten = false;
        while (!dateGotten) {
            System.out.println("What year? Enter year. ");
            y = inputPositiveInteger();
            System.out.println("What month? Enter month as number, i.e 1 for January. ");
            m = inputPositiveInteger() - 1;
            System.out.println("What day?");
            d = inputPositiveInteger();
            if (y > yearMin && y < yearMax){
                if (m >= 0 && m <= 11){
                    date.set(y, m, 1);
                    if (d >= 1 && d <= date.getActualMaximum(Calendar.DAY_OF_MONTH)){
                        date.set(y, m, d);
                        dateGotten = true;
                    }
                } else {
                    System.out.println("Nah that month ain't right");
                }
            } else {
                System.out.println("Nah man that year is bad.");
            }
        }
        return date;
    }

    //option E2
    public void homeManageInventoryViewInventory()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            Scanner system = new Scanner(System.in);
            //Let user select an item
            List<UUID> itemsId = this.inventory.findItemsByState(MFVConstants.STOCKED);
            List<String> itemsInfo = this.createItemsInfoList(itemsId);
            int index = this.selectionList(itemsInfo, "Select Item");
            if (index >= 0) {
                while (true) {
                    //Display item details
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Item: \n");
                    Item curr = this.inventory.getItem(itemsId.get(index));
                    List<String> info = getItemInfoStrings(curr.getItemId());
                    for (String line : info) { System.out.println(line); }
                    System.out.println("\nType e to edit item, r to remove item and q to quit.");
                    String move = system.nextLine().toUpperCase().trim();
                    if (move.equals("E")) {
                        int editIndex = this.selectionList(info, "Select Item property to edit");
                        switch (editIndex)
                        {
                            case 0: String name = this.inputProductName(); curr.setName(name); break;
                            case 1: double qty = this.getInputQty(curr.getProductId()); curr.setQty(qty); break;
                            case 2: BigDecimal price = this.inputPrice(); curr.setPrice(price); break;
                            case 3: Calendar date = this.inputDate(2000, 3000); curr.setExpiryDate(date); break;
                        }
                    } else if (move.equals("R")) {
                        this.inventory.removeItem(curr.getItemId());
                        break;
                    } else if (move.equals("Q")) {
                        break;
                    }
                }
                try {
                    this.inventory.readInventoryFromFile(inventoryFilename);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            this.setMenuIndex("E");
        }
        else
            homeUser();
    }

 // option F
 	public void homeReports() {
 		if (userList.isOwnerLogin(loginUsername)) {
 			System.out.println("");
 			System.out.println("Press F1. Sales Report");
 			System.out.println("Press F2. Delivery Report");
 			System.out.println("Press F3. Order Report");
 			System.out.println("");
 		} else
 			homeUser();
 	}

 	// option F1
 	public void homeSalesReport() {
 		if (userList.isOwnerLogin(loginUsername)) {
 			Calendar startCal = Calendar.getInstance();
 			Calendar endCal = Calendar.getInstance();
 			System.out.println("Enter start date in 'yyyy-MM-dd' format");
 			Scanner sc = new Scanner(System.in);
 			String startDate = sc.nextLine();
 	 		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 	 		System.out.println("Enter end date in 'yyyy-MM-dd' format (not inclusive)");
 	 		String endDate = sc.nextLine();
 	 		Date date1;
 	 		Date date2;
 			try {
                date1 = sdf.parse(startDate);
                startCal.setTime(date1);
 				date2 = sdf.parse(endDate);
 				endCal.setTime(date2);
 			} catch (ParseException e) {
                System.out.println("Those dates are bad.");
                setMenuIndex("F");
 			}
 			System.out.println(reporterController.getSalesReport(this.orderList, this.productList, startCal, endCal));
 			this.setMenuIndex("F");
 		} else
            this.setMenuIndex("!");
 	}

 	// option F2
 	public void homeDeliveryReport() {
 		if (userList.isOwnerLogin(loginUsername)) {
 			
 		Calendar startCal = Calendar.getInstance();
 		Calendar endCal = Calendar.getInstance();
 		System.out.println("Enter start date in 'yyyy-MM-dd' format");
 		Scanner sc = new Scanner(System.in);
 		String startDate = sc.nextLine();
 		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
 		System.out.println("Enter end date in 'yyyy-MM-dd' format (not inclusive)");
 		String endDate = sc.nextLine();
 		Date date1;
 		Date date2;
		try {
            date1 = sdf.parse(startDate);
            startCal.setTime(date1);
			date2 = sdf.parse(endDate);
			endCal.setTime(date2);
		} catch (ParseException e) {
		    System.out.println("Those dates are bad.");
			setMenuIndex("F");
		}
		
 		System.out.println("Select the Delivery Method for the Sales Report. \n 1. Pickup \n Any other key for Delivery");
 		String deliveryOption = sc.nextLine();
 		String deliveryMethod = "";
 		if(deliveryOption.equals("1"))
 			deliveryMethod = MFVConstants.PICK_UP;
 		else
 			deliveryMethod = MFVConstants.DELIVERY;

 		System.out.println(reporterController.getDeliveries(this.orderList, this.productList, startCal, endCal, deliveryMethod));
 		this.setMenuIndex("F");
 	    } else {
            this.setMenuIndex("!");
        }
    }

 	//Option F3
    private void homeOrderReport() {
 		if (userList.isOwnerLogin(loginUsername)) {
 			
 		Calendar startCal = Calendar.getInstance();
 		Calendar endCal = Calendar.getInstance();
 		System.out.println("Enter start date in 'yyyy-MM-dd' format");
 		Scanner sc = new Scanner(System.in);
 		String startDate = sc.nextLine();
 		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
 		System.out.println("Enter end date in 'yyyy-MM-dd' format (not inclusive)");
 		String endDate = sc.nextLine();
 		Date date1;
 		Date date2;
		try {
            date1 = sdf.parse(startDate);
            startCal.setTime(date1);
			date2 = sdf.parse(endDate);
			endCal.setTime(date2);
		} catch (ParseException e) {
		    System.out.println("Those dates are bad.");
			setMenuIndex("F");
		}
		
 		System.out.println(reporterController.getOrderReport(this.orderList,startCal, endCal));
 		this.setMenuIndex("F");
 	    } else {
            this.setMenuIndex("!");
        }
    }

}
