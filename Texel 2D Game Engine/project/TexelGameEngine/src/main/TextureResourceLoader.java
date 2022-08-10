package main;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class TextureResourceLoader {
    static HashMap<String, BufferedImage> loadedTextures = new HashMap<>();

    private TextureResourceLoader(){}

    public static void loadTexturesFromPath(File path){
        File[] dirFiles = path.listFiles();

        for(int i = 0; i < dirFiles.length; i++){
            String absPath = dirFiles[i].getAbsolutePath();
            
            if(absPath.contains(".png") || absPath.contains(".jpg")){
                try{
                    String ref = absPath.substring(absPath.lastIndexOf("\\")+1);
                    BufferedImage texture = ImageIO.read(dirFiles[i]);
                    
                    loadedTextures.put(ref, texture);
                } catch(IOException e) {}
            }
        }
    }

    /**
     * give filename with type (hello.png) NOT a file path/directory
     * memory is saved by giving a preloaded BufferedImage reference
     * If the file isnt found the default texture is returned.
     */
    public static BufferedImage getTexture(String filename){
        return loadedTextures.get(filename) != null ? 
                    loadedTextures.get(filename) : 
                    loadedTextures.get("defaulttexture.png");
    }
}
