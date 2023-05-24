package chessgame.chess.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import Engine.Engine;
import Engine.Minimax;
import chessgame.chess.board.Board;
import chessgame.chess.manager.GameManager;
import chessgame.chess.manager.Move;
import chessgame.chess.manager.MoveWriter;
import chessgame.chess.piece.Piece;
import chessgame.chess.piece.Type;
import chessgame.chess.player.Winner;

/**
 * ChessGUI is the Graphical User Interface for the Chess Game. This class 
 * is the View part of the Model View Controller Design Pattern (MVC). It 
 * contains functionality to create a playable Chess Board and allow users to 
 * interact with it against a computer player. It has a move log, displays 
 * captured pieces, allows players to save their game to a file, load a game from 
 * file, and flip the board orientation. 
 * @author maxharsh
 *
 */
public class ChessGUI {
 
    private static final long serialVersionUID = 1L;
    private GameManager gm;
    private MoveLog moveLog;
    private Board board;
    private JFrame frame;
    private GameHistoryPanel gameHistoryPanel;
    private TakenPiecesPanel takenPiecesPanel;
    private BoardPanel boardPanel;
    private MoveWriter moveWriter;
    private Piece piece;
    private Piece capturedPiece;
    private Winner winner;
    private boolean computerDoneWithMove;
    private Minimax minimax;
    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 600;
    private static final int ROW = 8;
    private static final int COL = 8;
    
    private static final Dimension FRAME_DIMENSION = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    private Point initialClick;
    private Point finalClick;
    private BoardDirection boardDirection;
    private boolean highlightLegalMoves;
    private boolean humanMoveDone;
    private ArrayList<Integer[]> listAllValidMoves;
    
    /**
     * Constructs a ChessGUI object. 
     */
    public ChessGUI() {
        this.frame = new JFrame("Chess") ;
        this.frame.setLayout(new BorderLayout());
        JMenuBar tableMenuBar = createTableMenuBar();
        this.gm = new GameManager(new Board(), 0);
        this.board = gm.getBoard();
        this.moveLog = new MoveLog();
        this.boardPanel = new BoardPanel();
        this.winner = Winner.NONE;
        this.minimax = new Minimax(this.gm);
        this.moveWriter = new MoveWriter();
        this.gameHistoryPanel = new GameHistoryPanel();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.boardPanel.setLocation(0, 0);
        this.frame.setJMenuBar(tableMenuBar);
        this.listAllValidMoves = null;
        this.highlightLegalMoves = false;
        this.boardDirection = BoardDirection.NORMAL;
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(FRAME_DIMENSION);
        this.frame.add(this.boardPanel, BorderLayout.CENTER);
        this.frame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.frame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.frame.pack();
        this.frame.setVisible(true);
        
    }
   
    /**
     * 
     * @return
     */
    private JMenuBar createTableMenuBar() {
    	JMenuBar tableMenuBar = new JMenuBar();
    	tableMenuBar.add(createFileMenu());
    	tableMenuBar.add(createPreferencesMenu());
    	return tableMenuBar;
    }
    
