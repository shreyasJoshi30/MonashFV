package Entity;

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
    private String fname;
    private String lname;
    private String dob;
    private String phoneNumber;
    private String email;
    

    /**
     * Constructor for objects of class UserList
     */
    public User(String enterUsername, String enterPassword, String enterFName, String enterLName, String enterDOB,
                    String enterPhoneNumber, String enterEmail)
    {
        username = enterUsername;
        password = enterPassword;
        fname = enterFName;
        lname = enterLName;
        dob = enterDOB;
        phoneNumber = enterPhoneNumber;
        email = enterEmail;
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
    
    public String getFName()
    {
        return fname;
    }
    
    public void setFName(String enterFName)
    {
        fname = enterFName;
    }
    
    public String getLName()
    {
        return lname;
    }
    
    public void setLName(String enterLName)
    {
        lname = enterLName;
    }
    
    public String getDOB()
    {
        return dob;
    }
    
    public void setDOB(String enterDOB)
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
}