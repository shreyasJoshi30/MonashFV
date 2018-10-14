package boundary;

import controller.UserList;
import controller.Inventory;
import controller.ProductList;
import controller.ReporterController;
import controller.ShoppingController;
import entity.Item;
import entity.MFVConstants;
import entity.ProductProfile;
import entity.Util;
import javafx.util.Pair;

import java.math.BigDecimal;
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
        this.loginUsername = "";
        this.userList = new UserList();
        this.reporterController = new ReporterController();
        this.inventory = Inventory.readInventoryFromFile(this.inventoryFilename);
        this.shoppingController = new ShoppingController();
        this.productList = ProductList.readProductListFromFile(this.productListFilename);
        this.nextPageGotten = false;
        this.menuIndex = "!";
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
        this.loginUsername = "";
        this.userList = new UserList();
        this.reporterController = new ReporterController();
        this.inventory = new Inventory();
        this.shoppingController = new ShoppingController();
        this.productList = new ProductList();
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
        if (!Util.checkNullEmptyStr(loginUsername))
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
            System.out.print("Please enter your date of birth - Year: ");
            String dobYear = system.nextLine().trim();
            System.out.print("Please enter your date of birth - Month: ");
            String dobMonth = system.nextLine().trim();
            System.out.print("Please enter your date of birth - Date: ");
            String dobDate = system.nextLine().trim();
            Calendar dob = Calendar.getInstance();
            dob.set(Calendar.YEAR, Integer.parseInt(dobYear));
            dob.set(Calendar.MONTH, Integer.parseInt(dobMonth));
            dob.set(Calendar.DATE, Integer.parseInt(dobDate));
            System.out.print("Please enter your phone number: ");
            String phoneNumber = system.nextLine().trim();
            System.out.print("Please enter your email: ");
            String email = system.nextLine().trim();
            if (userList.registerMember(username, password, firstName, lastName, dob, 
                phoneNumber, email))
            {
                System.out.println("");
                System.out.println("Thank you for joinning with us.");
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
            printUserProfile();
            System.out.println("");
            homeUser();
        }
        else
        {
            System.out.println("");
            System.out.println("Invalid !");
            homeUser();
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
            double amount = this.getInputQty(found.get(index));
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
                if (this.validateNumber(n) && n > 0) {
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
            //Get order details
            System.out.println("Please enter your payment information...");
            String name = this.inputText("Enter name on card: ");
            String cardNumber = inputDigits(16, "Enter card number: ");
            String CVV = inputDigits(3, "Enter CVV: ");
            String paymentDetails = cardNumber + "-" + CVV;
            String destAddress = this.inputText("Enter Shipping Address: ");
            String deliveryMethod = this.inputDeliveryMethod();
            Scanner system = new Scanner(System.in);
            System.out.println("Do you want to proceed with payment? y for yes, anything else to return to shopping.");
            String move = system.nextLine().toUpperCase().trim();
            if (move.equals("Y")) {
                //UUID orderId = this.shoppingController.checkout(this.orderList, this.inventory, this.loginUsername,
                //this.shoppingController.getCartProducts(), deliveryMethod, destAddress, "Credit Card", paymentDetails);
                //Handle bad payment, not enough stock, etc
                //Print receipt

                System.out.println("\nYour order has been confirmed.\nType in anything to return.");
                system.nextLine().toUpperCase().trim();
            }
            setMenuIndex("B");
        }
        else
        {
            System.out.print("Please log in before you checkout.");
            this.setMenuIndex("A");
        }
    }

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
            this.setMenuIndex("D");
        }
        else
            homeUser();
    }

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

    private String inputProductName(){
        return this.inputText("Enter product name: ");
    }

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

    private String inputProductSource(){
        return this.inputText("What's the source");
    }

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

    private List<String> printProductInfo(UUID productId){
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
            List<Pair<UUID, String >> products = this.productList.getAllProducts();
            List<String> tmp = this.productsItemsToStringList(products);
            int index = this.selectionList(tmp, "Select Product Profile");
            if (index >= 0) {
                while (true) {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Product: \n");
                    List<String> info = printProductInfo(products.get(index).getKey());
                    for (String line : info) { System.out.println(line); }
                    System.out.println("Type e to edit product, r to remove product and q to quit.");
                    String move = system.nextLine().toUpperCase().trim();
                    if (move.equals("E")) {
                        int editIndex = this.selectionList(info, "Select Product property to edit");
                        ProductProfile curr = this.productList.getProduct(products.get(index).getKey());
                        switch (editIndex)
                        {
                            case 0: String name = this.inputProductName(); curr.setName(name); break;
                            case 1: List<String> altNames = this.inputProductAltNames();
                            curr.setAltNames(altNames); break;
                            case 2: String category = inputProductCategory(); curr.setCategory(category); break;
                            case 3: String source = this.inputProductSource(); curr.setSource(source); break;
                            case 4: List<Integer> shelfLife = this.inputProductShelfLife(); curr.setShelfLife(shelfLife); break;
                            case 5: String oldSM = curr.getSalesMode(); String newSM = this.inputProductSalesMode();
                            curr.setSalesMode(newSM);
                            if (oldSM.equals(MFVConstants.BATCH) && newSM.equals(MFVConstants.LOOSE)) {
                                curr.setName(curr.getName().replace("([a-zA-Z]*)", ""));
                            } else if (oldSM.equals(MFVConstants.LOOSE) && newSM.equals(MFVConstants.BATCH)) {
                                int i = this.selectionList(MFVConstants.BATCH_PRODUCT_DESCRIPTORS, "Select Batch Type");
                                curr.setName(curr.getName() + " " + MFVConstants.BATCH_PRODUCT_DESCRIPTORS.get(i));
                            }
                            break;
                            case 6: BigDecimal price = inputPrice(); curr.setPrice(price); break;
                        }
                    } else if (move.equals("R")) {
                        this.productList.removeProduct(products.get(index).getKey());
                        break;
                    } else if (move.equals("Q")) {
                        break;
                    }
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
            List<Pair<UUID, String >> allProducts = this.productList.getAllProducts();
            List<String> allProductsB = this.productsItemsToStringList(allProducts);
            int index = this.selectionList(allProductsB, "Select the product type of the item to add");
            UUID productId = allProducts.get(index).getKey();
            double qty = this.getInputQty(productId);
            this.inventory.addItem(this.productList.getProduct(productId), qty);
            this.setMenuIndex("E");
        }
        else
            homeUser();
    }

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

    public Calendar inputDate() {
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
            if (y > 2000 && y < 3000){
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
            List<UUID> itemsId = this.inventory.findItemsByState(MFVConstants.STOCKED);
            List<String> itemsInfo = this.createItemsInfoList(itemsId);
            int index = this.selectionList(itemsInfo, "Select Item");
            if (index >= 0) {
                while (true) {
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
                            case 3: Calendar date = this.inputDate(); curr.setExpiryDate(date); break;
                        }
                    } else if (move.equals("R")) {
                        this.inventory.removeItem(curr.getItemId());
                        break;
                    } else if (move.equals("Q")) {
                        break;
                    }
                }
            }
            this.setMenuIndex("E");
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
