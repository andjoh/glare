package gui;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
/**
*
* @author Andreas J
*/

public class IconRenderer  extends DefaultTableCellRenderer
	{
	    /**
	 * 
	 */
	public IconRenderer(){}
	private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table,
	                                                   Object value,
	                                                   boolean isSelected,
	                                                   boolean hasFocus,
	                                                   int row, int column)
	    {
	        JLabel label = (JLabel)super.getTableCellRendererComponent(table,
	                                   value, isSelected, hasFocus, row, column);
	        ImageIcon icon = (ImageIcon)value;
	        label.setIcon(icon);
	        label.setText("");
	        return label;
	    }
	    
}
