package chessgame.chess.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Engine.Minimax;
import chessgame.chess.board.Board;
import chessgame.chess.piece.Color;
import chessgame.chess.piece.Piece;
import chessgame.chess.piece.Type;

class GameManagerTest {

	@BeforeEach
	void setUp() {
		
	}
	
	@Test
	void testGameManager() {
		GameManager gm = new GameManager(new Board(), 0);
		Board b = gm.getBoard();
		Piece[][] p = gm.getBoard().getBoard();
		assertEquals(8, p.length);
		assertEquals(8, p[0].length);
		
		// test black pieces are loaded on board correctly
		assertEquals(Type.ROOK, b.getPieceFromBoard(0, 0).getType());
		assertEquals(Color.BLACK, b.getPieceFromBoard(0, 0).getColor());
		assertEquals(Type.KNIGHT, b.getPieceFromBoard(0, 1).getType());
		assertEquals(Color.BLACK, b.getPieceFromBoard(0, 1).getColor());
		assertEquals(Type.BISHOP, b.getPieceFromBoard(0, 2).getType());
		assertEquals(Color.BLACK, b.getPieceFromBoard(0, 2).getColor());
		assertEquals(Type.QUEEN, b.getPieceFromBoard(0, 3).getType());
		assertEquals(Color.BLACK, b.getPieceFromBoard(0, 3).getColor());
		assertEquals(Type.KING, b.getPieceFromBoard(0, 4).getType());
		assertEquals(Color.BLACK, b.getPieceFromBoard(0, 4).getColor());
		assertEquals(Type.BISHOP, b.getPieceFromBoard(0, 5).getType());
		assertEquals(Color.BLACK, b.getPieceFromBoard(0, 5).getColor());
		assertEquals(Type.KNIGHT, b.getPieceFromBoard(0, 6).getType());
		assertEquals(Color.BLACK, b.getPieceFromBoard(0, 6).getColor());
		assertEquals(Type.ROOK, b.getPieceFromBoard(0, 7).getType());
		assertEquals(Color.BLACK, b.getPieceFromBoard(0, 7).getColor());
		
		assertEquals(Type.PAWN, b.getPieceFromBoard(1, 0).getType());
		assertEquals(Color.BLACK, b.getPieceFromBoard(1, 0).getColor());
		assertEquals(Type.PAWN, b.getPieceFromBoard(1, 1).getType());
		assertEquals(Color.BLACK, b.getPieceFromBoard(1, 1).getColor());
		assertEquals(Type.PAWN, b.getPieceFromBoard(1, 2).getType());
		assertEquals(Color.BLACK, b.getPieceFromBoard(1, 2).getColor());
		assertEquals(Type.PAWN, b.getPieceFromBoard(1, 3).getType());
		assertEquals(Color.BLACK, b.getPieceFromBoard(1, 3).getColor());
		assertEquals(Type.PAWN, b.getPieceFromBoard(1, 4).getType());
		assertEquals(Color.BLACK, b.getPieceFromBoard(1, 4).getColor());
		assertEquals(Type.PAWN, b.getPieceFromBoard(1, 5).getType());
		assertEquals(Color.BLACK, b.getPieceFromBoard(1, 5).getColor());
		assertEquals(Type.PAWN, b.getPieceFromBoard(1, 6).getType());
		assertEquals(Color.BLACK, b.getPieceFromBoard(1, 6).getColor());
		assertEquals(Type.PAWN, b.getPieceFromBoard(1, 7).getType());
		assertEquals(Color.BLACK, b.getPieceFromBoard(1, 7).getColor());
		
		// test white pieces are loaded on board correctly
		assertEquals(Type.ROOK, b.getPieceFromBoard(7, 0).getType());
		assertEquals(Color.WHITE, b.getPieceFromBoard(7, 0).getColor());
		assertEquals(Type.KNIGHT, b.getPieceFromBoard(7, 1).getType());
		assertEquals(Color.WHITE, b.getPieceFromBoard(7, 1).getColor());
		assertEquals(Type.BISHOP, b.getPieceFromBoard(7, 2).getType());
		assertEquals(Color.WHITE, b.getPieceFromBoard(7, 2).getColor());
		assertEquals(Type.QUEEN, b.getPieceFromBoard(7, 3).getType());
		assertEquals(Color.WHITE, b.getPieceFromBoard(7, 3).getColor());
		assertEquals(Type.KING, b.getPieceFromBoard(7, 4).getType());
		assertEquals(Color.WHITE, b.getPieceFromBoard(7, 4).getColor());
		assertEquals(Type.BISHOP, b.getPieceFromBoard(7, 5).getType());
		assertEquals(Color.WHITE, b.getPieceFromBoard(7, 5).getColor());
		assertEquals(Type.KNIGHT, b.getPieceFromBoard(7, 6).getType());
		assertEquals(Color.WHITE, b.getPieceFromBoard(7, 6).getColor());
		assertEquals(Type.ROOK, b.getPieceFromBoard(7, 7).getType());
		assertEquals(Color.WHITE, b.getPieceFromBoard(7, 7).getColor());
		
		assertEquals(Type.PAWN, b.getPieceFromBoard(6, 0).getType());
		assertEquals(Color.WHITE, b.getPieceFromBoard(6, 0).getColor());
		assertEquals(Type.PAWN, b.getPieceFromBoard(6, 1).getType());
		assertEquals(Color.WHITE, b.getPieceFromBoard(6, 1).getColor());
		assertEquals(Type.PAWN, b.getPieceFromBoard(6, 2).getType());
		assertEquals(Color.WHITE, b.getPieceFromBoard(6, 2).getColor());
		assertEquals(Type.PAWN, b.getPieceFromBoard(6, 3).getType());
		assertEquals(Color.WHITE, b.getPieceFromBoard(6, 3).getColor());
		assertEquals(Type.PAWN, b.getPieceFromBoard(6, 4).getType());
		assertEquals(Color.WHITE, b.getPieceFromBoard(6, 4).getColor());
		assertEquals(Type.PAWN, b.getPieceFromBoard(6, 5).getType());
		assertEquals(Color.WHITE, b.getPieceFromBoard(6, 5).getColor());
		assertEquals(Type.PAWN, b.getPieceFromBoard(6, 6).getType());
		assertEquals(Color.WHITE, b.getPieceFromBoard(6, 6).getColor());
		assertEquals(Type.PAWN, b.getPieceFromBoard(6, 7).getType());
		assertEquals(Color.WHITE, b.getPieceFromBoard(6, 7).getColor());
		
		// test players are set up correctly and they can be switched
		assertEquals(Color.WHITE, gm.getCurrentPlayer().getColor());
		assertEquals("Player 1", gm.getCurrentPlayer().getName());
		assertEquals(16, gm.getCurrentPlayer().getPieces().size());
		assertEquals(0, gm.getCurrentPlayer().getCapturedPieces().size());
		gm.switchPlayer();
		assertEquals(Color.BLACK, gm.getCurrentPlayer().getColor());
		assertEquals("Player 2", gm.getCurrentPlayer().getName());
		assertEquals(16, gm.getCurrentPlayer().getPieces().size());
		assertEquals(0, gm.getCurrentPlayer().getCapturedPieces().size());
	}
	
