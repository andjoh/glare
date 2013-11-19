package gui;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

/*
 * @author Andreas J
 */
public class SettingsViewModulDummy{
private static int w=30 , h=40;
  private final String[] urls = new String[]{
      "win2.jpg","win3.jpg","win4.jpg","win5.jpg",
      
      "win6.jpg","win7.jpg","win8.jpeg","win9.jpeg","win10.jpg"};
  private ArrayList<ImageIcon> images;
  // Used for testing of the ImageTable
  // Can request 1 image which will be drawn from a list
  // if List is empty. Refill
  public SettingsViewModulDummy(){
  images=  new ArrayList<ImageIcon>();

  }
  public void load(){
  URL url;
    BufferedImage tmp=null;
      for (String url1 : urls) {
          
          url = this.getClass().getResource("/resource/img/"+url1);
      
          try {
             
              tmp= ImageIO.read(url);
              
              
          } catch (IOException ex) {
           
          }   
        if(tmp!=null)
        	{
        	ImageIcon ic=getScaledIcon(tmp);
        	images.add(ic);
        	
        	}
      }
  }
  public void setW(int w){
	  SettingsViewModulDummy.w=w;
  }
  public void setH(int h){
	  SettingsViewModulDummy.h=h;
  }
  // creates ImageIcon scaled to fit the table columns
  // This is just temporary.
  private ImageIcon getScaledIcon(BufferedImage in)
  {
     ;
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
  
  public ImageIcon getThumbnail()
        {
            if(images.isEmpty())load();
            ImageIcon  tmp=images.get(0);
            images.remove(tmp);
            return tmp;
  
        }

}
