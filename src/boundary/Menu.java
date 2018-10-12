package boundary;

import controller.UserList;
import controller.Inventory;
import controller.ProductList;
import controller.ReporterController;
import controller.ShoppingController;
import entity.MFVConstants;
import javafx.util.Pair;

import java.util.*;

public class Menu
{
    private String loginUsername;
    private UserList userList;
    private ReporterController reporterController;
    private Inventory inventory;
    private ShoppingController shoppingController; 
    private ProductList productList;
    private static final int LIST_SIZE = 10;
    private final String inventoryFilename = "inventoryFile";
    private final String productListFilename = "productListFile";
    private boolean nextPageGotten;
    private String menuIndex;

    private void setMenuIndex(String x){
        this.menuIndex = x;
        this.nextPageGotten = true;
    }

    public static void main(String[] args){
        Menu m = new Menu();
        m.main();
        /*List<String> l = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            l.add(String.valueOf(i));
        }
        System.out.println("selected: " + m.selectionList(l, "This guy"));*/


    }

    public Menu()
    {
        userList = new UserList();
        reporterController = new ReporterController();
        inventory = Inventory.readInventoryFromFile(inventoryFilename);
        shoppingController = new ShoppingController();
        productList = ProductList.readProductListFromFile(productListFilename);
        nextPageGotten = false;
        menuIndex = "!";
    }

    public void main()
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
                case "C": homeShoppingCart(); break;
                case "C1": homeShoppingCartChangeCart(); break;
                case "C2": homeShoppingCartCheckout(); break;
                case "D": homeManageProfile(); break;
                case "D1": homeManageProfileAddProfile(); break;
                case "D2": homeManageProfileViewProfile(); break;
                case "E": homeManageInventory(); break;
                case "E1": homeManageInventoryAddInventory(); break;
                case "E2": homeManageInventoryViewInventory(); break;
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



