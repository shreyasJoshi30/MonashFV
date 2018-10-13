package controller;
import java.util.*;
import entity.User;
import java.io.*;

/**
 * Implement the UserList which contains Users.
 * Provide functions to manage the Users.
 *
 * @author Hsinhan Chung
 */
public class UserList
{
    private HashMap<String, User> currentUsers;
    private String fileName;

    /**
     * Constructor for the UserList.
     */
    public UserList()
    {
        currentUsers = new HashMap<>();
        fileName = "Users.txt";
    }
    
    /**
     * return the value of currentUsers.
     * @return the value of currentUsers.
     */   
    public HashMap<String, User> getCurrentUsers()
    {
        return currentUsers;
    }
    
    /**
     * set the fileName to be read.
     * @param enterFileName represents the file name to be read.
     */ 
    public void setFileName(String enterFileName)
    {
        fileName = enterFileName;
    }
    
    /**
     * return the value of fileName.
     * @return the value of fileName.
     */  
    public String getFileName()
    {
        return fileName;
    }
    
    /**
     * register owner.
     * @param username represents the username of the account.
     * @param password represents the password of the account.
     * @param firstName represents the first name of the owner.
     * @param lastName represents the last name of the owner.
     * @param dob represents the date of birth of the owner.
     * @param phoneNumber represents the phone number of the owner.
     * @param email represents the email of the owner.
     * @return the result of the execution.
     */
    public boolean registerOwner(String username, String password, String firstName, String lastName, Calendar dob, 
                            String phoneNumber, String email)
    {
        String isOwner = "true";       
        if (usernameValidation(username) == true)
        {
            writeNewUserFiles(username, password, firstName, lastName, dob, phoneNumber, email, isOwner);
            return true;
        }
        else
            return false;
    }   
    
    /**
     * register member.
     * @param username represents the username of the account.
     * @param password represents the password of the account.
     * @param firstName represents the first name of the member.
     * @param lastName represents the last name of the member.
     * @param dob represents the date of birth of the member.
     * @param phoneNumber represents the phone number of the member.
     * @param email represents the email of the member.
     * @return the result of the execution.
     */
    public boolean registerMember(String username, String password, String firstName, String lastName, Calendar dob, 
                            String phoneNumber, String email)
    {
        String isOwner = "false";
        if (usernameValidation(username) == true)
        {
            writeNewUserFiles(username, password, firstName, lastName, dob, phoneNumber, email, isOwner);
            return true;
        }
        else
            return false;
    }   
    
    /**
     * unregister user. (both owner account and member account)
     * @param username represents the username of the account to be deleted.
     * @return the result of the execution.
     */
    public boolean unregisterUser(String username)
    {
        if (usernameValidation(username) == false) // username existance in the database.
        {
            unregisterNewUserFiles(username);
            return true;
        }
        else
            return false;
    }
    
    /**
     * view the user file.
     */
    public void viewUserlist()
    {
        try
        {
            FileReader inputFile = new FileReader(fileName);
            Scanner fileReader = new Scanner(inputFile);        
            while (fileReader.hasNextLine())
            {
                String line = fileReader.nextLine();
                String lineArray[] = line.split(",");
                for (int index = 0; index < lineArray.length; index++)
                {
                    System.out.print(lineArray[index] + "\t");
                }      
                System.out.println("");
            }
        }            
        catch(IOException e)
        {
            throw new RuntimeException("error");
        }
    }
    
    /**
     * write new user file with one account eliminate.
     * @param unregisterUsername represents the account to be eliminated.
     */
    public void unregisterNewUserFiles(String unregisterUsername)
    {
        try
        {
            FileReader inputFile = new FileReader(fileName);
            Scanner fileReader = new Scanner(inputFile);       
            ArrayList<String> lineArray = new ArrayList<>();
            while (fileReader.hasNextLine())
            {
                String line = fileReader.nextLine();
                String lineSpliter[] = line.split(",");
                if (!lineSpliter[0].equals(unregisterUsername))
                    lineArray.add(line);          
            }
            PrintWriter outputFile = new PrintWriter(fileName);
            for (int index = 0; index < lineArray.size(); index++)
            {
                outputFile.println(lineArray.get(index));
            }
            outputFile.close();
        }            
        catch(IOException e)
        {
            throw new RuntimeException("error");
        }
    }  
    
