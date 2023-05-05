package chessgame.chess.manager;

import chessgame.chess.board.Board;
import chessgame.chess.piece.Color;
import chessgame.chess.piece.Pawn;

/**
 * Class that reads in FEN chess notation. Allows for games to easily be read in to 
 * the chess system. All methods are static and there is no constructor because 
 * there is no need to ever construct a FENReader object. 
 * @author maxharsh
 *
 */
public class FENReader {

	/**
	 * Reads FEN string in
	 */
	public static void readFEN(String fenStr) {
		Board board = new Board();
		int count = 0;
		
		for(int i = 0; i < fenStr.length(); i++) {
			for(int j = 0; j < 8; j++) {
				
			}
			if(fenStr.substring(i, i + 1).equals("p")) {
				board.getBoard()[i][i] = new Pawn(Color.BLACK, 0, 0);
			} else if(fenStr.substring(i, i + 1).equals("r")) {
				
			} else if(fenStr.substring(i, i + 1).equals("b")) {
				
			} else if(fenStr.substring(i, i + 1).equals("n")) {
				
			} else if(fenStr.substring(i, i + 1).equals("q")) {
				
			} else if(fenStr.substring(i, i + 1).equals("k")) {
				
			} else if(fenStr.substring(i, i + 1).equals("P")) {
				
			} else if(fenStr.substring(i, i + 1).equals("R")) {
				
			} else if(fenStr.substring(i, i + 1).equals("B")) {
				
			} else if(fenStr.substring(i, i + 1).equals("N")) {
				
			} else if(fenStr.substring(i, i + 1).equals("K")) {
				
			} else if(fenStr.substring(i, i + 1).equals("Q")) {
				
			}
		}
	}
}
