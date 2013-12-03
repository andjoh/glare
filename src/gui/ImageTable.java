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
		//
	    setDragEnabled(false);
		setOpaque(false);
		setTableHeader(null);
		this.setAutoCreateColumnsFromModel(true);
		// Set ListSelectionListener properties and selectionmodel
		setSelectionBackground(Color.blue);
		this.setCellSelectionEnabled(true);
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		getSelectionModel().addListSelectionListener(
				new ImageTableListener());
		getColumnModel().getSelectionModel().addListSelectionListener(
				new ImageTableListener());
		// Set table and column size properties
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setColumnSize();
		setRowSize();
		// Set border, spacing and grid properties 
		this.setShowVerticalLines(false);
		this.setShowHorizontalLines(false);
		setIntercellSpacing(new Dimension(0, 0));
		setBorder(null);
		setShowGrid(false);
		
	

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

			column.setPreferredWidth(450/model.getColumnCount());
			//column.sizeWidthToFit();
		}
	}

	public JTable getTable() {
		return this;
	}

	public void setSelected(Object[] sel) {

	}

	private class ImageTableListener implements
			ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			int rStart = 0, rEnd = 0, cStart = 0, cEnd = 0;
			model.clearFlags();
			if (!e.getValueIsAdjusting()) {
				rStart = getSelectedRow();
				rEnd = getSelectionModel().getMaxSelectionIndex();
				cStart = getSelectedColumn();
				cEnd = getColumnModel().getSelectionModel()
						.getMaxSelectionIndex();

			}
			getSelectedCells(rStart, rEnd, cStart, cEnd);
		}
	}

	public void getSelectedCells(int rStart, int rEnd, int cStart, int cEnd) {
		System.out.println("------------------------------\n");
		for (int i = rStart; i <= rEnd; i++) {
			for (int j = cStart; j <= cEnd; j++) {
				if (isCellSelected(i, j)) {
					System.out.printf("Selected [Row,Column] = [%d,%d]\n", i, j);
				}
			}
		}
		System.out.println("--------------------------------------\n");

	}

}
