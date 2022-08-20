package texelgameengine.physics;

/**
 * you know what this class is for :)
 */
public class ExplosiveEntity extends GameEntity
{
    private float blastForce;
    private float blastRadius;

    public ExplosiveEntity(InitialPoint initial, float width, float height, String textureFilepath){
        super(initial, width, height, textureFilepath);
    }

    public float getBlastForce() { return this.blastForce; }
    public void setBlastForce(float blastForce) { this.blastForce = blastForce; }
    public float getBlastRadius() { return this.blastRadius; }
    public void setBlastRadius(float blastRadius) { this.blastRadius = blastRadius; }
}
