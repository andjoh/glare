package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 
 * @author Andreas J
 */

public class IconRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */

	public IconRenderer() {

	}

	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, column);

		BufferedImage icon = (BufferedImage) value;
		// this.setPreferredSize(new Dimension(50,50));
		this.setBorder(null);
		this.setOpaque(false);
		label.setIcon((Icon) icon);
		label.setText("");
		return label;
	}

}
