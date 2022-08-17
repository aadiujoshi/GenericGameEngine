package texelgameengine.physics;

import texelgameengine.game.GameObject;
import texelgameengine.graphics.GameUpdateEvent;
import texelgameengine.graphics.GraphicsEvent;
import texelgameengine.world.GameChunk;
import texelgameengine.world.GameMap;

public class GameEntity extends GameObject{
    private InitialPoint initial; // stores inital velocities and initial point
    private float elapsedTime;
    private float terminalVelocity;

    public GameEntity(InitialPoint initial, float width, float height, String textureFilepath)  {
        super(initial.getX(), initial.getY(), width, height, textureFilepath);
        // this.dragCoefficient = dragCoefficient;
        this.initial = initial;
        this.elapsedTime = 0;
        this.terminalVelocity = 50;
    }

    @Override 
    public void gameUpdated(GameUpdateEvent e){
        if(collision(e.getGameMap().getBlockAt(Math.round(getX()), Math.round(getY()-getHeight()/2)), false) == FLOOR_COLLISION)
            snap(e.getGameMap());
        PhysicsEngine.calculateEntityMotion(this, e.getGameMap(), e.getTickSpeed());
        System.out.println(getY());

        super.gameUpdated(e);
    }

    @Override
    public void paintObject(GraphicsEvent e){
        super.paintObject(e);
        // if(GameDisplayPanel.isDebugging()){
        //     initial.paintObject(e);
        // }
    }

    
    /**
     * clip hitbox
     */
    public void snap(GameMap map){
        GameChunk local = map.getChunkAt(getX());
        int nv = local.getNextVacant(getY(), getHeight());
        // System.out.println(System.nanoTime());
        this.setInitial(new InitialPoint(getX(), nv+.5f, GameEntity.STANDING_VECTOR));
        this.setY(nv+0.5f);
        this.setTime(0);
    }


    public InitialPoint getInitial() { return this.initial; }
    public void setInitial(InitialPoint initial) { this.initial = initial; }
    public float getTime() { return this.elapsedTime; }
    public void setTime(float elapsedTime) { this.elapsedTime = elapsedTime; }
}
