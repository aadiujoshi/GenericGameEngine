package gameui.gamecomponents;

import gameui.FractionalLayoutValues;
import javax.swing.JPanel;

public class FractionalLayoutPanel extends JPanel implements FractionalLayoutValues{

    private double heightPositionCoefficient;
    private double widthPositionCoefficient;
    private double heightDimensionCoefficient;
    private double widthDimensionCoefficient;

    public FractionalLayoutPanel(double widthPositionCoefficient, double heightPositionCoefficient, double widthDimensionCoefficient, double heightDimensionCoefficient){
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
