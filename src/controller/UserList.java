package controller;
import Entity.User;
import java.util.*;
import java.io.*;

public class UserList
{
    private HashMap<String, User> currentUsers;
    private String fileName;

    public UserList()
    {
        currentUsers = new HashMap<>();
        fileName = "Users.txt";
    }
    
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
            //throw new Exception(" not found", exception);
            throw new RuntimeException("error");
        }        
        catch(IOException exception)
        {            
            //throw new IOException("Cannot find");
            throw new RuntimeException("error");
        }
    }
    
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