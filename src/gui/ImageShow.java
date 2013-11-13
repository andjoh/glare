package gui;

import javax.swing.JPanel;
/*
/edited out to do tests without bll - Andreas J

//import bll.DisplayController;

*/
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class ImageShow extends JPanel {

	/**
	 * @author Andreas J
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage currImg;
	private DisplayController dc;



	/*
	 * Class to shuffle trough images in the slideshow. 
	 * Images are drawn to the  screen in this class
	 * Uses DisplayController to extract images from urls. 
	 * 
	 * 
	 * 
	 * 	 */
	public ImageShow(DisplayController dc)  {
		this.dc = dc;;
		currImg = null;
	
	}

	@Override
	//  Resizes currImage,  adds rendering hints, draws and dispose of Graphics object
	//  
	public void paint(Graphics g) {
		if (currImg != null) {
			BufferedImage resized = new BufferedImage(getWidth() / 2,
					getHeight() / 2, currImg.getType());
			Graphics2D g2 = resized.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(currImg, 0, 0, getWidth(), getHeight(), 0, 0,
					currImg.getWidth(), currImg.getHeight(), null);
			g.dispose();
		}
	}

	@Override
	public void update(Graphics g) {

		paint(g);
	}
   
   /*
    *  sets currImg to returned image from displaycontroller
    * 
    * 
    * 
    * */
	public void moveNext() throws IOException {	
	
		
			currImg = dc.getCurrentPicture(false);
		
	}
   /*
    * 
    */


	


}
