package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import net.coobird.thumbnailator.Thumbnails;
import bll.ViewController;

/**
 * @author Andreas Johnstad
 *
 */
class ImageSlider extends JPanel implements Runnable, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	private volatile boolean stop = false; // indicates that slideshow is stopped
	private ImageShow show;    // A class which updates pictures during slideshow
	private ViewController viewCtrl; // the ViewController
	private volatile Thread th;   // Thread to run slides in
    private BufferedImage currImg,backgroundImage;
    private Dimension dim;
    private int w,h;
    private final static double SCALE_FACTOR=1.4;
	/**
	 * @param parent
	 * @param viewCtrl
	 * @param dim
	 * @param show
	 * @throws IOException
	 */
	public ImageSlider(ViewController viewCtrl,
		Dimension dim) throws IOException {
		this.dim=dim;
		w=dim.width;
		h= dim.height;
		setLayout(null);
		loadBackground();
		
        this.viewCtrl=viewCtrl;
   	    show= new ImageShow(viewCtrl, dim.width, dim.height);
		setPreferredSize(new Dimension(dim.width, dim.height));
		
		setBackground(Color.black);
		setDoubleBuffered(true);
		start();

	}
	public BufferedImage loadBackground() throws IOException {
		URL url = this.getClass().getResource("/resource/img/glare.png");
		backgroundImage = ImageIO.read(url);
		backgroundImage = Thumbnails.of(backgroundImage).scale(1.7)
				.asBufferedImage();
		return backgroundImage;

	}

	/**
	 * 
	 */
	public void start() {
		th = new Thread(this);
		th.start();

	}

	/**
	 *  (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			int w = 0, h = 0;
			Graphics2D g2 = (Graphics2D) g;
			if(currImg==null){
				
				currImg = backgroundImage;
			}
				w = (dim.width - currImg.getWidth()) / 2;
				h = (dim.height - currImg.getHeight()) / 2;

			
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC)	
		;
			g2.drawImage(currImg, w, h, null);
			
		super.paintChildren(g);
		g2.dispose();
		}
	

	/**
	 * Stop slideshow
	 */
	public void stopClick() {
		stop = true;
		try {
			th.join();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

	}

	/**
	 * The run method, slideshow shuffle through images in its own
	 * Thread
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		stop = false;
		//get new display time
		int d = viewCtrl.getDisplayTime() * 1000;
		try {
			while (stop != true) {
				//moce to next picture
				moveNext();
				repaint();
				Thread.sleep(d);


			}
			// th.join();

		} catch (InterruptedException ie) {
			System.out.println("Slideshow was interrupted");
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}
	public void moveNext() throws IOException {
		BufferedImage bf = viewCtrl.getCurrentPicture();
		
		if (bf != null)
			currImg = Thumbnails.of(bf).scale(SCALE_FACTOR).asBufferedImage();
		else
			currImg = null;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}