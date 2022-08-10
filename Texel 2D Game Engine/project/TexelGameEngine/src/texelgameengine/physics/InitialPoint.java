package texelgameengine.physics;

import texelgameengine.graphics.GraphicsEvent;
import texelgameengine.graphics.PaintableObject;

public class InitialPoint extends PaintableObject
{
    private float x; //(cartesian, NOT graphical coordinates)
    private float y;

    private Vector horizontalVelocity;
    private Vector verticalVelocity;
    private Vector velocity;

    /**
     * Records the initial values of a Projectile/Object. It is NOT a GameObject child, and 
     * can be used for jumping, falling, or projectile physics.
     */
    public InitialPoint(float x, float y, Vector velocity)
    {
        super();

        this.x = x;
        this.y = y;
        this.velocity = velocity;

        this.horizontalVelocity = new Vector((float)(Math.cos(velocity.getRadians())*velocity.getVelocity()), (velocity.getAngle()>90 && velocity.getAngle()<270)?180:0);
        this.verticalVelocity = new Vector((float)(Math.sin(velocity.getRadians())*velocity.getVelocity()), (velocity.getAngle()>=0 && velocity.getAngle()<=180)?90:270);
        this.horizontalVelocity.setX((int)this.x);
        this.horizontalVelocity.setY((int)this.y);
    }

    /**
     * Parameters must be relative Cartesian coordinates not Screen Space coordinates.
     */
    public void updateVectorDirection(float ox, float oy) //point in mouse direction (cartesian points)
    {
        float cx = ox - this.x;
        float cy = oy - this.y;

        float theta = (float)(Math.atan2(cy, cx)*(180/Math.PI));
        theta = theta>=0 ? theta : 360+theta;

        this.velocity.setAngle(theta);
        this.velocity.setRadians((float)(velocity.getAngle()*(Math.PI/180)));

        updateXYVectorMagnitudes();
    }

    public void updateXYVectorMagnitudes()
    {
        this.horizontalVelocity = new Vector((float)(Math.cos(velocity.getRadians())*velocity.getVelocity()), (velocity.getAngle()>90 && velocity.getAngle()<270)?180:0);
        this.verticalVelocity = new Vector((float)(Math.sin(velocity.getRadians())*velocity.getVelocity()), (velocity.getAngle()>=0 && velocity.getAngle()<=180)?90:270);
    }

    public String toString()
    {
        return "x=" + x + "  y=" + y + "  (Vx=" + horizontalVelocity + ")  (Vy=" + verticalVelocity + ")  (V=" + velocity + ")";
    }

    @Override
    public void paintObject(GraphicsEvent e) 
    {
        horizontalVelocity.paintObject(e);
        verticalVelocity.paintObject(e);
        velocity.paintObject(e);
    }

    public float getX() { return this.x; }
    public void setX(float x) { this.x = x; }
    public float getY() { return this.y; }
    public void setY(float y) { this.y = y; }
    public Vector getHorizontalVelocity() { return this.horizontalVelocity; }
    public void setHorizontalVelocity(Vector horizontalVelocity) { this.horizontalVelocity = horizontalVelocity; }
    public Vector getVerticalVelocity() { return this.verticalVelocity; }
    public void setVerticalVelocity(Vector verticalVelocity) { this.verticalVelocity = verticalVelocity; }
    public Vector getVelocity() { return this.velocity; }
    public void setVelocity(Vector velocity) { this.velocity = velocity; updateXYVectorMagnitudes(); }
}