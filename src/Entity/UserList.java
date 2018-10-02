package Entity;
import java.util.*;
import java.io.*;
import java.util.*;
import java.io.*;

public class UserList
{
    private HashMap<String, User> users;
    private String fileName;

    public UserList()
    {
        users = new HashMap<>();
        fileName = "Users.txt";
    }
    
    public boolean registerOwner(String username, String password, String firstName, String lastName, String dob, 
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
    
    public boolean registerMember(String username, String password, String firstName, String lastName, String dob, 
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
            System.out.println("Error. File was not saved.");
        }
    }
    
    public void unregisterNewUserFiles(String unregisterUsername)
    {
        try
        {
            FileReader inputFile = new FileReader(fileName);
            Scanner fileReader = new Scanner(inputFile);
            int userIndex = 0;            
            ArrayList<String> lineArray = new ArrayList<>();
            while (fileReader.hasNextLine())
            {
                String line = fileReader.nextLine();
                String lineSpliter[] = line.split(",");
                if (!lineSpliter[0].equals(unregisterUsername))
                    lineArray.add(line);                
                userIndex++;
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
            System.out.println("Error. File was not saved.");
        }
    }  
    
    public void writeNewUserFiles(String newUsername, String newPassword, String newFirstName, String newLastName,
                                    String newDOB, String newPhoneNumber, String newEmail, String newIsOwner)
    {
        String fileName = "Users.txt";
        try
        {
            FileReader inputFile = new FileReader(fileName);
            Scanner fileReader = new Scanner(inputFile);
            int userIndex = 0;            
            ArrayList<String> lineArray = new ArrayList<>();
            while (fileReader.hasNextLine())
            {
                String line = fileReader.nextLine();
                lineArray.add(line);                
                userIndex++;
            }
            PrintWriter outputFile = new PrintWriter(fileName);
            for (int index = 0; index < lineArray.size(); index++)
            {
                outputFile.println(lineArray.get(index));
            }            
            outputFile.println(newUsername + "," + newPassword + "," + newFirstName + "," + newLastName + "," +
                                newDOB + "," + newPhoneNumber + "," + newEmail+ "," + newIsOwner);
            outputFile.close();
        }            
        catch(IOException e)
        {
            System.out.println("Error. File was not saved.");
        }
    }    
    
    public boolean usernameValidation(String newUsername)
    {
        String fileName = "Users.txt";
        try
        {
            FileReader inputFile = new FileReader(fileName);
            try 
            {
                Scanner fileReader = new Scanner(inputFile);
                int userIndex = 0;
                while (fileReader.hasNextLine())
                {
                    String line = fileReader.nextLine();                     
                    String lineArray[] = line.split(",");
                    if (lineArray[0].equals(newUsername))
                        return false;
                    userIndex++;
                }
                inputFile.close();
                return true;
            }
            catch(IOException exception)
            {
                return false;
            }
        }
        catch(FileNotFoundException exception)
        {
            //throw new FileNotFoundException(fileName + " not found");
            return false;
        }        
        catch(IOException exception)
        {            
            System.out.println("Unexpected I/O error");
            return false;
        }
    }
    
    public boolean checkOwnerLogin(String enterUsername)
    {
        if (users.containsKey(enterUsername))
        {
            if (users.get(enterUsername).getIsOwner())
                return true;
            else 
                return false;
        }
        else
            return false;
    }
    
    public boolean checkMemberLogin(String enterUsername)
    {
        if (users.containsKey(enterUsername))
        {
            if (!users.get(enterUsername).getIsOwner())
                return true;
            else 
                return false;
        }
        else
            return false;
    }
    
    public boolean userLogin(String enterUsername, String enterPassword)
    {
        if (users.containsKey(enterUsername))
            return true;
        String filename = "Users.txt";
        try
        {
            FileReader inputFile = new FileReader(filename);
            try 
            {
                Scanner fileReader = new Scanner(inputFile);
                int userIndex = 0;
                while (fileReader.hasNextLine())
                {
                    String line = fileReader.nextLine();                     
                    String lineArray[] = line.split(",");
                    if (lineArray[0].equals(enterUsername) && lineArray[1].equals(enterPassword))
                    {
                        users.put(lineArray[0], new User(lineArray[0], lineArray[1], lineArray[2], lineArray[3], lineArray[4], lineArray[5], lineArray[6], Boolean.valueOf(lineArray[7])));
                        return true;
                    }                        
                    userIndex++;
                }
                inputFile.close();
                return false;
            }
            catch(IOException exception)
            {
                return false;
            }
        }
        catch(FileNotFoundException exception)
        {
            return false;
        }        
        catch(IOException exception)
        {            
            return false;
        }
    }
    
    public boolean userLogout(String enterUsername)
    {               
        try
        {
            users.remove(enterUsername);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }        
    }
}