package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

public class ImageTable extends JTable implements TableModelListener {

	/**
	 * @author Andreas J
	 */
	private static final long serialVersionUID = 1L;
	// private static DefaultTableModel model = new DefaultTableModel();
	private static ImageTableModel model;
	private  ImageTableListener imgtablist;
	private static int w = 50, h = 50;
	private static Dimension dim;
	/*
	 * Constructor for the ImageTableSet number of rows, columns, renderer
	 */
	public ImageTable(ImageTableModel imgm, Dimension dim) {
		super(imgm);
       this.dim=dim;
		model = imgm;
		setOpaque(false);

		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		setTableHeader(null);
		setSelectionBackground(Color.blue);
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		setColumnSelectionAllowed(true);
		setColumnSize();
		setRowSize();
		this.setShowVerticalLines(true);
		this.setShowHorizontalLines(true);
		//setIntercellSpacing(new Dimension(0, 0));
		setBorder(null);
		setDragEnabled(false);
		setShowGrid(false);
		Action action = new AbstractAction()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	 imgtablist = (ImageTableListener)e.getSource();
		        System.out.println("Row   : " + imgtablist .getRow());
		        System.out.println("Column: " + imgtablist .getColumn());
		        System.out.println("Old   : " + imgtablist .getOldValue());
		        System.out.println("New   : " + imgtablist .getNewValue());
		    }
		};
		// ColumnsAutoResizer s; - to be implemente nbd
	}

	public void setRowSize(){
		for(int i=0;i<model.getRowCount();i++){
			
			this.setRowHeight(i, 50);
			this.setRowMargin(5);
		}
		System.out.println("Row count"+getRowCount());
		
	}
	
	public void setColumnSize() {
		TableColumn column = null;
		for (int i = 0; i < model.getColumnCount(); i++) {
			column = getColumnModel().getColumn(i);
			
				column.setPreferredWidth(400/model.getColumnCount());
			
			}
		}

}
