package bll;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import dal.*;

/**
 * @author Simen Sollie, Kristine Svaboe, Petter Austerheim
 * @since 2013-11-04
 */

public class ViewController {

	/**
	 * Manage list with pictures
	 */
	private PictureController picCtrl;
	private DatabaseManager dbMan;

	private List<PictureData> sortedPictureList;
	private List<PictureData> randomPictureList;
	private List<PictureData> pictureDataList;
	private Set<String> hashtags;
	private boolean isRandom;
	private int displayTime;

	public ViewController(PictureController picCtrl, DatabaseManager dbMan){
		this.picCtrl      = picCtrl;
		this.dbMan        = dbMan;
		sortedPictureList = new ArrayList<PictureData>();
		
		// Default settings
		isRandom    = false;
		displayTime = 1000;
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
	
	public void removePictures(List<String> pictureIds) {
		//TODO Flag pictures in db and from current pictures
	}

	public Set<String> getHashtags() {
		hashtags = dbMan.getHashtags();
		return hashtags;
	}

	public void updateHashtags(Set<String> hashtagList) {

		// Check if hashtags have been added
		Set<String> hashtagAdded   = new HashSet<String>();
		for ( String ht : hashtagList ) {
			if ( !hashtags.contains(ht) ) {
				hashtagAdded.add(ht);
			}		
		}
				
		// Check if hashtags have been deleted
		Set<String> hashtagDeleted = new HashSet<String>();
		for ( String ht : hashtags ) {
			if ( !hashtagList.contains(ht) ) {
				hashtagDeleted.add(ht);
			}		
		}
				
		// Update db regarding hashtags deleted
		// Delete pictures that are connected to these, and only these, hashtags
		if ( !hashtagDeleted.isEmpty() ) {
			dbMan.removeHashtags(hashtagDeleted);
			dbMan.removePicturesWithoutHashtagFromDB();			
		}
		
		// Update db with new hashtags
		if ( !hashtagAdded.isEmpty() ) {
			dbMan.addHashtags(hashtagAdded);
		}	
		
		// Set the new list as current list
		hashtags = hashtagList;
	}

	public boolean isRandom() {
		return isRandom;
	}

	public void setRandom(boolean isRandom) {
		System.out.println("Random is " + isRandom);
		this.isRandom = isRandom;
	}
	
	public int getDisplayTime() {
		return displayTime / 1000;
	}

	public void setDisplayTime(int displayTime) {
		System.out.println("Set DisplayTime: " + displayTime);
		this.displayTime = displayTime * 1000;
	}	
}