package gui;

import javax.swing.JPanel;
import javax.swing.UIManager;

import bll.ViewController;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class ImageShow extends JPanel {

	/**
	 * @author Andreas J
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage currImg;
	private ViewController ctrl;



	/*
	 * Class to shuffle trough images in the slideshow. 
	 * Images are drawn to the  screen in this class
	 * Uses LoadImages to extract images from urls. 
	 */
	public ImageShow(ViewController ctrl) throws IOException {
		this.ctrl = ctrl;
//		currImg = ctrl.getCurrentPicture(false);
		currImg = null;
	}

	@Override
	//  Resizes currImage,  adds rendering hints, draws and dispose of Graphics object
	//  
	public void paint(Graphics g) {
		if (currImg != null) {
			
			
			BufferedImage before = currImg;
			int w = before.getWidth();
			int h = before.getHeight();
			BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(2.0, 2.0);
			AffineTransformOp scaleOp = 
			   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			after = scaleOp.filter(before, after);
			g.drawImage(after, 0, 0, null);
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
		currImg = ctrl.getCurrentPicture();
	}
}
