package texelgameengine.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;

import main.TextureResourceLoader;
import texelgameengine.game.GameWorld;

public class GameBackground extends PaintableObject
{
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
    public GameBackground(String bgtexturefilename, Color bgColor, double scrollCoefficient) {
        super(bgtexturefilename != null ? TextureResourceLoader.getTexture(bgtexturefilename) : null);
        
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

        if(getTexture() != null){
            Point center = GameWorld.toScreenSpace(0, 0, e);

            int rx = (center.x-getTexture().getWidth()/2)+(int)(e.getXOffset()*scrollCoefficient);
            int ry = (center.y-getTexture().getHeight()/2)-(int)(e.getYOffset()*scrollCoefficient);

            //center
            g.drawImage(getTexture(), null, rx, ry);
            //left 
            g.drawImage(getTexture(), null, rx-getTexture().getWidth(), ry);
            //right
            g.drawImage(getTexture(), null, rx+getTexture().getWidth(), ry);
        }
    }
}
