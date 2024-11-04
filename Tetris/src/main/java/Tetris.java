import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Tetris extends GameProcess implements KeyListener{
    private GameWindow win;
    private Piece currentPiece;
    private Timer pieceTimer;
    private Grid grid;

    private int gridSize = 40;
    private int moveSpeed = 500; // in ms

    private int score = 0;

    private SidePanel sidePanel;

    private ArrayList<PieceData> nextPieces;

    private int currentLinesToLevel = 0;
    private int level = 1;

    public Tetris() {
        win = new GameWindow();
        win.addKeyListener(this);

        nextPieces = new ArrayList<PieceData>();

        grid = new Grid(gridSize, WindowInfo.gridSize, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                score += (int) ((JPanel) e.getSource()).getClientProperty("score");
                currentLinesToLevel += (int) ((JPanel) e.getSource()).getClientProperty("linesCleared");
                sidePanel.setLinesToLevelUp(currentLinesToLevel);
                sidePanel.setScore(score);
            }
        });

        sidePanel = new SidePanel(gridSize, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        win.add(sidePanel, BorderLayout.CENTER);

        grid.setBackground(Color.BLACK);
        win.add(grid);
    }
    @Override
    protected void onStart() {
        win.setVisible(true);
        generateNextPieces();
        addPiece(Pieces.randomPiece());
    }

    @Override
    protected void render() {
    }

    @Override
    protected void update() {
        generateNextPieces();

        if(currentLinesToLevel >= GameSettings.linesToLevelUp) {
            if(currentLinesToLevel > GameSettings.linesToLevelUp) {
                currentLinesToLevel = GameSettings.linesToLevelUp - currentLinesToLevel;
            }
            else currentLinesToLevel = 0;
            level++;
            sidePanel.setLevel(level);
            sidePanel.setLinesToLevelUp(currentLinesToLevel);
            increaseSpeed();
        }
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
        if(grid.isFirstRowUsed()) {
            reset();
            return;
        }
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
                addPiece(nextPieces.removeFirst());
            }
        });
        //currentPiece.enableDebug();
        win.add(currentPiece);
        win.setComponentZOrder(currentPiece, 0);
        if(pieceData.getTileImage() != null)
            win.setIconImage(pieceData.getTileImage());
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

    private void generateNextPieces () {
        boolean modified = false;
        while (nextPieces.size() < 4) {
            nextPieces.add(Pieces.randomPiece());
            if(!modified) modified = true;
        }

        if(modified) sidePanel.setNextPieces(nextPieces);
    }

    private void reset () {
        score = 0;
        level = 1;
        nextPieces = new ArrayList<PieceData>();
        grid.reset();
        sidePanel.reset();
        currentPiece = null;
        win.remove(0);
        addPiece(Pieces.randomPiece());
        sidePanel.repaint();
        win.revalidate();
        win.repaint();
    }

    private void increaseSpeed () {
        if(moveSpeed > 50)
            moveSpeed -= GameSettings.speedIncreasePerLevelInMs;
    }
}
