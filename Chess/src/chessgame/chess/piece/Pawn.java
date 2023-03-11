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
	private boolean enPassant = false;
	
	/**
	 * Constructs Pawn object with values for all fields
	 * @param name
	 * @param color
	 * @param row
	 * @param column
	 */
	public Pawn(String name, String color, int row, int column) {
		super(name, color, row, column);
	}
	
	/**
	 * 
	 */
	@Override
	public boolean isValid(int row, int column, Board board) {
		// check if square player clicked is out of bounds
		if(row < 0 || row > 7 || column < 0 || column > 7) {
			//throw new IllegalArgumentException("Invalid move");
			return false;
		}
		// white pawn moves
		else if("White".equals(this.getColor())) {
			// checks for first move
			if(firstMove) {
				if(row < this.getRow() && this.getRow() - row <= 2 && this.getRow() - row >= 1) {
					//this.setRow(row);
					return true;
				} else {
					//throw new IllegalArgumentException("Invalid move");
					return false;
				}
			} else {
				// after first move, no capture
				if(column == this.getColumn()) {
					if(row < this.getRow() && this.getRow() - row == 1) {
						//this.setRow(row);
						return true;
					} else {
						//throw new IllegalArgumentException("Invalid move");
						return false;
					}
				// check for capture diagonally
				} else if(column == Math.abs(this.getColumn() - 1) && row < this.getRow() && row == this.getRow() - 1 
						&& board.getPieceFromBoard(row, column) != null && "Black".equals(board.getPieceFromBoard(row, column).getColor())) {
					//GameManager.capture(this.getRow(), this.getColumn(), row, column, board);
					return true;
				// check for en passant
				} else if(row == 1) {
					
				}
			}
		}
		// black pawn moves 
		else if("Black".equals(this.getColor())) {
			// check if square player clicked is out of bounds
			if(row < 0 || row > 7 || column < 0 || column > 7) {
				//throw new IllegalArgumentException("Invalid move");
				return false;
			}
			// checks for first move
			else if(firstMove) {
				if(row > this.getRow() && row - this.getRow() <= 2 && row - this.getRow() >= 1) {
					//this.setRow(row);
					return true;
				} else {
					//throw new IllegalArgumentException("Invalid move");
					return false;
				}
			} else {
				// after first move, no capture 
				if(column == this.getColumn()) {
					if(row > this.getRow() && row - this.getRow() == 1) {
						//this.setRow(row);
						return true;
					} else {
						throw new IllegalArgumentException("Invalid move");
					}
				// check for capture diagonally
				} else if(column == Math.abs(this.getColumn() - 1) && row > this.getRow() && row == this.getRow() + 1 
						&& board.getPieceFromBoard(row, column) != null && "White".equals(board.getPieceFromBoard(row, column).getColor())) {
					//GameManager.capture(this.getRow(), this.getColumn(), row, column, board);
					return true;
				// check for en passant
				} else if(row == 1) {
					
				}
			}
		} 
		return false;
	}
	
	/**
	 * Provides functionality for a pawn to perform en passant capture on
	 * another piece. 
	 */
	public boolean enPassant() {
		return false;
	}

}
