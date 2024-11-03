import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.net.URL;

public class PieceData {
    private String name;
    private Color color;
    private Point[][] shape;
    private Image tileImage = null;

    public PieceData(String name, Color color, Point[][] shape) {
        this.name = name;
        this.shape = shape;
        this.color = color;

        try {
            URL imageUrl = getClass().getClassLoader().getResource(name + ".png");
            if (imageUrl != null)
                this.tileImage = ImageIO.read(imageUrl);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public Point[][] getShape() {
        return shape;
    }

    public Image getTileImage() {
        return tileImage;
    }
}
