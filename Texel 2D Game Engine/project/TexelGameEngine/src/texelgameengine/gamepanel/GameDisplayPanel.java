package texelgameengine.gamepanel;

import javax.swing.JPanel;

import texelgameengine.game.GameWorld;
import texelgameengine.graphics.GameCamera;
import texelgameengine.graphics.GraphicsEvent;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.*;

import java.awt.image.BufferedImage;

public class GameDisplayPanel extends JPanel{

    private final GameWorld WORLD;

    private GameCamera camera;

    private double panelLocationX;
    private double panelLocationY;
    
    private boolean fullscreen;

    private Thread graphicsThread;

    public static boolean SHADERS;
    public static boolean DEBUGGING;

    private final float FPS = 1000;
    private float averageFPS;

    //scrolling
    private int xOffset;
    private int yOffset;
    
    public GameDisplayPanel(GameWorld world){
        this.WORLD = world;

        this.xOffset = 0;
        this.yOffset = 0;

        DEBUGGING = true;

        this.averageFPS = FPS;


        //=====================================================================
        //ANON CLASSES
        //=====================================================================

        this.graphicsThread = new Thread(new Runnable(){    
            public void run() {
                long startNanos;
                float tempAverageFPS = FPS;

                while(true){
                    startNanos = System.nanoTime();

                    repaint();

                    if(System.nanoTime() != startNanos)
                        tempAverageFPS = (int)(tempAverageFPS + 1000000000/(System.nanoTime()-startNanos))/2;
                    if(System.currentTimeMillis() % 1000 < 10) averageFPS = tempAverageFPS;
                }
            }
        });

        this.addComponentListener(new ComponentListener(){
            public void componentMoved(ComponentEvent e) { try{ setLocalFrameLocations(); } catch(java.awt.IllegalComponentStateException i){} }
            public void componentResized(ComponentEvent e) { try{ setLocalFrameLocations(); } catch(java.awt.IllegalComponentStateException i) {} }
            public void componentHidden(ComponentEvent e) {}
            public void componentShown(ComponentEvent e) {}
        });

        this.graphicsThread.start();
    }

    @Override
    public void paintComponent(Graphics _gPanel){
        super.paintComponent(_gPanel);
        
        Graphics2D gPanel = (Graphics2D)_gPanel;

        if(camera == null){ System.out.println("no camera added"); return; }

        xOffset = (int)camera.getX();
        yOffset = (int)camera.getY();

        gPanel.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        gPanel.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        this.paintGameWorld(gPanel);

        gPanel.dispose();
    }

    public void paintGameWorld(Graphics2D gPanel){

        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();

        //BUFFEREDIMAGES
        BufferedImage colorModel = new BufferedImage((int)screenDimension.getWidth(), (int)screenDimension.getHeight(), BufferedImage.TYPE_INT_ARGB);
        BufferedImage outputBuffer = new BufferedImage(colorModel.getWidth(), colorModel.getHeight(), BufferedImage.TYPE_INT_ARGB);


        //BUFFEREDIMAGE GRAPHICS CONTEXTS
        Graphics2D gColorModel = colorModel.createGraphics();
        Graphics2D gOutputBuffer = outputBuffer.createGraphics();


        //CHEATS
        gColorModel.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        gColorModel.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        gOutputBuffer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        gOutputBuffer.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);


        //PAINT GAMEWORLD ANCESTOR TREE
        this.invokePaintObject(gColorModel);

        //MERGE WITH SHADER PERCENTAGE VALUES LATER
        gOutputBuffer.drawImage(colorModel, null, 0, 0);

        if(!fullscreen){
            Image scaledOutputImage = outputBuffer.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
            BufferedImage scaledOutputBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
            scaledOutputBuffer.getGraphics().drawImage(scaledOutputImage, 0, 0, null);
            gPanel.drawImage(scaledOutputBuffer, null, 0, 0);
        }
        else
            gPanel.drawImage(outputBuffer, null, 0, 0);

        if(DEBUGGING) {
            gPanel.setColor(Color.LIGHT_GRAY);
            gPanel.setFont(new Font("Arial", Font.BOLD, 10));
            // gPanel.fillRect(0, 0, 160, 20);
            // gPanel.fillRect(0, 20, 160, 20);
            // gPanel.fillRect(0, 40, 160, 20);
            gPanel.setColor(Color.WHITE);
            gPanel.drawString("AVG FPS: " + averageFPS, 2, 0+15);
            gPanel.drawString("99% TICKS PER SECOND: " + WORLD.getTickSpeed99thPercent(), 2, 20+15);
            gPanel.drawString("ENTITY COUNT: " + WORLD.getEntities().size(), 2, 40+15);
        }
    }

    public void save(){

    }

    public void close(){

    }

    private void invokePaintObject(Graphics2D colorModelGraphics){
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();

        WORLD.paintObject(new GraphicsEvent((int)screenDimension.getWidth(), (int)screenDimension.getHeight(), xOffset, yOffset, System.currentTimeMillis(), colorModelGraphics));
    }

    
    public static boolean shadersIsEnabled() { return SHADERS; }
    public static void enableShaders(boolean shaders) { SHADERS = shaders; }
    public static boolean isDebugging() { return DEBUGGING; }
    public static void setDebugging(boolean d) { DEBUGGING = d; }
    public GameWorld getWorld(){ return WORLD; }
    public double getMouseX() { return MouseInfo.getPointerInfo().getLocation().getX() - panelLocationX; }
    public double getMouseY() { return MouseInfo.getPointerInfo().getLocation().getY() - panelLocationY; }
    public int getXOffset() { return this.xOffset; }
    public void setXOffset(int xOffset) { this.xOffset = xOffset; }

    private void setLocalFrameLocations(){
        panelLocationX = getLocationOnScreen().getX();
        panelLocationY = getLocationOnScreen().getY(); 
    }
}