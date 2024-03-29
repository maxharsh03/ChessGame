package chessgame.chess.piece;

import chessgame.chess.board.Board;

// need to add that a king must have a distance of 1 square from an enemy king at all times

/** 
 * 
 * @author maxharsh
 *
 */ 
public class King extends Piece {

	private boolean castled = false;
	private boolean hasMoved = false;
	private static final int VALUE = 10000;
	
	/**
	 * Creates new King object with values for all fields.
	 * @param name name of piece
	 * @param color color of piece
	 * @param row row of piece on board
	 * @param column column of piece on board
	 */
	public King(Color color, int row, int column) {
		super(Type.KING, color, row, column, VALUE);
	}

	// should delegate to canCastle if a King is pressed in its original position and a castling square is 
	// subsequently pressed 
	@Override
	public boolean isValid(int row, int column, Board board) {
		int rowDiff = Math.abs(row - this.getRow());
		int columnDiff = Math.abs(column - this.getColumn());
		
		if(canCastle(row, column, board)) {
			return true;
		}
		
		if((rowDiff == 0 && columnDiff > 1) || (columnDiff == 0 && rowDiff > 1) || (rowDiff > 1 && columnDiff > 1) 
				|| (rowDiff == 1 && columnDiff > 1) || (columnDiff == 1 && rowDiff > 1)) {
			return false;
		}
		
		// case of moving too close to enemy King
		if(this.getColor() == Color.WHITE) { 
			King oppositeKing = (King) board.getKing(Color.BLACK);
			if((Math.abs(oppositeKing.getRow() - row) < 2 && Math.abs(oppositeKing.getColumn() - column) == 0) ||
					(Math.abs(oppositeKing.getColumn() - column) < 2 && Math.abs(oppositeKing.getRow() - row) == 0)
				|| (Math.abs(oppositeKing.getColumn() - column) == 1 && Math.abs(oppositeKing.getRow() - row) == 1)) {
				return false;
			}
		} else {
			King oppositeKing = (King) board.getKing(Color.WHITE);
			if((Math.abs(oppositeKing.getRow() - row) < 2 && Math.abs(oppositeKing.getColumn() - column) == 0) ||
					(Math.abs(oppositeKing.getColumn() - column) < 2 && Math.abs(oppositeKing.getRow() - row) == 0)
				|| (Math.abs(oppositeKing.getColumn() - column) == 1 && Math.abs(oppositeKing.getRow() - row) == 1)) {
				return false;
			}
		}
		return true;
		//return !isKingInCheck(row, column, board);
	} 
	
	/** 
	 * King's implementation of isValid in Piece. First checks that the move is not of bounds. 
	 * Then checks if the king can castle.
	 */
	// this method should only handle the possibility of a king castling, not other moves 
	public boolean canCastle(int row, int column, Board board) {

		if(row < 0 || row > 7 || column  < 0 || column > 7) {
			return false;
		}  
		
		// check for white's castling moves
		else if(Color.WHITE == this.getColor() && !castled && !hasMoved
				&& !isKingInCheck(this.getRow(), this.getColumn(), board)) {
			// check for right castling move first
			if(board.getPieceFromBoard(7, 7) != null && board.getPieceFromBoard(7, 7).getType() == Type.ROOK && 
					(this.getRow() == row && row == 7 && this.getColumn() == 4 && column == 6)) {
				Rook tempRook = (Rook) board.getPieceFromBoard(7, 7);
				if(!tempRook.getHasMoved() && tempRook.getColor() == Color.WHITE  
						&& board.getPieceFromBoard(7, 5) == null && board.getPieceFromBoard(7, 6) == null 
						&& !isKingInCheck(7, 5, board) && !isKingInCheck(7, 6, board)) {
					return true;
				} 
			}
			if(board.getPieceFromBoard(7, 0) != null && board.getPieceFromBoard(7, 0).getType() == Type.ROOK && 
					(this.getRow() == row && row == 7 && this.getColumn() == 4 && column == 2)) {
				Rook tempRook2 = (Rook) board.getPieceFromBoard(7, 0);
				if(!tempRook2.getHasMoved() && tempRook2.getColor() == Color.WHITE
						&& board.getPieceFromBoard(7, 3) == null && board.getPieceFromBoard(7, 2) == null 
						&& !isKingInCheck(7, 3, board) && !isKingInCheck(7, 2, board)) {
					return true;
				}
			}
			return false;
		// check for black's castling moves
		} else if(Color.BLACK == this.getColor() && !castled && !hasMoved 
				&& !isKingInCheck(this.getRow(), this.getColumn(), board)) {
			// check for right castling move first
			if(board.getPieceFromBoard(0, 7) != null && board.getPieceFromBoard(0, 7).getType() == Type.ROOK && 
					(this.getRow() == row && row == 0 && this.getColumn() == 4 && column == 6)) {
				Rook tempRook = (Rook) board.getPieceFromBoard(0, 7);
				if(!tempRook.getHasMoved() && tempRook.getColor() == Color.BLACK 
						&& board.getPieceFromBoard(0, 5) == null && board.getPieceFromBoard(0, 6) == null 
						&& !isKingInCheck(0, 5, board) && !isKingInCheck(0, 6, board) 
						&& this.getRow() == row && row == 0) {
					// call castling function in GameManager
					return true;
				} 
			}
			if(board.getPieceFromBoard(0, 0) != null && board.getPieceFromBoard(0, 0).getType() == Type.ROOK && 
					(this.getRow() == row && row == 0 && this.getColumn() == 4 && column == 2)) {
				Rook tempRook2 = (Rook) board.getPieceFromBoard(0, 0);
				if(!tempRook2.getHasMoved() && tempRook2.getColor() == Color.BLACK 
						&& board.getPieceFromBoard(0, 3) == null && board.getPieceFromBoard(0, 2) == null 
						&& !isKingInCheck(0, 3, board) && !isKingInCheck(0, 2, board) 
						&& this.getRow() == row && row == 0) {
					// call castling function in GameManager
					return true;
				}
			}
			return false;
		} 
		return false;
	}
	
	/** 
	 * Determines if a given position results in a king being in check.
	 * @param row
	 * @param column
	 * @param board
	 * @return whether king in check or not
	 */
	public boolean isKingInCheck(int row, int column, Board board) {		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				Piece piece = board.getPieceFromBoard(i, j);
				if(piece != null && piece.getColor() != this.getColor() &&
						piece.getType() != Type.KING) {
					if(piece.isValid(row, column, board)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * @return the castled
	 */
	public boolean isCastled() {
		return castled;
	}

	/**
	 * @param castled the castled to set
	 */
	public void setCastled(boolean castled) {
		this.castled = castled;
	}

	/**
	 * @return the hasMoved
	 */
	public boolean isHasMoved() {
		return hasMoved;
	}

	/**
	 * @param hasMoved the hasMoved to set
	 */
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
}
