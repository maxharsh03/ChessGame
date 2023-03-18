package chessgame.chess.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import chessgame.chess.piece.Piece;

public class ChessGUI extends JFrame {

    private static final long serialVersionUID = 1L;
	private final List<Piece> pieces = new ArrayList<Piece>();
    private JPanel ChessBoard;
    private Piece selectedPiece;
    private Point selectedPoint;

    public ChessGUI() {
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the board panel
        ChessBoard = new JPanel(new GridLayout(8, 8));
        ChessBoard.setSize(400, 400);
        add(ChessBoard, BorderLayout.CENTER);

        // Add the squares to the board
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                JLabel square = new JLabel();
                square.setOpaque(true);
                square.setHorizontalAlignment(SwingConstants.CENTER);
                square.setVerticalAlignment(SwingConstants.CENTER);
                square.setBackground((x + y) % 2 == 0 ? Color.WHITE : Color.GRAY);
                square.addMouseListener(new SquareMouseListener(x, y));
                ChessBoard.add(square);
            }
        }

        // Add the pieces to the board
        addPiece(new Rook(0, 0, Player.WHITE));
        addPiece(new Knight(1, 0, Player.WHITE));
        addPiece(new Bishop(2, 0, Player.WHITE));
        addPiece(new Queen(3, 0, Player.WHITE));
        addPiece(new King(4, 0, Player.WHITE));
        addPiece(new Bishop(5, 0, Player.WHITE));
        addPiece(new Knight(6, 0, Player.WHITE));
        addPiece(new Rook(7, 0, Player.WHITE));
        for (int i = 0; i < 8; i++) {
            addPiece(new Pawn(i, 1, Player.WHITE));
        }
        addPiece(new Rook(0, 7, Player.BLACK));
        addPiece(new Knight(1, 7, Player.BLACK));
        addPiece(new Bishop(2, 7, Player.BLACK));
        addPiece(new Queen(3, 7, Player.BLACK));
        addPiece(new King(4, 7, Player.BLACK));
        addPiece(new Bishop(5, 7, Player.BLACK));
        addPiece(new Knight(6, 7, Player.BLACK));
        addPiece(new Rook(7, 7, Player.BLACK));
        for (int i = 0; i < 8; i++) {
            addPiece(new Pawn(i, 6, Player.BLACK));
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addPiece(Piece piece) {
        pieces.add(piece);
        ImageIcon icon = new ImageIcon(piece.getImage());
        JLabel label = new JLabel(icon, SwingConstants.CENTER);
        label.addMouseListener(new PieceMouseListener(piece));
        board.add(label, piece.getX() + piece.getY() * 8);
    }

    private void movePiece(Piece piece, Point point) {
        piece.move(point.x, point.y);
        board.remove(piece.getX() + piece.getY() * 8);
        board.add(new JLabel(new ImageIcon(piece.getImage()), SwingConstants.CENTER),
                piece.getX() + piece.getY() * 8);
        board.validate();
        board.repaint();
    }

    private class SquareMouseListener extends MouseAdapter {
        private final int x;
        private final int y;

        public SquareMouseListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (selectedPiece != null) {
                movePiece(selectedPiece, new Point(x, y));
                selectedPiece = null;
            }
        }
    }

    private class PieceMouseListener extends MouseAdapter {
        private final Piece piece;

        public PieceMouseListener(Piece piece) {
            this.piece = piece;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (selectedPiece == null) {
                selectedPiece = piece;
                selectedPoint = new Point(piece.getX(), piece.getY());
            } else {
                movePiece(selectedPiece, selectedPoint);
                selectedPiece = null;
            }
        }
    }

    public static void main(String[] args) {
        new ChessGUI();
    }

