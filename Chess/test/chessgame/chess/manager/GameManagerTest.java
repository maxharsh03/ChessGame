package chessgame.chess.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
		GameManager gm = new GameManager();
		assertEquals(null, gm.getLastPieceMoved());
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
	void testMakeMove() {
		GameManager gm = new GameManager();
		/**
		 * test you can't make a move out of bounds, to the same square, try to move a piece that doesn't exist,
		 * or try to move opponent's piece
		 */
		assertFalse(gm.makeMove(-2, 0, 0, 0));
		assertFalse(gm.makeMove(4, -3, 0, 0));
		assertFalse(gm.makeMove(4, 3, 8, 0));
		assertFalse(gm.makeMove(4, 4, 5, 12));
		assertFalse(gm.makeMove(4, 4, 4, 4));
		assertFalse(gm.makeMove(3, 3, 0, 0));
		assertFalse(gm.makeMove(1, 4, 2, 4));
		
		//test pawn moves and captures
		assertTrue(gm.makeMove(6, 3, 4, 3));
		assertEquals(Type.PAWN, gm.getBoard().getPieceFromBoard(4, 3).getType());
		assertEquals(Color.WHITE, gm.getBoard().getPieceFromBoard(4, 3).getColor());
		assertEquals("Player 2", gm.getCurrentPlayer().getName());
		assertEquals(Color.BLACK, gm.getCurrentPlayer().getColor());
		assertEquals(null, gm.getBoard().getPieceFromBoard(2, 3));
		assertEquals(null, gm.getBoard().getPieceFromBoard(3, 3));
		assertTrue(gm.makeMove(1, 3, 3, 3));
		assertEquals(Type.PAWN, gm.getBoard().getPieceFromBoard(3, 3).getType());
		assertEquals(Color.BLACK, gm.getBoard().getPieceFromBoard(3, 3).getColor());
		assertTrue(gm.makeMove(6, 4, 4, 4));
		assertTrue(gm.makeMove(3, 3, 4, 4));
		assertEquals(1, gm.getPlayers()[1].getCapturedPieces().size());
		
		// test en passant
		//assertTrue(gm.makeMove(4, 3, 3, 3));
		//assertTrue(gm.makeMove(1, 2, 3, 2));
		//assertTrue(gm.makeMove(3, 3, 2, 2));
		
		// test bishop move
		assertFalse(gm.makeMove(7, 5, 5, 2));
		assertTrue(gm.makeMove(7, 5, 5, 3));
		
		// test knight move
		assertFalse(gm.makeMove(0, 1, 3, 0));
		assertTrue(gm.makeMove(0, 1, 2, 0));
		assertFalse(gm.makeMove(7, 6, 5, 6));
		assertTrue(gm.makeMove(7, 6, 5, 5));
		
		// test queen move
		assertFalse(gm.makeMove(0, 3, 5, 6));
		assertTrue(gm.makeMove(0, 3, 2, 3));
		
		// test white castling right move
		assertTrue(gm.makeMove(7, 4, 7, 6));
		assertEquals(Type.ROOK, gm.getBoard().getPieceFromBoard(7, 5).getType());
		assertEquals(Type.KING, gm.getBoard().getPieceFromBoard(7, 6).getType());
		
		// test check on opponent
		assertTrue(gm.makeMove(5, 3, 3, 1));
		assertTrue(gm.isCheck());
		assertFalse(gm.makeMove(1, 6, 2, 6));
		assertTrue(gm.makeMove(1, 2, 2, 2));
		
	}

	@Test
	void testHandleCapture() {
		fail("Not yet implemented");
	}

	@Test
	void testRightCastle() {
		fail("Not yet implemented");
	}

	@Test
	void testLeftCastle() {
		fail("Not yet implemented");
	}

	@Test
	void testMoveHelper() {
		fail("Not yet implemented");
	}

	@Test
	void testIsCheck() {
		fail("Not yet implemented");
	}

	@Test
	void testIsCheckmate() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateScore() {
		fail("Not yet implemented");
	}

	@Test
	void testSetLastPieceMoved() {
		fail("Not yet implemented");
	}

	@Test
	void testIsKingInCheck() {
		fail("Not yet implemented");
	}

}