    public int selectionList(List<String> list, String pageName) {
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
            //get input
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

    public boolean validateNumber(double x) {
        return (!Double.isNaN(x) && Double.isFinite(x) && x >= 0);
    }

    public void closeProgram()
    {
        loginUsername = "";
        userList = new UserList();
        reporterController = new ReporterController();
        inventory = new Inventory();
        shoppingController = new ShoppingController();
        productList = new ProductList();
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
            System.out.println("Press A6. Unregister current account");
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
        if (loginUsername.equals(""))
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
                home();
            }
            else
            {
                System.out.println("");
                System.out.println("Error! Please try again.");
                homeUserLogin();

            }
        }    
        else 
        {
            System.out.println("");
            System.out.println("Error! You have logged in.");
            homeUser();
        }
    }

    //option A2
    public void homeUserMemberSignup()
    {
        if (loginUsername.equals(""))
        {
            Scanner system = new Scanner(System.in); 
            System.out.print("Please enter your username: ");        
            String username = system.nextLine().trim();
            System.out.print("Please enter your password: ");
            String password = system.nextLine().trim();
            System.out.print("Please enter your first name: ");
            String firstName = system.nextLine().trim();
            System.out.print("Please enter your last name: ");
            String lastName = system.nextLine().trim();
            System.out.print("Please enter your date of birth: ");
            Calendar dob = Calendar.getInstance();
            System.out.print("Please enter your phone number: ");
            String phoneNumber = system.nextLine().trim();
            System.out.print("Please enter your email: ");
            String email = system.nextLine().trim();
            if (userList.registerMember(username, password, firstName, lastName, dob, 
                phoneNumber, email))
            {
                System.out.println("");
                System.out.println("Thank you for joinning us.");
                homeUser();
            }
            else
            {
                System.out.println("");
                System.out.println("Error! Please try again.");
                homeUserMemberSignup();
            }
        }
        else 
        {
            System.out.println("");
            System.out.println("Error! You have logged in.");
            homeUser();
        }
    }

    //option A3
    public void homeUserChangePassword()
    {
        if (userList.isMemberLogin(loginUsername) || userList.isOwnerLogin(loginUsername))
        {
            Scanner system = new Scanner(System.in); 
            System.out.print("Please enter your new password: "); 
            String newPassword = system.nextLine().trim();
            if (userList.changePassword(loginUsername, newPassword))
            {
                System.out.println("");
                System.out.println("Your password has been changed.");
                homeUser();
            }
            else
            {
                System.out.println("");
                System.out.println("Error! Please try again.");
                homeUserChangePassword();
            }    
        }
        else 
        {
            System.out.println("");
            System.out.println("Invalid !");
            homeUser();
        }
    }

    //option A4
    public void homeUserChangeEmail()
    {
        if (userList.isMemberLogin(loginUsername) || userList.isOwnerLogin(loginUsername))
        {
            Scanner system = new Scanner(System.in); 
            System.out.print("Please enter your new email: "); 
            String newEmail = system.nextLine().trim();
            if (userList.changeEmail(loginUsername, newEmail))
            {
                System.out.println("");
                System.out.println("Your email has been changed.");
                homeUser();
            }
            else
            {
                System.out.println("");
                System.out.println("Error! Please try again.");
                homeUserChangeEmail();
            }   
        }
        else 
        {
            System.out.println("");
            System.out.println("Invalid !");
            homeUser();
        }
    }

    //option A5
    public void homeUserChangePhoneNumber()
    {
        if (userList.isMemberLogin(loginUsername) || userList.isOwnerLogin(loginUsername))
        {
            Scanner system = new Scanner(System.in); 
            System.out.print("Please enter your new phone number: "); 
            String newPhoneNumber = system.nextLine().trim();
            if (userList.changePhoneNumber(loginUsername, newPhoneNumber))
            {
                System.out.println("");
                System.out.println("Your phone number has been changed.");
                homeUser();
            }
            else
            {
                System.out.println("");
                System.out.println("Error! Please try again.");
                homeUserChangePhoneNumber();
            }   
        }
        else 
        {
            System.out.println("");
            System.out.println("Invalid !");
            homeUser();
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
                homeUser();
            }
            else
            {
                System.out.println("");
                System.out.println("Error! Please try again.");
                homeUser();
            }   
        }
        else 
        {
            System.out.println("");
            System.out.println("Invalid !");
            homeUser();
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
            homeUser();
        }
        else
        {
            System.out.println("");
            System.out.println("Invalid !");
            homeUser();
        }        
    }

    //option A8
    public void homeUserOwnerSignup()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            Scanner system = new Scanner(System.in); 
            System.out.print("Please enter your username: ");        
            String username = system.nextLine().trim();
            System.out.print("Please enter your password: ");
            String password = system.nextLine().trim();
            System.out.print("Please enter your first name: ");
            String firstName = system.nextLine().trim();
            System.out.print("Please enter your last name: ");
            String lastName = system.nextLine().trim();
            System.out.print("Please enter your date of birth: ");
            Calendar dob = Calendar.getInstance();
            System.out.print("Please enter your phone number: ");
            String phoneNumber = system.nextLine().trim();
            System.out.print("Please enter your email: ");
            String email = system.nextLine().trim();
            if (userList.registerMember(username, password, firstName, lastName, dob, 
                phoneNumber, email))
            {
                System.out.println("");
                System.out.println("Another owner account has been created.");
                homeUser();
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
            homeUser();
        }   
    }

    //option A9
    public void homeUserViewUserlist()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            userList.viewUserlist();
            homeUser();
        }
        else
        {
            System.out.println("");
            System.out.println("Invalid !");
            homeUser();
        }   
    }

    //option A10
    public void homeUserUnregisterUser()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            System.out.println("User List:");
            System.out.println("");
            userList.viewUserlist();
            System.out.println("");
            Scanner system = new Scanner(System.in); 
            System.out.print("Please enter the username of the account to be deleted: ");        
            String username = system.nextLine().trim();
            if (userList.unregisterUser(username))
            {
                System.out.println("");
                System.out.println(username + " account has been deleted.");
                homeUser();
            }
            else
            {
                System.out.println("");
                System.out.println("Error! Please try again.");
                homeUser();
            }        
        }
        else
        {
            System.out.println("");
            System.out.println("Invalid !");
            homeUser();
        }   
    }

    //option B
    public void browseProducts()
    {
        //Present Inventory List
        System.out.println("Browse Products\n");
        Scanner system = new Scanner(System.in);
        System.out.println("Press B1. Add product to your shopping cart.");
        System.out.println("Press C. View shopping cart / Checkout");
        System.out.println("");
    }

    //option B1
    public void customerAddProduct()
    {
        Scanner system = new Scanner(System.in);
        System.out.println("Add product\n");
        System.out.println("Yo dawg type a product name ya want ta seaaaaaarch!");
        System.out.print("Search: ");

        String searchName =  system.nextLine().trim();
        List<UUID> found = this.productList.searchProducts(searchName);
        List<String> foundNames = new ArrayList<String>();
        for (UUID x:found) { foundNames.add(this.productList.getProduct(x).getName()); }
        int index = this.selectionList(foundNames, "Browsing products for: " + searchName);
        if (index >= 0) {
            System.out.println(foundNames.get(index));
            double amount = getInputQty(found.get(index));
            if (shoppingController.addProduct(found.get(index), amount)) {
                System.out.println("Hey you wanted some " + foundNames.get(index) +
                        " so I added some " + foundNames.get(index) + " to ya shoppin cart.");
            } else {
                System.out.println("You alreadies gotz these prodoct buddi. Iz in ye cart already.");
            }
        }
        System.out.println("\nPress B1. Add another product to your shopping cart.");
        System.out.println("Press C. View shopping cart / Checkout");
    }

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
                if (validateNumber(n) && n > 0) {
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
        //present shoppingcart
        System.out.println("Shopping Cart\n");
        Scanner system = new Scanner(System.in);
        List<Pair<UUID, Double>> cartItems = this.shoppingController.getCartProducts();
        List<String> tmp = cartItemsToStringList(cartItems);
        for (String s : tmp) { System.out.print(s); }
        System.out.println("Press C1. Edit/Remove Shopping Cart product");
        System.out.println("Press C2. Checkout");
        System.out.println("Press B. back to Browse products");
        System.out.println("");
    }

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

    //option C1
    public void homeShoppingCartChangeCart()
    {
        Scanner system = new Scanner(System.in);
        System.out.println("Change Shopping Cart Item Quantity\n");
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
    public void homeShoppingCartCheckout()
    {
        if (userList.isMemberLogin(loginUsername) || userList.isOwnerLogin(loginUsername))
        {
            //Validating order
            String customers = loginUsername;
            System.out.println("");
            System.out.println("Please enter your payment information...");
            Scanner system = new Scanner(System.in); 
            System.out.print("Enter name on card: ");
            String name = system.nextLine().trim();
            System.out.print("Enter card number: ");
            String card = system.nextLine().trim();
            System.out.print("Enter CVV: ");
            String CVV = system.nextLine().trim();
            System.out.print("Enter card expiry: ");
            String expiry = system.nextLine().trim();
            //Validate input details
            System.out.print("Enter Shipping Address: ");
            String destAddress = system.nextLine().trim();
            System.out.print("Enter delivery method: ");
            String deliveryMethod = system.nextLine().trim();
            System.out.println("");
            System.out.println("Press enter to CONFIRM PAYMENT");
            System.out.print("*************ENTER*************");
            system.nextLine().trim();
            //payment method
            System.out.println("");
            System.out.println("Thank you for shopping with us. Your order has been confirmed.");
            home();
        }
        else
        {
            System.out.print("Please log in before you checkout.");
            homeUser();
        }
    }

    //option D
    public void homeManageProfile()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            System.out.println("");
            Scanner system = new Scanner(System.in); 
            System.out.println("Press D1. Add profile");   
            System.out.println("Press D2. View profile");   
            System.out.println("Press D3. Edit profile");   
            System.out.println("Press D4. Delete profile");   
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
            System.out.println("");
            Scanner system = new Scanner(System.in); 
            System.out.print("Enter product name: ");
            //List<String> altName = 
            System.out.print("Enter product alternative name: ");
            String category = system.nextLine().trim();
            System.out.print("Enter product source: ");
            String source = system.nextLine().trim();
            System.out.print("Enter product shelfLife: ");
            //List<Integer> shelfLife = 
            System.out.print("Enter product salesMode: ");
            String salesMode = system.nextLine().trim();
            System.out.print("Enter product price: ");
            //BigDecimal price = 

            //addProduct function
            homeManageProfile();
        }
        else
            homeUser();
    }

    //option D2
    public void homeManageProfileViewProfile()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            //present all profile        
            homeManageProfile();
        }
        else
            homeUser();
    }

    //option D3
    public void homeManageProfileEditProfile()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            //present all profile

            System.out.println("");
            System.out.print("Enter product ID: ");
            Scanner system = new Scanner(System.in); 
            String productID = system.nextLine().trim();

            //edit function?

            homeManageProfile();
        }
        else
            homeUser();
    }

    //option D4
    public void homeManageProfileDeleteProfile()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            //present all profile

            System.out.println("");
            System.out.print("Enter product ID: ");
            Scanner system = new Scanner(System.in); 
            String productID = system.nextLine().trim();

            //delete function?

            homeManageProfile();
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
            Scanner system = new Scanner(System.in); 
            System.out.println("Press E1. Add inventory");   
            System.out.println("Press E2. View inventory");   
            System.out.println("Press E3. Edit inventory");   
            System.out.println("Press E4. Delete inventory");   
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
            System.out.print("Enter product ID: ");
            Scanner system = new Scanner(System.in); 
            String productID = system.nextLine().trim();
            System.out.print("Enter product amount: ");
            String amount = system.nextLine().trim();
            //addInventory
            System.out.println("");
            System.out.println("The product has been added to the inventory.");
            homeManageInventory();
        }
        else
            homeUser();
    }

    //option E2
    public void homeManageInventoryViewInventory()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            //present all inventory        
            homeManageInventory();
        }
        else
            homeUser();
    }

    //option E3
    public void homeManageInventoryEditInventory()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            //present all inventory

            System.out.print("Enter product ID: ");
            Scanner system = new Scanner(System.in); 
            String productID = system.nextLine().trim();        
            //edit Inventory?
            homeManageInventory();
        }
        else
            homeUser();
    }

    //option E4
    public void homeManageInventoryDeleteInventory()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            //present all inventory

            System.out.print("Enter product ID: ");
            Scanner system = new Scanner(System.in); 
            String productID = system.nextLine().trim();
            //delete Inventory
            System.out.println("");
            System.out.println("The product has been deleted from the inventory.");
            homeManageInventory();
        }
        else
            homeUser();
    }

    //option F
    public void homeViewSalesReport()
    {
        if (userList.isOwnerLogin(loginUsername))
        {
            //Present Sales Report
            System.out.println("");
            home();
        }
        else
            homeUser();
    }


}
