package entity;

import java.util.logging.*;
import java.io.*;

/**
 * This class handles file input and output for entity classes. Uses the Serializable interface.
 *
 * @author W0\/E|\| L|_|
 */
public class MFVFileIO {

    private static final Logger logger = Logger.getLogger(MFVFileIO.class.getPackage().getName());

    public MFVFileIO(){}

    /**
     * Function to write a Serializable object to a file
     * @param object Input object.
     * @param filename Name of file to write to.
     */
    public void writeObjectToFile(Object object, String filename) {
        try (
                OutputStream file = new FileOutputStream(filename);
                OutputStream buffer = new BufferedOutputStream(file);
                ObjectOutput output = new ObjectOutputStream(buffer);
        ) {
            output.writeObject(object);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Cannot perform output.", e);
        }
    }

    /**
     * Reads an serialized object from a file.
     * @param filename Name of file to read.
     * @return Object.
     */
    public Object readObjectFromFile(String filename) {
        Object recoveredObject = new Object();
        try(
                InputStream file = new FileInputStream(filename);
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream (buffer);
        ){
            recoveredObject = input.readObject();

        }
        catch(ClassNotFoundException e){
            logger.log(Level.SEVERE, "Cannot perform input. Class not found.", e);
        }
        catch(IOException e){
            logger.log(Level.SEVERE, "Cannot perform input.", e);
        }
        return recoveredObject;
    }



}
