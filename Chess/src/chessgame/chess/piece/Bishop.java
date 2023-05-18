package chessgame.chess.piece;

import chessgame.chess.board.Board;

public class Bishop extends Piece {
	
	private static final int VALUE = 300;
	 
	public Bishop(Color color, int row, int column) {
		super(Type.BISHOP, color, row, column, VALUE);
	}

	/**
	 * Checks if a selected Bishop move is valid
	 * @return whether the move is valid 
	 */
	@Override
	public boolean isValid(int row, int column, Board board) {
		int rowDiff = Math.abs(this.getRow() - row);
        int columnDiff = Math.abs(this.getColumn() - column);
        
		if(row < 0 || row > 7 || column < 0 || column > 7) {
			return false;
		// checks slope of bishop move is 1, meaning its on the same diagonal
		} else if(rowDiff != columnDiff) {
			return false;
		} else if((board.getPieceFromBoard(row, column) == null || !(this.getColor().equals(board.getPieceFromBoard(row, column).getColor())))) {
			int rowDirection = Integer.signum(row - this.getRow());
            int colDirection = Integer.signum(column - this.getColumn());
            
            // checks if path is clear 
            for (int i = 1; i < rowDiff; i++) {
                Piece piece = board.getPieceFromBoard(this.getRow() + i * rowDirection, this.getColumn() + i * colDirection);
                if(piece != null) {
                    return false;
                }
            }
            return true;
		}
		return false;
	}
}
