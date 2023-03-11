package chessgame.chess.piece;

import chessgame.chess.board.Board;

public class Rook extends Piece {

	public Rook(String name, String color, int row, int column) {
		super(name, color, row, column);
	}

	@Override
	public boolean isValid(int row, int column, Board board) {
		if(row < 0 || row > 7 || column < 0 || column > 7) {
			return false; 
		} else if(row == this.getRow() || column == this.getColumn()) {
			if(board.isStraightPathClear(this.getRow(), this.getColumn(), row, column)) {
				return true;
			}
		}
		return false;	
	}
}
