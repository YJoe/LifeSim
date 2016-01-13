package LS;

import java.io.*;

public class Serialize {
    public static void serialize(Configuration configuration, String fileName){
        try{
            FileOutputStream fileOut = new FileOutputStream("SavedWorlds/" + fileName + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(configuration);
            out.close();
            fileOut.close();
            System.out.println("World saved");
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    public static Configuration deserialize(String name){
        Configuration configuration;
        try {
            FileInputStream fileIn = new FileInputStream("Saved Worlds/" + name);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            configuration = (Configuration) in.readObject();
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