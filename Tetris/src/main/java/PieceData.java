import java.awt.*;

public class PieceData {
    private String name;
    private Color color;
    private Point[][] shape;

    public PieceData(String name, Color color, Point[][] shape) {
        this.name = name;
        this.shape = shape;
        this.color = color;
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
}
