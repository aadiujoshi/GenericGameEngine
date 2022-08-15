package texelgameengine.physics;

import texelgameengine.game.GameObject;
import texelgameengine.graphics.GameUpdateEvent;
import texelgameengine.graphics.GraphicsEvent;

public class GameEntity extends GameObject{
    private InitialPoint initial; // stores inital velocities and initial point
    private float elapsedTime;

    public GameEntity(InitialPoint initial, float width, float height, String textureFilepath)  {
        super(initial.getX(), initial.getY(), width, height, textureFilepath);
        // this.dragCoefficient = dragCoefficient;
        this.initial = initial;
        this.elapsedTime = 0;
    }

    @Override 
    public void gameUpdated(GameUpdateEvent e){
        super.gameUpdated(e);
    }

    @Override
    public void paintObject(GraphicsEvent e){
        super.paintObject(e);
        // if(GameDisplayPanel.isDebugging()){
        //     initial.paintObject(e);
        // }
    }

    public InitialPoint getInitial() { return this.initial; }
    public void setInitial(InitialPoint initial) { this.initial = initial; }
    public float getTime() { return this.elapsedTime; }
    public void setTime(float elapsedTime) { this.elapsedTime = elapsedTime; }
}
