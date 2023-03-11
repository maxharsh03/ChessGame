package chessgame.chess.piece;

import chessgame.chess.board.Board;

public class Queen extends Piece {

	public Queen(String name, String color, int row, int column) {
		super(name, color, row, column);
	}

	@Override
	public boolean isValid(int row, int column, Board board) {
		// TODO Auto-generated method stub
		return false;
	}

}
