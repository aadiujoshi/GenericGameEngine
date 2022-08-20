package texelgameengine.graphics;

import texelgameengine.world.GameMap;

public class GameUpdateEvent {

    private final long updateTimeMillis;
    private final int mouseX;
    private final int mouseY;
    private final GameMap map;
    private final float tickSpeed;

    public GameUpdateEvent(GameMap map, long updateTimeMillis, float tickSpeed, int mouseX, int mouseY) {
        this.updateTimeMillis = updateTimeMillis;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.map = map;
        this.tickSpeed = tickSpeed;
    }

    public long getUpdateTimeMillis() { return this.updateTimeMillis; }
    public GameMap getGameMap() { return map; }
    public int getMouseX() { return this.mouseX; }
    public int getMouseY() { return this.mouseY; }
    public float getTickSpeed() { return tickSpeed; }
}
