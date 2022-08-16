package main;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class TextureResourceLoader {
    static HashMap<String, BufferedImage> loadedTextures = new HashMap<>();

    private TextureResourceLoader(){}

    public static void loadTexturesFromPath(File path){
        File[] dirFiles = path.listFiles();

        Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();

        double scaleWidth = screenDimensions.getWidth()/1920;
        double scaleHeight = screenDimensions.getHeight()/1080;

        // System.out.println(Toolkit.getDefaultToolkit().getScreenResolution());

        for(int i = 0; i < dirFiles.length; i++){
            String absPath = dirFiles[i].getAbsolutePath();
            
            if(absPath.contains(".png") || absPath.contains(".jpg")){
                try{
                    // String ref = absPath.substring(absPath.lastIndexOf("\\")+1);
                    String ref = dirFiles[i].getName();

                    BufferedImage unscaledTexture = ImageIO.read(dirFiles[i]);

                    Image scaledImage = unscaledTexture.getScaledInstance((int)(unscaledTexture.getWidth()*scaleWidth), (int)(unscaledTexture.getHeight()*scaleHeight), Image.SCALE_SMOOTH);
                    BufferedImage texture = new BufferedImage((int)(unscaledTexture.getWidth()*scaleWidth), (int)(unscaledTexture.getHeight()*scaleHeight), BufferedImage.TYPE_INT_ARGB);

                    Graphics2D g = texture.createGraphics();

                    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
                    g.drawImage(scaledImage, 0, 0, null);

                    System.out.println(absPath);
                    // System.out.println(texture.getWidth() + "  " + texture.getHeight());
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
