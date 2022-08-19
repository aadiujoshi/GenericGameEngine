package texelgameengine.graphics;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import texelgameengine.game.GameWorld;

public class GameAnimation extends PaintableObject
{
    public static final int DURATION_FOREVER = -1;
    private final int ANIMATION_HASH_CODE;

    private BufferedImage[] frames;
    private int x;  //center of animations
    private int y;
    private final long INIT_TIME;
    private int frameIndex = 0;
    private final int MAX_CYCLES;
    private final int DELAY_MILLIS;
    private boolean expired = false;
    // private boolean registeredInPipeline = false;

    /**
     * delay is in milliseconds
     */
    public GameAnimation(int x, int y, String[] filepaths, int maxCycles, int delayMillis, int zLayer)
    {
        super();
        this.x = x;
        this.y = y;
        this.MAX_CYCLES = maxCycles;
        this.DELAY_MILLIS = delayMillis;
        this.INIT_TIME = System.currentTimeMillis();

        BufferedImage[] frames = new BufferedImage[filepaths.length];

        for(int i = 0; i < filepaths.length; i++)
            try { frames[i] = ImageIO.read(new File(filepaths[i])); } 
            catch (IOException e) { e.printStackTrace(); }
            
        this.frames = frames;
        this.ANIMATION_HASH_CODE = GameWorld.getNextFreeHashCode();
    }

    @Override
    public void paintObject(GraphicsEvent e) {
        if(expired) return;
        if(MAX_CYCLES != GameAnimation.DURATION_FOREVER)
            if((int)((frameIndex % frames.length) / frames.length) > MAX_CYCLES){ 
                expired = true;
                return;
            }
        
        if((INIT_TIME-e.getPaintTimeMillis()) % DELAY_MILLIS < DELAY_MILLIS)
            frameIndex++;

        Graphics2D g = e.getGraphics();

        BufferedImage b = frames[frameIndex % frames.length];
        Point p = GameWorld.toScreenSpace(x, y, e);

        g.drawImage(b, null, (int)p.getX()-(b.getWidth()/2), (int)p.getY()-(b.getHeight()/2));

        // else if((int)((a.getframeIndex()%a.getFrames().length)/a.getFrames().length) < a.getMaxCycles()){
        //     a.paintObject(e);
        //     if((a.getInitTime()-currentTime)%a.getDelay() < 250) //10 millis of error
        //         a.incframeIndex();
    }
    
    @Override
    public boolean equals(Object other) {
        return this.ANIMATION_HASH_CODE == ((GameAnimation)other).getHashCode();
    }

    public BufferedImage[] getFrames() { return this.frames; }
    public long getInitTime() { return this.INIT_TIME; }
    public int getDelay() { return this.DELAY_MILLIS; }
    public void incframeIndex() { this.frameIndex++; }
    public int getframeIndex() { return this.frameIndex; }
    // public boolean isRegisteredInPipeline() { return this.registeredInPipeline; }
    // public void setRegisteredInPipeline(boolean registeredInPipeline) { this.registeredInPipeline = registeredInPipeline; }
    public int getMaxCycles() { return this.MAX_CYCLES; }
    public int getX() { return this.x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return this.y; }
    public void setY(int y) { this.y = y; }
    public int getHashCode() { return this.ANIMATION_HASH_CODE; }
}
