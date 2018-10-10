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
    private UserList loginUser;
    private ReporterController reporterController;
    private Inventory inventory;
    private ShoppingController shoppingController; 
    private ProductList productlist;
    
    public Menu()
    {
        loginUsername = "";
        loginUser = new UserList();
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
                case "X": System.out.println("See you"); break;
            }
            menuIndex = system.nextLine().toUpperCase().trim();
        }while (!menuIndex.equals("X"));
        cloesProgram();
    }
    
    public void home()
    {
        System.out.println("Welcome to Monash FV Store.");
        System.out.println("");
        System.out.println("Press A. Account");
        System.out.println("Press B. Browse our products");
        System.out.println("Press C. View your shopping");      
        if (loginUser.isOwnerLogin(loginUsername))
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
        if (!loginUser.isMemberLogin(loginUsername) && !loginUser.isOwnerLogin(loginUsername))
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
        if (loginUser.isOwnerLogin(loginUsername))
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
        System.out.println("This is Log in page");        
        Scanner system = new Scanner(System.in); 
        System.out.print("Please enter your username: ");        
        String enterUsername = system.nextLine().trim();
        System.out.print("Please enter your password: ");
        String enterPassword = system.nextLine().trim();
        if (loginUser.userLogin(enterUsername, enterPassword))
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
    
    //option A2
    public void homeUserMemberSignup()
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
        if (loginUser.registerMember(username, password, firstName, lastName, dob, 
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
    
    //option A3
    public void homeUserChangePassword()
    {
        Scanner system = new Scanner(System.in); 
        System.out.print("Please enter your new password: "); 
        String newPassword = system.nextLine().trim();
        if (loginUser.changePassword(loginUsername, newPassword))
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
    
    //option A4
    public void homeUserChangeEmail()
    {
        Scanner system = new Scanner(System.in); 
        System.out.print("Please enter your new email: "); 
        String newEmail = system.nextLine().trim();
        if (loginUser.changeEmail(loginUsername, newEmail))
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
    
    //option A5
    public void homeUserChangePhoneNumber()
    {
        Scanner system = new Scanner(System.in); 
        System.out.print("Please enter your new phone number: "); 
        String newPhoneNumber = system.nextLine().trim();
        if (loginUser.changePhoneNumber(loginUsername, newPhoneNumber))
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
    
    //option A6
    public void homeUserCurrentUnregister()
    {
        if (loginUser.unregisterUser(loginUsername))
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
    
    //option A7
    public void homeUserLogout()
    {
        if (loginUser.userLogout(loginUsername))
        {
            loginUsername = "";
            System.out.println("");
            System.out.println("Log out successfully.");
            homeUser();
        }
        else
        {
            System.out.println("");
            System.out.println("Error! Please try again.");
            homeUser();
        }        
    }
    
    //option A8
    public void homeUserOwnerSignup()
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
        if (loginUser.registerMember(username, password, firstName, lastName, dob, 
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
    
    //option A9
    public void homeUserViewUserlist()
    {
        loginUser.viewUserlist();
        homeUser();
    }
    
    //option A10
    public void homeUserUnregisterUser()
    {
        System.out.println("User List:");
        System.out.println("");
        loginUser.viewUserlist();
        System.out.println("");
        Scanner system = new Scanner(System.in); 
        System.out.print("Please enter the username of the account to be deleted: ");        
        String username = system.nextLine().trim();
        if (loginUser.unregisterUser(username))
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
    
    public void cloesProgram()
    {
        loginUsername = "";
        loginUser = new UserList();
        reporterController = new ReporterController();
        inventory = new Inventory();
        shoppingController = new ShoppingController();
        productlist = new ProductList();
        System.out.println("See you next time !");
    }
}