	public void executeAIMove() {
    	ArrayList<Piece> whiteCaptured = new ArrayList<Piece>();
    	ArrayList<Piece> blackCaptured = new ArrayList<Piece>();
    	
    	for(int i = 0; i < gm.getPlayers()[0].getCapturedPieces().size(); i++) {
    		whiteCaptured.add(gm.getPlayers()[0].getCapturedPieces().get(i));
    	}
    	for(int i = 0; i < gm.getPlayers()[1].getCapturedPieces().size(); i++) {
    		blackCaptured.add(gm.getPlayers()[1].getCapturedPieces().get(i));
    	}
    	
		Move move = minimax.getBestMove(2, board);
		int[] mv = move.getMove();
		
		piece = board.getPieceFromBoard(mv[0], mv[1]);
		capturedPiece = board.getPieceFromBoard(mv[2], mv[3]);
		
		gm.getPlayers()[0].getCapturedPieces().clear();
    	gm.getPlayers()[1].getCapturedPieces().clear();
    	    	
    	for(int i = 0; i < whiteCaptured.size(); i++) {
    		gm.getPlayers()[0].getCapturedPieces().add(whiteCaptured.get(i));
    	}
    	for(int i = 0; i < blackCaptured.size(); i++) {
    		gm.getPlayers()[1].getCapturedPieces().add(blackCaptured.get(i));
    	}
		
    	this.gm.makeMove(mv[0], mv[1], mv[2], mv[3], board, board.getPieceFromBoard(mv[0], mv[1]));
    	
    	String moveText = moveWriter.moveWrite(mv[0], mv[1], mv[2], mv[3], piece, capturedPiece, gm);
		
		moveLog.addMove(moveText);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				highlightLegalMoves = false;
				gameHistoryPanel.redraw(moveLog);
				takenPiecesPanel.redraw(moveLog.getMoveLog(), gm.getPlayers()[0].getCapturedPieces(), gm.getPlayers()[1].getCapturedPieces());
				boardPanel.drawBoard();
			}
		});
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// skip
		}
	}
    
    /**
     * Handles the case of a player winning. 
     */
    public void endGameScenario() {
    	// handle case of checkmate after a move is made
		if(gm.isCheckmate(board)) {
			System.out.println("Checkmate!");
			if(piece.getColor() == chessgame.chess.piece.Color.WHITE) {
				winner = Winner.WHITE;
			} else {
				winner = Winner.BLACK;
			}
		} else if(gm.isStalemate(board)) {
			winner = Winner.DRAW;
		}
    }
    
    /**
     * 
     * @return
     */
    private JMenu createFileMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem openPGN = new JMenuItem("Load PGN File");
		openPGN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenuItem saveGameToFile = new JMenuItem("Save Game to File");
		saveGameToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		fileMenu.add(openPGN);
		fileMenu.add(exitMenuItem);
		fileMenu.add(saveGameToFile);
		 
		return fileMenu;
	}
    
    /**
     * 
     * @return
     */
    public JMenu createPreferencesMenu() {
    	JMenu preferencesMenu = new JMenu("Preferences");
    	JMenuItem flipBoardItem = new JMenuItem("Flip Board");
    	flipBoardItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boardDirection = boardDirection.opposite();
				boardPanel.drawBoard();
			}
    	});
    	preferencesMenu.add(flipBoardItem);
    	preferencesMenu.addSeparator();
    	
    	JCheckBoxMenuItem legalMoveHighlighter = new JCheckBoxMenuItem("Highlight Legal Moves", false);
    	legalMoveHighlighter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				highlightLegalMoves = legalMoveHighlighter.isSelected();
			}
    	});
    	
    	preferencesMenu.add(legalMoveHighlighter);
    	
    	return preferencesMenu;
    }
    
    // If not working could be bc the class needs to be static
    private class AIMove extends SwingWorker<Move, String> {

		@Override
		protected Move doInBackground() throws Exception {
			Minimax miniMax = new Minimax(gm);
			Move bestMove = minimax.getBestMove(2, board);
			return bestMove;
		}    
		
		@Override 
		public void done() {
			try {
				Move bestMove = get();
				highlightLegalMoves = false;
				gameHistoryPanel.redraw(moveLog);
				takenPiecesPanel.redraw(moveLog.getMoveLog(), gm.getPlayers()[0].getCapturedPieces(), gm.getPlayers()[1].getCapturedPieces());
				boardPanel.drawBoard();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    }
    
    public static class MoveLog {
    	private ArrayList<String> moveLog;
    	
    	public MoveLog() {
    		this.moveLog = new ArrayList<String>();
    	}
    	
    	public ArrayList<String> getMoveLog() {
    		return this.moveLog;
    	}
    	
    	public void addMove(String move) {
    		this.moveLog.add(move);
    	}
    }
    
    /*
    private JMenu createOptionsMenu() {
    	JMenu optionsMenu = new JMenu("Options");
    	
    	JMenuItem setupGameMenuItem = new JMenuItem("Setup Game");
    	setupGameMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ChessGUI.get().getGameSetup.promptUser();
				ChessGUI.get().setupUpdate(ChessGUI.get().getGameSetup());
				
			}
    	});
    	optionsMenu.add(setupGameMenuItem);
    	return optionsMenu;
    }
    */
    
    /**
     * 
     * @author maxharsh
     *
     */
    public enum BoardDirection {
    	
    	NORMAL {
    		ArrayList<TilePanel> traverse(ArrayList<TilePanel> boardTiles) {
    			return boardTiles;
    		}
    		BoardDirection opposite() {
    			return FLIPPED;
    		}
    	}, FLIPPED {
    		ArrayList<TilePanel> traverse(ArrayList<TilePanel> boardTiles) {
    			Collections.reverse(boardTiles);
    			return boardTiles;
    		}
    		BoardDirection opposite() {
    			return NORMAL;
    		}
    	};
    	
    	abstract ArrayList<TilePanel> traverse(ArrayList<TilePanel> boardTiles);
    	abstract BoardDirection opposite();
    }
    
    /**
     * 
     * @author maxharsh
     *
     */
    private class BoardPanel extends JPanel {
    	ArrayList<TilePanel> boardTiles;
    	
    	public BoardPanel() {
    		super(new GridLayout(8, 8));
    		this.boardTiles = new ArrayList<TilePanel>();
    		for(int i = 0; i < 8; i++) {
    			for(int j = 0; j < 8; j++) {
    				TilePanel tilePanel = new TilePanel(this, i, j);
    				this.boardTiles.add(tilePanel);
    				add(tilePanel);
    			}
    		}
    		validate();
    	}
    	
    	public void drawBoard() {
    		removeAll();
    		for(TilePanel tilePanel : boardDirection.traverse(boardTiles)) {
    			tilePanel.drawTile();
    			add(tilePanel);
    		}
    		validate();
    		repaint();
    	}
    }
    
    /**
     * 
     * @author maxharsh
     *
     */
    private class TilePanel extends JPanel {
    	private int tileId;
    	private int row;
    	private int col;
    	
    	public TilePanel(BoardPanel boardPanel, int row, int col) {
    		super(new GridBagLayout());
    		this.tileId = row + col;
    		this.row = row;
    		this.col = col;
    		setPreferredSize(new Dimension(FRAME_WIDTH/ROW, FRAME_HEIGHT/COL));
    		assignTileColor();
    		setBorder(BorderFactory.createLineBorder(Color.black));
    		assignImage();
    		
    		addMouseListener(new MouseListener() {
    			
				@Override
				public void mouseReleased(MouseEvent e) {
					
					// cancels piece selection
					if(SwingUtilities.isRightMouseButton(e)) {
						initialClick = null;
						finalClick = null;
						
						// ensure when a player cancels clicking on a piece all valid moves that were highlighted are cleared
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								highlightLegalMoves = false;
								boardPanel.drawBoard();
							}
						});
					} 
					// allows player to move piece 
					else if(SwingUtilities.isLeftMouseButton(e)) {
						if(initialClick == null) {
							initialClick = new Point(row, col);

							listAllValidMoves = gm.getListAllValidMoves(board, board.getPieceFromBoard(row, col));
							
							// to draw out the legal moves on the board
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									highlightLegalMoves = true;
									boardPanel.drawBoard();
								}
							});
							
						} else {
							finalClick = new Point(row, col);
							// after a valid move, reset the final and initial clicks
							if(gm.canMove((int)initialClick.getX(), (int)initialClick.getY(), (int)finalClick.getX(), (int)finalClick.getY(), board)) {
								
								// set piece and captured piece
								piece = board.getPieceFromBoard((int)initialClick.getX(), (int)initialClick.getY());
								capturedPiece = board.getPieceFromBoard((int)finalClick.getX(), (int)finalClick.getY());
								
								gm.makeMove((int)initialClick.getX(), (int)initialClick.getY(), row, col, board, 
										board.getPieceFromBoard((int)initialClick.getX(), (int)initialClick.getY()));
								
								// add move to move log
								String moveText = moveWriter.moveWrite((int)initialClick.getX(), (int)initialClick.getY(), 
										(int)finalClick.getX(), (int)finalClick.getY(), piece, capturedPiece, gm);
								
								moveLog.addMove(moveText);
								endGameScenario();
								
								initialClick = null;
								finalClick = null;
								
								SwingUtilities.invokeLater(new Runnable() {
									@Override 
									public void run() {
										highlightLegalMoves = false;
										gameHistoryPanel.redraw(moveLog);
										takenPiecesPanel.redraw(moveLog.getMoveLog(), gm.getPlayers()[0].getCapturedPieces(), gm.getPlayers()[1].getCapturedPieces());
										boardPanel.drawBoard();
										humanMoveDone = true;
									}
								});
								
								if(gm.getCurrentPlayer().getColor() == chessgame.chess.piece.Color.BLACK) {
									executeAIMove();
									endGameScenario();
									humanMoveDone = false;
								}
								
							} else {
								// make new pop-up window detailing error
								initialClick = null;
								finalClick = null;
								JOptionPane.showMessageDialog(frame, "Illegal move. Try again.",
							               "Move Detector", JOptionPane.ERROR_MESSAGE);
								throw new IllegalArgumentException("Invalid move. Try again.");
							}
						}
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
    		});
    		
    		validate();
    	}

    	public void drawTile() {
    		removeAll();
    		assignTileColor();
    		assignImage();
    		highlightLegalMoveTiles();
    		highlightKingInCheck();
    		validate();
    		repaint();
    	}
    	
		private void assignTileColor() {
			boolean isLight = ((this.tileId) % 2) == 0;
			setBackground(isLight ?  new Color(64, 47, 29) : new Color(210, 180, 140));
		}
		
		private void highlightLegalMoveTiles() {
			if(listAllValidMoves != null && highlightLegalMoves) {
				for(int i = 0; i < listAllValidMoves.size(); i++) {
					if(listAllValidMoves.get(i)[0] == row && listAllValidMoves.get(i)[1] == col) {
						try {
							setBackground(new Color(144, 238, 144));
						} catch(Exception e) {
							// skip
						}
					}
				}
			}
		}
		
		private void highlightKingInCheck() {
			if(board.getPieceFromBoard(row, col) != null && board.getPieceFromBoard(row, col).getType() == Type.KING && 
					board.getPieceFromBoard(row, col).getColor() == gm.getCurrentPlayer().getColor() && gm.isCheck(board)) {
				setBackground(new Color(255,0,0));
			}
		}
		
		private void assignImage() { 
			if(board.getPieceFromBoard(row, col) != null) {
				try {
					BufferedImage image = ImageIO.read(new File(board.getPieceFromBoard(row, col).getImage()));
					add(new JLabel(new ImageIcon(image.getScaledInstance(75, 75, Image.SCALE_DEFAULT)), JLabel.CENTER));
				} catch (IOException e) {
					// skip, this exception should never occur
				}
			}
		}
    }

	public static void main(String[] args) {
        new ChessGUI();
    }
}
