package chessgame.chess.board;

import java.util.ArrayList;

import chessgame.chess.piece.Bishop;
import chessgame.chess.piece.Color;
import chessgame.chess.piece.King;
import chessgame.chess.piece.Knight;
import chessgame.chess.piece.Pawn;
import chessgame.chess.piece.Piece;
import chessgame.chess.piece.Queen;
import chessgame.chess.piece.Rook;
import chessgame.chess.piece.Type;

/**
 * Board represents the Chess Board that players play a game on. It has 64 tiles in 
 * the form of a 2D array. Contains all of the pieces on the Board. Is able to be cloned 
 * for functionality necessary within GameManager such as looking for check and checkmate, 
 * and for the AI when it is looking for moves. 
 * @author maxharsh
 *
 */
public class Board implements Cloneable {

	//private static Board singleton;
	private static final int ROWS = 8;
	private static final int COLUMNS = 8;
	private Piece[][] pieces;
	private ArrayList<Piece> whitePieces;
	private ArrayList<Piece> blackPieces;
	
	public Board() {
		pieces = new Piece[ROWS][COLUMNS];
		this.createBoard();
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
	 * Retrieves cloned object.
	 */
	public Object clone(){  
	    try{  
	        return super.clone();  
	    }catch(Exception e){ 
	        return null; 
	    }
	}
	
	public void setPieceAt(int row, int column, Piece piece) {
		pieces[row][column] = piece;
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
	 * Retrieve list of all white pieces currently on the board
	 * @return
	 */
	public ArrayList<Piece> getWhitePieces() {
		return whitePieces;
	}
	
	/**
	 * Retrieve list of all black pieces currently on the board
	 * @return
	 */
	public ArrayList<Piece> getBlackPieces() {
		return blackPieces;
	}
	
	/**
	 * Retrieves king for a given color.
	 * @param color
	 * @return
	 */
	public Piece getKing(Color color) {
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++) {
				if(this.getPieceFromBoard(i, j) != null && this.getPieceFromBoard(i, j).getType() == Type.KING) {
					if(this.getPieceFromBoard(i, j).getColor() == color) {
						return this.getPieceFromBoard(i, j);
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Before a game starts, fills the board with pieces for each square 
	 * that contains a piece in the starting position. 
	 */
	public void createBoard() {
		// black pieces
		Piece r1 = new Rook(Color.WHITE, 0, 0);
		Piece r2 = new Rook(Color.WHITE, 0, 7);
		Piece n1 = new Knight(Color.WHITE, 0, 1);
		Piece n2 = new Knight(Color.WHITE, 0, 6);
		Piece b1 = new Bishop(Color.WHITE, 0, 2);
		Piece b2 = new Bishop(Color.WHITE, 0, 5);
		Piece k1 = new King(Color.WHITE, 0, 4);
		Piece q1 = new Queen(Color.WHITE, 0, 3);
		
		// add black pieces to blackPieces list
		blackPieces.add(r1);
		blackPieces.add(r2);
		blackPieces.add(n1);
		blackPieces.add(n2);
		blackPieces.add(b1);
		blackPieces.add(b2);
		blackPieces.add(k1);
		blackPieces.add(q1);
		
		// white pieces
		Piece r3 = new Rook(Color.BLACK, 7, 0);
		Piece r4 = new Rook(Color.BLACK, 7, 7);
		Piece n3 = new Knight(Color.BLACK, 7, 1);
		Piece n4 = new Knight(Color.BLACK, 7, 6);
		Piece b3 = new Bishop(Color.BLACK, 7, 2);
		Piece b4 = new Bishop(Color.BLACK, 7, 5);
		Piece k2 = new King(Color.BLACK, 7, 4);
		Piece q2 = new Queen(Color.BLACK, 7, 3);
		
		// add white pieces to whitePieces list
		whitePieces.add(r3);
		whitePieces.add(r4);
		whitePieces.add(n3);
		whitePieces.add(n4);
		whitePieces.add(b3);
		whitePieces.add(b4);
		whitePieces.add(k2);
		whitePieces.add(q2);
		
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
			Pawn p = new Pawn(Color.BLACK, 1, i);
			pieces[1][i] = p;
			// add black pawns to blackPieces list
			blackPieces.add(p);
		}
		
		// fill in empty squares as null
		for(int i = 2; i < 6; i++) {
			for(int j = 0; j < 8; j++) {
				pieces[i][j] = null;
			}
		}
		
		// fill in 2nd bottom row with white pawns
		for(int i = 0; i < 8; i++) {
			Pawn p = new Pawn(Color.WHITE, 6, i);
			pieces[6][i] = p;
			// add white pawns to whitePieces list
			whitePieces.add(p);
		}
		
		// add white pieces to whitePieces list
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 8; j++) {
				whitePieces.add(pieces[i][j]);
			}
		}
		
		// add black pieces to blackPieces list
		for(int i = 6; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				blackPieces.add(pieces[i][j]);
			}
		}
		
	}
}
