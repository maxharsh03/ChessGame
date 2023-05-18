package chessgame.chess.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import chessgame.chess.ui.ChessGUI.MoveLog;

public class TakenPiecesPanel extends JPanel {

	private JPanel northPanel;
	private JPanel southPanel;
	private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
	private static final Color PANEL_COLOR = Color.decode("0xFDFE6");
	private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(40, 80);
	
	public TakenPiecesPanel() {
		
		super(new BorderLayout());
		setBackground(Color.decode("0xFDF5E6"));
		setBorder(PANEL_BORDER);
		this.northPanel = new JPanel(new GridLayout(8, 2));
		this.southPanel = new JPanel(new GridLayout(8, 2));
		this.northPanel.setBackground(PANEL_COLOR);
		this.southPanel.setBackground(PANEL_COLOR);
		this.add(this.northPanel, BorderLayout.NORTH);
		this.add(this.southPanel, BorderLayout.SOUTH);
		setPreferredSize(TAKEN_PIECES_DIMENSION);
	}
	
	public void clear(ArrayList<String> moveLog) {
		this.southPanel.removeAll();
		this.northPanel.removeAll();
		
		
	}
	
}
