package controller;
import Entity.UserList;

public class UserController
{
    private UserList users;
    /**
     * Constructor for objects of class UserController
     */
    public UserController()
    {
        users = new UserList();
    }
    
    public boolean registerOwner(String username, String password, String firstName, String lastName, 
                            String dob, String phoneNumber, String email)
    {
        return (users.registerOwner(username, password, firstName, lastName, dob, phoneNumber, email) == true ?
                    true : false);
    }
    
    public boolean unregisterOwner(String username)
    {
        return (users.unregisterUser(username) == true ? true : false);
    }
    
    public boolean registerCustomer(String username, String password, String firstName, String lastName, 
                            String dob, String phoneNumber, String email)
    {
        return (users.registerMember(username, password, firstName, lastName, dob, phoneNumber, email) == true ? 
                true : false);
    }
    
    public boolean unregisterCustomer(String username)
    {
        return (users.unregisterUser(username) == true ? true : false);
    }
    
    public boolean login(String enterUsername, String enterPassword)
    {
        return (users.userLogin(enterUsername, enterPassword) == true ? true : false);
    }
    
    public boolean logout(String enterUsername)
    {
        return (users.userLogout(enterUsername) == true ? true : false);
    }
    
    public boolean checkMemberLogin(String enterUsername)
    {
        return (users.checkMemberLogin(enterUsername) == true? true : false);
    }
    
    public boolean checkOwnerLogin(String enterUsername)
    {
        return (users.checkOwnerLogin(enterUsername) == true ?  true : false);
    }
}