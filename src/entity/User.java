package entity;
import java.util.Calendar;
/**
 * Write a description of class UserList here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class User
{
    // instance variables - replace the example below with your own
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Calendar dob;
    private String phoneNumber;
    private String email;
    private boolean isOwner;
    
    /**
     * Constructor for objects of class UserList
     */
    public User(String enterUsername, String enterPassword, String enterFName, String enterLName, Calendar enterDOB,
                    String enterPhoneNumber, String enterEmail, boolean isOwner)
    {
        username = enterUsername;
        password = enterPassword;
        firstName = enterFName;
        lastName = enterLName;
        dob = enterDOB;
        phoneNumber = enterPhoneNumber;
        email = enterEmail;
        this.isOwner = isOwner;
    }

    public String getUsername()
    {
        return username;
    }
    
    public void setUserName(String enterUsername)
    {
        username = enterUsername;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String enterPassword)
    {
        password = enterPassword;
    }
    
    public String getFirstName()
    {
        return firstName;
    }
    
    public void setFName(String enterFName)
    {
        firstName = enterFName;
    }
    
    public String getLastName()
    {
        return lastName;
    }
    
    public void setLName(String enterLName)
    {
        lastName = enterLName;
    }
    
    public Calendar getDOB()
    {
        return dob;
    }
    
    public void setDOB(Calendar enterDOB)
    {
        dob = enterDOB;
    }
    
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String enterPhoneNumber)
    {
        phoneNumber = enterPhoneNumber;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String enterEmail)
    {
        email = enterEmail;
    }
    
    public boolean getIsOwner()
    {
        return isOwner;
    }
    
    public void setIsOwner(boolean isOwner)
    {
        this.isOwner = isOwner;
    }
}