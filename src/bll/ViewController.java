package bll;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import dal.*;

/**
 * @author Simen Sollie & Kristine Svaboe
 * @since 2013-11-04
 */

public class ViewController {

	/**
	 * Manage list with pictures
	 */
	private PictureController picCtrl;

	private List<PictureData> sortedPictureList;
	private List<PictureData> randomPictureList;
	private List<PictureData> pictureDataList;
	private Set<String> hashtags;
	private boolean isRandom;
	private int displayTime;

	public ViewController(PictureController picCtrl){
		this.picCtrl      = picCtrl;
		sortedPictureList = new ArrayList<PictureData>();
		
		// Default settings
		isRandom    = false;
		displayTime = 100;
	}
	

	public BufferedImage getCurrentPicture() throws IOException{
		if ( sortedPictureList.isEmpty() ) {
			System.out.println("Henter ny liste");
			getSortedList();
			System.out.println("Ferdig å hente liste");
		}
		
		PictureData p;

		if ( isRandom ) {
			p = randomPictureList.remove(0);
			sortedPictureList.remove(p);
		}
		else {
			p = sortedPictureList.remove(0);
			randomPictureList.remove(p);
		}
		
		return getBufImage(p.getUrlStd());
	}

	public void getSortedList() {
		pictureDataList   = picCtrl.getSortedPictureData();
		sortedPictureList = new ArrayList<PictureData>(pictureDataList);
		randomPictureList = new ArrayList<PictureData>(sortedPictureList);
		pictureDataList   = new ArrayList<PictureData>(sortedPictureList);
		Collections.shuffle(randomPictureList);
	}
	
	private BufferedImage getBufImage(String url) throws IOException{
		URL imageUrl        = new URL(url);
		InputStream in      = imageUrl.openStream();
		BufferedImage image = ImageIO.read(in);
		in.close();
		return image;
	}




	public List<SettingsPicture> getSettingsPictures() throws IOException {
		System.out.println("ViewController: getSettingsPictures");
		
		List<SettingsPicture> settingsPictures = new ArrayList<SettingsPicture>();

		String id, url;

		for ( PictureData pd : pictureDataList) {
			id  = pd.getId();
			url = pd.getUrlThumb();
			
			settingsPictures.add(new SettingsPicture(id, getBufImage(url)));
		}	
		
		return settingsPictures;
	}


	public Set<String> getHashtags() {
		return hashtags;
	}

	public void setHashtags(Set<String> hashtags) {
		this.hashtags = hashtags;
	}

	public boolean isRandom() {
		return isRandom;
	}

	public void setRandom(boolean isRandom) {
		this.isRandom = isRandom;
	}
	

	public int getDisplayTime() {
		return displayTime;
	}

	public void setDisplayTime(int displayTime) {
		this.displayTime = displayTime;
	}	
}