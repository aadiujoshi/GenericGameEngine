package texelgameengine.world;

import texelgameengine.game.GameObject;
import texelgameengine.graphics.GameUpdateListener;
import texelgameengine.graphics.GraphicsEvent;
import texelgameengine.graphics.PaintableObject;
import texelgameengine.graphics.GameUpdateEvent;

public class GameChunk extends PaintableObject implements GameUpdateListener
{
    private GameObject[] blocks;  //index 0 is the bottom of the chunk
    private int chunkIndex;

    private boolean nonIndexed;

    /**
     * the lowest object is the 0th index in the chunk array
     */
    public GameChunk(GameObject[] blocks, int chunkIndex)
    {
        super();
        this.blocks = blocks;
        this.chunkIndex = chunkIndex;
        this.nonIndexed = false;
    }

    /**
     * added to either the left or right most side of the map
     */
    public GameChunk(GameObject[] blocks){
        super();
        this.blocks = blocks;
        this.nonIndexed = true;
    }

    @Override
    public void gameUpdated(GameUpdateEvent e) {
        for(int i = 0; i < blocks.length; i++){
            GameObject go = blocks[i];

            if(go == null) continue;

            go.gameUpdated(e);

            if(go.isExpired())
                go = null;

            blocks[i] = go;
        }
    }

    @Override
    public void paintObject(GraphicsEvent e) 
    {
        for(GameObject go: blocks)
            if(go != null)
                go.paintObject(e);
    }

    public GameObject getBlockAtIndexY(int y){
        if(y+blocks.length-1 > blocks.length-1 || y+blocks.length-1 < 0) return null;
        return blocks[y+blocks.length-1];
    }

    //testing purposes only
    public GameChunk clone() {
        GameObject[] newBlocks = new GameObject[this.blocks.length];
        for(int i = 0; i < newBlocks.length; i++){
            newBlocks[i] = this.blocks[i];
        }
        return new GameChunk(blocks);
    }

    public GameObject[] getBlocks() { return this.blocks; }
    public int getChunkIndex() { return this.chunkIndex; }
    public boolean isNonIndexed() { return this.nonIndexed; }
    public void setChunkIndex(int chunkIndex) { this.chunkIndex = chunkIndex; }
    public void setNonIndexed(boolean nonIndexed) { this.nonIndexed = nonIndexed; }
    public void setBlocksArray(GameObject[] blocks) { this.blocks = blocks; }
    // public int getChunkPixelWidth() { return CHUNK_PIXEL_WIDTH; }
}
