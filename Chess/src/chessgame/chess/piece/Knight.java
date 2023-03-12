package chessgame.chess.piece;

import chessgame.chess.board.Board;

public class Knight extends Piece {

	public Knight(Color color, int row, int column) {
		super(Type.KNIGHT, color, row, column);
	}

	/** 
	 * Will determine if a chosen Knight move is valid or not
	 */
	@Override
	public boolean isValid(int row, int column, Board board) {
		int rowDiff = Math.abs(this.getRow() - row);
		int columnDiff = Math.abs(this.getColumn() - column);
		
		// ensure chosen square is in bounds
		if(row < 0 || row > 7 || column < 0 || column > 7) {
			throw new IllegalArgumentException("Invalid move");
		// check if move is L-shaped and doesn't result in a capture of a friendly piece 
		} else if(rowDiff == 2 && columnDiff == 1 || rowDiff == 1 && columnDiff == 2) {
			if(board.getPieceFromBoard(row, column) == null || 
					!(this.getColor().equals(board.getPieceFromBoard(row, column).getColor()))) {
				return true;
			}
		}
		return false;
	}
}
