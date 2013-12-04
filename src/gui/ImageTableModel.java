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
    //  Creates a new object based on the data
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
  //  Defined in the abstract superclass, has to be implemented
     
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
        if(data.get(row).get(column)!=null)
                return data.get(row).get(column).getIcon(100, 100);
        else return null;
    }
   //  Sets value - a SettingsPicture object into the data object
 
    public void setValueAt(Object value, int row, int col) {
        SettingsPicture pic ;
         
        if(value!=null){
        pic= (SettingsPicture) value;
        data.get(row).set(col, pic);
        }
        else data.get(row).set(col, null);
         
        fireTableCellUpdated(row, col);
    }
    public boolean cellIsFlagged(int row, int col){
        if(data.get(row).get(col)==null)return true;
        else if (data.get(row).get(col).getIsFlagged())return true;
        else return false;
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
    // Removes a cell fires event to update table
    public void removeCell(int row, int col) {
        data.get(row).remove(col);
        fireTableStructureChanged();
    }
  // Flags picture based on row, column
    public void setflagOnPicture(int row, int column, boolean isFlagged) {
     if (data.get(row).get(column)==null);
     else data.get(row).get(column).setIsFlagged(isFlagged);
    }
 
    public void clearFlags() {
        for (int i = 0; i < getRowCount(); i++) {
 
            for (int j = 0; j < getColumnCount(); j++) {
                 
                if( getValueAt(i, j)==null);
                else setflagOnPicture(i, j, false);
                 
            }
        }
    }
 
    // Iterates through all the objects in the arraylist
    //  They are removed if they have the isFlagged property.
 
   // returns the list
    public List<List<SettingsPicture>> getTableModelData() {
        return data;
    }
 
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Class getColumnClass(int column) {
        Object value=this.getValueAt(0,column);  
        return (value==null?ImageIcon.class:value.getClass()); 
         
    }
 
}