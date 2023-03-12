package chessgame.chess.manager;

import java.util.HashMap;

import chessgame.chess.board.Board;
import chessgame.chess.piece.Color;
import chessgame.chess.piece.Piece;
import chessgame.chess.piece.Type;
import chessgame.chess.player.Player;

/**
 * GameManager handles the rules of a chess game. This means that it controls how pieces move, 
 * how captures are made, and how the game starts and ends. GameManager manages the board, the players, 
 * and the state the game is in. 
 * @author maxharsh
 *
 */
public class GameManager {

	/** Represents the Piece that was last moved on the board */
	public Piece lastPieceMoved;
	/** Maps each starting coordinate to its ending coordinate for all valid moves */
	public HashMap<Integer[], Integer[]> listAllValidMoves;
	/** whether it is white's turn or not*/
	public boolean whitesTurn;
	
	public GameManager() {
		listAllValidMoves = new HashMap<Integer[], Integer[]>();
		lastPieceMoved = null;
		whitesTurn = true;
	}
	
	/**
	 * 
	 * @param rowInitial
	 * @param columnInitial
	 * @param rowFinal
	 * @param columnFinal
	 * @param board
	 */
	public void move(int rowInitial, int columnInitial, int rowFinal, int columnFinal, Board board, Piece piece) {
		if(whitesTurn && piece.getColor() == Color.BLACK) {
			throw new IllegalArgumentException("Invalid move");
		} else if(!whitesTurn && piece.getColor() == Color.BLACK) {
			throw new IllegalArgumentException("Invalid move");
		}
		// no pieces for a color can move if their king is in check, unless it is the king getting out of check
		// or the piece getting the king out of check by either capturing the piece putting the king in check or 
		// blocking the piece putting the king in check
		// a piece should not be moved if it results in the capture of the opposing king
		// a piece should not be moved if it results in its own king being put in check
		else if(piece.getType() == Type.PAWN && piece.isValid(rowFinal, columnFinal, board)) {
			
			moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
		} else if(piece.getType() == Type.BISHOP && piece.isValid(rowFinal, columnFinal, board)) {
			moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
		} else if(piece.getType() == Type.KNIGHT && piece.isValid(rowFinal, columnFinal, board)) {
			moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
		} else if(piece.getType() == Type.ROOK && piece.isValid(rowFinal, columnFinal, board)) {
			moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
		} else if(piece.getType() == Type.QUEEN && piece.isValid(rowFinal, columnFinal, board)) {
			moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
		} else if(piece.getType() == Type.KING && piece.isValid(rowFinal, columnFinal, board)) {
			moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
		}
	}
	
	public void moveHelper(int rowInitial, int columnInitial, int rowFinal, int columnFinal, Board board, Piece piece) {
		board.getBoard()[rowFinal][columnFinal] = piece;
		 board.getBoard()[rowInitial][columnInitial] = null;
		
	}
	
	/** 
	 * Handles functionality when a piece captures another piece 
	 * @param rowInitial
	 * @param columnInitial
	 * @param rowFinal
	 * @param columnFinal
	 */
	public void capture(int rowInitial, int columnInitial, int rowFinal, int columnFinal, Board board) {
		
	}
	
	public static void rightCastle() {
		
	}
	
	public static void leftCastle() {
		
	}
	
	/** 
	 * Updates score of the game when a piece is captured
	 * @param player
	 * @return
	 */
	public int updateScore(Player player, Piece piece) {
		return 0;
	}

	/**
	 * @return the lastPieceMoved
	 */
	public Piece getLastPieceMoved() {
		return lastPieceMoved;
	}

	/**
	 * @param lastPieceMoved the lastPieceMoved to set
	 */
	public void setLastPieceMoved(Piece lastPieceMoved) {
		if(lastPieceMoved != null) {
			this.lastPieceMoved = lastPieceMoved;
		}
	}

	/**
	 * @return the listAllValidMoves
	 */
	public HashMap<Integer[], Integer[]> getListAllValidMoves() {
		return listAllValidMoves;
	}
	
	public void resetListAllValidMoves() {
		listAllValidMoves.clear();
	}

	/**
	 * @param listAllValidMoves the listAllValidMoves to set
	 */
	public void setListAllValidMoves(Board board, Piece piece) {
		int initRow = piece.getRow();
		int initColumn = piece.getColumn();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if((i != initRow && j != initColumn) && piece.isValid(i, j, board)) {
					listAllValidMoves.put(new Integer[] {initRow, initColumn}, new Integer[] {i, j});
				}
			}
		}
	}

	/**
	 * @return the whitesTurn
	 */
	public boolean getWhitesTurn() {
		return whitesTurn;
	}

	/**
	 * @param whitesTurn the whitesTurn to set
	 */
	public void setWhitesTurn(boolean whitesTurn) {
		this.whitesTurn = whitesTurn;
	}
}
