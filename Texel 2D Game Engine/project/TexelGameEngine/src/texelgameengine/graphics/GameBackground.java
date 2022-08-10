package texelgameengine.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import texelgameengine.game.GameWorld;

public class GameBackground extends PaintableObject
{
    private BufferedImage bgTexture;
    private Color bgColor;
    /**
     * Ratio of Background scroll to xOffset
     */
    private double scrollCoefficient;
    
    /**
     * Constructs a background that is displayed in the game. {@code voidTexture} is the default texture for unoccupied space
     * in the background.
     * @throws IOException
     */
    public GameBackground(String bgTextureFilepath, Color bgColor, double scrollCoefficient) throws IOException {
        super(bgTextureFilepath != null ? ImageIO.read(new File(bgTextureFilepath)) : null);
        
        if(bgTextureFilepath != null){
            try { bgTexture = ImageIO.read(new File(bgTextureFilepath)); } 
            catch (IOException e) { e.printStackTrace(); }
        }
        this.scrollCoefficient = scrollCoefficient;
        this.bgColor = bgColor;
    }

    @Override
    public void paintObject(GraphicsEvent e){
        Graphics2D g = e.getGraphics();

        if(bgColor != null){
            g.setColor(bgColor);
            g.fillRect(0, 0, e.getScreenWidth(), e.getScreenHeight());
        }

        if(bgTexture != null){
            Point center = GameWorld.toScreenSpace(0, 0, e);

            int rx = (center.x-bgTexture.getWidth()/2)+(int)(e.getXOffset()*scrollCoefficient);
            int ry = (center.y-bgTexture.getHeight()/2)-(int)(e.getYOffset()*scrollCoefficient);

            //center
            g.drawImage(bgTexture, null, rx, ry);
            //left 
            g.drawImage(bgTexture, null, rx-bgTexture.getWidth(), ry);
            //right
            g.drawImage(bgTexture, null, rx+bgTexture.getWidth(), ry);
        }
    }
}
