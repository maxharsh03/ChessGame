package Engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import chessgame.chess.manager.GameManager;
import chessgame.chess.manager.Move;
import chessgame.chess.piece.Color;
import chessgame.chess.piece.Piece;
import chessgame.chess.player.Player;

public class Engine {

	private GameManager gm;
	private static Player computer; 
	
	public Engine(GameManager gm) {
		this.gm = gm;
		this.computer = gm.getPlayers()[1];
	} 
	
	public Move getBestMove() {
		Minimax mx = new Minimax(gm);
		
		return null;
	}
}
