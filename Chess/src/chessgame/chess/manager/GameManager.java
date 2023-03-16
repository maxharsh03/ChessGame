package chessgame.chess.manager;

import java.util.HashMap;

import chessgame.chess.board.Board;
import chessgame.chess.piece.Color;
import chessgame.chess.piece.Piece;
import chessgame.chess.piece.Rook;
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
	/** Maps each starting coordinate to its ending coordinate for all valid moves. For generating AI moves 
	 * primarily.
	 */
	public HashMap<Integer[], Integer[]> listAllValidMoves;
	/** Board object that contains the pieces.  */
	private Board board;
	/** Array of the 2 players playing the chess game. */
	private Player[] players;
	/** Index of the current player  */
	private int currentPlayerIndex;
	
	/**
	 * Creates a new GameManger object. 
	 */
	public GameManager() {
		listAllValidMoves = new HashMap<Integer[], Integer[]>();
		lastPieceMoved = null;
		board = new Board();
		players[0] = new Player("Player 1", Color.WHITE);	
		players[1] = new Player("Player 2", Color.BLACK);
		currentPlayerIndex = 0;
		initPlayerPieces();
	}
	
	/**
	 * Loads white and black pieces for each respective player. 
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
	
	/**
	 * After a turn is completed, switches the current player.
	 */
	public void switchPlayer() {
	 	currentPlayerIndex = (currentPlayerIndex + 1) % 2;
	}
	public Player getCurrentPlayer() {
		return players[currentPlayerIndex];
	}
	
	/**
	 * Allows GUI to get the board. 
	 * @return the chess board 
	 */
	public Board getBoard() {
	 	return board;
	}
	
	/**
	 * Called to start a new game. Resets everything. 
	 */
	public void createNewGame() {
		
	}
	
	/**
	 * This method can check whether the position passed in is empty through board.getPiece(), check if it is out of bounds, 
	 * if the move results in a player exposing a friendly king to a check (through a pin), or if the player is already in 
	 * check if the move gets the player's king out of check. Otherwise, if there is no special condition, allow the player 
	 * to move to the desired square and then determine if there is a capture. This method should only need to receive the 
	 * coordinates for the initial and final position on the board.
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
		Piece possibleCapture = board.getPieceFromBoard(rowFinal, columnFinal);
		
		// checks if player attempted to move a piece that does not exist or one that is not theirs
		if(piece == null || getCurrentPlayer().getColor() != piece.getColor()) {
			return false;
		} 
		
		// must handle castling moves entirely separately within King class
		if(piece.getType() == Type.KING) {
			King king = (King) piece;
			if((rowInitial == 0 && columnInitial == 4 && rowFinal == rowInitial && columnFinal == 2)
					|| (rowInitial == 7 && columnInitial == 4 && rowFinal == rowInitial && columnFinal == 2)) {
				if(piece.isValid(rowFinal, columnFinal, board)) {
					leftCastle(piece.getColor(), board);
					king.setCastled(true);
					king.setHasMoved(true);
					return true;
				}
			} 
			else if((rowInitial == 0 && columnInitial == 4 && rowFinal == rowInitial && columnFinal == 6)
					|| (rowInitial == 7 && columnInitial == 4 && rowFinal == rowInitial && columnFinal == 6)) {
				if(piece.isValid(rowFinal, columnFinal, board)) {
					rightCastle(piece.getColor(), board);
					king.setCastled(true);
					king.setHasMoved(true);
					return true;
				}
			} 
			else {
				if(piece.isValid(rowFinal, columnFinal, board)) {
					moveHelper(columnInitial, rowFinal, rowFinal, columnFinal, piece);
					king.setHasMoved(true);
				}
			}
		}
				
		// checks that player does not try to capture piece of same color
		if(possibleCapture != null && possibleCapture.getColor() == piece.getColor()) {
			return false;
		}
		
		// checks that the player made a valid move for the piece type 
		if(!piece.isValid(rowFinal, columnFinal, board)) {
			return false;
		}
		
		// create copy of board to verify check and checkmate 
		Board boardCopy = (Board) board.clone();
		Piece[][] piecesCopy = new Piece[8][8];
		
		// make a deep copy of the new pieces array 
		for(int i = 0; i < board.getBoard().length; i++) {
			for(int j = 0; j < board.getBoard()[0].length; j++) {
				//boardCopy.setPieceAt(i, j, board.getPieceFromBoard(row, column));
				if(board.getPieceFromBoard(i, j) != null) {
					piecesCopy[i][j] = board.getPieceFromBoard(i, j);
				}
			}
		}
		
		// now do move on cloned pieces array
		piecesCopy[rowFinal][columnFinal] = piece;
		piecesCopy[piece.getRow()][piece.getColumn()] = null;
		
		//checks that king is not currently in check
		if(!isKingInCheck(piece.getColor(), board)) {
			// after the mock move, we need to ensure that the king is not exposed to a check
			if(isKingInCheck(piece.getColor(), boardCopy)) {
				return false;
			}
		// if player is in check, we have to see if the move will get the player out of check
		} else {
			if(isKingInCheck(piece.getColor(), boardCopy)) {
				return false;
			}
		}
		// make move on board and set last piece moved
		// also have to set rook to moved if it was moved for castling purposes
		moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, piece);
		if(piece.getType() == Type.ROOK) {
			Rook rook = (Rook) piece;
			rook.setHasMoved(true);
		} 
		setLastPieceMoved(piece);
		
		// switch player and handle capture
		switchPlayer();
		if(possibleCapture != null) {
			getCurrentPlayer().removePiece(possibleCapture);
			players[(currentPlayerIndex + 1) % 2].addCaptured(possibleCapture);
		}
		
		// determine if there is a new check or checkmate that has occurred
		if(isCheck()) {
			System.out.println(getCurrentPlayer().getColor() + " is in check!");
		} else if(isCheckmate()) {
			System.out.println(getCurrentPlayer().getColor() + " is in checkmate!");
			return false;
		}
		return true;
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
	
	/**
	 * Determines if king for a given player is currently in check.
	 * @return
	 */
	
	public boolean isCheck() {
		King king = (King) board.getKing(getCurrentPlayer().getColor());
		return king.isKingInCheck(king.getRow(), king.getColumn(), board);
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
	public boolean isKingInCheck(Color color, Board board) {
	
		King king = (King) board.getKing(color);
		
		// checks if moving a piece will expose a player's king to check
		for(int i = 0; i < board.getBoard().length; i++) {
			for(int j = 0; i < board.getBoard()[0].length; j++) {
				Piece p = board.getBoard()[i][j];
				if(p != null && p.getColor() != color) {
					if(p.isValid(king.getRow(), king.getColumn(), board)) {
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
