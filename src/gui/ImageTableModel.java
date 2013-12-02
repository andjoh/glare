package gui;

import javax.swing.table.AbstractTableModel;

import bll.SettingsPicture;

import java.util.ArrayList;
import java.util.List;

public class ImageTableModel extends AbstractTableModel {
	/**
	 * author Andreas J
	 */
	private static final long serialVersionUID = 1L;

	private List<List<SettingsPicture>> data;

	private List<String> columnNames;

	ImageTableModel(List<List<SettingsPicture>> data1) {

		this.data = new ArrayList<List<SettingsPicture>>(data1);
		System.out.println("Size" + data.size());
		columnNames = new ArrayList<String>();
		addColumns();
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
		for (@SuppressWarnings("unused")
		final List<SettingsPicture> sublist : data) {
			total++;
		}
		return total;
	}

	public Object getValueAt(int row, int column) {
		return data.get(row).get(column).getIcon(50, 50);
	}

	public void setValueAt(Object value, int row, int col) {
		SettingsPicture pic = (SettingsPicture) value;
		data.get(row).set(col, pic);
		fireTableCellUpdated(row, col);
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public void removeRow(int row) {
		data.remove(row);
		fireTableDataChanged();
	}

	public void removeCell(int row, int col) {
		data.get(row).remove(col);
		fireTableDataChanged();
	}

	public void setflagOnPicture(int row, int column, boolean isFlagged) {
		data.get(row).get(column).setIsFlagged(isFlagged);
	}

	public void clearFlags() {
		for (int i = 0; i < getRowCount(); i++) {

			for (int j = 0; j < getColumnCount(); j++) {
				setflagOnPicture(i, j, false);
			}
		}
	}

	public SettingsPicture getModelObjectAt(int row, int col) {

		return data.get(row).get(col);

	}

	public void removeFlagged() {
		SettingsPicture pic;
		for (int i = 0; i < getRowCount(); i++) {

			for (int j = 0; j < getColumnCount(); j++) {
				pic = (SettingsPicture) getModelObjectAt(i, j);
				if (pic.getIsFlagged())
					removeCell(i, j);
			}
		}
	}

	public List<List<SettingsPicture>> getTableModelData() {
		return data;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Class getColumnClass(int column) {
		return (getValueAt(0, column).getClass());
	}

}
