package boundary;

import controller.UserList;
import controller.Inventory;
import controller.ProductList;
import controller.ReporterController;
import controller.ShoppingController;

import java.util.*;

public class Menu
{
    private String loginUsername;
    private UserList userList;
    private ReporterController reporterController;
    private Inventory inventory;
    private ShoppingController shoppingController; 
    private ProductList productlist;

    public Menu()
    {
        loginUsername = "";
        userList = new UserList();
        reporterController = new ReporterController();
        inventory = new Inventory();
        shoppingController = new ShoppingController();
        productlist = new ProductList();
    }

    public void main() 
    {
        Scanner system = new Scanner(System.in); 
        String menuIndex = "!";
        do 
        {
            switch (menuIndex)
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
                case "B": homeViewInventory(); break;
                case "B1": homeViewInventoryAddProduct(); break;
                case "B2": homeViewInventoryCheckout(); break;                
                case "C": homeShoppingCart(); break;
                case "C1": homeShoppingCartChangeCart(); break;
                case "C2": homeShoppingCartCheckout(); break;
                case "C3": homeShoppingCartViewInventory(); break;
                case "D": homeManageProfile(); break;
                case "D1": homeManageProfileAddProfile(); break;
                case "D2": homeManageProfileViewProfile(); break;
                case "D3": homeManageProfileEditProfile(); break;
                case "D4": homeManageProfileDeleteProfile(); break;
                case "E": homeManageInventory(); break;
                case "E1": homeManageInventoryAddInventory(); break;
                case "E2": homeManageInventoryViewInventory(); break;
                case "E3": homeManageInventoryEditInventory(); break;
                case "E4": homeManageInventoryDeleteInventory(); break;
                case "X": break;
            }
            System.out.println("Press ! Back to homepage");
            menuIndex = system.nextLine().toUpperCase().trim();
        }while (!menuIndex.equals("X"));
        cloesProgram();
    }

    public void cloesProgram()
    {
        loginUsername = "";
        userList = new UserList();
        reporterController = new ReporterController();
        inventory = new Inventory();
        shoppingController = new ShoppingController();
        productlist = new ProductList();
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
    public void homeViewInventory()
    {
        //Present Inventory List
        System.out.println("");
        Scanner system = new Scanner(System.in); 
        System.out.println("Press B1. Add product to your shopping cart.");
        System.out.println("Press B2. View shopping cart / Checkout");
        System.out.println("");
    }

    //option B1
    public void homeViewInventoryAddProduct()
    {
        System.out.println("");
        Scanner system = new Scanner(System.in); 
        System.out.print("Enter the product ID: ");
        String inventoryID =  system.nextLine().trim();
        System.out.print("Enter amounts: ");
        String amount = system.nextLine().trim();
        //addProduct
        System.out.println("");
        homeViewInventory();
    }

    //option B2
    public void homeViewInventoryCheckout()
    {
        homeShoppingCart();
    }

    //option C
    public void homeShoppingCart()
    {
        //present shoppingcart
        System.out.println("");
        Scanner system = new Scanner(System.in); 
        System.out.println("Press C1. Change Shopping Cart product quantity");
        System.out.println("Press C2. Checkout");
        System.out.println("Press C3. back to Browse products");
        System.out.println("");
    }

    //option C1
    public void homeShoppingCartChangeCart()
    {
        System.out.println("");
        Scanner system = new Scanner(System.in); 
        System.out.print("Enter the product ID: ");
        String inventoryID =  system.nextLine().trim();
        System.out.print("Enter amounts: ");
        String amount = system.nextLine().trim();
        //Alter shoppingcart items
        System.out.println("Your shopping cart has been updated.");
        homeShoppingCart();
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

    //option C3
    public void homeShoppingCartViewInventory()
    {
        homeViewInventory();
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
