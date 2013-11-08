
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageShow extends JPanel {

	/**
	 * @author Andreas J
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage currImg;
	private LoadImages loadimgs;
	private ArrayList<BufferedImage> images;
	private int curImgIndex;

	/*
	 * Class to shuffle trough images in the slideshow. 
	 * Images are drawn to the  screen in this class
	 * Uses LoadImages to extract images from urls. 
	 * 
	 * 
	 * 
	 * 	 */
	public ImageShow() {
		images = new ArrayList<>();
		loadimgs = new LoadImages();
		loadimgs.load();
		images = loadimgs.getImages();
		if (images != null) {

		}
		//getToolkit().getScreenSize();

		currImg = null;
		curImgIndex = 0;

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
    /* Goes to first image
     *  
     */
	public void moveFirst() {
		if (!images.isEmpty()) {
			curImgIndex = 0;
			currImg = images.get(curImgIndex);
		}
	}
   /*
    * Changes currImage to the next Image in the list.
    * Only if the list contains images and that that the
    * 
    * 
    * */
	public void moveNext() {	
		curImgIndex++;
		if (!images.isEmpty() && curImgIndex<getImgsSize()) {
		
			currImg = images.get(curImgIndex);
		}
	}
   /*
    * 
    */
	public ArrayList<BufferedImage> getImages() {
		return images;
	}

	public void setImages() {
		loadimgs.load();
		images = loadimgs.getImages();
	}
    /* 
     * Returns number of images. 
     * Used by the ImageSlider so that it knows when to stop.
	*/
	public int getImgsSize() {

		if (!images.isEmpty())
			return images.size();
		return 0;
	}

}
