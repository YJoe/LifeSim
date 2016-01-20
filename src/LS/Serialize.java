package LS;

import java.io.*;

/**
 * Serialize consists of static functions allowing the saving and loading of serialised
 * files, used within the SimulationMenu this allows the user to define their own
 * Configuration files
 */
public class Serialize {
    /**
     * A static function used to Serialise the given Configuration with the given name to the defined
     * file path
     * @param configuration Configuration to save
     * @param fileName Name of the configuration to save under
     */
    public static void serialize(Configuration configuration, String fileName){
        // Try catch to check that the file saved correctly
        try{
            // Open the output path
            FileOutputStream fileOut = new FileOutputStream("LifeSim/SavedWorlds/" + fileName + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            // Write the object
            out.writeObject(configuration);
            // Close the stream
            out.close();
            fileOut.close();
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * A static function used to deserialize a given file path returning a Configuration object
     * @param file File path to load
     * @return A Configuration object
     */
    public static Configuration deserialize(File file){
        // Create a configuration file
        Configuration configuration;
        // Try and load the file to the Configuration object
        try {
            // Create the input stream
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            // Store the object
            configuration = (Configuration) in.readObject();
            // Close the input stream
            in.close();
            fileIn.close();
        }
        catch(IOException i){
            i.printStackTrace();
            return null;
        }
        catch(ClassNotFoundException c){
            System.out.println("Configuration class not found");
            c.printStackTrace();
            return null;
        }
        return configuration;
    }
}