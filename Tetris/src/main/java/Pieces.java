import java.awt.*;

public class Pieces {

    public static PieceData o_piece = new PieceData(
            "O_Piece",
            Color.yellow,
            new Point[][]{
                    {
                            new Point(0, 0), new Point(0, 1),
                            new Point(1, 0), new Point(1, 1)
                    }
            }
    );

    public static PieceData l_piece = new PieceData(
            "L_Piece",
            Color.orange,
            new Point[][]{
                    {
                            new Point(0, 0), new Point(0, 1),
                            new Point(0, 2), new Point(1, 2)
                    },
                    {
                            new Point(0, 1), new Point(1, 1),
                            new Point(2, 1), new Point(2, 0)
                    },
                    {
                            new Point(1, 0), new Point(1, 1),
                            new Point(1, 2), new Point(0, 0)
                    },
                    {
                            new Point(0, 1), new Point(1, 1),
                            new Point(2, 1), new Point(0, 2)
                    }
            }
    );

    public static PieceData j_piece = new PieceData(
            "J_Piece",
            Color.blue,
            new Point[][]{
                    {
                            new Point(1, 0), new Point(1, 1),
                            new Point(1, 2), new Point(0, 2)
                    },
                    {
                            new Point(0, 0), new Point(0, 1),
                            new Point(1, 1), new Point(2, 1)
                    },
                    {
                            new Point(1, 0), new Point(1, 1),
                            new Point(1, 2), new Point(2, 0)
                    },
                    {
                            new Point(0, 1), new Point(1, 1),
                            new Point(2, 1), new Point(2, 2)
                    }
            }
    );

    public static PieceData t_piece = new PieceData(
            "T_Piece",
            Color.magenta,
            new Point[][]{
                    {
                            new Point(0, 1), new Point(1, 0),
                            new Point(1, 1), new Point(1, 2)
                    },
                    {
                            new Point(0, 1), new Point(1, 1),
                            new Point(1, 2), new Point(2, 1)
                    },
                    {
                            new Point(1, 0), new Point(1, 1),
                            new Point(1, 2), new Point(2, 1)
                    },
                    {
                            new Point(0, 1), new Point(1, 0),
                            new Point(1, 1), new Point(2, 1)
                    }
            }
    );

    public static PieceData s_piece = new PieceData(
            "S_Piece",
            Color.green,
            new Point[][]{
                    {
                            new Point(1, 0), new Point(1, 1),
                            new Point(0, 1), new Point(0, 2)
                    },
                    {
                            new Point(0, 1), new Point(1, 1),
                            new Point(1, 2), new Point(2, 2)
                    }
            }
    );

    public static PieceData z_piece = new PieceData(
            "Z_Piece",
            Color.red,
            new Point[][]{
                    {
                            new Point(0, 0), new Point(0, 1),
                            new Point(1, 1), new Point(1, 2)
                    },
                    {
                            new Point(1, 0), new Point(1, 1),
                            new Point(0, 1), new Point(2, 0)
                    }
            }
    );

    public static PieceData i_piece = new PieceData(
            "I_Piece",
            Color.cyan,
            new Point[][]{
                    {
                            new Point(0, 0), new Point(0, 1),
                            new Point(0, 2), new Point(0, 3)
                    },
                    {
                            new Point(0, 0), new Point(1, 0),
                            new Point(2, 0), new Point(3, 0)
                    }
            }
    );

    private static PieceData[] allPieces = new PieceData[]{
            o_piece, l_piece, j_piece, t_piece, s_piece, z_piece, i_piece
    };

    public static PieceData randomPiece() {
        int i = (int) (Math.random() * allPieces.length);
        return allPieces[i];
    }
}
