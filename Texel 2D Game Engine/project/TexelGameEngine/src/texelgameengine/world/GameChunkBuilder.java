package texelgameengine.world;

import java.util.HashMap;
import java.util.List;

import texelgameengine.game.GameObject;

public class GameChunkBuilder {
    
    private GameChunkBuilder(){}

    /**
     * returns and indexed gamechunk based on given specifications
     */
    public static GameChunk buildChunk(List<GameObject> blocks, int chunkIndex, int minBlockIndex, int maxBlockIndex){
        HashMap<Integer, GameObject> newBlocks = new HashMap<>();
        
        for(int i = minBlockIndex; i <= maxBlockIndex; i++){
            GameObject go = null;

            if(i-minBlockIndex < blocks.size())
                go = blocks.get(i-minBlockIndex);
            
            if(go == null){
                newBlocks.put(i, null);
                continue;
            }

            // int nx = (int)blockWidth*chunkIndex;
            // int ny = (minBlockIndex*(int)blockHeight)+(i*(int)blockHeight);

            go.setX(chunkIndex);
            go.setY(i);
            newBlocks.put(i, go);
        }

        return new GameChunk(newBlocks, chunkIndex);
    }

    public static GameChunk buildChunk(int chunkIndex, int minBlockIndex, int maxBlockIndex, int airStartIndex){
        HashMap<Integer, GameObject> blocks = new HashMap<>();
        
        for(int i = minBlockIndex; i <= airStartIndex; i++){
            float heightPercent = ((i-minBlockIndex)*100)/(Math.abs(minBlockIndex-airStartIndex));
            
            if(heightPercent <= 80){
                blocks.put(i, new GameObject(chunkIndex, i, 1, 1, "stone.png"));
            } else if(heightPercent >= 80){
                blocks.put(i, new GameObject(chunkIndex, i, 1, 1, "dirt-block.png"));
                if(heightPercent == 100){
                    blocks.put(i, new GameObject(chunkIndex, i, 1, 1, "grass-block.png"));
                }
            }
            
            // if(heightPercent <= 80){
            //     blocks.put(i, new GameObject(chunkIndex, i, 1, 1, "stone.png"));
            // } else if(heightPercent >= 80){
            //     blocks.put(i, new GameObject(chunkIndex, i, 1, 1, "dirt-block.png"));
            //     if(heightPercent == 100){
            //         blocks.put(i, new GameObject(chunkIndex, i, 1, 1, "grass-block.png"));
            //     }
            // }
        }

        return new GameChunk(blocks, chunkIndex);
    }
}
