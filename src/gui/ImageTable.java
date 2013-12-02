package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
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
	//private static List<SettingsPicture> selected;
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
		this.setAutoCreateColumnsFromModel(true);
		setSelectionBackground(Color.blue);
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		setColumnSelectionAllowed(true);
		setColumnSize();
		setRowSize();
		this.setShowVerticalLines(true);
		this.setShowHorizontalLines(true);
		setIntercellSpacing(new Dimension(0, 0));
		setBorder(null);
		setDragEnabled(false);
		setShowGrid(false);
		getSelectionModel().addListSelectionListener(
                new RowColumnListSelectionListener());
	
	}

	public void setRowSize(){
		for(int i=0;i<model.getRowCount();i++){
			
			this.setRowHeight(i, 60);
			this.setRowMargin(5);
		}
		System.out.println("Row count"+getRowCount());
		
	}
	
	public void setColumnSize() {
		TableColumn column = null;
		for (int i = 0; i < model.getColumnCount(); i++) {
			column = getColumnModel().getColumn(i);
			
				//column.setPreferredWidth(400/model.getColumnCount());
			column.sizeWidthToFit();
			}
	}
	public JTable getTable(){return this;}
	
	public void setSelected(Object[] sel){
		
	}
    private class RowColumnListSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
        	
        if ( getTable().getCellSelectionEnabled()) {
        	//clearSelection();
        	model.clearFlags();
        	if (! e.getValueIsAdjusting()){
        	int selectionMode =  getTable().getSelectionModel().getSelectionMode();
                
        	
        	System.out.println("selectionMode = " + selectionMode);
              if (selectionMode == ListSelectionModel.SINGLE_INTERVAL_SELECTION || 
                        selectionMode == ListSelectionModel.MULTIPLE_INTERVAL_SELECTION) {
                    int rowIndexStart =  getTable().getSelectedRow();
                    int rowIndexEnd =  getTable().getSelectionModel().getMaxSelectionIndex();
                    int colIndexStart =  getTable().getSelectedColumn();
                    int colIndexEnd =  getTable().getColumnModel().getSelectionModel().getMaxSelectionIndex();
                    System.out.println("--------------------------------------");
                    for (int i = rowIndexStart; i <= rowIndexEnd; i++) {
                        for (int j = colIndexStart; j <= colIndexEnd; j++) {
                            if ( getTable().isCellSelected(i, j)) {
                                System.out.printf("Selected [Row,Column] = [%d,%d]n", i, j);
                            System.out.println("");
                            model.setflagOnPicture(i, j,true);
                           
                            }
                        } 
                    }
                    System.out.println("--------------------------------------");
                }
        }
            }
        }
    }


}
