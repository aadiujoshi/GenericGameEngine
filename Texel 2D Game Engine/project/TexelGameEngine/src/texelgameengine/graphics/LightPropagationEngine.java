package texelgameengine.graphics;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

import texelgameengine.game.GameObject;
import texelgameengine.gamepanel.GameDisplayPanel;
import texelgameengine.world.GameMap;


public class LightPropagationEngine
{
    private static float globalLightIntensity = 16;
    private static float bloom = 1;

    private LightPropagationEngine() {}

    /** 
     * calculates shader buffer
    */
    public static BufferedImage shade(GameMap map, BufferedImage colorBuffer, float lightAngle) {
        //if angle is less than 90 degrees, take left vertex
        //else take right vertex and calculate from vertex origin (go both directions)
        
        //leave in game coordinates conver to screen space later
        
        Vector<GameObject> blocks = map.getAllGameObjects();
        
        // for(int bIndex = 0; bIndex < blocks.size(); bIndex++){
        ArrayList<Point2D.Float> vertices = new ArrayList<>();
        float bw = map.getBlockWidth();
        float bh = map.getBlockHeight();

        //PROPOGATE GLOBAL SUN SHADOWS
        for(int i = 0; i < blocks.size(); i++){
            if(blocks.get(i) == null) continue;
            float bx = blocks.get(i).getX();
            float by = blocks.get(i).getY();
            float bw2 = blocks.get(i).getWidth();
            float bh2 = blocks.get(i).getWidth();
            if(lightAngle <= 90 && lightAngle >= 270) {
                if(map.getBlockAt(bx-bw, by) == null || 
                    map.getBlockAt(bx, by+bh) == null || 
                    map.getBlockAt(bx, by-bh) == null) {
                        //top left and bottom right
                        vertices.add(new Point2D.Float(bx-bw2/2, by+bh2/2));
                        vertices.add(new Point2D.Float(bx+bw2/2, by-bh2/2));
                }
            }
            else if(lightAngle > 90 && lightAngle < 270){
                if(map.getBlockAt(bx+bw, by) == null || 
                map.getBlockAt(bx, by+bh) == null || 
                map.getBlockAt(bx, by-bh) == null) {
                    //top right and bottom left
                    vertices.add(new Point2D.Float(bx-bw2/2, by-bh2/2));
                    vertices.add(new Point2D.Float(bx+bw2/2, by+bh2/2));
                }
            }
        }

        //caluclate slope (use origin as point 2)
        float _y1 = (float)(bw*Math.tan(lightAngle*(Math.PI/180)));
        float slope = (float)(_y1/bw);

        int bpw = GameDisplayPanel.BLOCK_PIXEL_DIMENSIONS.width;
        int bph = GameDisplayPanel.BLOCK_PIXEL_DIMENSIONS.height;

        //DRAW SHADED GRAPH ONTO COLOR MODEL
        for(int i = 0; i < vertices.size(); i++){
            //DERIVE SLOPE FROM ANGLE AT A DELTA X 1
            //USE VERTEX AS RELATIVE ORIGIN FOR SLOPE CALCULATIONS
            //CONVERT TO COLOR MODEL SPACE COORDINATES (ONLY USE NEGATIVE X QUADRANTS IF THE ANGLE IS <= 90 OR >= 270)
            
            //GO NEGATIVE
            if(lightAngle <= 90 && lightAngle >= 270){
                for(float rel_px = 0, rel_py = 0; map.getBlockAt((int)rel_px, (int)rel_py) == null; 
                    rel_px-=(bw/bpw), rel_py = rel_px*slope){
                    
                }
            }
            //GO POSITIVE
            else if(lightAngle > 90 && lightAngle < 270){

            }
        }

        return colorBuffer;
    }
}
