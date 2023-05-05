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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import chessgame.chess.board.Board;
import chessgame.chess.manager.GameManager;
import chessgame.chess.piece.Piece;

public class ChessGUI{

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
    private Piece pieceToMove;

    public ChessGUI() {
        this.frame = new JFrame("Chess") ;
        this.frame.setLayout(new BorderLayout());
        JMenuBar tableMenuBar = createTableMenuBar();
        this.boardPanel = new BoardPanel();
        this.boardPanel.setLocation(0, 0);
        
        //this.frame.setLocation(0, 0);
        this.frame.setJMenuBar(tableMenuBar);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(FRAME_DIMENSION);
        this.frame.add(this.boardPanel, BorderLayout.CENTER);
        this.frame.pack();
        this.frame.setVisible(true);
    }
   
    private JMenuBar createTableMenuBar() {
    	JMenuBar tableMenuBar = new JMenuBar();
    	tableMenuBar.add(createFileMenu());
    	return tableMenuBar;
    }

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
    		for(TilePanel tilePanel : boardTiles) {
    			tilePanel.drawTile();
    			add(tilePanel);
    		}
    		validate();
    		repaint();
    	}
    }
    
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
				public void mouseClicked(MouseEvent e) {
					// cancels piece selection
					if(SwingUtilities.isRightMouseButton(e)) {
						initialClick = null;
						finalClick = null;
						System.out.println(row + "," + col);
					} 
					// cancels piece selection
					else if(SwingUtilities.isLeftMouseButton(e)) {
						if(initialClick == null) {
							initialClick = new Point(row, col);
						} else {
							finalClick = new Point(row, col);
							// after a valid move, reset the final and initial clicks
							System.out.println("Initial Click: " + initialClick.getX() + "," + initialClick.getY());
							System.out.println("Final Click: " + finalClick.getX() + "," + finalClick.getY());

							if(gm.makeMove((int)initialClick.getX(), (int)initialClick.getY(), (int)finalClick.getX(), (int)finalClick.getY())) {
								initialClick = null;
								finalClick = null;
								
								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										boardPanel.drawBoard();
									}
								});
							} else {
								// make new pop-up window detailing error
								initialClick = null;
								finalClick = null;
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
				public void mouseReleased(MouseEvent e) {
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
    		validate();
    		repaint();
    	}
    	
		private void assignTileColor() {
			boolean isLight = ((this.tileId) % 2) == 0;
			setBackground(isLight ?  new Color(64, 47, 29) : new Color(210, 180, 140));
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
