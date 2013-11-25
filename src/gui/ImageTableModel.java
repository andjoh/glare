package gui;


 
/*
 * TableDemo.java requires no other files.
 */
 
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import bll.SettingsPicture;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
public class ImageTableModel extends AbstractTableModel {
  private List<List<SettingsPicture>> data;
  private final static int DEFAULT_COLS=10, DEFAULT_ROWS=10;
  public ImageTableModel( List<List<SettingsPicture>> thumbs){
	 
	 data = new ArrayList<List<SettingsPicture>>(thumbs); 

	  
  }


    public int getRowCount() {
        return data.size();
    }


    public Object getValueAt(int row, int col) {
        return data.get(row).get(col);
    }
    public Class<? extends Object> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
     
            return false;
  
    }
    public void setValueAt(Object value, int row, int col) {
 
        (data.get(row)).set(col,(SettingsPicture) value);
        fireTableCellUpdated(row, col);

      
    }
  public   List<List<SettingsPicture>> getTableSettingsPictures(){
	return data;
	  
  }
  public  SettingsPicture getTableSettingsPicture(int row,int col){
		return data.get(row).get(col);
		  
	  }

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void insertRow(int r,List<SettingsPicture> rowData)
	    {
	        data.add(r,rowData);
	        fireTableRowsInserted(data.size() - 1, data.size() - 1);
	    }

	public void insertRow(int r, Object object) {
		// TODO Auto-generated method stub
		
	}

}