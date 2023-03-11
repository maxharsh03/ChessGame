package chessgame.chess.manager;

import chessgame.chess.board.Board;
import chessgame.chess.piece.Piece;
import chessgame.chess.player.Player;

/**
 * GameManager handles the rules of a chess game. This means that it controls how pieces move, 
 * how captures are made, and how the game starts and ends. GameManager manages the board, the players, 
 * and the state the game is in. 
 * @author maxharsh
 *
 */
public class GameManager {

	public Piece lastPieceMoved;
	
	
	public GameManager() {
		
	}
	
	/** 
	 * Handles functionality when a piece captures another piece 
	 * @param rowInitial
	 * @param columnInitial
	 * @param rowFinal
	 * @param columnFinal
	 */
	public static void capture(int rowInitial, int columnInitial, int rowFinal, int columnFinal, Board board) {
		
	}
	
	/** 
	 * Updates score of the game when a piece is captured
	 * @param player
	 * @return
	 */
	public int updateScore(Player player) {
		return 0;
	}
}
