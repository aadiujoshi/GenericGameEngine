package texelgameengine.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Map;

import main.TextureResourceLoader;
import texelgameengine.gamepanel.GameDisplayPanel;
import texelgameengine.graphics.GameAnimation;
import texelgameengine.graphics.GameUpdateEvent;
import texelgameengine.graphics.GameUpdateListener;
import texelgameengine.graphics.GraphicsEvent;
import texelgameengine.graphics.PaintableObject;

public class GameObject extends PaintableObject implements GameUpdateListener, Cloneable
{
    private final int OBJECT_HASH_CODE;

    // static fields
    public final static int NO_COLLISION = 0;
    public final static int SIDE_COLLISION = 1;
    public final static int FLOOR_COLLISION = 2;

    public final static int DURATION_FOREVER = -1;
    //graphics
    // private BufferedImage texture;
    private String name;

    private GameAnimation animation;

    private float emittableLightLevel;

    private boolean hitboxEnabled = true;
    private boolean expired = false;

    private int gameZLayer;

    private int durationMillis = DURATION_FOREVER;
    
    //stuff
    private float x; // (cartesian, NOT graphical coordinates) | also the center of the objects/shape
    private float y;
    
    private GameTags tags;

    // @Deprecated 
    // private double depth;
    private float width;
    private float height;
    private float mass;

    // @Deprecated
    // private double rotation;
    private long INIT_TIME;

    // @Desprecated
    // private double volume;

    @Deprecated
    private boolean grounded = true;

    
    public GameObject(float x, float y, float width, float height, BufferedImage texture){
        super(texture);
        
        this.x = x;
        this.y = y;
        this.tags = new GameTags();
        // this.rotation = 0;
        // this.depth = depth;
        this.width = width;
        this.height = height;
        // this.volume = volume;
        this.hitboxEnabled = true;
        this.expired = false;
        this.INIT_TIME = System.currentTimeMillis();
        this.OBJECT_HASH_CODE = GameWorld.getNextFreeHashCode();
    }

    public GameObject(float x, float y, float width, float height, String objName, String textureFilename){
        this(height, height, height, height, TextureResourceLoader.getTexture(textureFilename));
        name = objName;
    }

    public GameObject(float x, float y, float width, float height, String textureFilename) {
        this(x, y, width, height, TextureResourceLoader.getTexture(textureFilename));
    }

    public int collision(GameObject c, boolean ignoreSideCollision) {
        if(c == null) return NO_COLLISION;
        if(!this.hitboxEnabled || !c.hitboxEnabled()) return NO_COLLISION;
        if (((getX() - getWidth() / 2) < (c.getX() + c.getWidth() / 2) && getX() >= c.getX()) || ((getX() + getWidth() / 2) > (c.getX() - c.getWidth() / 2) && getX() <= c.getX())){
            if (((getY() + getHeight()) / 2 > (c.getY() - c.getHeight() / 2) && getY() <= c.getY()) || ((getY() - getHeight() / 2) < (c.getY() + c.getHeight() / 2) && getY() >= c.getY())){
                if ((getWidth() / 2 + c.getWidth() / 2) - (Math.abs(getX() - c.getX())) < (getHeight() / 2 + c.getHeight() / 2) - (getY() - c.getY()) && c.isGrounded() == true && !ignoreSideCollision){
                    return SIDE_COLLISION;
                }
                else if (c.isGrounded() == true){
                    return FLOOR_COLLISION;
                }
            }
        }
        return NO_COLLISION;
    }

    @Override
    public void gameUpdated(GameUpdateEvent e) {
        if(durationMillis != DURATION_FOREVER && e.getUpdateTimeMillis()-INIT_TIME >= durationMillis)
            expired = true;
    }

