package gui;

import java.awt.AlphaComposite;
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
 * @author Andreas Johnstad, Andreas B
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
    private BufferedImage image1,image2,currImg,backgroundImage;
    private Dimension dim;
    private int w,h;
    private float alphaS = (float)0.0;
    private static boolean alphaAdd = true;
    private static int counter=0;
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
	/*	
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
	
*/
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
	//public void paint(Graphics g){
	//	 super.paintChildren(g);
	//}
	public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
       image1=image1==null?backgroundImage:image1;
       image2=image2==null?backgroundImage:image2;
			int
			w1 = (w - image1.getWidth()) / 2,
			h1 = (h - image1.getHeight()) / 2,
			w2 = (w - image2.getWidth()) / 2,
			h2 = (h - image2.getHeight()) / 2;
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC)	;
		
		g2.drawImage(image1, w1, h1, null);
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaS);
        g2.setComposite(ac);
        g2.drawImage(image2, w2, h2,null);
		
    //    super.paintChildren(g);
    }
	/**
	 * The run method, slideshow shuffle through images in its own
	 * 
	 * Thread
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		stop = false;
		// gets display time from viewcontroller
		int d = viewCtrl.getDisplayTime() * 1000;
        float step = (float)0.02;
        while(stop==false) {
           // logic to adjust alpha values 
           // used for creating fade in and fade out
      
        	if(alphaS >= 0.98){
                try {
                    Thread.sleep(d);
                } catch (InterruptedException e) {
               
                    e.printStackTrace();
                }
                try {
                    image1 = getNext();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                
                alphaAdd = false;
            }
            else if (alphaS <= 0.02){
                try {
                    Thread.sleep(d);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    image2 = getNext();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
               
                alphaAdd = true;
            }
            if (alphaAdd)
                alphaS += step;
            else
                alphaS -= step;
            repaint();
            try {
                Thread.sleep(30);
            } catch (InterruptedException ex) {}
        }
    }  

	public BufferedImage getNext() throws IOException {
		BufferedImage bf = viewCtrl.getCurrentPicture();
			
		return bf;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}