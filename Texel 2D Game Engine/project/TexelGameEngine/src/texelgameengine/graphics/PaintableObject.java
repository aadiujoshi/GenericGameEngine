package texelgameengine.graphics;
import java.awt.image.BufferedImage;

public abstract class PaintableObject {

    // public static final int TOP_LAYER = -1;
    // public static final int IGNORE_LAYER = -2;

    private BufferedImage texture;

    public PaintableObject(BufferedImage texture) {
        this.texture = texture;
    }

    public PaintableObject(){}

    // public static BufferedImage getScaledTexture(String textureFilepath, int width, int height) { 
    //     if(textureFilepath == null) return null;
    //     try {
    //         BufferedImage plainTexture = ImageIO.read(new File(textureFilepath));

    //         if(plainTexture.getWidth() == width && plainTexture.getHeight() == height)
    //             return plainTexture;

    //         Image scaledTexture = plainTexture.getScaledInstance((int)width, (int)height, Image.SCALE_SMOOTH); 
    //         BufferedImage output = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_ARGB);
    //         output.getGraphics().drawImage(scaledTexture, 0, 0, null);
    //         return output;
    //     }
    //     catch (IOException e) { e.printStackTrace(); }
    //     return null;
    // }

    public abstract void paintObject(GraphicsEvent e);

    public BufferedImage getTexture(){ return this.texture; }
    public void setTexture(BufferedImage bi) { this.texture = bi; }
}
