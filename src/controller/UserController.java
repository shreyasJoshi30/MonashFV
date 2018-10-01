package controller;
import Entity.UserList;
/**
 * Write a description of class UserController here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class UserController
{
    /**
     * Constructor for objects of class UserController
     */
    public UserController(){}
    
    public void registerOwner(UserList users, String username, String password, String firstName, String lastName, String dob, 
                            String phoneNumber, String email)
    {
        users.registerOwner(username, password, firstName, lastName, dob, phoneNumber, email);
    }
    
    public void unregisterOwner(UserList users, String username)
    {
        users.unregisterUser(username);
    }
    
    public void registerCustomer(UserList users, String username, String password, String firstName, String lastName, String dob, 
                            String phoneNumber, String email)
    {
        users.registerMember(username, password, firstName, lastName, dob, phoneNumber, email);
    }
    
    public void unregisterCustomer(UserList users, String username)
    {
        users.unregisterUser(username);
    }
    
    public void login(UserList users, String enterUsername, String enterPassword)
    {
        users.userLogin(enterUsername, enterPassword);
    }
    
    public void logout(UserList users, String enterUsername)
    {
        users.userLogout(enterUsername);
    }
    
    public void validateUser(UserList users, String enterUsername)
    {
        users.validateUser(enterUsername);
    }
}