package chessgame.chess.manager;

import java.util.ArrayList;
import java.util.HashMap;

import chessgame.chess.board.Board;
import chessgame.chess.piece.Color;
import chessgame.chess.piece.Piece;
import chessgame.chess.piece.Queen;
import chessgame.chess.piece.Rook;
import chessgame.chess.piece.Type;
import chessgame.chess.piece.King;
import chessgame.chess.piece.Pawn;
import chessgame.chess.player.Player;

/**
 * GameManager handles the rules of a chess game. This means that it controls how pieces move, 
 * how captures are made, and how the game starts and ends. GameManager manages the board, the players, 
 * and the state the game is in. 
 * @author maxharsh
 *
 */
public class GameManager {

	/** List of all valid moves in coordinate form (y, x) */
	public ArrayList<Integer[]> listAllValidMoves;
	/** Board object that contains the pieces.  */
	private Board board;
	/** Array of the 2 players playing the chess game. */
	private Player[] players = new Player[2];
	/** Index of the current player  */
	private int currentPlayerIndex;
	/** Represents coordinates of en passant target */
	private Piece enPassantTarget;
	/** allows GameManager to move pieces */
	private Move move;
	
	/**
	 * Creates a new GameManger object. 
	 */
	public GameManager() {
		listAllValidMoves = new ArrayList<Integer[]>();
		board = new Board();
		move = new Move();
		enPassantTarget = null;
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
	public boolean canMove(int rowInitial, int columnInitial, int rowFinal, int columnFinal) {
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
		
		// checks that player does not try to capture piece of same color
		if(possibleCapture != null && possibleCapture.getColor() == piece.getColor()) {
			return false;
		}
		
		// must handle king moves entirely separately
		// implementation not entirely correct, must handle event where king can capture a piece
		if(piece.getType() == Type.KING) {
			King king = (King) piece;
			if((rowInitial == 0 && columnInitial == 4 && rowFinal == rowInitial && columnFinal == 2)
					|| (rowInitial == 7 && columnInitial == 4 && rowFinal == rowInitial && columnFinal == 2)) {
				if(piece.isValid(rowFinal, columnFinal, board)) {
					return true;
				}
			} 
			else if((rowInitial == 0 && columnInitial == 4 && rowFinal == rowInitial && columnFinal == 6)
					|| (rowInitial == 7 && columnInitial == 4 && rowFinal == rowInitial && columnFinal == 6)) {
				if(piece.isValid(rowFinal, columnFinal, board)) {
					return true;
				}
			} 
			/*
			else {
				if(piece.isValid(rowFinal, columnFinal, board)) {
					return true;
				}
			}
			*/
		}
		
		// checks that the player made a valid move for the piece type 
		if(!piece.isValid(rowFinal, columnFinal, board)) {
			return false;
		}
		
		
		// moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
		
		//checks that king is not currently in check
		if(!isKingInCheck(piece.getColor(), board)) {
			moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
			// after the mock move, we need to ensure that the king is not exposed to a check
			if(isKingInCheck(piece.getColor(), board)) {
				moveHelper(rowFinal, columnFinal, rowInitial, columnInitial, board, piece);
				board.getBoard()[rowFinal][columnFinal] = possibleCapture;
				return false;
			}
		// if player is in check, we have to see if the move will get the player out of check
		} else {
			moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
			if(isKingInCheck(piece.getColor(), board)) {
				moveHelper(rowFinal, columnFinal, rowInitial, columnInitial, board, piece);
				board.getBoard()[rowFinal][columnFinal] = possibleCapture;
				return false;
			}
		}
		moveHelper(rowFinal, columnFinal, rowInitial, columnInitial, board, piece);
		board.getBoard()[rowFinal][columnFinal] = possibleCapture;
		
		// handle possible en passant
		if(piece.getType() == Type.PAWN && enPassantTarget != null 
				&& board.getPieceFromBoard(rowFinal, columnFinal) == null) {
			if(getCurrentPlayer().getColor() == Color.WHITE && rowInitial == 3) {
				if(enPassantTarget.getRow() == piece.getRow() && enPassantTarget.getColumn() == columnFinal) {
					return true;
				} 
			} else if(getCurrentPlayer().getColor() == Color.BLACK && rowInitial == 4) {
				if(enPassantTarget.getRow() == piece.getRow() && enPassantTarget.getColumn() == columnFinal) {
					return true;
				} 
			}
		}
		return true;
	}
	
	public void makeMove(int rowInitial, int columnInitial, int rowFinal, int columnFinal, Board board, Piece piece) {
		move.movePiece(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
	}
	
	public void moveHelper(int rowInitial, int columnInitial, int rowFinal, int columnFinal, Board board, Piece piece) {
		board.getBoard()[rowFinal][columnFinal] = piece;
		board.getPieceFromBoard(rowFinal, columnFinal).setRow(rowFinal);
		board.getPieceFromBoard(rowFinal, columnFinal).setColumn(columnFinal);
		board.getBoard()[rowInitial][columnInitial] = null;
	}
	
	/**
	 * Retrieve list of players.
	 * @return
	 */
	public Player[] getPlayers() {
		return players;
	}
	
	/**
	 * Determines if king for a given player is currently in check.
	 * @return
	 */
	
	public boolean isCheck() {
		return isKingInCheck(getCurrentPlayer().getColor(), board);
	}
	
	/** 
	 * Determines whether the king for a given player is currently in checkmate. 
	 * @return
	 */
	public boolean isCheckmate() {
		if(!isCheck()) {
			return false;
		}
		
		// loop through every piece on the board
		for(int startRow = 0; startRow < 8; startRow++) {
			for(int startCol = 0; startCol < 8; startCol++) {
				Piece piece = board.getPieceFromBoard(startRow, startCol);
				if(piece != null && piece.getColor() == getCurrentPlayer().getColor()) {
					// check every position to see if making the move can get the king out of check
					for(int endRow = 0; endRow < 8; endRow++) {
						for(int endCol = 0; endCol < 8; endCol++) {
							if(canMove(startRow, startCol, endRow, endCol)) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Determines if stalemate has occured (when a player is not in check but also does not have any valid moves).
	 * @param color
	 * @return
	 */
	public boolean isStalemate() {
		if(!isCheck()) {
			for(int startRow = 0; startRow < 8; startRow++) {
				for(int startCol = 0; startCol < 8; startCol++) {
					Piece piece = board.getPieceFromBoard(startRow, startCol);
					if(piece != null && piece.getColor() == getCurrentPlayer().getColor()) {
						for(int endRow = 0; endRow < 8; endRow++) {
							for(int endCol = 0; endCol < 8; endCol++) {
								if(canMove(startRow, startCol, endRow, endCol)) {
									return false;
								}
							}
						}
					}
				}
			}
			return true;
		}
		return false;
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
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				Piece p = board.getPieceFromBoard(i, j);
				if(p != null && p.getColor() != color && 
						p.getType() != Type.KING) {
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
	 * Return all valid moves for a particular piece. Returns an ArrayList filled with 
	 * positions of valid move for a piece.
	 * @param listAllValidMoves the listAllValidMoves to set
	 */
	public ArrayList<Integer[]> getListAllValidMoves(Board board, Piece piece) {
		resetListAllValidMoves();
		
		if(piece == null) {
			return listAllValidMoves;
		}
		
		// must also verify isKingInCheck for each move
		int initRow = piece.getRow();
		int initColumn = piece.getColumn();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(canMove(piece.getRow(), piece.getColumn(), i, j)) {
					listAllValidMoves.add(new Integer[] {i, j});
				}
			}
		}
		return listAllValidMoves;
	}
	
	/**
	 * Resets the list of valid moves for a piece (ex. after it has just moved).
	 */
	public void resetListAllValidMoves() {
		listAllValidMoves.clear();
	}
	
	/**
	 * Move is the inner class of GameManager. Move will do the heavy weight of moving pieces across the board, including 
	 * special cases like en passant, castling, and pawn promotions. Move also handles the capturing of pieces and updating 
	 * these changes in the Player class. 
	 * @author maxharsh
	 *
	 */
	private class Move {
		public void movePiece(int rowInitial, int columnInitial, int rowFinal, int columnFinal, Board board, Piece piece) {
			Piece possibleCapture = board.getPieceFromBoard(rowFinal, columnFinal);

			if(piece.getType() == Type.KING) {
				enPassantTarget = null;
				King king = (King) piece;
				// handle left castle scenario 
				if((rowInitial == 0 && columnInitial == 4 && rowFinal == rowInitial && columnFinal == 2)
						|| (rowInitial == 7 && columnInitial == 4 && rowFinal == rowInitial && columnFinal == 2)) {
					leftCastle(piece.getColor(), board);
					king.setCastled(true);
					king.setHasMoved(true);
					switchPlayer();
				// handle right castle scenario 
				} else if((rowInitial == 0 && columnInitial == 4 && rowFinal == rowInitial && columnFinal == 6)
						|| (rowInitial == 7 && columnInitial == 4 && rowFinal == rowInitial && columnFinal == 6)) {
					rightCastle(piece.getColor(), board);
					king.setCastled(true);
					king.setHasMoved(true);
					switchPlayer();
				} else {
					moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
					switchPlayer();
					handleCapture(possibleCapture);
					king.setHasMoved(true);
				}
			} else if(piece.getType() == Type.PAWN) {
				// if enPassantTarget not null, that means we can enPassant
				if(enPassantTarget != null && enPassantTarget.getColumn() == columnFinal 
						&& enPassantTarget.getRow() == rowInitial) {
						enPassantMove(rowInitial, columnInitial, rowFinal, columnFinal, board);
						enPassantTarget = null;
				} else {
					if(Math.abs(rowFinal - rowInitial) == 2 && (rowInitial == 1 || rowInitial == 6)) {
						enPassantTarget = piece;
						moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
						switchPlayer();
					// handles pawn promotion, turning pawn into Queen 
					} else if(getCurrentPlayer().getColor() == Color.WHITE && rowFinal == 0) {
						board.getBoard()[rowFinal][columnFinal] = new Queen(Color.WHITE, rowFinal, columnFinal);
						board.getBoard()[rowInitial][columnInitial] = null;
						enPassantTarget = null;
						switchPlayer();
					} else if(getCurrentPlayer().getColor() == Color.BLACK && rowFinal == 7) {
						board.getBoard()[rowFinal][columnFinal] = new Queen(Color.BLACK, rowFinal, columnFinal);
						board.getBoard()[rowInitial][columnInitial] = null;
						enPassantTarget = null;
						switchPlayer();
					} else {
						moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
						switchPlayer();
						handleCapture(possibleCapture);
						enPassantTarget = null;
					}
				}
				
			} else if(piece.getType() == Type.ROOK) {
				Rook rook = (Rook) piece;
				rook.setHasMoved(true);
				moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
				switchPlayer();
				handleCapture(possibleCapture);
				enPassantTarget = null;
			} else {
				moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, piece);
				switchPlayer();
				handleCapture(possibleCapture);
				enPassantTarget = null;
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
				Rook rook = (Rook) board.getPieceFromBoard(7, 3);
				rook.setHasMoved(true);
			} else {
				moveHelper(0, 0, 0, 3, board, board.getPieceFromBoard(0, 0));
				moveHelper(0, 4, 0, 2, board, board.getPieceFromBoard(0, 4));
				Rook rook = (Rook) board.getPieceFromBoard(0, 3);
				rook.setHasMoved(true);
			}
		}
		
		/**
		 * Handles whenever a king is castling to the right.
		 * @param color
		 * @param board
		 */
		public void rightCastle(Color color, Board board) {
			if(color == Color.WHITE) {
				moveHelper(7, 7, 7, 5, board, board.getPieceFromBoard(7, 7));			
				moveHelper(7, 4, 7, 6, board, board.getPieceFromBoard(7, 4));
				Rook rook = (Rook) board.getPieceFromBoard(7, 5);
				rook.setHasMoved(true);
			} else {
				moveHelper(0, 7, 0, 5, board, board.getPieceFromBoard(0, 7));
				moveHelper(0, 4, 0, 6, board, board.getPieceFromBoard(0, 4));
				Rook rook = (Rook) board.getPieceFromBoard(0, 5);
				rook.setHasMoved(true);
			}
		}
		
		public void enPassantMove(int rowInitial, int columnInitial, int rowFinal, int columnFinal, Board board) {
			switchPlayer();
			handleCapture(board.getPieceFromBoard(rowInitial, columnFinal));
			moveHelper(rowInitial, columnInitial, rowFinal, columnFinal, board, board.getPieceFromBoard(rowInitial, columnInitial));
			board.getBoard()[rowInitial][columnFinal] = null;
		}
		
		public void handleCapture(Piece possibleCapture) {
			if(possibleCapture != null) {
				getCurrentPlayer().removePiece(possibleCapture);
				players[(currentPlayerIndex + 1) % 2].addCaptured(possibleCapture);
				updateScore(players[(currentPlayerIndex + 1) % 2], possibleCapture);
			}
		}
	}
}
