package chessgame.chess.manager;

import java.util.ArrayList;
import java.util.HashMap;

import chessgame.chess.board.Board;
import chessgame.chess.piece.Color;
import chessgame.chess.piece.Piece;
import chessgame.chess.piece.Type;
import chessgame.chess.piece.King;
import chessgame.chess.player.Player;

/**
 * GameManager handles the rules of a chess game. This means that it controls how pieces move, 
 * how captures are made, and how the game starts and ends. GameManager manages the board, the players, 
 * and the state the game is in. 
 * @author maxharsh
 *
 */
public class GameManager {

	/** Represents the Piece that was last moved on the board */
	public Piece lastPieceMoved;
	/** List of black pieces white has captured */
	public ArrayList<Piece> whiteHasCaptured;
	/** List of white pieces black has captured*/
	public ArrayList<Piece> blackHasCaptured;
	/** Maps each starting coordinate to its ending coordinate for all valid moves. For generating AI moves 
	 * primarily.
	 */
	public HashMap<Integer[], Integer[]> listAllValidMoves;
	/** whether it is white's turn or not*/
	public boolean whitesTurn;
	
	public GameManager() {
		listAllValidMoves = new HashMap<Integer[], Integer[]>();
		lastPieceMoved = null;
		whitesTurn = true;
		whiteHasCaptured = new ArrayList<Piece>();
		blackHasCaptured = new ArrayList<Piece>();
	}
	
	/**
	 * Called to start a new game. Resets everything. 
	 */
	public void createNewGame() {
		
	}
	
	/**
	 * 
	 * @param rowInitial
	 * @param columnInitial
	 * @param rowFinal
	 * @param columnFinal
	 * @param board
	 */
	public void move(int rowInitial, int columnInitial, int rowFinal, int columnFinal, Board board, Piece piece) {
		if(whitesTurn && piece.getColor() == Color.BLACK) {
			throw new IllegalArgumentException("Invalid move");
		} else if(!whitesTurn && piece.getColor() == Color.BLACK) {
			throw new IllegalArgumentException("Invalid move");
		}
		// no pieces for a color can move if their king is in check, unless it is the king getting out of check
		// or the piece getting the king out of check by either capturing the piece putting the king in check or 
		// blocking the piece putting the king in check
		// a piece should not be moved if it results in the capture of the opposing king
		// a piece should not be moved if it results in its own king being put in check
		
		// if the final destination for a piece is not null and not the same color as the piece moved, then 
		// call capture method to handle functionality
		else if(piece.getType() == Type.PAWN && piece.isValid(rowFinal, columnFinal, board)) {
			if(isKingInCheck(board.getKing(piece.getColor()), rowFinal, columnFinal, piece, board));
			moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
		} else if(piece.getType() == Type.BISHOP && piece.isValid(rowFinal, columnFinal, board)) {
			moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
		} else if(piece.getType() == Type.KNIGHT && piece.isValid(rowFinal, columnFinal, board)) {
			moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
		} else if(piece.getType() == Type.ROOK && piece.isValid(rowFinal, columnFinal, board)) {
			moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
		} else if(piece.getType() == Type.QUEEN && piece.isValid(rowFinal, columnFinal, board)) {
			moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
		} else if(piece.getType() == Type.KING && piece.isValid(rowFinal, columnFinal, board)) {
			moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
		}
		// switch turn after move for a player
		setWhitesTurn();
	}
	
	/** 
	 * Handles functionality when a piece captures another piece 
	 * @param rowInitial
	 * @param columnInitial
	 * @param rowFinal
	 * @param columnFinal
	 */
	public void capture(int rowInitial, int columnInitial, int rowFinal, int columnFinal, Piece piece, Board board) {
		
	}
	
	/**
	 * Handles whenever a king is castling to the right.
	 * @param color
	 * @param board
	 */
	public void rightCastle(Color color, Board board) {
		//Piece[][] pieces = board.getBoard();
		
		if(color == Color.WHITE) {
			moveHelper(7, 7, 7, 5, board, board.getPieceFromBoard(7, 7));
			//board.getBoard()[7][5] = board.getBoard()[7][7];
			//board.getPieceFromBoard(7, 5).setColumn(5);
			//board.getBoard()[7][7] = null;
			moveHelper(7, 4, 7, 6, board, board.getPieceFromBoard(7, 4));
			//board.getBoard()[7][6] = board.getBoard()[7][4];
			//board.getPieceFromBoard(7, 6).setColumn(6);
			//board.getBoard()[7][4] = null;
		} else {
			moveHelper(0, 7, 0, 5, board, board.getPieceFromBoard(0, 7));
			//board.getBoard()[0][5] = board.getBoard()[0][7];
			//board.getPieceFromBoard(0, 5).setColumn(5);
			//board.getBoard()[0][7] = null;
			moveHelper(0, 4, 0, 6, board, board.getPieceFromBoard(0, 4));
			//board.getBoard()[0][6] = board.getBoard()[0][4];
			//board.getPieceFromBoard(0, 6).setColumn(6);
			//board.getBoard()[0][4] = null;
		}
	}
	
