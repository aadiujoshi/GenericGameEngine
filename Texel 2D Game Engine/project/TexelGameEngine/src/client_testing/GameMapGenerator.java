package client_testing;

import java.util.Random;

import texelgameengine.world.GameChunk;

public class GameMapGenerator {

    private Random rng;
    private long seed;

    public GameMapGenerator(long seed){
        this.seed = seed;
        this.rng = new Random(seed);
    }
    
    public GameChunk generate(){return null;}
}
