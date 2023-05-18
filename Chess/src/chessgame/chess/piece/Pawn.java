package chessgame.chess.piece;

import chessgame.chess.board.Board;
//import chessgame.chess.manager.GameManager;

/**
 * 
 * @author maxharsh
 *
 */
public class Pawn extends Piece {

	private boolean firstMove = true;
	private boolean hasDoneEnPassant = false;
	
	/**
	 * Constructs Pawn object with values for all fields
	 * @param name
	 * @param color
	 * @param row
	 * @param column
	 */
	public Pawn(Color color, int row, int column) {
		super(Type.PAWN, color, row, column);
	}
	
	/**
	 * 
	 */
	@Override
	public boolean isValid(int row, int column, Board board) {
		int deltaRow = this.getRow() - row;
		int deltaCol = this.getColumn() - column;
		
		// check if square player clicked is out of bounds
		if(row < 0 || row > 7 || column < 0 || column > 7) {
			//throw new IllegalArgumentException("Invalid move");
			return false;
		}
		// white pawn moves
		if(Color.WHITE == this.getColor()) {
			// handle first move
			if(this.getRow() == 6) {
				if(deltaCol == 0) {
					if(deltaRow == 2 && board.getPieceFromBoard(row + 1, column) == null 
							&& board.getPieceFromBoard(row, column) == null) {
						return true;
					}
					if(deltaRow == 1 && board.getPieceFromBoard(row, column) == null) {
						return true;
					}
					return false;
				}
				if(deltaRow == 1 && (deltaCol == 1 || deltaCol == -1) && board.getPieceFromBoard(row, column) != null
						&& board.getPieceFromBoard(row, column).getColor() != Color.WHITE) {
					return true;
				} else {
					return false;
				}
			} else {
				// after first move, no capture
				if(deltaCol == 0) {
					if(deltaRow == 1 && board.getPieceFromBoard(row, column) == null) {
						return true;
					} 
					return false;
				// check for capture diagonally
				} else if(Math.abs(deltaCol) == 1 && deltaRow == 1 && board.getPieceFromBoard(row, column) != null && 
						Color.BLACK == board.getPieceFromBoard(row, column).getColor()) {
					return true;
				// check for en passant
				} else if(this.getRow() == 3) {
					if(board.getPieceFromBoard(this.getRow(), this.getColumn() - 1) != null 
							&& board.getPieceFromBoard(this.getRow(), this.getColumn() - 1).getType() == Type.PAWN &&
							board.getPieceFromBoard(this.getRow(), this.getColumn() - 1).getColor() == Color.BLACK) {
						if(deltaRow == 1 && deltaCol == 1 && board.getPieceFromBoard(row, column) == null) {
							return true;
						}
						return false;
					}
					if(board.getPieceFromBoard(this.getRow(), this.getColumn() + 1) != null 
							&& board.getPieceFromBoard(this.getRow(), this.getColumn() + 1).getType() == Type.PAWN &&
							board.getPieceFromBoard(this.getRow(), this.getColumn() + 1).getColor() == Color.BLACK) {
						if(deltaRow == 1 && deltaCol == -1 && board.getPieceFromBoard(row, column) == null) {
							return true;
						}
						return false;
					}
					return false;
				}
			}
		}
		// black pawn moves 
		else if(Color.BLACK == this.getColor()) {
			// checks for first move
			if(this.getRow() == 1) {
				if(deltaCol == 0) {
					if(deltaRow == -2 && board.getPieceFromBoard(row - 1, column) == null 
							&& board.getPieceFromBoard(row, column) == null) {
						return true;
					}
					if(deltaRow == -1 && board.getPieceFromBoard(row, column) == null) {
						return true;
					}
					return false;
				}
				if(deltaRow == -1 && (deltaCol == 1 || deltaCol == -1) && board.getPieceFromBoard(row, column) != null
						&& board.getPieceFromBoard(row, column).getColor() != Color.BLACK) {
					return true;
				} else {
					return false;
				}
			} else {
				if(deltaCol == 0) {
					if(deltaRow == -1 && board.getPieceFromBoard(row, column) == null) {
						return true;
					} 
					return false;
				// check for capture diagonally
				} else if(Math.abs(deltaCol) == 1 && deltaRow == -1 && board.getPieceFromBoard(row, column) != null 
						&& Color.WHITE == board.getPieceFromBoard(row, column).getColor()) {
					return true;
				// check for en passant
				} else if(this.getRow() == 4) {
					if(board.getPieceFromBoard(this.getRow(), this.getColumn() - 1) != null 
							&& board.getPieceFromBoard(this.getRow(), this.getColumn() - 1).getType() == Type.PAWN &&
							board.getPieceFromBoard(this.getRow(), this.getColumn() - 1).getColor() == Color.WHITE) {
						if(deltaRow == -1 && deltaCol == 1 && board.getPieceFromBoard(row, column) == null) {
							return true;
						}
						return false;
					}
					if(board.getPieceFromBoard(this.getRow(), this.getColumn() + 1) != null 
							&& board.getPieceFromBoard(this.getRow(), this.getColumn() + 1).getType() == Type.PAWN &&
							board.getPieceFromBoard(this.getRow(), this.getColumn() + 1).getColor() == Color.WHITE) {
						if(deltaRow == -1 && deltaCol == -1 && board.getPieceFromBoard(row, column) == null) {
							return true;
						}
						return false;
					}
					return false;
				}
			}
		} 
		return false;
	}

	/**
	 * @return the firstMove
	 */
	public boolean isFirstMove() {
		return firstMove;
	}

	/**
	 * @param firstMove the firstMove to set
	 */
	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}

	/**
	 * @return the hasDoneEnPassant
	 */
	public boolean getHasDoneEnPassant() {
		return hasDoneEnPassant;
	}

	/**
	 * @param hasDoneEnPassant the hasDoneEnPassant to set
	 */
	public void setHasDoneEnPassant(boolean hasDoneEnPassant) {
		this.hasDoneEnPassant = hasDoneEnPassant;
	}
}
