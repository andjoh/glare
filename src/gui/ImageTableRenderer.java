package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

/**
 * @author Andreas Johnstad
 * 
 */
class ImageTableRenderer implements TableCellRenderer {
	TableCellRenderer render; // the table cell renderer
	private Color h = Color.YELLOW, d = Color.ORANGE;
	private Border border; // border to be set around a cell

	/**
	 * @param r
	 * @param c
	 */
	public ImageTableRenderer(TableCellRenderer r, Color c) {
		render = r;
		border = BorderFactory.createBevelBorder(BevelBorder.RAISED, h,
				h.brighter(), d, d.darker());

	}

	/**
	 * ' Override of method to set border around selected cells (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
	 *      java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JComponent result = (JComponent) render.getTableCellRendererComponent(
				table, value, isSelected, hasFocus, row, column);

		// if the value in the cell is not null and is selected
		// set border around the cell
		if (value != null) {

			if (isSelected && result != null)
				result.setBorder(border);
		}

		return result;

	}

}
