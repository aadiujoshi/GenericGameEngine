package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import texelgameengine.game.GameWorld;

public class WorldLoader {

    private WorldLoader(){}

    public static GameWorld loadWorld(String worldname){
        try{
            FileInputStream fileIn = new FileInputStream(new File(worldname));
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            GameWorld gw = (GameWorld)objIn.readObject();

            objIn.close();
            fileIn.close();
            return gw;
        } catch(IOException | ClassCastException | ClassNotFoundException e) {}

        return null;
    }

    public static File getWorldFile(String worldname){
        File[] savedworlds = new File("TexelGameEngine\\src\\worldsaves").listFiles();
        for(File world : savedworlds){
            if((worldname+".txt").equals(world.getName())){
                return world;
            }
        }
        return null;
    }

    public static String createWorldFile(GameWorld world){
        try{
            File newFolder = new File("TexelGameEngine\\src\\worldsaves\\" + world.getWorldName() + "\\");
            newFolder.createNewFile();
            return newFolder.getAbsolutePath();
        } catch(IOException e) {}
        
        return null;
    }

    public static void saveWorld(GameWorld world){
        try{
            FileOutputStream fileOut = new FileOutputStream(new File("TexelGameEngine\\src\\worldsaves"));
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

            objOut.writeObject(world);

            objOut.close();
            fileOut.close();

        } catch(IOException | ClassCastException e){
            System.out.println("Failed to save... creating new file");
            saveWorld(world);
        }
    }

    public static void main(String agsfdsafdas[]){
        
    }
}
