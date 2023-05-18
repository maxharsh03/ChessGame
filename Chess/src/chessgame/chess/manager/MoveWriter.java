package chessgame.chess.manager;

import java.util.HashMap;
import java.util.Map;

import chessgame.chess.piece.Color;
import chessgame.chess.piece.Piece;
import chessgame.chess.piece.Rook;
import chessgame.chess.piece.Type;

public class MoveWriter {
	
	private Map<Integer, String> moveMap;
	
	public MoveWriter() {
		moveMap = new HashMap<Integer, String>();
		// for columns only with the square (0, a) corresponding to white's bottom left piece 
		moveMap.put(0, "a");
		moveMap.put(1, "b");
		moveMap.put(2, "c");
		moveMap.put(3, "d");
		moveMap.put(4, "e");
		moveMap.put(5, "f");
		moveMap.put(6, "g");
		moveMap.put(7, "h");
	}
	
	public String moveWrite(Piece piece, int rowInit, int colInit, int rowFinal, int colFinal) {
		String value = "";
		String pieceType = "";
		
		if(piece.getType() == Type.PAWN) {
			pieceType += "p";
		} else if(piece.getType() == Type.ROOK) {
			pieceType += "r";
		} else if(piece.getType() == Type.BISHOP) {
			pieceType += "b";
		} else if(piece.getType() == Type.KNIGHT) {
			pieceType += "n";
		} else if(piece.getType() == Type.QUEEN) {
			pieceType += "q";
		} else if(piece.getType() == Type.KING) {
			pieceType += "k";
		} 
		
		if(piece.getColor() == Color.WHITE) {
			pieceType = pieceType.toUpperCase();
		}
		
		value += pieceType;
		value += moveMap.get(colInit) + Integer.toString(rowInit) + moveMap.get(colFinal) + Integer.toString(rowFinal);
		
		return value;
	}
	
	public static void main(String[]args) {
		MoveWriter mv = new MoveWriter();
		Piece piece = new Rook(Color.WHITE, 0, 0);
		
		System.out.println(mv.moveWrite(piece, 0, 0, 3, 3));
	}
}
