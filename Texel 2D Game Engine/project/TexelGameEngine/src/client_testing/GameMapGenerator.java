package client_testing;

import java.util.Random;

import texelgameengine.world.GameChunkBuilder;
import texelgameengine.world.GameMap;

public interface GameMapGenerator {
    public void generateChunkAt(GameMap map, int index, long seed);

    public class PerlinNoise implements GameMapGenerator{

        @Override
        public void generateChunkAt(GameMap map, int index, long seed) {
            System.out.println("not implemented yet");
        }
    }

    public class GaussianTerrain implements GameMapGenerator{

        @Override
        public void generateChunkAt(GameMap map, int index, long seed) {
            seed *= index >= 0 ? 1 : -1;

            Random rng = new Random(seed);

            int bias = 0;

            // GameChunk prevChunk = map.getChunkAt(index > 0 ? index-1 : index+1);

            // if(index != 0 && prevChunk != null)
            //     bias = (int)prevChunk.getHeighestBlock().getY();
            // else
            //     bias = map.getMaxBlockIndex()/2;

            for(int i = 0; i < Math.abs(index); i++){ rng.nextGaussian(); }

            int maxY = ((map.getMaxBlockIndex()-map.getMinBlockIndex())/2)+(int)Math.round(bias+rng.nextGaussian()/1.5); 

            // System.out.println((map.getMaxBlockIndex()-map.getMinBlockIndex())/2 + "  "+maxY);

            map.addIndexedChunk(GameChunkBuilder.buildChunk(index, map.getMinBlockIndex(), map.getMaxBlockIndex(), maxY));
        }
    }
}
