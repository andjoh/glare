package gui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import bll.SettingsPicture;

public class ImageTable extends JTable implements TableModelListener {

	/**
	 * @author Andreas J
	 */
	private static final long serialVersionUID = 1L;
	private ImageTableModel model;
	private ImageTableListener listener;
	/** Constructor for the ImageTableSet number of rows, columns, renderer
	 * @param model
	 * @param dim
	 */
	public ImageTable(ImageTableModel model, Dimension dim) {
		// call super class and initialize objects
		super(model);
		this.model = model;
		listener = new ImageTableListener(this, model);
		// this.setPreferredSize(new Dimension(600, 300));
		// remove table header
		setTableHeader(null);
		// set table sizing properties
		// setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setAutoCreateColumnsFromModel(true);
		this.setFocusable(true);
		this.setRequestFocusEnabled(true);
		setColumnSize();
		setRowSize();
		// set grid and spacing properties
		setIntercellSpacing(new Dimension(0, 0));

		setBorder(null);
		setDragEnabled(false);
		setShowGrid(false);
		this.setBackground(Color.WHITE);
		this.setForeground(Color.WHITE);
		this.setSelectionBackground(this.getBackground());
		this.setSelectionForeground(this.getBackground());
		// Set listener models and add listener
		this.setCellSelectionEnabled(true);
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		getColumnModel().getSelectionModel().addListSelectionListener(listener);
		getSelectionModel().addListSelectionListener(listener);
		// set color for column selection
		setOpaque(false);
		ImageTableRenderer imageTabelRenderer = new ImageTableRenderer(
				getDefaultRenderer(Object.class), Color.yellow);
		setDefaultRenderer(ImageIcon.class, imageTabelRenderer);

	}

	
	/**
	 * // sets Size of rows based on tabledimensions

	 */
	public void setRowSize() {
		for (int i = 0; i < model.getRowCount(); i++) {

			this.setRowHeight(i, 60);
			this.setRowMargin(5);
		}
		
	}

	
	/**
	 * // sets Size of columns based on table dimensions
	 */
	public void setColumnSize() {
		TableColumn column = null;
		for (int i = 0; i < model.getColumnCount(); i++) {
			column = getColumnModel().getColumn(i);
			column.sizeWidthToFit();
		}
	}


	/**
	 *  Iterates through all cells
	 *  <p>Check if cell was selected 
	 *  if selected it should have a temp flag
	 *  then set a permanent flag
	 *  This will cause the thumbnail in the cell not be shown
	 *  as defined by the table model
	 *  <p>
	 */
	public void removeFlagged() {
		SettingsPicture pic;
		for (int r = 0; r < getRowCount(); r++) {
			for (int c = 0; c < getColumnCount(); c++) {

				int i = 0, j = 0;
				i = convertRowIndexToModel(r);
				j = convertColumnIndexToModel(c);
				pic = model.getSetPic(i, j);
				if (pic!=null && pic.getIsTempFlagged()) {
					pic.setIsFlagged(true);
				}

			}
		}
	}
}
