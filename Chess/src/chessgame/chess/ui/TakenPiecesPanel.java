package chessgame.chess.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import chessgame.chess.piece.Piece;
import chessgame.chess.ui.ChessGUI.MoveLog;

public class TakenPiecesPanel extends JPanel {

	private JPanel northPanel;
	private JPanel southPanel;
	private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
	private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(60, 140);
	
	public TakenPiecesPanel() {
		
		super(new BorderLayout());
		setOpaque(false);
		setBorder(PANEL_BORDER);
		this.northPanel = new JPanel(new GridLayout(8, 2));
		this.southPanel = new JPanel(new GridLayout(8, 2));
		this.add(this.northPanel, BorderLayout.NORTH);
		this.add(this.southPanel, BorderLayout.SOUTH);
		setPreferredSize(TAKEN_PIECES_DIMENSION);
	}
	
	public void redraw(ArrayList<String> moveLog, ArrayList<Piece> whiteCapturedPieces, ArrayList<Piece> blackCapturedPieces) {
		this.southPanel.removeAll();
		this.northPanel.removeAll();
		
		Collections.sort(whiteCapturedPieces, new Comparator<Piece>() {

			@Override
			public int compare(Piece o1, Piece o2) {
				return Integer.compare(o1.getValue(), o2.getValue());
			}
			
		});
		
		Collections.sort(blackCapturedPieces, new Comparator<Piece>() {

			@Override
			public int compare(Piece o1, Piece o2) {
				return Integer.compare(o1.getValue(), o2.getValue());
			} 
			
		});
		
		for(int i = 0; i < whiteCapturedPieces.size(); i++) {
			try {
				Piece piece = whiteCapturedPieces.get(i);
				BufferedImage image = ImageIO.read(new File(piece.getImage()));
				this.southPanel.add(new JLabel(new ImageIcon(image.getScaledInstance(32, 32, Image.SCALE_DEFAULT))));
			} catch (IOException e) {
				// skip, this exception should never occur
			}
		}
		
		for(int i = 0; i < blackCapturedPieces.size(); i++) {
			try {
				Piece piece = blackCapturedPieces.get(i);
				BufferedImage image = ImageIO.read(new File(piece.getImage()));
				this.northPanel.add(new JLabel(new ImageIcon(image.getScaledInstance(32, 32, Image.SCALE_DEFAULT))));
			} catch (IOException e) {
				// skip, this exception should never occur
			}
		}
		validate();
		setBackground(Color.decode("0xFDF5E6"));
		repaint();
	}
}
