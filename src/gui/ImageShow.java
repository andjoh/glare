package gui;

import javax.swing.JPanel;

import bll.DisplayController;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

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
	 * Uses LoadImages to extract images from urls. 
	 */
	public ImageShow(DisplayController displayController) throws IOException {
		dc = displayController;
		currImg = dc.getCurrentPicture(false);
//		currImg = null;
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
	 * Changes currImage to the next Image in the list.
	 * Only if the list contains images and that that the
	 * 
	 * 
	 * */
	public void moveNext() throws IOException {	
		currImg = dc.getCurrentPicture(false);
	}
}
