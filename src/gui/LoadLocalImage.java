package gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import net.coobird.thumbnailator.Thumbnails;

/**
 * @author Andreas Johnstad
 *
 */
public class LoadLocalImage { 
	/**
	 * Class to load local image resource
	 */
	public LoadLocalImage (){
		
		
	}
/**
 * Creates BufferedImage from source
 * Resizes with Thumbnailator 
 * Casts to ImageIcon 
 * Returns 
 * @param w
 * @param h
 * @param location
 * @return
 */
	
public  ImageIcon getIcon(int w,int h, String location){
	BufferedImage img = null;
	ImageIcon ic=null;
	URL urls = this.getClass().getResource(location);
	try {

		img = ImageIO.read(urls);
		img = Thumbnails.of(img).size(w, h).asBufferedImage();
	} catch (IOException e) {
		e.printStackTrace();
	}
    ic = new ImageIcon(img);
	
	return ic;
}
;
}
