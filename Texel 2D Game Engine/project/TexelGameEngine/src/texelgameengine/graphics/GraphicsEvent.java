package texelgameengine.graphics;

import java.awt.Graphics2D;

public class GraphicsEvent {
    private int screenWidth;
    private int screenHeight;
    private int xOffset;
    private long paintTimeMillis;

    private int yOffset;
    private Graphics2D g;

    public GraphicsEvent(int screenWidth, int screenHeight, int xOffset, int yOffset, long paintTimeMillis, Graphics2D g)
    {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.paintTimeMillis = paintTimeMillis;
        this.g = g;
    }

    public int getScreenWidth() { return this.screenWidth; }
    public int getScreenHeight() { return this.screenHeight; }
    public int getXOffset() { return this.xOffset; }
    public int getYOffset() { return this.yOffset; }
    public long getPaintTimeMillis() { return this.paintTimeMillis; }
    public void setPaintTimeMillis(long paintTimeMillis) { this.paintTimeMillis = paintTimeMillis; }
    public Graphics2D getGraphics() { return this.g; }

    @Override
    public String toString(){
        return screenWidth + " " + screenHeight + " " + xOffset + " " + yOffset;
    }
}
