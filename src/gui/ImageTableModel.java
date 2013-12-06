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

	private List<SettingsPicture> data;
	private List<String> header;
	private int ROWS=20, COLS=5;

	// Constructor. Receives the data object
	// Creates a new object based on the data
	ImageTableModel(List<SettingsPicture> data) {

		this.data = new ArrayList<SettingsPicture>(data);
	
		header = new ArrayList<String>();
		addColumns();
	}

	// Adds empty titles to the header
	public void addColumns() {
		for (int i = 0; i <COLS; i++) {
			header.add("");
		}
	}

	// Returns number of columns
	public int getColumnCount() {
	
		return COLS;
	}

	// Returns the name of a column
	// Defined in the abstract superclass, has to be implemented

	public String getColumnName(int column) {
		return header.get(column);
	}

	// Gets number of rows, which is the number of lists in the list
	public int getRowCount() {
	 int size= data.size();
	 int modulus=size%COLS;
	 if(modulus!=0)size++;
	 return size;
	}

	// Returns value based on row,column
	// The value is the icon returned from SettingsPicture's getIcon

	public Object getValueAt(int row, int column) {
		SettingsPicture pic = getSetPic(row,column);
		if (pic.getIsFlagged())
			return null;
		
		else return  pic.getIcon(100,50);
			
	}

	// Sets value - a SettingsPicture object into the data object

	public void setValueAt(Object value, int row, int col) {
		SettingsPicture pic;

		if (value != null) {
			pic = (SettingsPicture) value;
			data.set((COLS*row)+col,pic);
		}
		fireTableCellUpdated(row, col);
	}

	public boolean cellIsFlagged(int row, int col) {
         SettingsPicture pic=getSetPic(row,col);
      return   pic.getIsTempFlagged();
    
	}
	public SettingsPicture getSetPic(int row, int col){
		int i = (COLS*row)+col;
		if(i>=data.size())System.out.println("I out of bounds in getSetPic(): v: "+i);
		return data.get((COLS*row)+col);
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



	// Flags picture based on row, column
	public void setflagOnPicture(int row, int column, boolean isFlagged) {
	   getSetPic(row,column).setIsFlagged(isFlagged);
	}
	public void setTempflagOnPicture(int row, int column, boolean isTempFlagged) {
		getSetPic(row,column).setIsTempFlagged(isTempFlagged);
	}

	public void clearFlags() {
		for (int i = 0; i < getRowCount(); i++) {

			for (int j = 0; j < getColumnCount(); j++) {

					setTempflagOnPicture(i, j, false);
			}
		}
	}

	// Iterates through all the objects in the arraylist
	// They are removed if they have the isFlagged property.

	// returns the list
	public List<SettingsPicture> getTableModelData() {
		return data;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Class getColumnClass(int column) {
		Object value = this.getValueAt(0, column);
		return (value == null ? ImageIcon.class : value.getClass());

	}

}