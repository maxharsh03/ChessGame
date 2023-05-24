package chessgame.chess.player;

/**
 * Enum to hold different game states including when the game is in progress (none, no winner), a draw,
 * and the cases when white or black is the victor.
 * @author maxharsh
 *
 */
public enum Winner {

	NONE { 
		public String getState() {
			return "None";
		}
	} , DRAW {
		public String getState() {
			return "Draw";
		}
	} , WHITE {
		public String getState() {
			return "White";
		}
	} , BLACK {
		public String getState() {
			return "Black";
		}
	}
}
