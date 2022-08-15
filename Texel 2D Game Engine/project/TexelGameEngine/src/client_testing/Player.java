package client_testing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import texelgameengine.game.GameWorld;
import texelgameengine.graphics.GameCamera;
import texelgameengine.graphics.GameUpdateEvent;
import texelgameengine.graphics.GraphicsEvent;
import texelgameengine.physics.GameEntity;
import texelgameengine.physics.InitialPoint;
import texelgameengine.physics.Vector;

public class Player extends GameEntity implements KeyListener{

    private GameCamera camera;

    //keyboard stuff
    private boolean jumping;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean crouching;

    private boolean touchingGround;

    private boolean hasJumped;

    //used for jumping physics 
    // private InitialPoint initial;
    // private float elapsedTime; //seconds
    private static final Vector MOVERIGHT_JUMP_VECTOR = new Vector(200, 45);
    private static final Vector MOVELEFT_JUMP_VECTOR = new Vector(200, 45);
    private static final Vector STANDING_JUMP_VECTOR = new Vector(200, 90);
    private static final Vector STANDING_VECTOR = new Vector(0, 270);
    private static final Vector MOVELEFT_VECTOR = new Vector(200, 180);
    private static final Vector MOVERIGHT_VECTOR = new Vector(200, 90);

    
    public Player(float x, float y, float width, float height, String textureFilepath) {
        super(new InitialPoint(x, y, STANDING_VECTOR), width, height, textureFilepath);
        super.setHitboxEnabled(false);
        super.setGrounded(true);
        camera = new GameCamera(x, y);
        jumping = false;
        movingLeft = false;
        movingRight = false;
        crouching = false;
        hasJumped = false;
    }

    @Override
    public void gameUpdated(GameUpdateEvent e) {
        // if(movingLeft){
        //     setX(getX()-(float)0.5);
        //     camera.setX((int)getX());
        //     getInitial().setX(getX());

        // }
        // if(movingRight){
        //     setX(getX()+(float)0.5);
        //     // getInitial().setX(getX()+1);
        //     camera.setX((int)getX());
        //     getInitial().setX(getX());
        // }
        // }
        // if(jumping && !movingLeft && !movingRight){
        //     getInitial().setVelocity(STANDING_JUMP_VECTOR);
        // }
        // if(movingLeft && !jumping){
        //     getInitial().setVelocity(MOVELEFT_VECTOR);
        // }
        // if(movingRight && !jumping){
        //     getInitial().setVelocity(MOVERIGHT_VECTOR);
        // }
        // if((jumping && (movingLeft || movingRight)) && !hasJumped) //set vector to jumping Vector
        //     if(this.collision(e.getGameMap().getBlockAt((int)getX(), (int)getY()), false) == FLOOR_COLLISION){
        //         if(movingLeft)
        //             getInitial().setVelocity(MOVELEFT_JUMP_VECTOR);
        //         else if(movingRight)
        //             getInitial().setVelocity(MOVERIGHT_JUMP_VECTOR);
        //         hasJumped = true;
        //     }
        // if(!jumping)
        
        // super.gameUpdated(e);
    }

    @Override 
    public void paintObject(GraphicsEvent e){
        super.paintObject(e);
        // System.out.println(getTexture());
        // Point p = GameWorld.toScreenSpace((int)getY(), (int)getY(), e);

        // e.getGraphics().setColor(Color.BLACK);
        // e.getGraphics().fillRect((int)p.getX()-(int)getTexture().getWidth()/2, (int)p.getY()-(int)getTexture().getWidth()/2, (int)getTexture().getWidth(), (int)getTexture().getHeight());
        // System.out.println(getX() + "  " + getY());
    }

    @Override
    public void setX(float x){
        super.setX(x);
        camera.setX((int)(x*GameWorld.BLOCK_PIXEL_DIMENSIONS.width));
    }

    @Override
    public void setY(float y){
        super.setY(y);
        camera.setY((int)(y*GameWorld.BLOCK_PIXEL_DIMENSIONS.height));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'w' || e.getKeyChar() == ' '){
            jumping = true;
            hasJumped = true;
            setY(getY()+0.5f);
        }
        if(e.getKeyChar() == 'a'){
            movingLeft = true;
            setX(getX()-0.5f);
        }
        if(e.getKeyChar() == 'd'){
            movingRight = true;
            setX(getX()+0.5f);
        }
        if(e.getKeyChar() == 's'){
            setY(getY()-0.5f);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() == 'w' || e.getKeyChar() == ' '){
            jumping = false;
            hasJumped = false;
        }
        if(e.getKeyChar() == 'a'){
            movingLeft = false;
        }
        if(e.getKeyChar() == 'd'){
            movingRight = false;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    public GameCamera getCamera(){ return this.camera; }
}
