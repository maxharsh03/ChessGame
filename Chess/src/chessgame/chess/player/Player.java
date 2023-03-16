/**
 * A better implementation may be that GameManager has an instance of Board so its 
 * easier to get stuff from the board. From there, it can validate moves and look 
 * for checkmates. This information can be passed to a Player which has a list of 
 * Pieces. Look at chatGPT for more info
 */


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
	/** 
	 * 
	 */
	private String name;
	/**
	 * 
	 */
    private Color color;
    /**
     * 
     */
    private ArrayList<Piece> pieces;
    
    private ArrayList<Piece> captured;

    /**
     * 
     * @param name
     * @param color
     */
    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.pieces = new ArrayList<Piece>();
        this.captured = new ArrayList<Piece>();
    }

    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return
     */
    public Color getColor() {
        return color;
    }

    /**
     * 
     * @return
     */
    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    /**
     * 
     * @param piece
     */
    public void addPiece(Piece piece) {
        pieces.add(piece);
    }
    
    public void addCaptured(Piece piece) {
        captured.add(piece);
    }

    /**
     * 
     * @param piece
     */
    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }
}
