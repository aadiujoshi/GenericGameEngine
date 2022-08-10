package texelgameengine.world;

import java.util.List;

import texelgameengine.game.GameObject;

public class GameChunkBuilder {
    
    private GameChunkBuilder(){}

    /**
     * returns and indexed gamechunk based on given specifications
     */
    public static GameChunk buildChunk(List<GameObject> blocks, float blockHeight, float blockWidth, int chunkIndex, int minBlockIndex){
        GameObject[] newBlocks = new GameObject[blocks.size()];
        for(int i = 0; i < blocks.size(); i++){
            GameObject go = blocks.get(i);

            int nx = (int)blockWidth*chunkIndex;
            int ny = (minBlockIndex*(int)blockHeight)+(i*(int)blockHeight);

            go.setX(nx);
            go.setY(ny);
            newBlocks[i] = go;
        }

        return new GameChunk(newBlocks, chunkIndex);
    }
}
