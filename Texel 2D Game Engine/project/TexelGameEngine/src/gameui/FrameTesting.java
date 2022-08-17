package gameui;


import javax.swing.JFrame;
import javax.swing.JPanel;

import gameui.gamecomponents.ScaledThemedButton;
import gameui.gamecomponents.ScaledThemedTextField;

public class FrameTesting{ //implements java.awt.event.KeyListener{

    public static void main(String agrdfafdasfdas[]){
        new FrameTesting();
    }

    public FrameTesting(){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.addKeyListener(this);

        ScaledLayout gl = new ScaledLayout();

        panel.setLayout(gl);
        panel.add(new ScaledThemedTextField(0.5, 0.5, 0.1, 0.05));
        panel.add(new ScaledThemedButton("text", 0.75, 0.75, 0.2, 0.1));
        frame.add(panel);
        frame.setVisible(true);
    }

    // @Override
    // public void keyTyped(KeyEvent e) {
    //     // System.out.println(e.getKeyChar());
    // }

    // @Override
    // public void keyPressed(KeyEvent e) {
    //     // System.out.println(e.getKeyChar());
        
    // }

    // @Override
    // public void keyReleased(KeyEvent e) {
    //     System.out.println(e);
    // }
    
}
