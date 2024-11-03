import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.net.URL;

public class Grid extends JPanel {
    private int cellSize;
    private Dimension winDimensions;
    private Bounds gridBounds;
    private int rows, cols;
    private GridCell[][] cells;

    private ActionListener onRowFull;
    private Image cellImage;

    public Grid(int cellSize, Dimension windowDimensions, ActionListener onRowFull) {
        this.cellSize = cellSize;
        this.winDimensions = windowDimensions;
        this.onRowFull = onRowFull;

        rows = (int)(winDimensions.getHeight() / cellSize);
        cols = (int)(winDimensions.getWidth() / cellSize);
        cells = new GridCell[rows][cols];
        gridBounds = new Bounds(0, rows * cellSize, 0, cols * cellSize);

        try {
            URL imageUrl = getClass().getClassLoader().getResource("cell.png");
            if (imageUrl != null)
                this.cellImage = ImageIO.read(imageUrl);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(cellImage == null) {
            for (int row = 0; row <= rows; row++) {
                int y = row * cellSize;
                g.drawLine(0, y, getWidth(), y);
            }

            for (int col = 0; col <= cols; col++) {
                int x = col * cellSize;
                g.drawLine(x, 0, x, getHeight());
            }
        }

        g.setColor(Color.black);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * cellSize;
                int y = row * cellSize;
                if (cells[row][col] != null) {
                    GridCell cell = cells[row][col];
                    if(cell.getTileImage() != null)
                        g.drawImage(cell.getTileImage(), x, y, cellSize, cellSize, null);
                    else {
                        g.setColor(cells[row][col].getColor());
                        g.fillRect(x, y, cellSize, cellSize);
                    }
                }
                else if(cellImage != null)
                    g.drawImage(cellImage, x, y, cellSize, cellSize, null);
            }
        }
    }

    public Bounds getGridBounds() {
        return gridBounds;
    }

    public void setStateOccupied(int x, int y, PieceData data) {
        cells[y][x] = new GridCell(data.getName(), data.getColor(), data.getTileImage());
    }

    public boolean willCollide(Point point) {
        return (cells[point.y][point.x] != null);
    }

    public void checkForFullRows() {
        int numOfRows = 0;
        for (int row = 0; row < rows; row++) {
            boolean isFull = true;
            for (int col = 0; col < cols; col++) {
                if (cells[row][col] == null) {
                    isFull = false;
                    break;
                }
            }

            if (isFull) {
                for (int r = row; r > 0; r--) {
                    for (int col = 0; col < cols; col++)
                        cells[r][col] = cells[r - 1][col];

                    for (int col = 0; col < cols; col++)
                        cells[0][col] = null;

                    row--;
                }
                numOfRows++;
            }
        }

        if(numOfRows > 0) {
            int score = 0;
            switch (numOfRows) {
                case 1:
                    score += 100;
                    break;
                case 2:
                    score += 200;
                    break;
                case 3:
                    score += 500;
                    break;
                default:
                    if(numOfRows >= rows)
                        score += 800;
                    break;
            }

            putClientProperty("score", score);
            onRowFull.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "rowFull"));
        }
    }
}

class GridCell {
    private String pieceName;
    private Color color;
    private Image tileImage;

    public GridCell(String pieceName, Color color, Image tileImage) {
        this.pieceName = pieceName;
        this.color = color;
        this.tileImage = tileImage;
    }

    public String getPieceName() {
        return pieceName;
    }

    public Color getColor() {
        return color;
    }

    public Image getTileImage() {
        return tileImage;
    }
}