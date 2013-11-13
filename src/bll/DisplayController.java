package bll;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import dal.PictureData;

/**
 * @author Simen Sollie & Kristine Svaboe
 * @since 2013-11-04
 */

public class DisplayController {
	
	/**
	 * Manage list with pictures
	 */

	private PictureController pictureController;
	private BufferedImage currentPic;
	private int count = 0;
	
	public DisplayController(PictureController pictureController){
		this.pictureController = pictureController;
	}

	public BufferedImage getCurrentPicture(boolean random) throws IOException{
		ArrayList<BufferedImage> po = new ArrayList<BufferedImage>();
		List<PictureData> sortedPictureList = pictureController.getPictureDataToDisplay();
		int i = 1;
		for (PictureData p : sortedPictureList){
			po.add(getBufImage(p));
			i++;
			if (i > 100){ //hva skjer hvis listen ikke har 100 objekt?
				break;
			}
		}
		shufflePictures(random, po);
		currentPicture(po);
		
		return currentPic;
	}
	private BufferedImage getBufImage(PictureData p) throws IOException{
//		URL testUrl = new URL("http://pbs.twimg.com/media/BXrietbIgAAiroP.jpg");
		URL imageUrl = new URL(p.getUrlStd());
		InputStream in = imageUrl.openStream();
		BufferedImage image = ImageIO.read(in);
		in.close();
		return image;
	}
	private void shufflePictures(boolean random, ArrayList<BufferedImage> po){
		if (random = true){
			Collections.shuffle(po);
		}
	}
	private void currentPicture(ArrayList<BufferedImage> po){
		if (count < 100){
			currentPic = po.get(count);
			count++;
		} else {
			count = 0;
			currentPic = po.get(count);
			count++;
		}
	}
}
