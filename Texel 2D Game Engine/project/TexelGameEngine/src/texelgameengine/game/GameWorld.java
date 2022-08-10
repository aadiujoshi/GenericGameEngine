package texelgameengine.game;

import java.awt.Point;
import java.io.Serializable;
import java.util.Vector;

import texelgameengine.gamepanel.GameDisplayPanel;
import texelgameengine.graphics.GameUpdateEvent;
import texelgameengine.graphics.GraphicsEvent;
// import texelgameengine.graphics.LightPropagationEngine;
import texelgameengine.graphics.PaintableObject;
import texelgameengine.physics.GameEntity;
import texelgameengine.world.GameMap;

public class GameWorld extends PaintableObject implements Serializable
{
    private String worldName;
    //differentiate between worlds with similar names
    private final String WORLD_ID;

    //force method access
    private static int NEXT_FREE_HASH_CODE = 1;

    private GameDisplayPanel displayPanel;

    private Vector<GameEntity> entities;
    private Vector<GameMap> maps;
    private GameMap loadedMap;

    private transient Thread gameUpdateHandlerThread;
    private transient Thread gameTickThread;
    
    private final float DESIRED_TICK_SPEED = 1000; //determines how many times per second entity position is calculated
    private transient float tickSpeed99thPercent;

    /**
     * This class handles all the frame updates and graphics calls (texture drawing), and GameObjects such as Projectiles,
     * and map chunks. Also has general GUI info such as mouse posistion. 
     */
    public GameWorld(String name, GameDisplayPanel displayPanel)
    {
        //ASSIGN RANDOMIZED WORLDID
        String tempID = "";
        for(int i = 0; i < 15; i++){
            char c = (char)((int)(Math.random()*200)+33);
            if(c == '/' || c == '\\' || c == '<' || c == '>' || c == '*' || c == '?' || c == ':' || c == '"' || c == '|'){
                i--;
                continue;
            }
            tempID+=c;
        }
        this.WORLD_ID = tempID;

        this.displayPanel = displayPanel;

        this.entities = new Vector<GameEntity>();
        this.maps = new Vector<>();

        this.gameUpdateHandlerThread = new Thread(new Runnable(){
            public void run() {
                while(true) {

                    for(int i = 0; i < maps.size(); i++) {
                        if(maps.get(i).isLoaded())
                            loadedMap = maps.get(i);
                    }

                    for(int i = 0; i < entities.size(); i++){
                        if(entities.get(i).isExpired()) {
                            entities.remove(i);
                            i--;
                            continue;
                        }
                    }
                }
            }
        });

        this.gameTickThread = new Thread(new Runnable(){
            public void run(){
                long startNanos;
                long delayNanos = 0;
                // float tempAverageFPS = FPS;

                while(true) {
                    nanoDelay((long)(1000000000/DESIRED_TICK_SPEED)-delayNanos);
                    startNanos = System.nanoTime();
                    
                    invokeGameUpdate(new GameUpdateEvent(loadedMap, System.currentTimeMillis(), 1000/(DESIRED_TICK_SPEED)+(delayNanos/1000000), (int)displayPanel.getMouseX(), (int)displayPanel.getMouseY()));

                    delayNanos = System.nanoTime()-startNanos;
                    tickSpeed99thPercent = tickSpeed99thPercent < DESIRED_TICK_SPEED-(delayNanos/1000000) ? tickSpeed99thPercent : DESIRED_TICK_SPEED-(delayNanos/1000000);
                }
            }
        });

        //threads
        gameUpdateHandlerThread.start();
        gameTickThread.start();
    }

    /**
     * Converts cartesian x and y coordinates to screenspace coordinates with a GraphicsEvent context.
     * Used easy translation to display objects. Cartesian origin (0,0) is centered on the screen,.
     */
    public static Point toScreenSpace(int x, int y, GraphicsEvent e){
        int cx = (e.getScreenWidth()/2)+x-e.getXOffset();
        int cy = (e.getScreenHeight()/2)-y+e.getYOffset();
        
        return new Point(cx, cy);
    }

    public static void nanoDelay(long nanos)
    {
        if(nanos < 0) return;
        final long end = System.nanoTime() + nanos;
        long timeLeft = nanos;
        do {
            timeLeft = end - System.nanoTime();
        } while (timeLeft > 0);
    }

    public void addEntity(GameEntity p) throws java.lang.Exception { 
        if(entities.contains(p)) throw new Exception("Duplicate entity");       
        entities.add(p);
    }

    public void addGameMap(GameMap map) { 
        maps.add(map);                       
        map.setLoaded(true);
        this.loadedMap = map;
    }
    
    @Override
    public void paintObject(GraphicsEvent e) {
        loadedMap.paintObject(e);
    }    
    
    public void invokeGameUpdate(GameUpdateEvent e){
        loadedMap.gameUpdated(e);
    }

    public Vector<GameEntity> getEntities() { return this.entities; }
    public double getDesiredTickSpeed() { return this.DESIRED_TICK_SPEED; }
    public double getTickSpeed99thPercent() { return this.tickSpeed99thPercent; }
    public synchronized static int getNextFreeHashCode() { NEXT_FREE_HASH_CODE++; return NEXT_FREE_HASH_CODE; }
    public String getWorldName() { return this.worldName; }
    public void setWorldName(String worldName) { this.worldName = worldName; }
    public String getWorldID() { return this.WORLD_ID; }
}
