public class Main {
    public static void main(String[] args) {
        try {
            Tetris tetris = new Tetris();
            tetris.startGame();
        }
        catch (Exception e) {
            throw e;
        }
    }
}
