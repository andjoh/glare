package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

class ImageTableRenderer implements TableCellRenderer{
    TableCellRenderer render;
    private Color h= Color.YELLOW, d= Color.ORANGE;
    private Border border;
    public ImageTableRenderer(TableCellRenderer r, Color c){
        render = r;
        border=  
	    		 BorderFactory.createBevelBorder(BevelBorder.RAISED, h, h.brighter(), 
                                                                 d,d.darker());
        
    }
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row,
            int column) {
        JComponent result = (JComponent)render.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
       if(value!=null){
 	    
    	   if(isSelected && result!=null)result.setBorder(border);	    		
       }

      
        return result;
    
    }

}