    /**
     * write new user file with one account created.
     * @param newUsername represents the username of the account.
     * @param newPassword represents the password of the account.
     * @param newFirstName represents the first name of the account.
     * @param newLastName represents the last name of the account.
     * @param newDOB represents the date of birth of the account.
     * @param newPhoneNumber represents the phone number of the account.
     * @param newEmail represents the email of the account.
     * @param newIsOwner represents the identity of the account.
     */
    public void writeNewUserFiles(String newUsername, String newPassword, String newFirstName, String newLastName,
                                    Calendar newDOB, String newPhoneNumber, String newEmail, String newIsOwner)
    {
        try
        {
            FileReader inputFile = new FileReader(fileName);
            Scanner fileReader = new Scanner(inputFile);       
            ArrayList<String> lineArray = new ArrayList<>();
            while (fileReader.hasNextLine())
            {
                String line = fileReader.nextLine();
                lineArray.add(line);              
            }
            PrintWriter outputFile = new PrintWriter(fileName);
            for (int index = 0; index < lineArray.size(); index++)
            {
                outputFile.println(lineArray.get(index));
            }            
            String dobInsert = newDOB.get(Calendar.YEAR) + "/" + newDOB.get(Calendar.MONTH) + "/" + newDOB.get(Calendar.DATE);
            outputFile.println(newUsername + "," + newPassword + "," + newFirstName + "," + newLastName + "," +
                                dobInsert + "," + newPhoneNumber + "," + newEmail+ "," + newIsOwner);
            outputFile.close();
        }            
        catch(IOException e)
        {
            throw new RuntimeException("error");
        }
    }    
    
    /**
     * validate the availablity of the new username.
     * @param newUsername represents the username to be validated.
     * @return the validity of the new username. 
     */
    public boolean usernameValidation(String newUsername) 
    {
        try
        {
            FileReader inputFile = new FileReader(fileName);
            try
            {
                Scanner fileReader = new Scanner(inputFile);
                while (fileReader.hasNextLine())
                {
                    String line = fileReader.nextLine();                     
                    String lineArray[] = line.split(",");
                    if (lineArray[0].equals(newUsername))
                        return false;
                }
                inputFile.close();
                return true;
            }
            catch(IOException exception)
            {
                throw exception;
            }
        }
        catch(FileNotFoundException exception)
        {
            throw new RuntimeException("error");
        }        
        catch(IOException exception)
        {            
            throw new RuntimeException("error");
        }
    }
    
    /**
     * validate the user is a owner.
     * @param enterUsername represents the username to be validated.
     * @return the validity of owner.
     */
    public boolean isOwnerLogin(String enterUsername)
    {
        if (currentUsers.containsKey(enterUsername))
        {
            if (currentUsers.get(enterUsername).getIsOwner())
                return true;
            else 
                return false;
        }
        else
            return false;
    }
    
    /**
     * validate the user is a member.
     * @param enterUsername represents the username to be validated.
     * @return the validity of member.
     */
    public boolean isMemberLogin(String enterUsername)
    {
        if (currentUsers.containsKey(enterUsername))
        {
            if (!currentUsers.get(enterUsername).getIsOwner())
                return true;
            else 
                return false;
        }
        else
            return false;
    }
    
    /**
     * Log in function for all users.
     * @param enterUsername represents the username to be logged in.
     * @param enterPassword represents the password to be logged in
     * @return the result of login function.
     */
    public boolean userLogin(String enterUsername, String enterPassword)
    {
        if (currentUsers.containsKey(enterUsername))
            return true;
        try
        {
            FileReader inputFile = new FileReader(fileName);
            try 
            {
                Scanner fileReader = new Scanner(inputFile);
                while (fileReader.hasNextLine())
                {
                    String line = fileReader.nextLine();                     
                    String lineArray[] = line.split(",");
                    if (lineArray[0].equals(enterUsername) && lineArray[1].equals(enterPassword))
                    {
                        String intArray[] =lineArray[4].split("/");
                        Calendar dob = Calendar.getInstance();
                        dob.clear();                        
                        dob.set(Calendar.YEAR, Integer.parseInt(intArray[0]));
                        dob.set(Calendar.MONTH, Integer.parseInt(intArray[1]));
                        dob.set(Calendar.DATE, Integer.parseInt(intArray[2]));
                        currentUsers.put(lineArray[0], new User(lineArray[0], lineArray[1], lineArray[2], lineArray[3], dob, lineArray[5], lineArray[6], Boolean.valueOf(lineArray[7])));
                        return true;
                    }                        
                }
                inputFile.close();
                return false;
            }
            catch(IOException exception)
            {
                throw exception;
            }
        }
        catch(FileNotFoundException exception)
        {
            throw new RuntimeException("error");
        }        
        catch(IOException exception)
        {            
            throw new RuntimeException("error");
        }
    }
    
