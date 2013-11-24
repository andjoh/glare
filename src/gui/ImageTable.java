package gui;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;


public class ImageTable extends JTable implements TableModelListener {

	/**
	 *  @author Andreas J
	 */
	private static final long serialVersionUID = 1L;
	private static DefaultTableModel model = new DefaultTableModel();
/*Constructor for the ImageTable 
 *Set number of rows, columns, renderer 
 * 
 * 
 */
	public ImageTable(int cols, int rows) {
		super(model);
		setOpaque(false);
		getModel().addTableModelListener(this);
		addColumns(cols);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		addRows(rows);
		setRenderer();
		setRowHeight(50);
		setTableHeader(null);
		setSelectionBackground (Color.blue);
		setSelectionMode (ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		setColumnSelectionAllowed(true);
		setIntercellSpacing(new Dimension(0,0));
		setBorder(null);
		setDragEnabled(false);

	this.setShowGrid(false);
		// ColumnsAutoResizer s;    - to be implemented
	}

	public void addRows(int rows) {
		for (int r = 0; r < rows; r++) {
			model.insertRow(r, new Object[] { null, null, null, null, null });
		}
		;
	}
	public void addColumns(int cols) {
		
		for (int c= 0; c< cols; c++) {
			model.addColumn(new String(""));
		
			
		}
		;
	}
    // Sets the renderer for all columns
	public void setRenderer() {
		TableColumn col;
		for (int c = 0; c < getColumnCount(); c++) {
			col = getColumnModel().getColumn(c);
			//col.sizeColumnsToFit();
			col.sizeWidthToFit();
			col.setCellRenderer(new IconRenderer());
		}
		;
	}
}


