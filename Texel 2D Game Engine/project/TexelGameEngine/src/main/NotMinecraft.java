package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import client_testing.GameMapGenerator;
import client_testing.GameMapGenerator.GaussianTerrain;
import client_testing.Player;
import gameui.ScaledLayout;
import gameui.gamecomponents.ScaledLayoutPanel;
import gameui.gamecomponents.ScaledThemedButton;
import gameui.gamecomponents.ScaledThemedLabel;
import texelgameengine.game.GameWorld;
import texelgameengine.gamepanel.GameDisplayPanel;
import texelgameengine.graphics.GameBackground;
import texelgameengine.world.GameMap;

public class NotMinecraft {
    //always in view
    private JFrame frame;

    private JLabel backgroundLabel;
    private Container contentPanelLayoutPanel;

    public static final Dimension SCREEN_SIZE = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

    private GameDisplayPanel gamePanel;

    private Point frameLocation;

    //opened by buttons
    private ScaledLayoutPanel homeContentPanel;
    // private ScaledLayoutPanel worldPickerContentPanel;

    private boolean fullscreen = false;
    public static boolean validPaint = false;

    public static void main(String args[]){
        new NotMinecraft();
    }

    public NotMinecraft(){
        TextureResourceLoader.loadTexturesFromPath(new File("C:\\Users\\aadiu\\Desktop\\Programming Files\\Personal Projects\\Texel 2D Game Engine\\textures"));
        initGUI();
    }

    private void initListeners(){
        frame.addKeyListener(new KeyListener(){

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 122){ //f11
                    setFullscreen(!fullscreen);
                }
            }

            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
        });
        
        frame.addComponentListener(new ComponentListener(){
            public void componentMoved(ComponentEvent e) { try{ frameLocation = frame.getLocationOnScreen(); } catch(java.awt.IllegalComponentStateException i){} }
            public void componentResized(ComponentEvent e) { try{ frameLocation = frame.getLocationOnScreen(); } catch(java.awt.IllegalComponentStateException i) {} }
            public void componentHidden(ComponentEvent e) {} 
            public void componentShown(ComponentEvent e) {}
        });
    }

    private void initGUI(){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                //=====================================================================
                //INIT COMPONENTS
                //=====================================================================
                frame = new JFrame("Not Minecraft");
                backgroundLabel = new ScaledThemedLabel("notminecraftbanner.png", .5, 0.5, 1, 1);
                contentPanelLayoutPanel = new JPanel();
                homeContentPanel = new ScaledLayoutPanel(0.5,0.55,0.2,0.17);
                gamePanel = new GameDisplayPanel(getThis());

                //=====================================================================
                //SIZING / LAYOUT
                //=====================================================================
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
                
                contentPanelLayoutPanel.setPreferredSize(frame.getSize());
                contentPanelLayoutPanel.setLayout(new ScaledLayout());

                homeContentPanel.setLayout(new ScaledLayout());
                homeContentPanel.setOpaque(false);

                // worldPickerContentPanel.dostuff();

                //=====================================================================
                //INIT STUFF
                //=====================================================================
                initListeners();
                initHomeContentPanel();
                // initWorldPickerContentPanel();

                //=====================================================================
                //ADD STUFF
                //=====================================================================
                contentPanelLayoutPanel.add(homeContentPanel);
                contentPanelLayoutPanel.add(backgroundLabel);
                frame.getContentPane().add(contentPanelLayoutPanel);

                frame.setVisible(true);
                setFullscreen(fullscreen);
            }
        });
    }

    public void initHomeContentPanel(){
        ScaledThemedButton tb1 = new ScaledThemedButton("Play Game", 0.5, 0.15, 0.9, 0.3);
        ScaledThemedButton tb2 = new ScaledThemedButton("Multiplayer", 0.5, 0.5, 0.9, 0.3);
        ScaledThemedButton tb3 = new ScaledThemedButton("Quit Game", 0.5, 0.85, 0.9, 0.3);

        tb1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run(){
                        contentPanelLayoutPanel.remove(homeContentPanel);
                        frame.getContentPane().remove(contentPanelLayoutPanel);

                        gamePanel.setWorld(sampleWorld());

                        Player player = new Player(0, 1000, 1f, 1.9f, "playerskin_steve_1.png");

                        gamePanel.setCamera(player.getCamera());
                        gamePanel.getWorld().addEntity(player);
                        frame.addKeyListener(player);

                        frame.getContentPane().add(gamePanel);
                        gamePanel.startGraphicsThread();
                        
                        frame.validate();
                    }
                });
            }
        });
        tb2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("never happening");
            }
        });
        tb3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hit the x button idiot");
            }
        });

        homeContentPanel.add(tb1);
        homeContentPanel.add(tb2);
        homeContentPanel.add(tb3);
    }

    //just for testing
    public GameWorld sampleWorld(){
        GameMap overworld = new GameMap(-32, 32);
        overworld.setBg(new GameBackground(null, new Color(135, 206, 235), 1));
        
        GameWorld world = new GameWorld("testworld", 69696969L, gamePanel);

        GameMapGenerator.GaussianTerrain gen = new GaussianTerrain();

        for(int i = -100; i <= 100; i++){
            gen.generateChunkAt(overworld, i, world.getSeed());
        }

        world.addGameMap(overworld);

        return world;
    }

    //only to be used by inner classes
    private NotMinecraft getThis() { return this; }
    public JFrame getFrame() { return this.frame; }
    public Point getFrameLocation() { return this.frameLocation; }
    public boolean isFullscreen() { return this.fullscreen; }
    public void setFullscreen(boolean full) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                validPaint = false;
                fullscreen = full;
                if(fullscreen){
                    GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
                }
                else if(!fullscreen){
                    GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
                    frame.setSize(500,500);
                }
                validPaint = true;
            }
        });
    }
}
