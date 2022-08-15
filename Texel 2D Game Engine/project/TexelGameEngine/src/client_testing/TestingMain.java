package client_testing;

import java.util.Random;

public class TestingMain{
    public static void main(String fdfdas[]){
        Random r1 = new Random(10);

        System.out.println(r1.nextGaussian());
        System.out.println(r1.nextGaussian());
        System.out.println(r1.nextGaussian());
        System.out.println(r1.nextGaussian());
        System.out.println(r1.nextGaussian());
        System.out.println(r1.nextGaussian());
        System.out.println(r1.nextGaussian());
        System.out.println(r1.nextGaussian());
        System.out.println(r1.nextGaussian());
        System.out.println(r1.nextGaussian());
        System.out.println(r1.nextGaussian());
        System.out.println(r1.nextGaussian());
        System.out.println(r1.nextGaussian());
        System.out.println(r1.nextGaussian());
        System.out.println(r1.nextGaussian());
        System.out.println(r1.nextGaussian());
        System.out.println(r1.nextGaussian());
        System.out.println(r1.nextGaussian());

    }
}

// package client_testing;

// import java.awt.Color;
// import java.awt.Graphics2D;
// import java.io.File;
// import java.io.IOException;
// import java.util.ArrayList;

// import javax.imageio.ImageIO;

// import texelgameengine.game.GameWorld;
// import texelgameengine.game.GameObject;
// import texelgameengine.graphics.GameBackground;
// import texelgameengine.world.GameChunk;
// import texelgameengine.world.GameChunkBuilder;
// import texelgameengine.world.GameMap;

// public class TestingMain{
//     public static void main(String wowzers[]) throws InterruptedException, IOException{

//         Player player = new Player(0, 100, 50, 100, "C:\\Users\\aadiu\\Desktop\\Programming Files\\Personal Projects\\Texel 2D Game Engine\\textures\\playerskin_steve_1.png");

//         GameMap overworld = new GameMap(64, 64, -32, 32);
//         overworld.setBg(new GameBackground(null, new Color(135, 206, 235), 1));

//         ArrayList<GameObject> blocks = new ArrayList<>();
//         ArrayList<GameObject> blocks2 = new ArrayList<>();
//         ArrayList<GameObject> blocks3 = new ArrayList<>();

//         for(int i = 0; i < 32; i++){
//             GameObject go = new GameObject(64, 64, "C:\\Users\\aadiu\\Desktop\\Programming Files\\Personal Projects\\Texel 2D Game Engine\\textures\\dirt-block.png");
//             go.setGrounded(true);
//             blocks.add(go);
//         }

//         for(int i = 0; i < 32; i++){
//             GameObject go = new GameObject(64, 64, "C:\\Users\\aadiu\\Desktop\\Programming Files\\Personal Projects\\Texel 2D Game Engine\\textures\\dirt-block.png");
//             go.setGrounded(true);
//             blocks2.add(go);
//         }

//         for(int i = 0; i < 32; i++){
//             GameObject go = new GameObject(64, 64, "C:\\Users\\aadiu\\Desktop\\Programming Files\\Personal Projects\\Texel 2D Game Engine\\textures\\dirt-block.png");
//             go.setGrounded(true);
//             blocks3.add(go);
//         }

//         blocks.get(31).setTexture(ImageIO.read(new File("C:\\Users\\aadiu\\Desktop\\Programming Files\\Personal Projects\\Texel 2D Game Engine\\textures\\stone.png")));
//         blocks2.get(31).setTexture(ImageIO.read(new File("C:\\Users\\aadiu\\Desktop\\Programming Files\\Personal Projects\\Texel 2D Game Engine\\textures\\stone.png")));
//         blocks3.get(31).setTexture(ImageIO.read(new File("C:\\Users\\aadiu\\Desktop\\Programming Files\\Personal Projects\\Texel 2D Game Engine\\textures\\stone.png")));

//         GameChunk sc1 = GameChunkBuilder.buildChunk(blocks, 64, 64, 0, -32);
//         GameChunk sc2 = GameChunkBuilder.buildChunk(blocks2, 64, 64, 1, -32);
//         GameChunk sc3 = GameChunkBuilder.buildChunk(blocks3, 64, 64, -1, -32);


//         // for(GameObject go : sampleChunk.getBlock;s()){
//         //     System.out.println(go);
//         // }

//         overworld.addIndexedChunk(sc1);
//         overworld.addIndexedChunk(sc2);
//         overworld.addIndexedChunk(sc3);

//         ge.addGameMap(overworld);
//         ge.addEntity(player);


//         // GameMap overworld = new GameMap(32, 32, 32, 32);
//         // overworld.setBg(new Background(null, new Color(135, 206, 235), 1));
//         // GameObject[] blocks = new GameObject[65]; //-64 -> 0 -> +64
//         // GameChunk chunk = new GameChunk(blocks, 0);

//         // for(int i = 0; i < 65; i++){
//         //     blocks[i] = new GameObject(0, (i*32)-(32*65), 32, 32, "C:\\Users\\aadiu\\Desktop\\Programming Files\\Personal Projects\\Texel 2D Game Engine\\textures\\dirt-block.png");
//         // }

//         // overworld.addChunkToRight(new GameChunk(blocks));
//         // overworld.addIndexedChunk(chunk);
//         // overworld.setLoaded(true);
//         // ge.addGameMap(overworld);
//         // ge.addProjectile(player);

//         // while(true){
//         //     Thread.sleep(50);
//         //     InitialPoint ip = new InitialPoint(0, 200, new Vector((float)Math.random()*150, (float)(Math.random()*360))); 
//         //     Projectile p = new Projectile(ip, 5, 5, "C:\\\\Users\\\\aadiu\\\\Desktop\\\\Programming Files\\\\Personal Projects\\\\Texel 2D Game Engine\\\\textures\\\\water-particle.png", 100);
//         //     p.setHitboxEnabled(false);
//         //     p.setDurationMillis(500);
//         //     p.setGrounded(false);
//         //     ge.addProjectile(p);
//         // }
//     }
// }