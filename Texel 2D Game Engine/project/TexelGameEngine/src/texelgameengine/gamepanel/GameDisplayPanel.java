package texelgameengine.gamepanel;

import javax.swing.JPanel;

import main.NotMinecraft;
import texelgameengine.game.GameWorld;
import texelgameengine.graphics.GameCamera;
import texelgameengine.graphics.GraphicsEvent;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.*;

import java.awt.image.BufferedImage;

public class GameDisplayPanel extends JPanel{
    public static final Dimension BLOCK_PIXEL_DIMENSIONS = new Dimension((int)((NotMinecraft.SCREEN_SIZE.getWidth()/1920)*64), (int)((NotMinecraft.SCREEN_SIZE.getHeight()/1080)*64));

    private GameWorld world;
    private NotMinecraft parentFrame;

    private GameCamera camera;

    private boolean fullscreen;

    private Thread graphicsThread;

    public static boolean shaders;
    public static boolean debugging;

    private final float FPS = 1000;
    private float averageFPS;

    //scrolling
    private float xOffset;
    private float yOffset;
    
    public GameDisplayPanel(NotMinecraft parentFrame){
        this(null, parentFrame);
    }

    public GameDisplayPanel(GameWorld world, NotMinecraft parentFrame){
        this.world = world;
        this.parentFrame = parentFrame;

        this.xOffset = 0;
        this.yOffset = 0;

        debugging = true;

        this.averageFPS = FPS;

        //=====================================================================
        //ANONYMOUS CLASSES
        //=====================================================================

        this.graphicsThread = new Thread(new Runnable(){    
            public void run() {
                long startNanos;
                float tempAverageFPS = FPS;

                while(true){
                    startNanos = System.nanoTime();

                    if(NotMinecraft.validPaint)
                        repaint();

                    if(System.nanoTime() != startNanos)
                        tempAverageFPS = (int)(tempAverageFPS + 1000000000/(System.nanoTime()-startNanos))/2;
                    if(System.currentTimeMillis() % 1000 < 10) averageFPS = tempAverageFPS;
                }
            }
        });

        if(world != null)
            this.graphicsThread.start();
        parentFrame.getFrame().repaint();
    }

    @Override
    public void paintComponent(Graphics _gPanel){
        super.paintComponent(_gPanel);
        
        Graphics2D gPanel = (Graphics2D)_gPanel;

        if(camera == null){ System.out.println("no camera added"); return; }

        xOffset = camera.getX();
        yOffset = camera.getY();

        gPanel.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        gPanel.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        this.paintGameWorld(gPanel);

        gPanel.dispose();
    }

    public void paintGameWorld(Graphics2D gPanel){

        Dimension screenDimension = NotMinecraft.SCREEN_SIZE;

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

        if(debugging) {
            gPanel.setColor(Color.LIGHT_GRAY);
            gPanel.setFont(new Font("Arial", Font.BOLD, 10));
            // gPanel.fillRect(0, 0, 160, 20);
            // gPanel.fillRect(0, 20, 160, 20);
            // gPanel.fillRect(0, 40, 160, 20);
            gPanel.setColor(Color.WHITE);
            gPanel.drawString("AVG FPS: " + averageFPS, 2, 0+15);
            gPanel.drawString("99% TICKS PER SECOND: " + world.getTickSpeed99thPercent(), 2, 20+15);
            gPanel.drawString("ENTITY COUNT: " + world.getEntities().size(), 2, 40+15);
            gPanel.drawString("PLAYER X: " + camera.getX(), 2, 60+15);
            gPanel.drawString("PLAYER Y: " + camera.getY(), 2, 80+15);

        }
    }

    public void save(){

    }

    public void close(){

    }

    private void invokePaintObject(Graphics2D colorModelGraphics){
        if(world == null) return;
        
        Dimension screenDimension = NotMinecraft.SCREEN_SIZE;

        // System.out.println(xOffset + "  " + yOffset );

        world.paintObject(new GraphicsEvent((int)screenDimension.getWidth(), (int)screenDimension.getHeight(), (int)((double)xOffset*BLOCK_PIXEL_DIMENSIONS.getWidth()), (int)((double)yOffset*BLOCK_PIXEL_DIMENSIONS.getWidth()), System.currentTimeMillis(), colorModelGraphics));
    }

    
    public static boolean shadersIsEnabled() { return shaders; }
    public static void enableShaders(boolean s) { shaders = s; }
    public static boolean isDebugging() { return debugging; }
    public static void setDebugging(boolean d) { debugging = d; }
    public GameWorld getWorld(){ return world; } 
    public double getMouseX() { return MouseInfo.getPointerInfo().getLocation().getX() - parentFrame.getFrameLocation().getX(); }
    public double getMouseY() { return MouseInfo.getPointerInfo().getLocation().getY() - parentFrame.getFrameLocation().getY(); }
    public float getXOffset() { return this.xOffset; }
    public float getYOffset() { return this.yOffset; }
    public void setWorld(GameWorld world) { this.world = world; }
    public void setCamera(GameCamera camera) { this.camera = camera; }
    public void startGraphicsThread() { synchronized(this.getTreeLock()){ graphicsThread.start(); } }
}