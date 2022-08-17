package gameui.gamecomponents;

import gameui.ScaledLayoutValues;
import javax.swing.JPanel;

public class ScaledLayoutPanel extends JPanel implements ScaledLayoutValues{

    private double heightPositionCoefficient;
    private double widthPositionCoefficient;
    private double heightDimensionCoefficient;
    private double widthDimensionCoefficient;

    public ScaledLayoutPanel(double widthPositionCoefficient, double heightPositionCoefficient, double widthDimensionCoefficient, double heightDimensionCoefficient){
        super();
        this.heightPositionCoefficient = heightPositionCoefficient;
        this.widthPositionCoefficient = widthPositionCoefficient;
        this.widthDimensionCoefficient = widthDimensionCoefficient;
        this.heightDimensionCoefficient = heightDimensionCoefficient;
    }

    public double getHeightPositionCoefficient() { return heightPositionCoefficient; }
    public double getWidthPositionCoefficient() { return widthPositionCoefficient; }
    public double getHeightDimensionCoefficient() { return heightDimensionCoefficient; }
    public double getWidthDimensionCoefficient() { return widthDimensionCoefficient; }
}
