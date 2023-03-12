package chessgame.chess.piece;

import java.util.ArrayList;

import chessgame.chess.board.Board;

/** 
 * 
 * @author maxharsh
 *
 */ 
public class King extends Piece {

	// private boolean inCheck = false;
	private boolean castled = false;
	private boolean hasMoved = false;
	
	/**
	 * Creates new King object with values for all fields.
	 * @param name name of piece
	 * @param color color of piece
	 * @param row row of piece on board
	 * @param column column of piece on board
	 */
	public King(Color color, int row, int column) {
		super(Type.KING, color, row, column);
	}

	/** 
	 * King's implementation of isValid in Piece. First checks that the move is not of bounds. 
	 * Then checks if the king can castle. Then 
	 */
	@Override
	public boolean isValid(int row, int column, Board board) {
		int rowDiff = Math.abs(row - this.getRow());
		int columnDiff = Math.abs(column - this.getColumn());
		
		if(row < 0 || row > 7 || column  < 0 || column > 7) {
			return false;
		} 
		// check for castling move
		else if(Color.WHITE == this.getColor() && !castled && !hasMoved
				&& !kingCurrentlyInCheck(board)) {
			// check for right castling move first
			Rook tempRook = (Rook) board.getPieceFromBoard(7, 7);
			Rook tempRook2 = (Rook) board.getPieceFromBoard(7, 0);
			if(tempRook != null && !tempRook.getHasMoved() && tempRook.getColor() == Color.WHITE 
					&& !isKingInCheck(7, 5, board, this) && !isKingInCheck(7, 6, board, this) 
					&& this.getRow() == row && row == 6) {
				// call castling function in GameManager
				return true;
			} 
			else if(tempRook2 != null && !tempRook2.getHasMoved() && tempRook2.getColor() == Color.WHITE
					&& !isKingInCheck(7, 3, board, this) && !isKingInCheck(7, 2, board, this) 
					&& this.getRow() == row && row == 2) {
				// call castling function in GameManager
				return true;
			}
			return false;
		} else if(Color.BLACK == this.getColor() && !castled && !hasMoved 
				&& !kingCurrentlyInCheck(board)) {
			// check for right castling move first
			Rook tempRook = (Rook) board.getPieceFromBoard(0, 7);
			Rook tempRook2 = (Rook) board.getPieceFromBoard(0, 0);
			if(tempRook != null && !tempRook.getHasMoved() && tempRook.getColor() == Color.BLACK 
					&& !isKingInCheck(0, 5, board, this) && !isKingInCheck(0, 6, board, this) 
					&& this.getRow() == row && row == 6) {
				// call castling function in GameManager
				return true;
			} 
			else if(tempRook2 != null && !tempRook2.getHasMoved() && tempRook2.getColor() == Color.BLACK 
					&& !isKingInCheck(0, 3, board, this) && !isKingInCheck(0, 2, board, this) 
					&& this.getRow() == row && row == 2) {
				// call castling function in GameManager
				return true;
			}
			return false;
		} else if((rowDiff == 0 && columnDiff != 1) || (columnDiff == 0 && rowDiff != 1) || (rowDiff/columnDiff != 1)) {
			return false;
		}
		return isKingInCheck(row, column, board, this);
	}
	
	/** 
	 * Determines if a given position results in a king being in check.
	 * @param row
	 * @param column
	 * @param board
	 * @return whether king in check or not
	 */
	public boolean isKingInCheck(int row, int column, Board board, Piece piece) {
		// creates a copy of the board and moves piece to see if new position results in king being put in check
		Piece[][] piecesCopy = board.getBoard();
		piecesCopy[row][column] = piece;
		
		ArrayList<Piece> blackPieces= board.getBlackPieces();
		ArrayList<Piece> whitePieces= board.getWhitePieces();
		
		// checks if a given position the king wants to move to results in a check
		if(Color.WHITE == piece.getColor()) {
			for(int i = 0; i < blackPieces.size(); i++) {
				if(blackPieces.get(i).isValid(row, column, board)) {
					// will return true if the move is valid and results a check
					return true;
				}
			}
		} else {
			for(int i = 0; i < whitePieces.size(); i++) {
				if(whitePieces.get(i).isValid(row, column, board)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/** 
	 * Determines if a king is currently in check.
	 * @param board
	 * @return
	 */
	public boolean kingCurrentlyInCheck(Board board) {
		return isKingInCheck(this.getRow(), this.getColumn(), board, this);
	}

}
