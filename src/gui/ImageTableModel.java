package gui;


 
/*
 * TableDemo.java requires no other files.
 */
import javax.swing.table.AbstractTableModel;

import bll.SettingsPicture;

import java.util.ArrayList;
import java.util.List;
public class ImageTableModel extends AbstractTableModel {
private  List<List<SettingsPicture>> data;
	
private List<String> columnNames;

ImageTableModel( List<List<SettingsPicture>> data){
	this.data = new ArrayList<List<SettingsPicture>>(data);
	System.out.println("Size"+ data.size());
	columnNames= new ArrayList<String>();
	addColumns();
}
 
 public void addColumns(){
	 for(int i=0;i< getColumnCount();i++){
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
	 for (List<SettingsPicture> sublist : data)
	 {
	     // TODO: Null checking
	     total ++;
	 }
	return total;
 }

 public Object getValueAt(int row, int column) {
   return data.get(column).get(row).getIconTest();
 }

 public Class getColumnClass(int column) {
   return (getValueAt(0, column).getClass());
 }

}
