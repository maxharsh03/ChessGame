package chessgame.chess.manager;

/**
 * Stores moves as coordinates on the board. The first 2 refer to the initial tile it is on and the 
 * last 2 refer to the destination tile.
 * @author maxharsh
 *
 */
public class Move {
	private int[] moveCoords;

	public Move(int rowInit, int colInit, int rowFinal, int colFinal) {
		moveCoords = new int[] {rowInit, colInit, rowFinal, colFinal};
	}
	
	public int[] getMove() {
		return this.moveCoords;
	}
}
