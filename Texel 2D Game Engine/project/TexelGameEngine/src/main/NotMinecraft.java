package main;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.stream.Stream;
import java.awt.image.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import gameui.FractionalLayout;
import gameui.gamecomponents.FractionalLayoutPanel;
import gameui.gamecomponents.ThemedButton;

import java.awt.event.*;
import java.awt.*;

public class NotMinecraft {
    //always in view
    private JFrame frame;
    private JPanel backgroundPanel;
    private JPanel contentPanelLayoutPanel;

    //opened by buttons
    private FractionalLayoutPanel homeContentPanel;
    private FractionalLayoutPanel worldPickerContentPanel;

    private boolean fullscreen = false;

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
                    System.out.println(fullscreen);
                }
            }

            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
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
                backgroundPanel = new JPanel(){ 
                    public void paintComponent(Graphics gr){ 
                        Graphics2D g = (Graphics2D)gr;
                        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
                        BufferedImage bg = TextureResourceLoader.getTexture("notminecraftbanner.png");
                        g.drawImage(bg, 0, 0, null); 
                    } 
                };
                contentPanelLayoutPanel = new JPanel();
                homeContentPanel = new FractionalLayoutPanel(0.5,0.45,0.2,0.17);
                //worldPickerContentPanel = new FractionalLayoutPanel();
                
                //=====================================================================
                //SIZING / LAYOUT
                //=====================================================================
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
                JButton back = new JButton(){
                    @Override
                    public void paintComponent(Graphics g){
                        TextureResourceLoader.getTexture("backbutton.png");
                    }
                }
                
                backgroundPanel.setPreferredSize(frame.getSize());

                contentPanelLayoutPanel.setPreferredSize(frame.getSize());
                contentPanelLayoutPanel.setLayout(new FractionalLayout());
                contentPanelLayoutPanel.setOpaque(false);

                homeContentPanel.setLayout(new FractionalLayout());
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
                frame.add(backgroundPanel);
                // frame.add(contentPanelLayoutPanel);

                frame.setVisible(true);
                setFullscreen(true);
            }
        });
    }

    public void initHomeContentPanel(){
        ThemedButton tb1 = new ThemedButton("Play Game", 0.5, 0.25, 0.75, 0.2);
        ThemedButton tb2 = new ThemedButton("Multiplayer", 0.5, 0.5, 0.75, 0.2);
        ThemedButton tb3 = new ThemedButton("Quit Game", 0.5, 0.75, 0.75, 0.2);

        tb1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanelLayoutPanel.remove(homeContentPanel);
                contentPanelLayoutPanel.add(worldPickerContentPanel);
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
                frame.dispose();
            }
        });

        homeContentPanel.add(tb1);
        homeContentPanel.add(tb2);
        homeContentPanel.add(tb3);
    }
    
    public void initWorldPickerContentPanel(){
        //TODO 
    }
    
    public boolean isFullscreen() { return this.fullscreen; }
    public void setFullscreen(boolean full) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                fullscreen = full;
                if(fullscreen){
                    // frame.setUndecorated(true);
                    GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
                }
                else if(!fullscreen){
                    GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
                    frame.setSize(500,500);
                }
                frame.validate();
            }
        });
    }
}
