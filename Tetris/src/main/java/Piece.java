import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Piece extends JPanel {
    private Point position = new Point(0, 0);
    private int gridSize;
    private int currentRotIndex = 0;
    private boolean canMove = true;
    private boolean debug = false;
    private PieceData data;
    private Grid grid;

    private ActionListener onReachBottom;

    public Piece(PieceData data, int gridSize, Grid grid, ActionListener onReachBottom) {
        this.gridSize = gridSize;
        this.onReachBottom = onReachBottom;
        this.data = data;
        this.grid = grid;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(data.getColor());
        for(Point p : data.getShape()[currentRotIndex]) {
            if(data.getTileImage() == null)
                g.fillRect(p.x * gridSize + position.x, p.y * gridSize + position.y, gridSize, gridSize);
            else g.drawImage(data.getTileImage(), p.x * gridSize + position.x, p.y * gridSize + position.y, null);
        }

        if(debug) debug(g);
    }

    // for debugging
    public void debug(Graphics g) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for (Point p : data.getShape()[currentRotIndex]) {
            int x = p.x * gridSize + position.x;
            int y = p.y * gridSize + position.y;

            if (x < minX) minX = x;
            if (x > maxX) maxX = x;
            if (y < minY) minY = y;
            if (y > maxY) maxY = y;
        }

        g.setColor(Color.blue);
        g.drawRect(minX, minY, (maxX - minX) + gridSize, (maxY - minY) + gridSize);


        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString(String.format("Rot: [%d] Height: [%d]", currentRotIndex, getPieceHeight()), minX, minY - 20);
        g.drawString(String.format("Pos: [X: %d, Y: %d]", position.x, position.y),  minX, minY);
        g.drawString(String.format("Grid: [Top: %d, Bottom: %d, Left: %d, Right: %d]", grid.getGridBounds().getTop(), grid.getGridBounds().getBottom(), grid.getGridBounds().getLeft(), grid.getGridBounds().getRight()),  minX, minY - 40);
    }

    public void enableDebug () {
        debug = true;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    public void moveDown() {
        if (!canMove) return;
        Point offset = new Point(0, gridSize);
        if (!willCollide(offset)) {
            position.y += gridSize;
            repaint();
            grid.checkForFullRows();
        } else stopMoving();
    }

    public void moveLeft() {
        if (!canMove) return;
        Point offset = new Point(-gridSize, 0);
        if (!willCollide(offset)) {
            position.x -= gridSize;
            repaint();
        }
    }

    public void moveRight() {
        if (!canMove) return;
        Point offset = new Point(gridSize, 0);
        if (!willCollide(offset)) {
            position.x += gridSize;
            repaint();
        }
    }

    public void rotate() {
        if(!canMove) return;
        currentRotIndex = (currentRotIndex + 1) % data.getShape().length;
        repaint();
    }

    private int getPieceHeight () {
        // return piece hight based on current rotation
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Point p : data.getShape()[currentRotIndex]) {
            int y = p.y * gridSize + position.y;
            if (y < minY) minY = y;
            if (y > maxY) maxY = y;
        }

        return (maxY - minY) + gridSize;
    }

    public ArrayList<Point> getOccupiedCells() {
        ArrayList<Point> occupiedCells = new ArrayList<>();

        for (Point p : data.getShape()[currentRotIndex]) {
            int cellX = (p.x * gridSize + position.x) / gridSize;
            int cellY = (p.y * gridSize + position.y) / gridSize;
            occupiedCells.add(new Point(cellX, cellY));
        }

        return occupiedCells;
    }

    public PieceData getPiceData () {
        return data;
    }

    private boolean willCollide(Point offset) {
        for (Point p : data.getShape()[currentRotIndex]) {
            int cellX = (p.x * gridSize + position.x + offset.x) / gridSize;
            int cellY = (p.y * gridSize + position.y + offset.y) / gridSize;

            if (cellX < 0 || cellX >= grid.getWidth() / gridSize ||
                    cellY >= grid.getHeight() / gridSize ||
                    grid.willCollide(new Point(cellX, cellY))) {
                return true;
            }
        }
        return false;
    }


    private void stopMoving () {
        canMove = false;
        onReachBottom.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "reachedBottom"));
    }
}
