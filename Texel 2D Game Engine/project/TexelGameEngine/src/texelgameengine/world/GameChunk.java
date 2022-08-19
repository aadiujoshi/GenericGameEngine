package texelgameengine.world;

import java.util.HashMap;
import java.util.Map;

import texelgameengine.game.GameObject;
import texelgameengine.graphics.GameUpdateListener;
import texelgameengine.graphics.GraphicsEvent;
import texelgameengine.graphics.PaintableObject;
import texelgameengine.graphics.GameUpdateEvent;

public class GameChunk extends PaintableObject implements GameUpdateListener {
    
    private HashMap<Integer, GameObject> blocks;
    private int chunkIndex;
    private final int MAX_BLOCK_INDEX;
    private final int MIN_BLOCK_INDEX;
    /**
     * the lowest object is the 0th index in the chunk array
     */
    public GameChunk(HashMap<Integer, GameObject> blocks, int chunkIndex) {
        super();
        this.blocks = blocks;
        this.chunkIndex = chunkIndex;

        int min = (int)(((GameObject)(blocks.values().toArray()[0])).getY());
        int max = min;

        for(Map.Entry<Integer, GameObject> g : blocks.entrySet()){
            int ind = g.getKey();
            if(ind < min)
                min = ind;
            if(ind > max)
                max = ind;
        }
        MAX_BLOCK_INDEX = max;
        MIN_BLOCK_INDEX = min;
    }

    @Override
    public void gameUpdated(GameUpdateEvent e) {
        for(int i = 0; i < blocks.size(); i++){
            GameObject go = blocks.get(i);

            if(go == null) continue;

            go.gameUpdated(e);

            if(go.isExpired())
                go = null;

            blocks.put(i, go);
        }
    }

    @Override
    public void paintObject(GraphicsEvent e) 
    {
        for(Map.Entry<Integer, GameObject> ent : blocks.entrySet())
            if(ent != null)
                if(ent.getValue() != null){
                    ent.getValue().paintObject(e);
                }
    }

    public GameObject getBlockAt(int y){
        return blocks.get(y);
    }

    public int getNextVacant(float y, float headroom){
        headroom = Math.round(headroom);
        for(int i = Math.round(y); i < MAX_BLOCK_INDEX; i++){
            boolean free = true;
            for(int itemp = i; itemp <= i+headroom; itemp++){
                if(blocks.get(i) != null){
                    free = false;
                    break;
                }
            }
            if(!free) continue;
            return i;
        }
        return MAX_BLOCK_INDEX;
    }

    public GameObject getHeighestBlock(){
        int strt = (int)(((GameObject)(blocks.values().toArray()[0])).getY());
        GameObject heighest = blocks.get(strt);
        for(int i = 0; i < blocks.size(); i++){
            if(blocks.get(i) != blocks.get(strt) && blocks.get(i).getY() > heighest.getY()){
                heighest = blocks.get(i);
            }
        }
        return heighest;
    }

    //testing purposes only
    // public GameChunk clone() {
    //     GameObject[] newBlocks = new GameObject[this.blocks.length];
    //     for(int i = 0; i < newBlocks.length; i++){
    //         newBlocks[i] = this.blocks[i];
    //     }
    //     return new GameChunk(blocks);
    // }

    public HashMap<Integer, GameObject> getBlocks() { return this.blocks; }
    public int getChunkIndex() { return this.chunkIndex; }
    public void setChunkIndex(int chunkIndex) { this.chunkIndex = chunkIndex; }
    public void setBlocksList(HashMap<Integer, GameObject> blocks) { this.blocks = blocks; }
    // public int getChunkPixelWidth() { return CHUNK_PIXEL_WIDTH; }
}
