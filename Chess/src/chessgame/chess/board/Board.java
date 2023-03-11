package chessgame.chess.board;

import chessgame.chess.piece.Bishop;
import chessgame.chess.piece.King;
import chessgame.chess.piece.Knight;
import chessgame.chess.piece.Pawn;
import chessgame.chess.piece.Piece;
import chessgame.chess.piece.Queen;
import chessgame.chess.piece.Rook;

/**
 * Board represents the Chess Board that players play a game on. It has 64 tiles in 
 * the form of a 2D array. Contains all of the pieces on the Board. There should only be one 
 * instance of a Board object at a time because only one game can be played at a time. 
 * @author maxharsh
 *
 */
public class Board {

	private static Board singleton;
	private static final int ROWS = 8;
	private static final int COLUMNS = 8;
	private Piece[][] pieces;
	
	private Board() {
		pieces = new Piece[ROWS][COLUMNS];
		this.createBoard();
	}
	
	/**
	 * Will determine if a Board object already exists. If 
	 * it doesn't exist yet, this method will call the private constructor
	 * to make a new Board object.
	 * @return singleton instance of Board
	 */
	public static Board getInstance() {
		if (singleton == null) {
			singleton = new Board();
		}
		return singleton;
	}
	
	/**
	 * Will return Piece object from specified board coordinates
	 * @param row row of board
	 * @param column column of board
	 * @return piece from specified location on board
	 */
	public Piece getPieceFromBoard(int row, int column) {
		if(row >= 0 && row <= 7 && column >= 0 && column <= 7) {
			return pieces[row][column];
		}
		return null;
	}
	
	/** 
	 * Checks if a straight line path (horizontal/vertical) between 2 
	 * squares on the board is clear 
	 * @param piece
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isStraightPathClear(int startRow, int startColumn, int endRow, int endColumn) {
		int deltaRow = Integer.signum(endRow - startRow);
	    int deltaColumn = Integer.signum(endColumn - startColumn);
	    int currentRow = startRow + deltaRow;
	    int currentColumn = startColumn + deltaColumn;
	    
	    while (currentRow != endRow || currentColumn != endColumn) {
	        if(this.getPieceFromBoard(currentRow, currentColumn) != null) {
	            return false;
	        }
	        currentRow += deltaRow;
	        currentColumn += deltaColumn;
	    }
	    return true;
	}
	
	/**
	 * Checks if a diagonal path is clear between 2 squares on the board is clear 
	 * @param startRow
	 * @param startColumn
	 * @param endRow
	 * @param endColumn
	 * @return
	 */
	public boolean isDiagonalPathClear(int startRow, int startColumn, int endRow, int endColumn) {
		int deltaRow = Integer.signum(endRow - startRow);
	    int deltaColumn = Integer.signum(endColumn - startColumn);
	    int currentRow = startRow + deltaRow;
	    int currentColumn = startColumn + deltaColumn;
	    
	    while (currentRow != endRow && currentColumn != endColumn) {
	        if(this.getPieceFromBoard(currentRow, currentColumn) != null) {
	            return false;
	        }
	        currentRow += deltaRow;
	        currentColumn += deltaColumn;
	    }
	    return true;
	}
	
	/** 
	 * Retrieve 2D array of Board.
	 * @return 2D array with all pieces
	 */
	public Piece[][] getBoard() {
		return pieces;
	}
	
	/**
	 * Before a game starts, fills the board with pieces for each square 
	 * that contains a piece in the starting position. 
	 */
	public void createBoard() {
		// black pieces
		Piece r1 = new Rook("Rook", "White", 0, 0);
		Piece r2 = new Rook("Rook", "White", 0, 7);
		Piece n1 = new Knight("Knight", "White", 0, 1);
		Piece n2 = new Knight("Knight", "White", 0, 6);
		Piece b1 = new Bishop("Bishop", "White", 0, 2);
		Piece b2 = new Bishop("Bishop", "White", 0, 5);
		Piece k1 = new King("King", "White", 0, 4);
		Piece q1 = new Queen("Queen", "White", 0, 3);
		
		// white pieces
		Piece r3 = new Rook("Rook", "Black", 7, 0);
		Piece r4 = new Rook("Rook", "Black", 7, 7);
		Piece n3 = new Knight("Knight", "Black", 7, 1);
		Piece n4 = new Knight("Knight", "Black", 7, 6);
		Piece b3 = new Bishop("Bishop", "Black", 7, 2);
		Piece b4 = new Bishop("Bishop", "Black", 7, 5);
		Piece k2 = new King("King", "Black", 7, 4);
		Piece q2 = new Queen("Queen", "Black", 7, 3);
		
		// create pieces in top 1st row
		pieces[0][0] = r1;
		pieces[0][1] = n1;
		pieces[0][2] = b1;
		pieces[0][3] = q1;
		pieces[0][4] = k1;
		pieces[0][5] = b2;
		pieces[0][6] = n2;
		pieces[0][7] = r2;
		
		// create pieces in bottom 1st row
		pieces[7][0] = r3;
		pieces[7][1] = n3;
		pieces[7][2] = b3;
		pieces[7][3] = q2;
		pieces[7][4] = k2;
		pieces[7][5] = b4;
		pieces[7][6] = n4;
		pieces[7][7] = r4;
		
		// fill in 2nd top row with black pawns
		for(int i = 0; i < 8; i++) {
			pieces[1][i] = new Pawn("Pawn", "White", 1, i);
		}
		
		// fill in empty squares as null
		for(int i = 2; i < 6; i++) {
			for(int j = 0; j < 8; j++) {
				pieces[i][j] = null;
			}
		}
		
		// fill in 2nd bottom row with white pawns
		for(int i = 0; i < 8; i++) {
			pieces[6][i] = new Pawn("Pawn", "White", 6, i);
		}
	}
	
}
