package Engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import chessgame.chess.board.Board;
import chessgame.chess.manager.GameManager;
import chessgame.chess.manager.Move;
import chessgame.chess.piece.Color;
import chessgame.chess.piece.King;
import chessgame.chess.piece.Piece;
import chessgame.chess.piece.Type;

/**
 * 
 * @author maxharsh 
 *
 */

public class Minimax {
	
	private GameManager gm;
	private Heuristics evaluationFunction;
	private int score;
	
	public Minimax(GameManager gm) {
		this.gm = gm;
		this.evaluationFunction = new Heuristics();
	}
 	
	public Move getBestMove(int depth, Board board) {
		Move bestMove = null;
		score = 0;
		
		ArrayList<Move> legalMoves = gm.getAllLegalMoves(gm.getCurrentPlayer().getColor(), board);
		int highestSeenValue = Integer.MIN_VALUE;
		int lowestSeenValue = Integer.MAX_VALUE; 
		int currentValue;
				
		for(int i = 0; i < legalMoves.size(); i++) {
			int[] move = legalMoves.get(i).getMove();
			Board boardCopy = new Board(board);
			gm.makeMove(move[0], move[1], move[2], move[3], boardCopy, boardCopy.getPieceFromBoard(move[0], move[1]));
			score++;
			
			currentValue = gm.getCurrentPlayer().getColor() == Color.WHITE ? max(depth, boardCopy) : min(depth, boardCopy);
			gm.switchPlayer();

			if(gm.getCurrentPlayer().getColor() == Color.WHITE && currentValue >= highestSeenValue) {
				highestSeenValue = currentValue;
				bestMove = legalMoves.get(i);
			} else if(gm.getCurrentPlayer().getColor() == Color.BLACK && currentValue <= lowestSeenValue) {
				lowestSeenValue = currentValue;
				bestMove = legalMoves.get(i);
			}	
		}
		System.out.println(score);
		return bestMove;
	}
	
	public int min(int depth, Board board) {
		
		if(depth == 0 || gm.isCheckmate(board) || gm.isStalemate(board)) {
			return this.evaluationFunction.evaluate(board, depth);
		}
		
		ArrayList<Move> legalMoves = gm.getAllLegalMoves(Color.BLACK, board);
				
		int lowestValue = Integer.MAX_VALUE;
		for(int i = 0; i < legalMoves.size(); i++) {
			
			int[] move = legalMoves.get(i).getMove();
			Board boardCopy = new Board(board);
			gm.makeMove(move[0], move[1], move[2], move[3], boardCopy, boardCopy.getPieceFromBoard(move[0], move[1]));
			score++;
			
			lowestValue = Math.min(lowestValue, max(depth - 1, boardCopy));
			gm.switchPlayer();
		}
		return lowestValue; 
	}
	
	public int max(int depth, Board board) {
		
		if(depth == 0 || gm.isCheckmate(board) || gm.isStalemate(board)) {
			return this.evaluationFunction.evaluate(board, depth);
		}
		
		ArrayList<Move> legalMoves = gm.getAllLegalMoves(Color.WHITE, board);
				
		int highestValue = Integer.MIN_VALUE;
		for(int i = 0; i < legalMoves.size(); i++) {
			score++;
			
			int[] move = legalMoves.get(i).getMove();
			Board boardCopy = new Board(board);
			gm.makeMove(move[0], move[1], move[2], move[3], boardCopy, boardCopy.getPieceFromBoard(move[0], move[1]));
			
			highestValue = Math.max(highestValue, min(depth - 1, boardCopy));
			gm.switchPlayer();
		}
		return highestValue;
	}
	
	private void printBoard(Board board) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				Piece piece = board.getPieceFromBoard(i, j);

				String s = "";
				
				if(piece == null) {
					s = "-"; 
				}
				else if(piece.getType() == Type.PAWN) {
					s = "p";
				} else if(piece.getType() == Type.ROOK) {
					s = "r";
				} else if(piece.getType() == Type.KNIGHT) {
					s = "n";
				} else if(piece.getType() == Type.BISHOP) {
					s = "b";
				} else if(piece.getType() == Type.KING) {
					s = "k";
				} else if(piece.getType() == Type.QUEEN) {
					s = "q";
				}
				if(piece != null && piece.getColor() == Color.WHITE) {
					s = s.toUpperCase();
				} 
				System.out.print(s + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	
	private void printLegalMoves(ArrayList<Move> legalMoves) {
		for(int i = 0; i < legalMoves.size(); i++) {
			System.out.println(Arrays.toString(legalMoves.get(i).getMove()));
		}
	}
}
