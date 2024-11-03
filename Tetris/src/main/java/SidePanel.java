import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class SidePanel extends JPanel {
    private Font font;
    private int score = 0;
    private int level = 1;
    private int linesToLevelUp = GameSettings.linesToLevelUp;
    private int totalCleared = 0;
    private ArrayList<PieceData> nextPieces;
    private int gridSize;
    private ActionListener onResetPressed;

    public SidePanel(int gridSize, ActionListener onResetPressed) {
        this.gridSize = gridSize / 2;
        this.onResetPressed = onResetPressed;
        setSize(WindowInfo.sidePanelSize);
        setBounds(WindowInfo.gridSize.width, 0, WindowInfo.sidePanelSize.width, WindowInfo.sidePanelSize.height);
        setBackground(Color.BLACK);

        // Set layout to null for manual positioning
        setLayout(null);

        try {
            URL fontUrl = getClass().getClassLoader().getResource("font.ttf");
            if (fontUrl != null) {
                font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream()).deriveFont(30f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream()));
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        CustomTextButton button = new CustomTextButton("RESET", font);
        button.addActionListener(onResetPressed);
        button.setBounds(0, WindowInfo.sidePanelSize.height - 100, WindowInfo.sidePanelSize.width - 15, 30);
        add(button);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();

        g.drawString("Level: " + level, 10, 30);
        g.drawString("NEXT:", 10, 200);

        g.setFont(font.deriveFont(15f));
        g.drawString("Score: " + score, 10, 100);

        g.setFont(font.deriveFont(17f));
        g.drawString("Next level in: " + linesToLevelUp, 10, 50);
        g.drawString("Total cleared: " + totalCleared, 10, 70);

        if(nextPieces != null && !nextPieces.isEmpty()) {
            for(int i = 0; i < nextPieces.size(); i++)
                drawPiece(g, nextPieces.get(i), i);
        }
    }

    private void drawPiece (Graphics g, PieceData piece, int i) {
        g.setColor(piece.getColor());
        Point[] shape = (piece.getShape().length != 1) ? piece.getShape()[1] : piece.getShape()[0];
        int yOffset = 180;
        for (Point p : shape) {
            int x = p.x * gridSize + 10;
            int y = p.y * gridSize + yOffset + (gridSize * 2 + i * 70);
            if (piece.getTileImage() == null)
                g.fillRect(x, y, gridSize, gridSize);
            else g.drawImage(piece.getTileImage(), x, y, gridSize, gridSize, null);
        }
    }

    private int getCenterX (FontMetrics fm, String str) {
        int textWidth = fm.stringWidth(str);
        return (getWidth() - textWidth) / 2;
    }

    public void setScore (int score) {
        this.score = score;
        repaint();
    }

    public void setNextPieces (ArrayList<PieceData> nextPieces) {
        this.nextPieces = nextPieces;
        repaint();
    }

    public void setLevel (int level) {
        this.level = level;
        repaint();
    }

    public void setLinesToLevelUp (int linesCleared) {
        this.linesToLevelUp = GameSettings.linesToLevelUp - linesCleared;
    }

    public void setTotalCleared (int totalCleared) {
        this.totalCleared = totalCleared;
    }

    public void reset() {
        level = 1;
        score = 0;
        linesToLevelUp = GameSettings.linesToLevelUp;
        totalCleared = 0;
        repaint();
    }
}

class CustomTextButton extends JButton {

    public CustomTextButton(String text, Font font) {
        super(text);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setForeground(Color.WHITE);
        setFont(font);
        setFocusable(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(Color.WHITE);
            }
        });
    }
}