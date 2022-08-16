package gameui.gamecomponents;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import gameui.ScaledLayoutValues;

public class ScaledThemedTextField extends JTextArea implements ScaledLayoutValues{

    private double heightPositionCoefficient;
    private double widthPositionCoefficient;
    private double heightDimensionCoefficient;
    private double widthDimensionCoefficient;

    public ScaledThemedTextField(double widthPositionCoefficient, double heightPositionCoefficient, double widthDimensionCoefficient, double heightDimensionCoefficient){
        super.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        super.setBackground(Color.GRAY);
        super.setForeground(Color.WHITE);

        this.heightPositionCoefficient = heightPositionCoefficient;
        this.widthPositionCoefficient = widthPositionCoefficient;
        this.widthDimensionCoefficient = widthDimensionCoefficient;
        this.heightDimensionCoefficient = heightDimensionCoefficient;

        this.addMouseListener(new MouseListener(){

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(new Color(120, 120, 120));
                setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(Color.GRAY);
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