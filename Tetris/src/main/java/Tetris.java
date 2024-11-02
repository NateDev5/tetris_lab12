import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Tetris extends GameProcess implements KeyListener {
    private GameWindow win;
    private Piece currentPiece;
    private Timer pieceTimer;
    private Grid grid;

    private int gridSize = 40;
    private int moveSpeed = 500; // in ms

    public Tetris() {
        win = new GameWindow();
        grid = new Grid(gridSize, win.getSize());
        win.add(grid);
        win.setComponentZOrder(grid, 1);
        win.addKeyListener(this);
    }

    @Override
    protected void onStart() {
        win.setVisible(true);
        addPiece(Pieces.randomPiece());
    }

    @Override
    protected void render() {
    }

    @Override
    protected void update() {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();

        if(currentPiece == null) return;
        switch (key) {
            case 'w':
                currentPiece.rotate();
                break;
            case 's':
                pieceTimer.stop();
                currentPiece.moveDown();
                pieceTimer.restart();
                break;
            case 'a':
                currentPiece.moveLeft();
                break;
            case 'd':
                currentPiece.moveRight();
                break;
            default:
                break;
        }
    }

    private void addPiece (PieceData pieceData) {
        if(currentPiece != null) {
            win.remove(0);
            for(Point p : currentPiece.getOccupiedCells())
                grid.setStateOccupied(p.x, p.y, currentPiece.getPiceData());
            win.revalidate();
            win.repaint();
        }

        currentPiece = new Piece(pieceData, gridSize, grid, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPiece(Pieces.randomPiece());
            }
        });
        currentPiece.enableDebug();
        win.add(currentPiece);
        win.setComponentZOrder(currentPiece, 0);
        if(pieceTimer != null && pieceTimer.isRunning()) pieceTimer.stop();
        pieceTimer = new Timer(moveSpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPiece.moveDown();
            }
        });
        pieceTimer.start();

        currentPiece.revalidate();
        currentPiece.repaint();
    }
}
