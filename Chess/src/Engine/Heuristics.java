package Engine;

import chessgame.chess.board.Board;
import chessgame.chess.manager.GameManager;
import chessgame.chess.piece.Color;
import chessgame.chess.piece.King;
import chessgame.chess.piece.Piece;


/*
 * Heuristics should take into account: 
 * material values
 * king safety
 * possible captures
 * stalemate
 * checkmate
 * pawn structure
 * bishop strength (has a diagonal going across the whole board)
 * pins
 * check
 * centrality of pieces
 * mobility of pieces
 */


public class Heuristics {

	private GameManager gameManagerWhite;
	private GameManager gameManagerBlack;
    private static final int CHECKMATE_SCORE = 10000;
    private static final int STALEMATE_SCORE = 0;
    private static final int CHECK_SCORE = 50;
    private static final int CASTLING_SCORE = 60;

    public int evaluate(Board board, int depth) { 
    	this.gameManagerWhite = new GameManager(board, 0);
    	this.gameManagerBlack = new GameManager(board, 1);
    	
    	return scoreOfPlayer(board, Color.WHITE, depth) - scoreOfPlayer(board, Color.BLACK, depth);
    }

    public int scoreOfPlayer(Board board, Color color, int depth) {
    	
    	return pieceValues(board, color) + castled(board, color) + kingSafety(board) + pawnStructure(board) + 
        		checkmate(board, color, depth) + stalemate(board, color) + check(board, color) + pieceMobility(board, color);
    }
    
    private int pieceValues(Board board, Color color) {
        int score = 0;
        
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Piece piece = board.getPieceFromBoard(i, j);
                if(piece != null && piece.getColor() == color) {
                	score += piece.getValue();
                }
            }
        }
        return score;
    }

    private int kingSafety(Board board) {
        // Implement your king safety evaluation logic here
        return 0;
    }

    private int pawnStructure(Board board) {
        // Implement your pawn structure evaluation logic here
        return 0;
    }

    private int checkmate(Board board, Color color, int depth) {
        if(color == Color.WHITE) {
        	return gameManagerBlack.isCheckmate(board) ? CHECKMATE_SCORE * depthBonus(depth): 0;
        } else {
        	return gameManagerWhite.isCheckmate(board) ? CHECKMATE_SCORE * depthBonus(depth): 0;
        }
    }
    
    private int depthBonus(int depth) {
    	return depth == 0 ? 1 : depth * 100;
    }
    
    private int stalemate(Board board, Color color) {
    	if(color == Color.WHITE) {
        	if(gameManagerBlack.isStalemate(board)) {
        		return STALEMATE_SCORE;
        	}
        } else {
        	if(gameManagerWhite.isStalemate(board)) {
        		return STALEMATE_SCORE;
        	}
        }
    	return 0;
    }
    
    private int castled(Board board, Color color) {
    	King king = (King) board.getKing(color);
    	return king.isCastled() ? CASTLING_SCORE : 0;
    }
    
    private int check(Board board, Color color) {
    	if(color == Color.WHITE) {
        	return gameManagerBlack.isCheck(board) ? CHECK_SCORE : 0;
        } else {
        	return gameManagerWhite.isCheck(board) ? CHECK_SCORE : 0;
        }
    }

    private int pieceMobility(Board board, Color color) {
        if(color == Color.WHITE) {
        	return gameManagerWhite.getAllLegalMoves(Color.WHITE, board).size();
        } else {
        	return gameManagerBlack.getAllLegalMoves(Color.BLACK, board).size();
        }
    }
}
