package chessgame.chess.piece;

import chessgame.chess.board.Board;

public class Queen extends Piece {

	public Queen(String name, String color, int row, int column) {
		super(name, color, row, column);
	}

	@Override
	public boolean isValid(int row, int column, Board board) {
		if(row < 0 || row > 7 || column < 0 || column > 7) {
			return false; 
		} 
		int deltaRow = row - this.getRow();
        int deltaColumn = column - this.getColumn();

        // Check if the move is diagonal
        if (Math.abs(deltaRow) == Math.abs(deltaColumn)) {
            return board.isDiagonalPathClear(this.getRow(), this.getColumn(), row, column);
        }

        // Check if the move is horizontal or vertical
        if (deltaRow == 0 || deltaColumn == 0) {
            return board.isStraightPathClear(this.getRow(), this.getColumn(), row, column);
        }
		return false;
	}

}
