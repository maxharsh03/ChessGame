package chessgame.chess.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import chessgame.chess.ui.ChessGUI.MoveLog;

public class GameHistoryPanel extends JPanel {

	private DataModel model;
	private JScrollPane scrollPane;
	private static final Dimension GAME_HISTORY_PANEL_DIMENSION = new Dimension(100, 400);
	
	GameHistoryPanel() {
		this.setLayout(new BorderLayout());
		this.model = new DataModel();
		JTable table = new JTable(model);
		table.setRowHeight(15);
		this.scrollPane = new JScrollPane(table);
		scrollPane.setColumnHeaderView(table.getTableHeader());
		scrollPane.setPreferredSize(GAME_HISTORY_PANEL_DIMENSION);
		this.add(scrollPane, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	public void redraw(MoveLog moveLog) {
		int currentRow = 0;
		this.model.clear();
		
		for(int i = 0; i < moveLog.getMoveLog().size(); i++) {
			String moveText = moveLog.getMoveLog().get(i);
			
			if(i % 2 == 0) {
				this.model.setValueAt(moveText, currentRow, 0);
			} else {
				this.model.setValueAt(moveText, currentRow, 1);
				currentRow++;
			}
		}
		
		JScrollBar vertical = scrollPane.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
	}
	
	private static class DataModel extends DefaultTableModel {
		
		private ArrayList<Row> values;
		private static final String[] NAMES = new String[] {"White", "Black"};
		
		public DataModel() {
			this.values = new ArrayList<>();
		}
		
		public void clear() {
			this.values.clear();
			setRowCount(0);
		}
		
		@Override
		public int getRowCount() {
			if(this.values == null || this.values.isEmpty()) {
				return 0;
			} return this.values.size();
		}
		
		@Override
		public int getColumnCount() {
			return NAMES.length;
		}
		
		@Override
		public Object getValueAt(int row, int column) {
			Row currentRow = this.values.get(row);
			
			if(column == 0) {
				return currentRow.getWhiteMove();
			} else if(column == 1) {
				return currentRow.getBlackMove();
			} return null;
		}
		
		@Override 
		public void setValueAt(Object value, int row, int column) {
			Row currentRow;
			if(this.values.size() <= row) {
				currentRow = new Row();
				this.values.add(currentRow);
			} else {
				currentRow = this.values.get(row);
			}
			
			if(column == 0) {
				currentRow.setWhiteMove((String)value);
			} else if(column == 1) {
				currentRow.setBlackMove((String)value);
				fireTableCellUpdated(row, column);
			}
		}
		
		@Override 
		public String getColumnName(int column) {
			return NAMES[column];
		}
		
	}
	
	private static class Row {
		
		private String whiteMove;
		private String blackMove;
		
		Row() {
			
		}
		
		public void setWhiteMove(String whiteMove) {
			this.whiteMove = whiteMove;
		}
		
		public void setBlackMove(String blackMove) {
			this.blackMove = blackMove;
		}
		
		public String getWhiteMove() {
			return this.whiteMove;
		}
		
		public String getBlackMove() {
			return this.blackMove;
		}
	}
	
}
