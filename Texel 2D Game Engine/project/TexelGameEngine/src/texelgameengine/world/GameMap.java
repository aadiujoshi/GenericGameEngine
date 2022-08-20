package texelgameengine.world;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import texelgameengine.game.GameObject;
import texelgameengine.graphics.GameBackground;
import texelgameengine.graphics.GameUpdateEvent;
import texelgameengine.graphics.GameUpdateListener;
import texelgameengine.graphics.GraphicsEvent;
import texelgameengine.graphics.PaintableObject;

public class GameMap extends PaintableObject implements GameUpdateListener
{
    //vertical slice/column of blocks is 1 chunk
    private HashMap<Integer, GameChunk> chunks; //collection of chunks, unordered
    private GameBackground bg;
    private int leftmostChunkIndex;
    private int rightmostChunkIndex;
    private final int MIN_BLOCK_INDEX;
    //can be a negative
    private final int MAX_BLOCK_INDEX;

    private float blockWidth = 1;
    private float blockHeight = 1;

    private boolean loaded;

    public GameMap(int minBlockIndex, int maxBlockIndex){
        super();
        loaded = false;
        chunks = new HashMap<>();
        leftmostChunkIndex = 0;
        rightmostChunkIndex = 0;
        this.MIN_BLOCK_INDEX = minBlockIndex;
        this.MAX_BLOCK_INDEX = maxBlockIndex;
    }   

    /**
     * Add chunk to map. Returns true if it replaces a chunk at that index.
     * If the chunk doesnt have an index, it is placed at the left most index.
    */
    public boolean addIndexedChunk(GameChunk chunk) {
        validateChunk(chunk);

        boolean b = false;
        
        b = chunk.equals(chunks.put(chunk.getChunkIndex(), chunk));

        if(chunk.getChunkIndex() == 0 && leftmostChunkIndex == 0 && rightmostChunkIndex == 0){
            leftmostChunkIndex++;
            rightmostChunkIndex++;
        }
        else if(chunk.getChunkIndex() > 0){
            rightmostChunkIndex = chunk.getChunkIndex()+1;
        }
        else if(chunk.getChunkIndex() < 0){
            leftmostChunkIndex = chunk.getChunkIndex()-1;
        }

        return b;
    }

    public void addChunkToLeft(GameChunk chunk) {
        validateChunk(chunk);
        chunk.setChunkIndex(leftmostChunkIndex);
        chunk = conformChunkValuesToIndex(chunk, leftmostChunkIndex);
        chunks.put(leftmostChunkIndex, chunk);
        leftmostChunkIndex++;
    }

    public void addChunkToRight(GameChunk chunk){
        validateChunk(chunk);
        chunk.setChunkIndex(rightmostChunkIndex);
        chunk = conformChunkValuesToIndex(chunk, rightmostChunkIndex);
        chunks.put(rightmostChunkIndex, chunk);
        rightmostChunkIndex++;
    }

    /**
     * throws an error if the chunk doesnt meet requirements
     */
    private void validateChunk(GameChunk chunk){
        // for(int i = 0; i < chunk.getBlocks().length; i++){
        //     if(chunk.getBlocks()[i].getY() < MIN_BLOCK_INDEX || chunk.getBlocks()[i].getY() > MAX_BLOCK_INDEX){
        //         throw new Error("Invalid chunk added to GameMap: GameObject in GameChunk array at index " + i + " goes out of index range");
        //     }
        //     if(chunk.getBlocks()[i].getWidth() != BLOCK_PIXEL_WIDTH){
        //         throw new Error("Invalid chunk added to GameMap: GameObject in GameChunk array at index " + i + " does not match pixel width of" + BLOCK_PIXEL_WIDTH);
        //     }
        // }
    }

    @Override
    public void gameUpdated(GameUpdateEvent e) {
        for(Map.Entry<Integer, GameChunk> c : chunks.entrySet()) {
            GameChunk gc = c.getValue();
            gc.gameUpdated(e);
        }
    }
    
    @Override
    public void paintObject(GraphicsEvent e) {
        if(!loaded) return;
        
        if(bg != null)
            bg.paintObject(e);
        for(Map.Entry<Integer, GameChunk> c : chunks.entrySet()) {
            if(c != null)
                if(c.getValue() != null){
                    c.getValue().paintObject(e);
                }
        }
    }

    public GameChunk conformChunkValuesToIndex(GameChunk chunk, int chunkIndex){
        HashMap<Integer, GameObject> chunkBlocks = chunk.getBlocks();
        for(int i = 0; i < chunkBlocks.size(); i++){
            chunkBlocks.get(i).setX(chunkIndex);
        }
        chunk.setBlocksList(chunkBlocks);
        return chunk;
    }

    public GameChunk[] getChunksWithinRange(float x, float range){
        x = Math.round(x);
        GameChunk[] gc = new GameChunk[(int)(range*2+1)];
        int i = 0;
        int end = (int)(x+range+1);
        for(x-=range; x < end; x++){
            gc[i] = chunks.get((int)x);
            i++;
        }
        return gc;
    }

    /** 
     * returns a list of GameObjects from (x-radius, y+radius) to (x+radius, y-radius)
     * parameters are pixel values
     * assumes all gameobjects in the chunks are squares
    */
    public Vector<GameObject> getBlocksInRadius(float x, float y, int radius) {
        int indexX = (int)x;
        int indexY = (int)y;
        int indexRadius = ((int)(radius*2))+1;
        // System.out.println(indexX + "  " + indexY + "  " + indexRadius);
        // System.out.println(indexY+indexRadius + "    " + (indexY-indexRadius-1));
        Vector<GameObject> region = new Vector<>();
        for(int cx = indexX-indexRadius; cx < indexX+indexRadius+1; cx++){
            for(int cy = indexY+indexRadius; cy > indexY-indexRadius-1; cy--){
                GameObject go = getBlockAt(cx, cy);
                if(go != null)
                    region.add(go);
            }
        }
        return region;
    }

    /**
     * index values not real pixel values
     */
    public GameObject getBlockAt(float x, float y){
        int nx = Math.round(x);
        int ny = Math.round(y);
        GameChunk gc = chunks.get(x);
        if(gc == null) return null;
        return gc.getBlockAt(nx);
    }

    //exclude nulls
    public Vector<GameObject> getAllGameObjects(){
        Vector<GameObject> objects = new Vector<>();
        for(Map.Entry<Integer, GameChunk> c : chunks.entrySet()){
            for(Map.Entry<Integer, GameObject> b : c.getValue().getBlocks().entrySet()){
                if(b.getValue() != null)
                    objects.add(b.getValue());
            }
        }
//        for(entities) //refactory plz
        return objects;
    }

    public GameChunk getChunkAt(float i){
        return chunks.get(Math.round(i));
    }

    public HashMap<Integer, GameChunk> getChunks() { return chunks; }
    public GameBackground getBackground() { return this.bg; }
    public void setBg(GameBackground gameBackground) { this.bg = gameBackground; }
    public boolean isLoaded() { return this.loaded; }
    public void setLoaded(boolean loaded) { this.loaded = loaded; }
    public int getMaxBlockIndex() { return this.MAX_BLOCK_INDEX; }
    public int getMinBlockIndex() { return this.MIN_BLOCK_INDEX; }
    public int getNextFreeChunkLeft() { leftmostChunkIndex--; return this.leftmostChunkIndex; }
    public int getNextFreeChunkRight() { rightmostChunkIndex++; return this.rightmostChunkIndex; }
    public float getBlockWidth() { return blockWidth; }
    public float getBlockHeight() { return blockHeight; }
}
