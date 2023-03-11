package chessgame.chess.piece;

import chessgame.chess.board.Board;

/**
 * Piece is the parent class of all the pieces in a chess game including pawn, rook, bishop, knight, 
 * king, and queen. It contains common functionality that all pieces share like location, name, and how the piece 
 * moves. Each individual piece will have a different move implementation. 
 * @author maxharsh
 *
 */
public abstract class Piece {

	private String name;
	private String color;
	private int row;
	private int column;
	
	public Piece(String name, String color, int row, int column) {
		setName(name);
		setColor(color);
		setRow(row);
		setColumn(column);
	}

	/**
	 * Represents how each specific piece will move. Each piece will 
	 * have a different implementation of this method. 
	 */
	public abstract boolean isValid(int row, int column, Board board);
	
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the xLocation
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param xLocation the xLocation to set
	 */
	public void setRow(int row) {
		if(row < 0 || row > 7) {
			throw new IllegalArgumentException("Invalid move");
		}
		this.row = row;
	}

	/**
	 * @return the yLocation
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @param yLocation the yLocation to set
	 */
	public void setColumn(int column) {
		if(column < 0 || column > 7) {
			throw new IllegalArgumentException("Invalid move");
		}
		this.column = column;
	}
}
