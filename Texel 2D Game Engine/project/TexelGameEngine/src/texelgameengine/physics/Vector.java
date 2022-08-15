package texelgameengine.physics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import texelgameengine.graphics.GraphicsEvent;
import texelgameengine.graphics.PaintableObject;

public class Vector extends PaintableObject
{
    //just for rendering
    private float x;
    private float y;

    private float velocity; // pixels per second
    private float angle;
    private float radians;

    public Vector(float velocity, float angle)
    {
        super();
        this.velocity = velocity;
        this.angle = angle;
        this.radians = (float)(angle*(Math.PI/180));
        this.x = 0;
        this.y = 0;
    }
    
    @Override
    public void paintObject(GraphicsEvent e) 
    {
        Graphics2D g = e.getGraphics();
        int fWidth = e.getScreenWidth();
        int fHeight = e.getScreenHeight();
        int xOffset = e.getXOffset();
        int yOffset = e.getYOffset();

        g.setColor(angle%90 == 0 ? (angle == 90 || angle == 270) ? Color.RED : Color.BLUE : Color.YELLOW);
        g.setStroke(new BasicStroke(2));

        g.drawLine((fWidth/2)+(int)x+xOffset, fHeight-(int)y-40-yOffset, 
       (fWidth/2+(int)x)+(int)(velocity*Math.cos(radians))+xOffset, (fHeight-(int)y)-(int)(velocity*Math.sin(radians))-40-yOffset);
    }

    @Override
    public String toString()
    {
        return "velocity=" + velocity + "  angle=" + angle;
    }
    
    public float getVelocity() { return this.velocity; }
    public void setVelocity(float velocity) { this.velocity = velocity; }
    public float getAngle() { return this.angle; }
    public void setAngle(float angle) { this.angle = angle; }
    public float getRadians() { return this.radians; }
    public void setRadians(float radians) { this.radians = radians; }
    public float getX() { return this.x; }
    public void setX(float x) { this.x = x; }
    public float getY() { return this.y; }
    public void setY(float y) { this.y = y;}
}
