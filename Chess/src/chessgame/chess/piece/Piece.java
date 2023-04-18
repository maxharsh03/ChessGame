package chessgame.chess.piece;

import java.util.HashMap;
import java.util.Map;

import chessgame.chess.board.Board;

/**
 * Piece is the parent class of all the pieces in a chess game including pawn, rook, bishop, knight, 
 * king, and queen. It contains common functionality that all pieces share like location, name, type, 
 * and how the piece moves. Each individual piece will have a different move implementation. 
 * @author maxharsh
 *
 */
public abstract class Piece {

	private String name;
	private Color color;
	private int row;
	private int column;
	private Type type;
	private Map<Piece, String> map;
	
	public Piece(Type type, Color color, int row, int column) {
		setName(name);
		setColor(color);
		setRow(row);
		setColumn(column);
		setType(type);
		map = new HashMap<Piece, String>();
		initMap();
	}

	/**
	 * Represents how each specific piece will move. Each piece will 
	 * have a different implementation of this method. 
	 */
	public abstract boolean isValid(int row, int column, Board board);
	
	/**
	 * fills map with pieces matched to their image file
	 */
	public void initMap() {
		if(this.getColor() == Color.WHITE) {
			if(this.getType() == Type.PAWN) {
				map.put(this, "resources/white-pawn.png");
			} else if(this.getType() == Type.BISHOP) {
				map.put(this, "resources/white-bishop.png");
			} else if(this.getType() == Type.KNIGHT) {
				map.put(this, "resources/white-knight.png");
			} else if(this.getType() == Type.QUEEN) {
				map.put(this, "resources/white-queen.png");
			} else if(this.getType() == Type.ROOK) {
				map.put(this, "resources/white-rook.png");
			} else if(this.getType() == Type.KING) {
				map.put(this, "resources/white-king.png");
			}
		} else {
			if(this.getType() == Type.PAWN) {
				map.put(this, "resources/black-pawn.png");
			} else if(this.getType() == Type.BISHOP) {
				map.put(this, "resources/black-bishop.png");
			} else if(this.getType() == Type.KNIGHT) {
				map.put(this, "resources/black-knight.png");
			} else if(this.getType() == Type.QUEEN) {
				map.put(this, "resources/black-queen.png");
			} else if(this.getType() == Type.ROOK) {
				map.put(this, "resources/black-rook.png");
			} else if(this.getType() == Type.KING) {
				map.put(this, "resources/black-king.png");
			}
		}
	}
	
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
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

	public void setType(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
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
	
	public String getImage() {
		return map.get(this);
	}
}
