package texelgameengine.graphics;

public class GameCamera {
    private float x;
    private float y;

    public GameCamera(float x, float y){
        this.x = x;
        this.y = y;
    }

    //cartesian points
    public float getX() { return x; }
    public float getY() { return y; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }

    @Override
    public String toString(){
        return this.x + "  " + this.y;
    }
}
