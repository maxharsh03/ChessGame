package chessgame.chess.player;

import java.util.ArrayList;

import chessgame.chess.piece.Color;
import chessgame.chess.piece.Piece;

/**
 * Player represents a player for the chess game, either black or white. This includes information about 
 * the player like what pieces they have left, what state they are in, how many pieces they have 
 * captured, and their score based on their current position. 
 * @author maxharsh
 *
 */
public class Player {
	/** name of player */
	private String name;
	/** color of player */
    private Color color;
    /** List of pieces that a player has left on the board */
    private ArrayList<Piece> pieces;
    /** List of pieces that a player that has captured from their opponent */
    private ArrayList<Piece> captured;
    /** player of opponent */
    private Color opponentColor;

    /**
     * Constructs a Player object with values for all fields.
     * @param name
     * @param color
     */
    public Player(String name, Color color, Color opponentColor) {
        this.name = name;
        this.color = color;
        this.opponentColor = opponentColor;
        this.pieces = new ArrayList<Piece>();
        this.captured = new ArrayList<Piece>();
    }

    /**
     * Retrieve player's name.
     * @return
     */
    public String getName() {
        return name;
    }
    
    public Color getOpponentColor() {
    	return opponentColor;
    }
    
    /**
     * Retrieve player's color.
     * @return
     */
    public Color getColor() {
        return color;
    }

    /**
     * Retrieve list of player's pieces.
     * @return
     */
    public ArrayList<Piece> getPieces() {
        return pieces;
    }
    
    /**
     * Retrieve list of pieces a player captured.
     * @return
     */
    public ArrayList<Piece> getCapturedPieces() {
        return captured;
    }

    /**
     * Add a piece to a player's list of current pieces.
     * @param piece
     */
    public void addPiece(Piece piece) {
        pieces.add(piece);
    }
    
    /**
     * Add a piece that a player just captured. 
     * @param piece
     */
    public void addCaptured(Piece piece) {
        captured.add(piece);
    }

    /**
     * Remove a piece from a player's current list of pieces.
     * @param piece
     */
    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }
    
    /*
    public boolean isCheck(Board board) {
    	
    }
    */
}
