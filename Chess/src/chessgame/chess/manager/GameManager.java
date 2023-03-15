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
	
	private Board board;
	
	private Player[] players;
	
	private int currentPlayerIndex;
	
	/**
	 * 
	 */
	public GameManager() {
		listAllValidMoves = new HashMap<Integer[], Integer[]>();
		lastPieceMoved = null;
		whiteHasCaptured = new ArrayList<Piece>();
		blackHasCaptured = new ArrayList<Piece>();
		board = new Board();
		players[0] = new Player("Player 1", Color.WHITE);	
		players[1] = new Player("Player 2", Color.BLACK);
		currentPlayerIndex = 0;
		initPlayerPieces();
	}
	
	/**
	 * 
	 */
	public void initPlayerPieces() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board.getBoard()[i][j] != null) {
					if(board.getBoard()[i][j].getColor() == players[0].getColor()) {
						players[0].addPiece(board.getBoard()[i][j]);
					} else {
						players[1].addPiece(board.getBoard()[i][j]);
					}
				}
			}
		}
	}
	
	public void switchPlayer() {
	 	currentPlayerIndex = (currentPlayerIndex + 1) % 2;
	}
	public Player getCurrentPlayer() {
		return players[currentPlayerIndex];
	}
	public Board getBoard() {
	 	return board;
	}
	
	/**
	 * Called to start a new game. Resets everything. 
	 */
	public void createNewGame() {
		
	}
	
	/**
	 * This method can check whether the position passed in 
	 * position is empty through board.getPiece(), check if it is out of bounds, if the move results in a player exposing 
	 * a friendly king to a check (through a pin), or if the player is already in check if the move gets the player's king 
	 * out of check. Otherwise, if there is no special condition, allow the player to move to the desired square and then 
	 * determine if there is a capture. This method should only need to receive the coordinates for the initial and final 
	 * position on the board.
	 * @param rowInitial
	 * @param columnInitial
	 * @param rowFinal
	 * @param columnFinal
	 * @param board
	 */
	public boolean makeMove(int rowInitial, int columnInitial, int rowFinal, int columnFinal) {
		// check if starting or ending position are out of bounds
		if(rowInitial < 0 || rowInitial > 7 || columnInitial < 0 || columnInitial > 7 || rowFinal < 0
				|| rowFinal > 7 || columnFinal < 0 || columnFinal > 7) {
			return false;
		}
		
		// ensure that player is not trying to move piece to same position its on
		if(rowInitial == rowFinal && columnInitial == columnFinal) {
			return false;
		}
		
		Piece piece = board.getPieceFromBoard(rowInitial, columnInitial);
		
		// checks if player attempted to move a piece that does not exist or one that is not theirs
		if(piece == null || getCurrentPlayer().getColor() != piece.getColor()) {
			return false;
		} 
				
		// checks that player does not try to capture piece of same color
		if(board.getPieceFromBoard(rowFinal, columnFinal).getColor() == piece.getColor()) {
			return false;
		}
		
		// not a valid move for the piece type 
		if(!piece.isValid(rowFinal, columnFinal, board)) {
			return false;
		}
		
		if(!isKingInCheck(columnFinal, columnFinal, piece)) {
			moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, piece);
		}
		
		// switch turn after move for a player
		switchPlayer();
		return false;
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
		if(color == Color.WHITE) {
			moveHelper(7, 7, 7, 5, board.getPieceFromBoard(7, 7));			
			moveHelper(7, 4, 7, 6, board.getPieceFromBoard(7, 4));
		} else {
			moveHelper(0, 7, 0, 5, board.getPieceFromBoard(0, 7));
			moveHelper(0, 4, 0, 6, board.getPieceFromBoard(0, 4));
		}
	}
	
	/**
	 * Handles whenever a king is castling to the left.
	 * @param color
	 * @param board
	 */
	public void leftCastle(Color color, Board board) {
		if(color == Color.WHITE) {
			moveHelper(7, 0, 7, 3, board.getPieceFromBoard(7, 0));
			moveHelper(7, 4, 7, 2, board.getPieceFromBoard(7, 4));
		} else {
			moveHelper(0, 0, 0, 3, board.getPieceFromBoard(0, 0));
			moveHelper(0, 4, 0, 2, board.getPieceFromBoard(0, 4));
		}
	}
	
	public void moveHelper(int rowInitial, int columnInitial, int rowFinal, int columnFinal, Piece piece) {
		board.getBoard()[rowFinal][columnFinal] = piece;
		board.getPieceFromBoard(rowFinal, columnFinal).setRow(rowFinal);
		board.getPieceFromBoard(rowFinal, columnFinal).setColumn(columnFinal);
		board.getBoard()[rowInitial][columnInitial] = null;
	}
	
	public void canMove() {
		
	}
	
	/**
	 * Determines if king for a given player is currently in check.
	 * @return
	 */
	
	public boolean isCheck() {
		King king = (King) board.getKing(getCurrentPlayer().getColor());
		return king.kingCurrentlyInCheck(board);
	}
	
	/** 
	 * Determines whether the king for a given player is currently in checkmate. 
	 * @return
	 */
	public boolean isCheckmate() {
		
		if(!isCheck()) {
			return false;
		}
		return true;
		
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
	 * Determines if a player's king is in check at the current position. 
	 * @param king
	 * @param moveRow
	 * @param moveCol
	 * @param piece
	 * @param board
	 * @return
	 */
	public boolean isKingInCheck(int row, int column, Piece piece) {
		Board boardCopy = (Board) board.clone();
		ArrayList<Piece> blackPieces = new ArrayList<Piece>();
		ArrayList<Piece> whitePieces = new ArrayList<Piece>();
		
		// make a deep copy of the new pieces array 
		for(int i = 0; i < board.getBoard().length; i++) {
			for(int j = 0; j < board.getBoard()[0].length; j++) {
				boardCopy.setPieceAt(i, j, board.getPieceFromBoard(row, column));
			}
		}
		
		Piece[][] piecesCopy = boardCopy.getBoard();
		
		// now do move on cloned pieces array
		King king = (King) boardCopy.getKing(piece.getColor());
		piecesCopy[row][column] = piece;
		piecesCopy[piece.getRow()][piece.getColumn()] = null;
	
		
		// checks if moving a piece will expose a player's king to check
		for(int i = 0; i < boardCopy.getBoard().length; i++) {
			for(int j = 0; i < boardCopy.getBoard()[0].length; j++) {
				Piece p = boardCopy.getBoard()[i][j];
				if(p != null && p.getColor() != piece.getColor()) {
					if(p.isValid(king.getRow(), king.getColumn(), boardCopy)) {
						// will return true if the move is valid and results in a check
						return true;
					}
				}
			}
		}
		return false;
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
	/*
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
	*/

}
