package texelgameengine.graphics;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Vector;

import texelgameengine.game.GameObject;
import texelgameengine.world.GameMap;


public class LightPropagationEngine
{
    private LightPropagationEngine() {}

    /** 
     * calculates shader buffer
    */
    public static BufferedImage createShaderBuffer(GameMap map, BufferedImage shaderBuffer, float lightAngle, float globalLightIntensity) {
        //if angle is less than 90 degrees, take left vertex
        //else take right vertex and calculate from vertex origin (go both directions)

        //leave in game coordinates conver to screen space later
        ArrayList<Point2D.Float> vertices = new ArrayList<>();
        Vector<GameObject> blocks = map.getAllGameObjects();

        for(int i = 0; i < blocks.size(); i++){
            float bx = blocks.get(i).getX();
            float by = blocks.get(i).getY();
            if(lightAngle <= 90) {
                if (map.getBlockAt(bx - map.getBlockWidth(), by) == null) {

                }
            }
        }


        return null;
    }
}
