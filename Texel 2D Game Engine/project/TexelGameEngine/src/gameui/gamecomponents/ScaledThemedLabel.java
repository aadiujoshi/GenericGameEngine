package gameui.gamecomponents;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import gameui.ScaledLayoutValues;
import main.TextureResourceLoader;

public class ScaledThemedLabel extends JLabel implements ScaledLayoutValues{
    private double heightPositionCoefficient;
    private double widthPositionCoefficient;
    private double heightDimensionCoefficient;
    private double widthDimensionCoefficient;



    public ScaledThemedLabel(String imageFilename, double widthPositionCoefficient, double heightPositionCoefficient, double widthDimensionCoefficient, double heightDimensionCoefficient){
        super(new ImageIcon(TextureResourceLoader.getTexture(imageFilename)));

        this.heightPositionCoefficient = heightPositionCoefficient;
        this.widthPositionCoefficient = widthPositionCoefficient;
        this.widthDimensionCoefficient = widthDimensionCoefficient;
        this.heightDimensionCoefficient = heightDimensionCoefficient;
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
