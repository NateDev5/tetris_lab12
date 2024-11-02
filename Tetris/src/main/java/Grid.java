import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Grid extends JPanel {
    private int cellSize;
    private Dimension winDimensions;
    private Bounds gridBounds;
    private int rows, cols;
    private GridCell[][] cells;

    public Grid(int cellSize, Dimension windowDimensions) {
        this.cellSize = cellSize;
        this.winDimensions = windowDimensions;

        rows = (int)(winDimensions.getHeight() / cellSize);
        cols = (int)(winDimensions.getWidth() / cellSize);
        System.out.println("rows: " + rows);
        System.out.println("cols: " + cols);
        cells = new GridCell[cols][rows];
        gridBounds = new Bounds(0, rows * cellSize, 0, cols * cellSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row <= rows; row++) {
            int y = row * cellSize;
            g.drawLine(0, y, getWidth(), y);
        }

        for (int col = 0; col <= cols; col++) {
            int x = col * cellSize;
            g.drawLine(x, 0, x, getHeight());
        }

        g.setColor(Color.black);
        for (int cells_row = 0; cells_row < rows; cells_row++) {
            for (int cells_col = 0; cells_col < cols; cells_col++) {
                if (cells[cells_col][cells_row] != null) {
                    g.setColor(cells[cells_col][cells_row].getColor());
                    int x = cells_col * cellSize;
                    int y = cells_row * cellSize;
                    g.fillRect(x, y, cellSize, cellSize);
                }
            }
        }
    }

    public Bounds getGridBounds () {
        return gridBounds;
    }

    public void setStateOccupied (int x, int y, PieceData data) {
        cells[x][y] = new GridCell(data.getName(), data.getColor());
    }

    public boolean willCollide (Point point) {
        return (cells[point.x][point.y] != null);
    }

    public void checkForFullRows () {
        for (int row = 0; row < rows; row++) {
            boolean isFull = false;
            int i = 0;
            for (int col = 0; col < cols; col++) {
                if (cells[col][row] == null) {
                    isFull = false;
                    break;
                }
                else i++;
            }
            if(isFull)
                System.out.printf("row %d is full", row);

            if(row == rows - 1)
                System.out.printf("row %d\n", row);
        }
    }
}

class GridCell {
    private String pieceName;
    private Color color;

    public GridCell(String pieceName, Color color) {
        this.pieceName = pieceName;
        this.color = color;
    }

    public String getPieceName() {
        return pieceName;
    }

    public Color getColor() {
        return color;
    }
}