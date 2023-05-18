package chessgame.chess.manager;

import java.util.HashMap;
import java.util.Map;
import chessgame.chess.piece.Piece;
import chessgame.chess.piece.Type;

/**
 * Converts a move just made by a player into PGN format. This includes en passant, 
 * captures, castling, pawn promotion, checkmate, check, stalemate, and all other edge cases. 
 * @author maxharsh
 *
 */
public class MoveWriter {
	
	private Map<Integer, String> moveMap;
	
	public MoveWriter() {
		moveMap = new HashMap<Integer, String>();
		// for columns only; the square (0, a) corresponds to white's bottom left piece 
		moveMap.put(0, "a");
		moveMap.put(1, "b");
		moveMap.put(2, "c");
		moveMap.put(3, "d");
		moveMap.put(4, "e");
		moveMap.put(5, "f");
		moveMap.put(6, "g");
		moveMap.put(7, "h");
	}
	
	public String moveWrite(int rowInit, int colInit, int rowFinal, int colFinal, Piece piece, Piece capturedPiece, GameManager gm) {
		// castling moves 
		if(piece.getType() == Type.KING) {
			if(colFinal == 2 && colInit == 4) {
				return "O-O-O";
			} if(colFinal == 6 && colInit == 4) {
				return "O-O";
			}
		}
		
		String pieceString = getPieceSignature(piece);
		String captureString = "";
		String checkString = "";
		String checkMateString = "";
		String stalemateString = "";
		
		if(capturedPiece != null) {
			captureString = "x";
		}
		
		if(gm.isCheck()) {
			checkString = "+";
		} 
		
		/*
		if(gm.isCheckmate()) {
			checkMateString = "#";
		}
		
		if(gm.isStalemate()) {
			stalemateString = "$";
		}
		*/
		
		if("+".equals(checkString) && "#".equals(checkMateString)) {
			checkString = "";
		}
		
		// handles pawn moves including en passant and pawn promotion
		if(piece.getType() == Type.PAWN) {
			if(colFinal == 0 || colFinal == 7) {
				return moveMap.get(colInit) + moveMap.get(colFinal) + Integer.toString(rowFinal) + "=Q" + checkString + checkMateString + stalemateString;				
			} else if(capturedPiece != null) {
				return moveMap.get(colInit) + "x" + moveMap.get(colFinal) + Integer.toString(rowFinal) + checkString + checkMateString + stalemateString;
			} return moveMap.get(colFinal) + Integer.toString(rowFinal) + checkString + checkMateString + stalemateString;
		}
		
		return pieceString + captureString + moveMap.get(colFinal) + Integer.toString(rowFinal) + checkString + checkMateString + stalemateString;
	}
	
	// returns letter that represents piece in standard chess notation
	public String getPieceSignature(Piece piece) {
		if(piece.getType() == Type.PAWN) {
			return "";
		} if(piece.getType() == Type.ROOK) {
			return "R";
		} if(piece.getType() == Type.BISHOP) {
			return "B";
		} if(piece.getType() == Type.KNIGHT) {
			return "N";
		} if(piece.getType() == Type.QUEEN) {
			return "Q";
		} return "K";
	}
}
