package gui;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import bll.SettingsPicture;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andreas Johnstad
 * 
 */
public class ImageTableModel extends AbstractTableModel {
	/**
	 * author Andreas J
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private List<SettingsPicture> data; // data for the table
	private List<String> header; // table header
	private int COLS = 5; // fixed number of columns
    private Dimension dim;
	/**
	 * Constructor. Receives the data object /* Creates a new object based on
	 * the data
	 * 
	 * @param data
	 */
	ImageTableModel(List<SettingsPicture> data, Dimension dim) {

		this.data = new ArrayList<SettingsPicture>(data);
		this.dim=dim;
		;
		header = new ArrayList<String>();
		addColumns();
	}

	/**
	 * Adds empty titles to the header
	 */
	public void addColumns() {
		for (int i = 0; i < COLS; i++) {
			header.add("");
		}
	}

	/**
	 * Returns number of columns (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {

		return COLS;
	}

	/**
	 * Returns the name of a column
	 * <p>
	 * Returns value from header
	 * <p>
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int column) {
		return header.get(column);
	}

	/**
	 * 
	 * Gets number of rows
	 * <p>
	 * This is the size of the data The list of SettingsPicture objects Divided
	 * by columns
	 * <p>
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		int size = data.size();
		return size / COLS;
	}

	//
	/**
	 * Returns value based on row,column The value is the icon returned from
	 * SettingsPicture's getIcon
	 * <p>
	 * Returns null if the SettingsPicture is flagged Returns null if the value
	 * is null
	 * <p>
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int column) {
		SettingsPicture pic = getSetPic(row, column);
		if (pic == null) return null;
		else if (pic.getIsFlagged())
			return null;
        
		else
			return pic.getIcon(dim.width/COLS, dim.height/COLS);

	}

	/**
	 * 
	 * Sets value at row,col
	 * <p>
	 * Only if value is not null Call method to update tableCell
	 * <p>
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
	 *      int, int)
	 */
	public void setValueAt(Object value, int row, int col) {
		SettingsPicture pic;

		if (value != null) {
			pic = (SettingsPicture) value;
			data.set((COLS * row) + col, pic);

		}
		fireTableCellUpdated(row, col);
	}

	/**
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean cellIsFlagged(int row, int col) {
		SettingsPicture pic = getSetPic(row, col);
		if (pic == null)
			return false;
		else
			return pic.getIsTempFlagged();

	}

	/**
	 * Obtain SettingsPictue object
	 * <p>
	 * Calculates index in list to get object from based on parameters
	 * <p>
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public SettingsPicture getSetPic(int row, int col) {
		SettingsPicture pic=null ;
		int ind = (COLS * row) + col;
		if(ind>=0 && ind <data.size()){
			pic =data.get(ind);
			
		}
       return pic;
	}

	/**
	 * Returns true for all cells so they cannot be edited (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	/**
	 * Removes a row, fires event to update table
	 * 
	 * @param row
	 */
	public void removeRow(int row) {
		for (int c = 0; c < COLS; c++) {

			data.remove(row * COLS + c);
		}
		fireTableDataChanged();
	}

	/**
	 * Clear all pictures temporary flags
	 * <p>
	 * Called by TableListener
	 * <p>
	 * 
	 */
	public void clearFlags() {
		SettingsPicture pic = null;
		for (int i = 0; i < getRowCount(); i++) {

			for (int j = 0; j < getColumnCount(); j++) {
				pic = getSetPic(i, j);
				if (pic != null)
					pic.setIsTempFlagged(false);
			}
		}
	}

	/**
	 * @return
	 */
	public List<SettingsPicture> getTableModelData() {
		return data;
	}

	/**
	 * Gets the class type for eacb cell
	 * <p>
	 * If null value in column Specify that the returned class Be ImageIcon
	 * 
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Class getColumnClass(int column) {
		Object value = this.getValueAt(0, column);
		return (value == null ? ImageIcon.class : value.getClass());

	}

}