package chessgame.chess.piece;

import chessgame.chess.board.Board;

// DO NOT NEED TO CHECK FOR ANY check or checkmate FUNCTIONALITY IN THIS CLASS, let that be determined by GameManager

/** 
 * 
 * @author maxharsh
 *
 */ 
public class King extends Piece {

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

	// should delegate to canCastle if a King is pressed in its original position and a castling square is 
	// subsequently pressed 
	@Override
	public boolean isValid(int row, int column, Board board) {
		int rowDiff = Math.abs(row - this.getRow());
		int columnDiff = Math.abs(column - this.getColumn());
		
		if(canCastle(row, column, board)) {
			return true;
		}
		
		if((rowDiff == 0 && columnDiff != 1) || (columnDiff == 0 && rowDiff != 1) || (rowDiff != 1 && columnDiff != 1)) {
			return false;
		}
		return isKingInCheck(row, column, board);
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
			if(board.getPieceFromBoard(7, 7).getType() == Type.ROOK) {
				Rook tempRook = (Rook) board.getPieceFromBoard(7, 7);
				if(tempRook != null && !tempRook.getHasMoved() && tempRook.getColor() == Color.WHITE  
						&& board.getPieceFromBoard(7, 5) == null && board.getPieceFromBoard(7, 6) == null 
						&& !isKingInCheck(7, 5, board) && !isKingInCheck(7, 6, board)
						&& this.getRow() == row && row == 6) {
					return true;
				} 
			}
			if(board.getPieceFromBoard(7, 0).getType() == Type.ROOK) {
				Rook tempRook2 = (Rook) board.getPieceFromBoard(7, 0);
				if(tempRook2 != null && !tempRook2.getHasMoved() && tempRook2.getColor() == Color.WHITE
						&& board.getPieceFromBoard(7, 3) == null && board.getPieceFromBoard(7, 2) == null 
						&& !isKingInCheck(7, 3, board) && !isKingInCheck(7, 2, board) 
						&& this.getRow() == row && row == 2) {
					return true;
				}
			}
			return false;
		// check for black's castling moves
		} else if(Color.BLACK == this.getColor() && !castled && !hasMoved 
				&& !isKingInCheck(this.getRow(), this.getColumn(), board)) {
			// check for right castling move first
			if(board.getPieceFromBoard(0, 7).getType() == Type.ROOK) {
				Rook tempRook = (Rook) board.getPieceFromBoard(0, 7);
				if(tempRook != null && !tempRook.getHasMoved() && tempRook.getColor() == Color.BLACK 
						&& board.getPieceFromBoard(0, 5) == null && board.getPieceFromBoard(0, 6) == null 
						&& !isKingInCheck(0, 5, board) && !isKingInCheck(0, 6, board) 
						&& this.getRow() == row && row == 6) {
					// call castling function in GameManager
					return true;
				} 
			}
			if(board.getPieceFromBoard(0, 0).getType() == Type.ROOK) {
				Rook tempRook2 = (Rook) board.getPieceFromBoard(0, 0);
				if(tempRook2 != null && !tempRook2.getHasMoved() && tempRook2.getColor() == Color.BLACK 
						&& board.getPieceFromBoard(0, 3) == null && board.getPieceFromBoard(0, 2) == null 
						&& !isKingInCheck(0, 3, board) && !isKingInCheck(0, 2, board) 
						&& this.getRow() == row && row == 2) {
					// call castling function in GameManager
					return true;
				}
			}
			return false;
		} 
		return false;
		/*else if((rowDiff == 0 && columnDiff != 1) || (columnDiff == 0 && rowDiff != 1) || (rowDiff/columnDiff != 1)) {
			return false;
		}
		return isKingInCheck(row, column, board, this);
		*/
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
				if(piece != null && piece.getColor() != this.getColor()) {
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
