package gui;

/*
 * TableDemo.java requires no other files.
 */
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import bll.SettingsPicture;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

public class ImageTableModel extends AbstractTableModel {
	private List<List<SettingsPicture>> data;

	private List<String> columnNames;

	ImageTableModel(List<List<SettingsPicture>> data) {
		this.data = new ArrayList<List<SettingsPicture>>(data);
		System.out.println("Size" + data.size());
		columnNames = new ArrayList<String>();
		addColumns();
	/*
		addTableModelListener(new TableModelListener() {
			  @Override public void tableChanged(final TableModelEvent e) {
				    // TO DO: replace 100 with the actual preferred height.
				    EventQueue.invokeLater(new Runnable() {
				      @Override public void run() {
				      System.out.println("table changed");
				      }
				    });
				  }
				});
				*/
	}

	public void addColumns() {
		for (int i = 0; i < getColumnCount(); i++) {
			columnNames.add("");
		}

	}

	public int getColumnCount() {
		return data.get(0).size();
	}

	public String getColumnName(int column) {
		return columnNames.get(column);
	}

	public int getRowCount() {
		int total = 0;
		for (List<SettingsPicture> sublist : data) {
			// TODO: Null checking
			total++;
		}
		return total;
	}

	public Object getValueAt(int row, int column) {
		return data.get(row).get(column).getIconTest();
	}
	
	public void setValueAt(Object value, int row, int col){
		 SettingsPicture pic = (SettingsPicture)value;
		 data.get(row).set(col,pic);
		 fireTableCellUpdated(row,col);
		 }
		 
		 public boolean isCellEditable(int row, int col){
		 if (4 == col){
		 return true;
		 }
		 else {
		 return false;
		 }
		 }
		 
	
		 
		 public void removeRow(int row){
		 data.remove(row);
		 fireTableDataChanged();
		 }
		 
		 
	
	public void flagPicture(int row, int column) {
		data.get(row).get(column).setIsFlagged(true);
	}

	public List<List<SettingsPicture>> getTableModelData() {
		return data;
	}

	public Class getColumnClass(int column) {
		return (getValueAt(0, column).getClass());
	}

}
