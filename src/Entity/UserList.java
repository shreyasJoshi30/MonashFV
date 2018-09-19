package Entity;
import java.util.*;
import java.io.*;
/**
 * Write a description of class UserList here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class UserList
{
    private ArrayList<User> users;

    public UserList()
    {
        users = new ArrayList<>();
    }

    /**
     * return the value of ArrayList<User>.
     * @return the value of ArrayList<User>.
     */ 
    public ArrayList<User> getUsers()
    {
        return users;
    }
    
    public boolean registerUser(String username, String password, String fname, String lname, String dob, 
                            String phoneNumber, String email)
    {
        if (usernameValidation(username) == true)
        {
            getUsers().add(new User(username, password, fname, lname, dob, phoneNumber, email));
            writeNewUserFiles(username, password, fname, lname, dob, phoneNumber, email);
            return true;
        }
        else
            return false;
    }   
    
    public boolean unregisterUser(String username)
    {
        // username exists in the database.
        if (usernameValidation(username) == false)
        {
            unregisterNewUserFiles(username);
            return true;
        }
        else
            return false;
    }
    
    public void viewUserlist()
    {
        String fileName = "Users.txt";
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
    
    public void writeNewUserFiles(String newUsername, String newPassword, String newFName, String newLName,
                                    String newDOB, String newPhoneNumber, String newEmail)
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
            outputFile.println(newUsername + "," + newPassword + "," + newFName + "," + newLName + "," +
                                newDOB + "," + newPhoneNumber + "," + newEmail);
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
            System.out.println(fileName + " not found");
            return false;
        }        
        catch(IOException exception)
        {            
            System.out.println("Unexpected I/O error");
            return false;
        }
    }
    
    public boolean userLogin(String enterUsername, String enterPassword)
    {
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
                        return true;
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
            System.out.println(filename + " not found");
            return false;
        }        
        catch(IOException exception)
        {            
            System.out.println("Unexpected I/O error");
            return false;
        }
    }
}
