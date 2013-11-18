package bll;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import bll.SettingsController.ViewMode;

import dal.PictureData;

/**
 * @author Simen Sollie & Kristine Svaboe
 * @since 2013-11-04
 */

public class DisplayController {

	/**
	 * Manage list with pictures
	 */

	private ArrayList<BufferedImage> po;
	private List<PictureData> sortedPictureList;
	private PictureController pictureController;
	private BufferedImage currentPic;
	private int count = 0;
	private int current = 0;
	private final int MAX_SIZE = 100;
	private ViewMode viewMode;
	private int displayTime;

	public DisplayController(PictureController pictureController){
		this.pictureController = pictureController;

		// Default settings
		viewMode    = SettingsController.ViewMode.SEQUENTIAL;
		displayTime = 4000;
	}

	public void getSortedList() {
		sortedPictureList = pictureController.getSortedPictureData();
	}

	public BufferedImage getCurrentPicture() throws IOException{
		getSortedList();
		PictureData p = sortedPictureList.get(current);
		currentPic = getBufImage(p);
		if (current < sortedPictureList.size()-1 && current < MAX_SIZE-1){
			current++;
		} else {
			current = 0;
		}
		return currentPic;
	}

	/*public BufferedImage getCurrentPicture(boolean random) throws IOException{
		System.out.println("PictureController: getCurrentPicture");

		if (count == 0) {
			po = new ArrayList<BufferedImage>();
			sortedPictureList = pictureController.getSortedPictureData();

			int i = 1;
			for (PictureData p : sortedPictureList){
				po.add(getBufImage(p));
				System.out.println(p.getUrlStd());
				System.out.println(p.getId());
				i++;
				if (i > MAX_SIZE){ 
					break;
				}
			}
		}

		// TODO: Something like this...
		if ( viewMode == SettingsController.ViewMode.RANDOM ) {
			shufflePictures(random, po);
		}
		else
			currentPicture(po);

		return currentPic;
	}*/

	private void currentPicture(ArrayList<BufferedImage> po){
		if (count < po.size()-1){
			currentPic = po.get(count);
			count++;
		} else {
			currentPic = po.get(count);
			count = 0;
		}
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

	public void setViewMode(ViewMode viewMode) {
		this.viewMode = viewMode;
	}

	public int getDisplayTime() {
		return displayTime;
	}

	public void setDisplayTime(int displayTime) {
		this.displayTime = displayTime;
	}
}
