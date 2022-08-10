package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;

import texelgameengine.game.GameWorld;

public class WorldLoader {

    private WorldLoader(){}

    public static GameWorld loadWorld(String filepath){
        try{
            FileInputStream fileIn = new FileInputStream(new File(filepath));
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            GameWorld gw = (GameWorld)objIn.readObject();

            objIn.close();
            fileIn.close();
            return gw;
        } catch(IOException | ClassCastException | ClassNotFoundException e) {}

        return null;
    }

    public static String createWorldFile(GameWorld world){
        try{
            File newFolder = new File("\\worldsaves\\" + world.getWorldName());
            newFolder.createNewFile();
        } catch(IOException e) {}
        return "\\worldsaves\\" + world.getWorldName();
    }

    public static void saveWorld(GameWorld world, String filepath){
        try{
            FileOutputStream fileOut = new FileOutputStream(new File(filepath));
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

            objOut.writeObject(world);

            objOut.close();
            fileOut.close();

        } catch(IOException | ClassCastException e){
            saveWorld(world, createWorldFile(world));
        }
    }

    public static void main(String agsfdsafdas[]){
        
    }
}
