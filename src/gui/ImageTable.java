package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

public class ImageTable extends JTable implements TableModelListener {

	/**
	 * @author Andreas J
	 */
	private static final long serialVersionUID = 1L;
	// private static DefaultTableModel model = new DefaultTableModel();
	private static ImageTableModel model;
	private static Dimension dim;

	// private static List<SettingsPicture> selected;
	/*
	 * Constructor for the ImageTableSet number of rows, columns, renderer
	 */
	public ImageTable(ImageTableModel imgm, Dimension dim) {
		super(imgm);
		this.dim = dim;
		model = imgm;

		setOpaque(false);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setTableHeader(null);
		this.setAutoCreateColumnsFromModel(true);
		setSelectionBackground(Color.blue);
		setColumnSelectionAllowed(true);
		setRowSelectionAllowed(true);
		//setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		setColumnSize();
		setRowSize();
		this.setShowVerticalLines(true);
		this.setShowHorizontalLines(true);
		setIntercellSpacing(new Dimension(0, 0));
		setBorder(null);
		setDragEnabled(false);
		setShowGrid(false);
	getSelectionModel().addListSelectionListener(new RowColumnListSelectionListener());
	getColumnModel().getSelectionModel() .addListSelectionListener(new RowColumnListSelectionListener()); 
		
	}

	public void setRowSize() {
		for (int i = 0; i < model.getRowCount(); i++) {

			this.setRowHeight(i, 60);
			this.setRowMargin(5);
		}
		System.out.println("Row count" + getRowCount());

	}

	public void setColumnSize() {
		TableColumn column = null;
		for (int i = 0; i < model.getColumnCount(); i++) {
			column = getColumnModel().getColumn(i);

			// column.setPreferredWidth(400/model.getColumnCount());
			column.sizeWidthToFit();
		}
	}

	public JTable getTable() {
		return this;
	}

	public void setSelected(Object[] sel) {

	}

	private class RowColumnListSelectionListener implements
			ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if (getRowSelectionAllowed()) {
				int[] rows = getSelectedRows();
				System.out.println("Selected Rows: " + Arrays.toString(rows));
			}
			if (getColumnSelectionAllowed()) {
				int[] cols = getSelectedColumns();
				System.out
						.println("Selected Columns: " + Arrays.toString(cols));
			} else if (getCellSelectionEnabled()) {
				int selectionMode = getSelectionModel().getSelectionMode();
				System.out.println("selectionMode = " + selectionMode);
				if (selectionMode == ListSelectionModel.SINGLE_SELECTION) {
					int rowIndex = getSelectedRow();
					int colIndex = getSelectedColumn();
					System.out.printf("Selected [Row,Column] = [%d,%d]\n",
							rowIndex, colIndex);
				} else if (selectionMode == ListSelectionModel.SINGLE_INTERVAL_SELECTION
						|| selectionMode == ListSelectionModel.MULTIPLE_INTERVAL_SELECTION) {
					int rowIndexStart = getSelectedRow();
					int rowIndexEnd = getSelectionModel()
							.getMaxSelectionIndex();
					int colIndexStart = getSelectedColumn();
					int colIndexEnd = getColumnModel().getSelectionModel()
							.getMaxSelectionIndex();
                    System.out.println("--------------------------------------------------\n");
					for (int i = rowIndexStart; i <= rowIndexEnd; i++) {
						for (int j = colIndexStart; j <= colIndexEnd; j++) {
							if (isCellSelected(i, j)) {
								System.out.printf(
										"Selected [Row,Column] = [%d,%d]\n", i,
										j);
							}
						}
						
						   System.out.println("--------------------------------------------------\n");
					}
			
				}
			}
		}
	}

}
