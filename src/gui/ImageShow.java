package gui;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import net.coobird.thumbnailator.Thumbnails;

import bll.ViewController;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageShow extends JPanel {

	/**
	 * @author Andreas J
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage currImg, backgroundImage;
	private static int SCREEN_W, SCREEN_H;
	private static double SCALE_FACTOR;
	private ViewController ctrl;

	/*
	 * Class to shuffle trough images in the slideshow. Images are drawn to the
	 * screen in this class Uses LoadImages to extract images from urls.
	 */
	public ImageShow(ViewController ctrl, int w, int h) throws IOException {
		this.ctrl = ctrl;
		SCREEN_W = w;
		SCREEN_H = h;
		SCALE_FACTOR = 1.2;
		// currImg = ctrl.getCurrentPicture(false);
		currImg = null;
		backgroundImage = loadBackground();
	}

	public BufferedImage loadBackground() throws IOException {
		URL url = this.getClass().getResource("/resource/img/glare.png");
		backgroundImage = ImageIO.read(url);
		backgroundImage = Thumbnails.of(backgroundImage).scale(1.7)
				.asBufferedImage();
		return backgroundImage;

	}

	// Resizes currImage, adds rendering hints, draws and dispose of Graphics
	// object
	//
	@Override
	public void paint(Graphics g) {
		int w = 0, h = 0;
		Graphics2D g2 = (Graphics2D) g;
		if(currImg==null){
			
			currImg = backgroundImage;
		}
			w = (SCREEN_W - currImg.getWidth()) / 2;
			h = (SCREEN_H - currImg.getHeight()) / 2;

		
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2.drawImage(currImg, w, h, null);
		g2.dispose();
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public void moveNext() throws IOException {
		BufferedImage bf = ctrl.getCurrentPicture();

		if (bf != null)
			currImg = Thumbnails.of(bf).scale(SCALE_FACTOR).asBufferedImage();
		else
			currImg = null;

	}
}
