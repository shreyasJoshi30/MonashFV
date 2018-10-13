package entity;
import java.util.Calendar;

/**
 * Store the profile data of the logged in user.
 *
 * @author Hsinhan Chung
 */
public class User
{
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Calendar dob;
    private String phoneNumber;
    private String email;
    private boolean isOwner;
    
    /**
     * Constructor for the class User.
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

    /**
     * return the user's username.
     * @return the value of username.
     */   
    public String getUsername()
    {
        return username;
    }
    
    /**
     * set the user's username.
     * @param enterUsername represents the username to be set.
     */
    public void setUserName(String enterUsername)
    {
        username = enterUsername;
    }
    
    /**
     * return the user's password.
     * @return the value of password.
     */   
    public String getPassword()
    {
        return password;
    }
    
    /**
     * set the user's password.
     * @param enterPassword represents the password to be set.
     */
    public void setPassword(String enterPassword)
    {
        password = enterPassword;
    }
    
    /**
     * return the user's first name.
     * @return the value of first name.
     */  
    public String getFirstName()
    {
        return firstName;
    }
    
    /**
     * set the user's first name.
     * @param enterFirstName represents the password to be set.
     */
    public void setFName(String enterFirstName)
    {
        firstName = enterFirstName;
    }
    
    /**
     * return the user's last name.
     * @return the value of last name.
     */  
    public String getLastName()
    {
        return lastName;
    }
    
    /**
     * set the user's last name.
     * @param enterLastName represents the last name to be set.
     */
    public void setLName(String enterLastName)
    {
        lastName = enterLastName;
    }
    
    /**
     * return the user's date of birth.
     * @return the value of last name.
     */  
    public Calendar getDOB()
    {
        return dob;
    }
    
    /**
     * set the user's date of birth.
     * @param enterDOB represents the date of birth to be set.
     */
    public void setDOB(Calendar enterDOB)
    {
        dob = enterDOB;
    }
    
    /**
     * return the user's phone number.
     * @return the value of phone number.
     */  
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    
    /**
     * set the user's phone number.
     * @param enterPhoneNumber represents the phone number to be set.
     */
    public void setPhoneNumber(String enterPhoneNumber)
    {
        phoneNumber = enterPhoneNumber;
    }
    
    /**
     * return the user's email.
     * @return the value of email.
     */  
    public String getEmail()
    {
        return email;
    }
    
    /**
     * set the user's email.
     * @param enterEmail represents the email to be set.
     */
    public void setEmail(String enterEmail)
    {
        email = enterEmail;
    }
    
    /**
     * return the identity of owner.
     * @return the validity of owner.
     */  
    public boolean getIsOwner()
    {
        return isOwner;
    }
    
    /**
     * set the user's owner identity.
     * @param isOwner represents the user's owner identity to be set.
     */
    public void setIsOwner(boolean isOwner)
    {
        this.isOwner = isOwner;
    }
}