    /**
     * Log out function for all users.
     * @param enterUsername represents the username account to be logged out.
     * @return the result of logout function.
     */
    public boolean userLogout(String enterUsername)
    {   
        try
        {
            currentUsers.remove(enterUsername);
            return true;
        }
        catch(Exception e)
        {
            throw e;
        }        
    }
    
    /**
     * Change phone number of the user profile.
     * @param enterUsername represents the username account profile to be altered.
     * @param newPhoneNumber represents the new phone number to be replaced.
     * @return the result of change Phone Number.
     */
    public boolean changePhoneNumber(String enterUsername, String newPhoneNumber)
    {
        if (isOwnerLogin(enterUsername) || isMemberLogin(enterUsername))
        {
            currentUsers.get(enterUsername).setPhoneNumber(newPhoneNumber);
            String profile[] = getProfile(enterUsername);
            Calendar tempDOB = currentUsers.get(enterUsername).getDOB();
            unregisterNewUserFiles(enterUsername);
            writeNewUserFiles(profile[0], profile[1], profile[2], profile[3], tempDOB, profile[5], profile[6], profile[7]);
            return true;
        }            
        else
            return false;
    }
    
    /**
     * Change email of the user profile.
     * @param enterUsername represents the username account profile to be altered.
     * @param newEmail represents the new emailr to be replaced.
     * @return the result of change email.
     */
    public boolean changeEmail(String enterUsername, String newEmail)
    {
        if (isOwnerLogin(enterUsername) || isMemberLogin(enterUsername))
        {
            currentUsers.get(enterUsername).setEmail(newEmail);
            String profile[] = getProfile(enterUsername);
            Calendar tempDOB = currentUsers.get(enterUsername).getDOB();
            unregisterNewUserFiles(enterUsername);
            writeNewUserFiles(profile[0], profile[1], profile[2], profile[3], tempDOB, profile[5], profile[6], profile[7]);
            return true;
        }            
        else
            return false;
    }
    
    /**
     * Change password of the user profile.
     * @param enterUsername represents the username account profile to be altered.
     * @param newPassword represents the new password to be replaced.
     * @return the result of change password.
     */
    public boolean changePassword(String enterUsername, String newPassword)
    {
        if (isOwnerLogin(enterUsername) || isMemberLogin(enterUsername))
        {
            currentUsers.get(enterUsername).setPassword(newPassword);
            String profile[] = getProfile(enterUsername);
            Calendar tempDOB = currentUsers.get(enterUsername).getDOB();
            unregisterNewUserFiles(enterUsername);
            writeNewUserFiles(profile[0], profile[1], profile[2], profile[3], tempDOB, profile[5], profile[6], profile[7]);
            return true;
        }            
        else
            return false;
    }
    
    /**
     * Get the user profile.
     * @param enterUsername represents the username account to be retrieved.
     * @return a array of user profile.
     */
    public String[] getProfile(String enterUsername)
    {
        String profile[] = new String[8];
        profile[0] = currentUsers.get(enterUsername).getUsername();
        profile[1] = currentUsers.get(enterUsername).getPassword();
        profile[2] = currentUsers.get(enterUsername).getFirstName();
        profile[3] = currentUsers.get(enterUsername).getLastName();
        profile[5] = currentUsers.get(enterUsername).getPhoneNumber();
        profile[6] = currentUsers.get(enterUsername).getEmail();
        profile[7] = String.valueOf(currentUsers.get(enterUsername).getIsOwner());
        return profile;
    }
}