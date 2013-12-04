package gui;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import bll.SettingsPicture;

import java.util.ArrayList;
import java.util.List;

public class ImageTableModel extends AbstractTableModel {
	/**
	 * author Andreas J
	 */
	private static final long serialVersionUID = 1L;
	// The data. A list of lists.
	// The abstractmodel uses this object to create a
	// table

	private List<List<SettingsPicture>> data;
	private List<String> header;

	// Constructor. Receives the data object
	// Creates a new object based on the data
	ImageTableModel(List<List<SettingsPicture>> data) {

		this.data = new ArrayList<List<SettingsPicture>>(data);
		System.out.println("Size" + data.size());
		header = new ArrayList<String>();
		addColumns();
	}

	// Adds empty titles to the header
	public void addColumns() {
		for (int i = 0; i < getColumnCount(); i++) {
			header.add("");
		}

	}

	// Returns number of columns
	public int getColumnCount() {
		return data.get(0).size();
	}

	// Returns the name of a column
	// Defined in the abstract superclass, has to be implemented

	public String getColumnName(int column) {
		return header.get(column);
	}

	// Gets number of rows, which is the number of lists in the list
	public int getRowCount() {
		int total = 0;
		for (@SuppressWarnings("unused")
		final List<SettingsPicture> sublist : data) {
			total++;
		}
		return total;
	}

	// Returns value based on row,column
	// The value is the icon returned from SettingsPicture's getIcon

	public Object getValueAt(int row, int column) {
		SettingsPicture pic = getSetPic(row,column);
		if (pic.getIsFlagged()){
		
			return null;
		}
		else return  pic.getIcon(100, 100);
			
	}

	// Sets value - a SettingsPicture object into the data object

	public void setValueAt(Object value, int row, int col) {
		SettingsPicture pic;

		if (value != null) {
			pic = (SettingsPicture) value;
			data.get(row).set(col, pic);
		}
		fireTableCellUpdated(row, col);
	}

	public boolean cellIsFlagged(int row, int col) {
         SettingsPicture pic=getSetPic(row,col);
      return   pic.getIsTempFlagged() ;
    
	}
	public SettingsPicture getSetPic(int row, int col){
		  return data.get(row).get(col);
	}

	// Implementation from superclass
	// Returns true for all cells so they cannot be edited
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	// Removes a row, fires event to update table
	public void removeRow(int row) {
		data.remove(row);
		fireTableDataChanged();
	}

	public void clearFlags() {
		for (int i = 0; i < getRowCount(); i++) {

			for (int j = 0; j < getColumnCount(); j++) {

					this.getSetPic(i, j).setIsTempFlagged(false);
			}
		}
	}

	// Iterates through all the objects in the arraylist
	// They are removed if they have the isFlagged property.

	// returns the list
	public List<List<SettingsPicture>> getTableModelData() {
		return data;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Class getColumnClass(int column) {
		Object value = this.getValueAt(0, column);
		return (value == null ? ImageIcon.class : value.getClass());

	}

}