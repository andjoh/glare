package gui;


 
/*
 * TableDemo.java requires no other files.
 */
 
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;

import bll.SettingsPicture;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
public class ImageTableModel extends AbstractTableModel {
  private List<List<SettingsPicture>> data;
  private final static int DEFAULT_COLS=10, DEFAULT_ROWS=10;
  public ImageTableModel( List<List<SettingsPicture>> thumbs){
	 
	 data = new ArrayList<List<SettingsPicture>>(thumbs); 

	  
  }


    public int getRowCount() {
        return data.size();
    }


    public Object getValueAt(int row, int col) {
        return data.get(row).get(col);
    }
    public Class<? extends Object> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
     
            return false;
  
    }
    public void setValueAt(Object value, int row, int col) {
 
        (data.get(row)).set(col,(SettingsPicture) value);
        fireTableCellUpdated(row, col);

      
    }
  public   List<List<SettingsPicture>> getTableSettingsPictures(){
	return data;
	  
  }
  public  SettingsPicture getTableSettingsPicture(int row,int col){
		return data.get(row).get(col);
		  
	  }

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void insertRow(int r,List<SettingsPicture> rowData)
	    {
	        data.add(r,rowData);
	        fireTableRowsInserted(data.size() - 1, data.size() - 1);
	    }

	public void insertRow(int r, Object object) {
		// TODO Auto-generated method stub
		
	}
	
	 public ImageIcon get(){
                 ImageIcon ic=null;
		         BufferedImage tmp=null;
		        URL  url = this.getClass().getResource("resource/img/del.gif");
		      
		          try {
		             
		              tmp= ImageIO.read(url);
		              
		              
		          } catch (IOException ex) {
		           
		          }   
		        if(tmp!=null)
		        	{
		        	 ic=getScaledIcon(tmp);
		        	
		        	
		        	}
				return ic;
		   }
	
	 private ImageIcon getScaledIcon(BufferedImage in)
	  {
	    int w=50,h=50;
	      ImageIcon icon = new ImageIcon();
	    
	          int type = in.getType();
	                     
	          BufferedImage bi = new BufferedImage(w, h, type);
	          Graphics2D g2 = bi.createGraphics();
	          g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	                              RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	          g2.setPaint(UIManager.getColor("Table.background"));
	          g2.fillRect(0,0,w,h);
	          double xScale = (double)w / in.getWidth();
	          double yScale = (double)h / in.getHeight();
	          double scale = Math.min(xScale, yScale);   
	          double x = (w - scale*in.getWidth());
	          double y = (h - scale*in.getHeight());
	          AffineTransform at = AffineTransform.getTranslateInstance(x,y);
	          at.scale(scale, scale);
	          g2.drawRenderedImage(in, at);
	          g2.dispose();
	          icon = new ImageIcon(bi);
	     
	      return icon;
	  }

}