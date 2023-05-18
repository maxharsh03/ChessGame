package chessgame.chess.piece;

import chessgame.chess.board.Board;

public class Rook extends Piece {

	private static final int VALUE = 500;
	private boolean hasMoved = false;
	
	public Rook(Color color, int row, int column) {
		super(Type.ROOK, color, row, column, VALUE);
	}

	@Override
	public boolean isValid(int row, int column, Board board) {
		if(row < 0 || row > 7 || column < 0 || column > 7) {
			return false; 
		} else if(row == this.getRow() || column == this.getColumn()) {
			if(board.isStraightPathClear(this.getRow(), this.getColumn(), row, column)) {
				// should maybe set this after a rook moves
				// hasMoved = true;
				return true;
			}
		}
		return false;	
	}
	
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	public boolean getHasMoved() {
		return hasMoved;
	}
}
