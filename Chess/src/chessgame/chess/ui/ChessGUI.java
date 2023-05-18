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
import javax.imageio.ImageIO;
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
import chessgame.chess.board.Board;
import chessgame.chess.manager.GameManager;

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
    private GameManager gm = new GameManager();
    private Board board = gm.getBoard();
    private JFrame frame;
    private BoardPanel boardPanel;
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 600;
    private static final int ROW = 8;
    private static final int COL = 8;
    
    private static final Dimension FRAME_DIMENSION = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    
    private Point initialClick;
    private Point finalClick;
    private BoardDirection boardDirection;
    private boolean highlightLegalMoves;
    private ArrayList<Integer[]> listAllValidMoves;

    /**
     * 
     */
    public ChessGUI() {
        this.frame = new JFrame("Chess") ;
        this.frame.setLayout(new BorderLayout());
        JMenuBar tableMenuBar = createTableMenuBar();
        this.boardPanel = new BoardPanel();
        this.boardPanel.setLocation(0, 0);
        this.frame.setJMenuBar(tableMenuBar);
        this.listAllValidMoves = null;
        this.highlightLegalMoves = false;
        this.boardDirection = BoardDirection.NORMAL;
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(FRAME_DIMENSION);
        this.frame.add(this.boardPanel, BorderLayout.CENTER);
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

    /**
     * 
     * @return
     */
    private JMenu createFileMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem openPGN = new JMenuItem("Load PGN File");
		openPGN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Bjdsfj");
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
				System.out.println("Save");
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
							
							if(board.getPieceFromBoard(row, col) != null) {
								for(int i = 0; i < listAllValidMoves.size(); i++) {
									System.out.println(Arrays.toString(listAllValidMoves.get(i)));
								}
							}
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
							if(gm.canMove((int)initialClick.getX(), (int)initialClick.getY(), (int)finalClick.getX(), (int)finalClick.getY())) {
								// add most recent move to move log 
								gm.makeMove((int)initialClick.getX(), (int)initialClick.getY(), row, col, board, 
										board.getPieceFromBoard((int)initialClick.getX(), (int)initialClick.getY()));
								initialClick = null;
								finalClick = null;
								
								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										highlightLegalMoves = false;
										boardPanel.drawBoard();
									}
								});
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
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
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
    		});
    		
    		validate();
    	}

    	public void drawTile() {
    		removeAll();
    		assignTileColor();
    		assignImage();
    		highlightLegalMoveTiles();
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
							add(new JLabel(new ImageIcon(ImageIO.read(new File("resources/green_dot.png")))));
						} catch(Exception e) {
							// skip
						}
					}
				}
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