	@Test
	void testCreateNewGame() {
		fail("Not yet implemented");
	}

	@Test
	void testPawnMove() {
		GameManager gm = new GameManager(new Board(), 0);
		
		// test all white pawns can move 2 squares for first move
		assertTrue(gm.canMove(6, 0, 4, 0, gm.getBoard()));
		assertTrue(gm.canMove(6, 1, 4, 1, gm.getBoard()));
		assertTrue(gm.canMove(6, 2, 4, 2, gm.getBoard()));
		assertTrue(gm.canMove(6, 3, 4, 3, gm.getBoard()));
		assertTrue(gm.canMove(6, 4, 4, 4, gm.getBoard()));
		assertTrue(gm.canMove(6, 5, 4, 5, gm.getBoard()));
		assertTrue(gm.canMove(6, 6, 4, 6, gm.getBoard()));
		assertTrue(gm.canMove(6, 7, 4, 7, gm.getBoard()));
		
		// test all white pawns can move 1 square for first move
		assertTrue(gm.canMove(6, 0, 5, 0, gm.getBoard()));
		assertTrue(gm.canMove(6, 1, 5, 1, gm.getBoard()));
		assertTrue(gm.canMove(6, 2, 5, 2, gm.getBoard()));
		assertTrue(gm.canMove(6, 3, 5, 3, gm.getBoard()));
		assertTrue(gm.canMove(6, 4, 5, 4, gm.getBoard()));
		assertTrue(gm.canMove(6, 5, 5, 5, gm.getBoard()));
		assertTrue(gm.canMove(6, 6, 5, 6, gm.getBoard()));
		assertTrue(gm.canMove(6, 7, 5, 7, gm.getBoard()));
		
		// move random white pawn 2 moves
		gm.makeMove(6, 4, 4, 4, gm.getBoard(), gm.getBoard().getPieceFromBoard(6, 4));
		assertEquals(Type.PAWN, gm.getBoard().getPieceFromBoard(4, 4).getType());
		assertEquals(Color.WHITE, gm.getBoard().getPieceFromBoard(4, 4).getColor());
		
		// test all black pawns can move 2 squares for first move
		assertTrue(gm.canMove(1, 0, 3, 0, gm.getBoard()));
		assertTrue(gm.canMove(1, 1, 3, 1, gm.getBoard()));
		assertTrue(gm.canMove(1, 2, 3, 2, gm.getBoard()));
		assertTrue(gm.canMove(1, 3, 3, 3, gm.getBoard()));
		assertTrue(gm.canMove(1, 4, 3, 4, gm.getBoard()));
		assertTrue(gm.canMove(1, 5, 3, 5, gm.getBoard()));
		assertTrue(gm.canMove(1, 6, 3, 6, gm.getBoard()));
		assertTrue(gm.canMove(1, 7, 3, 7, gm.getBoard()));
		
		// test all black pawns can move 1 square for first move
		assertTrue(gm.canMove(1, 0, 2, 0, gm.getBoard()));
		assertTrue(gm.canMove(1, 1, 2, 1, gm.getBoard()));
		assertTrue(gm.canMove(1, 2, 2, 2, gm.getBoard()));
		assertTrue(gm.canMove(1, 3, 2, 3, gm.getBoard()));
		assertTrue(gm.canMove(1, 4, 2, 4, gm.getBoard()));
		assertTrue(gm.canMove(1, 5, 2, 5, gm.getBoard()));
		assertTrue(gm.canMove(1, 6, 2, 6, gm.getBoard()));
		assertTrue(gm.canMove(1, 7, 2, 7, gm.getBoard()));
		
		// move random white pawn 2 moves
		gm.makeMove(1, 4, 3, 4, gm.getBoard(), gm.getBoard().getPieceFromBoard(1, 4));
		assertEquals(Type.PAWN, gm.getBoard().getPieceFromBoard(3, 4).getType());
		assertEquals(Color.BLACK, gm.getBoard().getPieceFromBoard(3, 4).getColor());
	}
	
