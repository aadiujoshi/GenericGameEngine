package client_testing;

import java.util.HashMap;

import texelgameengine.game.GameObject;

public class GameObjectBuilder{
    
    private static HashMap<String, GameObject> savedObjects = new HashMap<>();

    public static void loadGameObjects(){
        GameObjectBuilder.add("stone", new GameObject(0, 0, 1, 1, "stone", "stone.png"));
        GameObjectBuilder.add("grass_block", new GameObject(0, 0, 1, 1, "grass_block", "grass-block.png"));
        GameObjectBuilder.add("dirt_block", new GameObject(0, 0, 1, 1, "dirt_block","dirt-block.png"));
        
    }

    /**
     * saves predefined gameobject instances which can be duplicated to avoid excess classes
     */
    private GameObjectBuilder(){}

    public static void add(String objName, GameObject obj){
        savedObjects.put(objName, obj);
    }

    /**
     * creates new gameobjects with the same tags and instance variable values as the object specified
     */
    public static GameObject createNew(String objName, float x, float y, float width, float height){
        GameObject clone = savedObjects.get(objName).clone();

        clone.setX(x);
        clone.setY(y);
        clone.setWidth(width);
        clone.setHeight(height);

        return clone;
    }
}