	/**
	 * Handles whenever a king is castling to the left.
	 * @param color
	 * @param board
	 */
	public void leftCastle(Color color, Board board) {
		if(color == Color.WHITE) {
			moveHelper(7, 0, 7, 3, board, board.getPieceFromBoard(7, 0));
			moveHelper(7, 4, 7, 2, board, board.getPieceFromBoard(7, 4));
		} else {
			moveHelper(0, 0, 0, 3, board, board.getPieceFromBoard(0, 0));
			moveHelper(0, 4, 0, 2, board, board.getPieceFromBoard(0, 4));
		}
	}
	
	public void moveHelper(int rowInitial, int columnInitial, int rowFinal, int columnFinal, Board board, Piece piece) {
		board.getBoard()[rowFinal][columnFinal] = piece;
		board.getPieceFromBoard(rowFinal, columnFinal).setRow(rowFinal);
		board.getPieceFromBoard(rowFinal, columnFinal).setColumn(columnFinal);
		board.getBoard()[rowInitial][columnInitial] = null;
	}
	
	/** 
	 * Updates score of the game for a player when a piece is captured. 
	 * @param player
	 * @return
	 */
	public int updateScore(Player player, Piece capturedPiece) {
		if(capturedPiece.getColor() == Color.WHITE) {
			if(capturedPiece.getType() == Type.PAWN) {
				return 1;
			} else if(capturedPiece.getType() == Type.BISHOP) {
				return 3;
			} else if(capturedPiece.getType() == Type.KNIGHT) {
				return 3;
			} else if(capturedPiece.getType() == Type.ROOK) {
				return 5;
			} else if(capturedPiece.getType() == Type.QUEEN) {
				return 9;
			} else { return 0; }
		} else {
			if(capturedPiece.getType() == Type.PAWN) {
				return 1;
			} else if(capturedPiece.getType() == Type.BISHOP) {
				return 3;
			} else if(capturedPiece.getType() == Type.KNIGHT) {
				return 3;
			} else if(capturedPiece.getType() == Type.ROOK) {
				return 5;
			} else if(capturedPiece.getType() == Type.QUEEN) {
				return 9;
			} else { return 0; }
		}
	}

	/**
	 * Retrieve last piece moved. 
	 * @return the lastPieceMoved
	 */
	public Piece getLastPieceMoved() {
		return lastPieceMoved;
	}

	/**
	 * Set last
	 * @param lastPieceMoved the lastPieceMoved to set
	 */
	public void setLastPieceMoved(Piece lastPieceMoved) {
		this.lastPieceMoved = lastPieceMoved;
	}

	/**
	 * Retrieve list of valid moves for a piece.
	 * @return the listAllValidMoves
	 */
	public HashMap<Integer[], Integer[]> getListAllValidMoves() {
		return listAllValidMoves;
	}
	
	/**
	 * Resets the list of valid moves for a piece (ex. after it has just moved).
	 */
	public void resetListAllValidMoves() {
		listAllValidMoves.clear();
	}

	/**
	 * Return all valid moves for a particular piece. Maps a piece's starting position to 
	 * each valid ending position. 
	 * @param listAllValidMoves the listAllValidMoves to set
	 */
	public void setListAllValidMoves(Board board, Piece piece) {
		// must also verify isKingInCheck for each move
		int initRow = piece.getRow();
		int initColumn = piece.getColumn();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if((i != initRow && j != initColumn) && piece.isValid(i, j, board) && 
						!isKingInCheck(board.getKing(piece.getColor()), i, j, piece, board)) {
					listAllValidMoves.put(new Integer[] {initRow, initColumn}, new Integer[] {i, j});
				}
			}
		}
	}

	/**
	 * Determines if after a player moves a piece (ex. moving a pinned pawn), if the player's king is 
	 * put into check. 
	 * @param king
	 * @param moveRow
	 * @param moveCol
	 * @param piece
	 * @param board
	 * @return
	 */
	public boolean isKingInCheck(Piece king, int row, int column, Piece piece, Board board) {
		Board boardCopy = board;
		Piece[][] piecesCopy = boardCopy.getBoard();
		int initRow = piece.getRow();
		int initCol = piece.getColumn();
		piecesCopy[row][column] = piece;
		piecesCopy[initRow][initCol] = null;
		
		
		ArrayList<Piece> blackPieces= boardCopy.getBlackPieces();
		ArrayList<Piece> whitePieces= boardCopy.getWhitePieces();
		
		// checks if a given position the king wants to move to results in a check
		if(Color.WHITE == piece.getColor()) {
			for(int i = 0; i < blackPieces.size(); i++) {
				if(blackPieces.get(i).isValid(king.getRow(), king.getColumn(), boardCopy)) {
					// will return true if the move is valid and results a check
					return true;
				}
			}
		} else {
			for(int i = 0; i < whitePieces.size(); i++) {
				if(whitePieces.get(i).isValid(king.getRow(), king.getColumn(), boardCopy)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @return whitesTurn
	 */
	public boolean getWhitesTurn() {
		return whitesTurn;
	}

	/**
	 * @param whitesTurn the whitesTurn to set
	 */
	public void setWhitesTurn() {
		this.whitesTurn = !whitesTurn;
	}
}