	@Test 
	void testEnPassant() {
		GameManager gm = new GameManager(new Board(), 0);
		
		gm.makeMove(6, 4, 4, 4, gm.getBoard(), gm.getBoard().getPieceFromBoard(6, 4));
		gm.makeMove(1, 0, 2, 0, gm.getBoard(), gm.getBoard().getPieceFromBoard(1, 0));
		gm.makeMove(4, 4, 3, 4, gm.getBoard(), gm.getBoard().getPieceFromBoard(4, 4));
		gm.makeMove(1, 3, 3, 3, gm.getBoard(), gm.getBoard().getPieceFromBoard(1, 3));
		assertTrue(gm.canMove(3, 4, 2, 3, gm.getBoard()));
		assertTrue(gm.canMove(3, 4, 2, 4, gm.getBoard()));
		
	}
 
	private void printBoard(GameManager gm) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				Piece piece = gm.getBoard().getPieceFromBoard(i, j);

				String s = "";
				
				if(piece == null) {
					s = "-";
				}
				else if(piece.getType() == Type.PAWN) {
					s = "p";
				} else if(piece.getType() == Type.ROOK) {
					s = "r";
				} else if(piece.getType() == Type.KNIGHT) {
					s = "n";
				} else if(piece.getType() == Type.BISHOP) {
					s = "b";
				} else if(piece.getType() == Type.KING) {
					s = "k";
				} else if(piece.getType() == Type.QUEEN) {
					s = "q";
				}
				System.out.print(s + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	@Test
	void testPin() {
		GameManager gm = new GameManager(new Board(), 0);
		gm.makeMove(6, 4, 4, 4, gm.getBoard(), gm.getBoard().getPieceFromBoard(6, 4));
		gm.makeMove(1, 4, 3, 4, gm.getBoard(), gm.getBoard().getPieceFromBoard(1, 4));
		gm.makeMove(7, 5, 3, 1, gm.getBoard(), gm.getBoard().getPieceFromBoard(7, 5));
		assertFalse(gm.canMove(1, 3, 2, 3, gm.getBoard()));
		assertFalse(gm.canMove(1, 3, 3, 3, gm.getBoard()));

	}

	@Test
	void testLeftCastle() {
		GameManager gm = new GameManager(new Board(), 0);
		
		gm.makeMove(6, 3, 5, 3, gm.getBoard(), gm.getBoard().getPieceFromBoard(6, 3));
		gm.makeMove(1, 3, 2, 3, gm.getBoard(), gm.getBoard().getPieceFromBoard(1, 3));
		gm.makeMove(7, 2, 5, 4, gm.getBoard(), gm.getBoard().getPieceFromBoard(7, 2));
		gm.makeMove(0, 2, 2, 4, gm.getBoard(), gm.getBoard().getPieceFromBoard(0, 2));
		gm.makeMove(7, 1, 5, 2, gm.getBoard(), gm.getBoard().getPieceFromBoard(7, 1));
		gm.makeMove(0, 1, 2, 2, gm.getBoard(), gm.getBoard().getPieceFromBoard(0, 1));
		gm.makeMove(7, 3, 6, 3, gm.getBoard(), gm.getBoard().getPieceFromBoard(7, 3));
		gm.makeMove(0, 3, 1, 3, gm.getBoard(), gm.getBoard().getPieceFromBoard(0, 3));
		
		assertFalse(gm.canMove(7, 4, 7, 1, gm.getBoard()));
	}

	@Test
	void testIsCheckmate() {
		GameManager gm = new GameManager(new Board(), 0);
		
		gm.makeMove(6, 5, 5, 5, gm.getBoard(), gm.getBoard().getPieceFromBoard(6, 5));
		gm.makeMove(1, 4, 3, 4, gm.getBoard(), gm.getBoard().getPieceFromBoard(1, 4));
		gm.makeMove(7, 1, 5, 0, gm.getBoard(), gm.getBoard().getPieceFromBoard(7, 1));
		gm.makeMove(0, 5, 1, 4, gm.getBoard(), gm.getBoard().getPieceFromBoard(0, 5));
		gm.makeMove(6, 7, 4, 7, gm.getBoard(), gm.getBoard().getPieceFromBoard(6, 7));
		gm.makeMove(1, 4, 4, 7, gm.getBoard(), gm.getBoard().getPieceFromBoard(1, 4));
		
		assertFalse(gm.isCheckmate(gm.getBoard()));
		
		gm.makeMove(6, 6, 5, 6, gm.getBoard(), gm.getBoard().getPieceFromBoard(6, 6));
		gm.makeMove(4, 7, 5, 6, gm.getBoard(), gm.getBoard().getPieceFromBoard(4, 7));
		
		assertTrue(gm.isCheckmate(gm.getBoard()));
	}

	@Test 
	void moveWriter() {
		GameManager gm = new GameManager(new Board(), 0);
		MoveWriter mv = new MoveWriter();
		gm.makeMove(6, 4, 4, 4, gm.getBoard(), gm.getBoard().getPieceFromBoard(6, 4));
		
		String move = mv.moveWrite(6, 4, 4, 4, gm.getBoard().getPieceFromBoard(4, 4), null, gm);
		System.out.println(move);
	}

	@Test 
	void allLegalMoves() {
		GameManager gm = new GameManager(new Board(), 0);
		
	}
	
	@Test 
	void movingMinimax() {
		GameManager gm = new GameManager(new Board(), 0);
		Minimax mm = new Minimax(gm);
		
		gm.makeMove(6, 4, 4, 4, gm.getBoard(), gm.getBoard().getPieceFromBoard(6, 4));
		Move move = mm.getBestMove(3, gm.getBoard());
		int[] mv = move.getMove();
		gm.makeMove(mv[0], mv[1], mv[2], mv[3], gm.getBoard(), gm.getBoard().getPieceFromBoard(mv[0], mv[1]));
		printBoard(gm);
		
		//assertEquals(gm.getBoard())
	}
	
	@Test 
	void minimax() {
		GameManager gm = new GameManager(new Board(), 0);
		Minimax mm = new Minimax(gm);
		
		Move move = mm.getBestMove(3, gm.getBoard());
		int[] mv = move.getMove();
	}
	
	@Test 
	void testFoolsmate() {
		GameManager gm = new GameManager(new Board(), 0);
		Minimax mm = new Minimax(gm);
		
		gm.makeMove(6, 5, 5, 5, gm.getBoard(), gm.getBoard().getPieceFromBoard(6, 5));
		gm.makeMove(1, 4, 2, 4, gm.getBoard(), gm.getBoard().getPieceFromBoard(1, 4));
		gm.makeMove(6, 6, 4, 6, gm.getBoard(), gm.getBoard().getPieceFromBoard(1, 6));
		
		Move move = mm.getBestMove(3, gm.getBoard());
		int[] mv = move.getMove();
		System.out.println(mv[0] + "," + mv[1] + "," + mv[2] + "," + mv[3]);		
	}
}
