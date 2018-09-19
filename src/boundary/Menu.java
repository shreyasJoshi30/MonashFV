package boundary;

import java.io.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
	
	
	public static void main(String[] args)
    {
        String filename = "members.txt";
        try
        {
            FileReader inputFile = new FileReader(filename);
            try 
            {
                Scanner fileReader = new Scanner(inputFile);
                int memberIndex = 0;
                while (fileReader.hasNextLine())
                {
                    String line = fileReader.nextLine();                     
                    String lineArray[] = line.split(",");
                    System.out.println(lineArray[0]);
                    System.out.println(lineArray[1]);
                    //addAccount(lineArray[0]);
                    //getTeams().get(teamIndex).setRanking(Integer.valueOf(lineArray[1]));
                    memberIndex++;
                }
            }
            finally
            {
                inputFile.close();
            }
        }
        catch(FileNotFoundException exception)
        {
            System.out.println(filename + " not found");
        }        
        catch(IOException exception)
        {            
            System.out.println("Unexpected I/O error");
        }
    }

}
