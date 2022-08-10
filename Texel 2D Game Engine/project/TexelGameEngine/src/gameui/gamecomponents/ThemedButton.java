package gameui.gamecomponents;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

import gameui.FractionalLayoutValues;

public class ThemedButton extends JButton implements FractionalLayoutValues{

    private double heightPositionCoefficient;
    private double widthPositionCoefficient;
    private double heightDimensionCoefficient;
    private double widthDimensionCoefficient;

    public ThemedButton(String text, double widthPositionCoefficient, double heightPositionCoefficient, double widthDimensionCoefficient, double heightDimensionCoefficient){
        super(text);
        super.setFocusable(false); 
        super.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        super.setBackground(Color.GRAY);
        super.setForeground(Color.LIGHT_GRAY);

        UIManager.put("Button.select", new Color(100, 100, 100));

        // super.setContentAreaFilled(false);

        this.heightPositionCoefficient = heightPositionCoefficient;
        this.widthPositionCoefficient = widthPositionCoefficient;
        this.widthDimensionCoefficient = widthDimensionCoefficient;
        this.heightDimensionCoefficient = heightDimensionCoefficient;

        this.addMouseListener(new MouseListener(){

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(new Color(100, 100, 100));
                setForeground(new Color(128, 128, 128));
                setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(Color.GRAY);
                setForeground(Color.LIGHT_GRAY);
                setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
            
            @Override
            public void mouseClicked(MouseEvent e) {}
        });
    }

    @Override
    public double getHeightPositionCoefficient() { return heightPositionCoefficient; }

    @Override
    public double getWidthPositionCoefficient() { return widthPositionCoefficient; }

    @Override
    public double getHeightDimensionCoefficient() { return heightDimensionCoefficient; }

    @Override
    public double getWidthDimensionCoefficient() { return widthDimensionCoefficient; }
}