    @Override
    public void paintObject(GraphicsEvent e) {
        if(getTexture() == null) return;
        if(animation != null){
            animation.paintObject(e);
            return;
        }

        Graphics2D g = e.getGraphics();

        Point p = GameWorld.toScreenSpace((int)(x*GameDisplayPanel.BLOCK_PIXEL_DIMENSIONS.width), (int)(y*GameDisplayPanel.BLOCK_PIXEL_DIMENSIONS.height), e);

        // if(this instanceof GameEntity){
        //     System.out.println(p + "     |   " + x + "  " + y + "    |   " + e.getXOffset() + "     " + e.getYOffset());
        // }

        // System.out.println(p);
        // System.out.println(getTexture().getWidth() + "   " + getTexture().getHeight());
        // System.out.println(x + "  " + y);
        // System.out.println(p.x*getTexture().getWidth() + "   " + ( p.y * getTexture().getHeight() ));

        g.drawImage(getTexture(), null, 
                    (int)(p.getX())-(getTexture().getWidth()/2), 
                    (int)(p.getY())-(getTexture().getHeight()/2));
    }


    @Override
    public boolean equals(Object other){
        return ((GameObject)other).getObjectHashCode() == this.OBJECT_HASH_CODE;
    }

    @Override
    public GameObject clone(){
        GameObject clone =  new GameObject(x, y, width, height, getTexture());

        clone.setMass(mass);
        clone.setAnimation(animation);
        clone.setDurationMillis(durationMillis);
        clone.setHitboxEnabled(hitboxEnabled);
        clone.setGameZLayer(gameZLayer);
        clone.setEmittableLightLevel(emittableLightLevel);
        clone.setName(name);

        for(Map.Entry<String, Boolean> tgs : tags.getAllTags().entrySet()){
            boolean tagVal = tgs.getValue();
            String tagStr = tgs.getKey();
            clone.addTag(tagStr, tagVal);
        }
        
        return clone;
    }
    
    @Override
    public String toString(){
        return this.x + "  " + this.y;
    }
    
    public float getX() { return this.x; }
    public void setX(float x) { this.x = x; }
    public float getY() { return this.y; }
    public void setY(float y) { this.y = y; }
    public float getWidth() { return this.width; }
    public void setWidth(float width) { this.width = width; }
    public float getHeight() { return this.height; }
    public void setHeight(float height) { this.height = height; }
    public float getMass() { return this.mass; }
    public void setMass(float mass) { this.mass = mass; }
    @Deprecated public boolean isGrounded() { return this.grounded; }
    @Deprecated public void setGrounded(boolean grounded) { this.grounded = grounded;}
    public BufferedImage getTexture() { return super.getTexture(); }
    // public void setTexture(String textureFilename) { super.setTexture(TextureResourceLoader.getTexture(textureFilename)); }
    // public void setTexture(BufferedImage texture) { super.setTexture(texture); }
    public GameAnimation getAnimation() { return this.animation; }
    public long getInitTime() { return INIT_TIME; }
    public void setDurationMillis(int millis) { this.durationMillis = millis; }
    public int getDurationMillis() { return this.durationMillis; }
    public boolean hitboxEnabled() { return this.hitboxEnabled; }
    public void setHitboxEnabled(boolean hitboxEnabled) { this.hitboxEnabled = hitboxEnabled; }
    public boolean isExpired() { return this.expired; }
    public void setExpired(boolean expired) { this.expired = expired; }
    public int getObjectHashCode() { return this.OBJECT_HASH_CODE; }
    public boolean isTag(String s) { return tags.isTag(s); }
    public void addTag(String tag, boolean value) { this.tags.addTag(tag, value); }
    public void setAnimation(GameAnimation a) { this.animation = a; }
    public int getGameZLayer() { return gameZLayer; }
    public void setGameZLayer(int gameZLayer) { this.gameZLayer = gameZLayer; }
    public float getEmittableLightLevel() { return this.emittableLightLevel; }
    public void setEmittableLightLevel(float emittableLightLevel) { this.emittableLightLevel = emittableLightLevel; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
}
