package chessgame.chess.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import chessgame.chess.board.Board;
import chessgame.chess.manager.GameManager;
import chessgame.chess.piece.Piece;

import java.awt.color.*;

public class ChessGUI{

    private static final long serialVersionUID = 1L;
    private GameManager gm = new GameManager();
    private JFrame frame;
    private BoardPanel boardPanel;

    public ChessGUI() {
        this.frame = new JFrame("Chess") ;
        this.frame.setLayout(new BorderLayout());
        JMenuBar tableMenuBar = createTableMenuBar();
        this.boardPanel = new BoardPanel();
        
        this.frame.setJMenuBar(tableMenuBar);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(600, 600);
        this.frame.add(this.boardPanel, BorderLayout.CENTER);
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
		fileMenu.add(openPGN);
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		return fileMenu;
	}
    
    private class BoardPanel extends JPanel {
    	ArrayList<TilePanel> boardTiles;
    	
    	public BoardPanel() {
    		super(new GridLayout(8, 8));
    		this.boardTiles = new ArrayList<TilePanel>();
    		for(int i = 0; i < 8; i++) {
    			for(int j = 0; j < 8; j++) {
    				TilePanel tilePanel = new TilePanel(this, i + j, gm.getBoard().getPieceFromBoard(i, j));
    				this.boardTiles.add(tilePanel);
    				add(tilePanel);
    			}
    		}
    		setPreferredSize(new Dimension(10, 10));
    		validate();
    	}
    }
    
    private class TilePanel extends JPanel {
    	private int tileId;
    	private Piece piece;
    	
    	public TilePanel(BoardPanel boardPanel, int tileId, Piece piece) {
    		super(new GridBagLayout());
    		this.tileId = tileId;
    		this.piece = piece;
    		setPreferredSize(new Dimension(10, 10));
    		assignTileColor();
    		assignImage();
    		validate();
    	}

		private void assignTileColor() {
			boolean isLight = ((this.tileId) % 2) == 0;
			setBackground(isLight ?  Color.white : Color.gray);
		}
		
		private void assignImage() { 
			if(this.piece != null) {
				try {
					BufferedImage image = ImageIO.read(new File(piece.getImage()));
				} catch (IOException e) {
					throw new IllegalArgumentException();
				}
			}
		}
    }


	public static void main(String[] args) {
        new ChessGUI();
    }
}
