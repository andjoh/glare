package bll;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
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

	public ViewController(PictureController picCtrl, DatabaseManager dbMan) {
		this.picCtrl = picCtrl;
		this.dbMan = dbMan;
		sortedPictureList = new ArrayList<PictureData>();

		// Default settings
		isRandom = false;
		displayTime = 1000;
	}

	public BufferedImage getCurrentPicture() throws IOException {
		if (sortedPictureList.isEmpty()) {
			System.out.println("Henter ny liste");
			getSortedList();
			System.out.println("Ferdig å hente liste");
		}

		System.out.println("PictureData left in sorted list: " + sortedPictureList.size());

		PictureData p;

		if (isRandom) {
			p = randomPictureList.remove(0);
			sortedPictureList.remove(p);
		} else {
			p = sortedPictureList.remove(0);
			randomPictureList.remove(p);
		}

		return getBufImage(p.getUrlStd());
	}

	public void getSortedList() {
		pictureDataList = picCtrl.getSortedPictureData();

		System.out.println("");
		System.out.println("ViewController: getSortedList from PictureController");
		for ( PictureData pd : pictureDataList)
			System.out.println(pd.getId());
		System.out.println("");


		sortedPictureList = new ArrayList<PictureData>(pictureDataList);
		randomPictureList = new ArrayList<PictureData>(sortedPictureList);
		Collections.shuffle(randomPictureList);
	}

	private BufferedImage getBufImage(String url) {
		URL imageUrl;
		BufferedImage image = null;
		try {
			imageUrl = new URL(url);
			InputStream in = imageUrl.openStream();
			image = ImageIO.read(in);
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}

	public List<List<SettingsPicture>> getSettingsPictures(int rows, int cols) {
		System.out.println("");
		System.out.println("ViewController: getSettingsPictures. Print pictureDataList");
		System.out.println("Size: " + pictureDataList.size());

		for ( PictureData pd : pictureDataList)
			System.out.println(pd.getId());
		System.out.println("");

		List<List<SettingsPicture>> settingsPictures=null;

		if(rows*cols==100){

			settingsPictures =new ArrayList<List<SettingsPicture>>();


			PictureData pic=null;
			String id="",url;
			int s=0;
			for (int r=0;r < pictureDataList.size()/cols; r++){
				List<SettingsPicture> tmp= new ArrayList<SettingsPicture>();
				for (int c=0;c<cols;c++){
					pic=pictureDataList.get(s);
					url=pic.getUrlThumb();
					id=pic.getId();
					tmp.add(new SettingsPicture(id,this.getBufImage(url)));
					System.out.println("index s is: " + s);
					s++;
				}
				settingsPictures.add(tmp);				
			}
		}
		return settingsPictures;
	}

	public void removePictures(List<List<SettingsPicture>> list2d) {
		Set<String> flaggedList = new HashSet<String>();
		String id=null;
		for(List<SettingsPicture> list:list2d){

			for(SettingsPicture pic :list){
				if(pic.getIsFlagged()){
					id=pic.getId();
					flaggedList.add(id);
					System.out.println("Got a flagged SettingsPicture object, sending ID: "+id+"to dbMan");
				}

			}

		}
		dbMan.setRemoveFlag(flaggedList);
	}

	public Set<String> getHashtags() {
		hashtags = dbMan.getHashtags();
		return hashtags;
	}

	public void updateHashtags(Set<String> hashtagList) {
		System.out.println("Hashtag i viewCtrl fra gui");
		for ( String ht : hashtagList ) {
			System.out.println(ht);
		}
		System.out.println("");
		System.out.println("Hashtag vi har fra før i viewCtrl");
		for ( String ht : hashtags ) {
			System.out.println(ht);
		}
		System.out.println("");

		// Check if hashtags have been added
		Set<String> hashtagAdded = new HashSet<String>();
		for (String ht : hashtagList) {
			if (!hashtags.contains(ht)) {
				hashtagAdded.add(ht);
			}
		}

		System.out.println("hashtagAdded");
		for ( String ht : hashtagAdded ) {
			System.out.println(ht);
		}

		// Check if hashtags have been deleted
		Set<String> hashtagDeleted = new HashSet<String>();
		for (String ht : hashtags) {
			if (!hashtagList.contains(ht)) {
				hashtagDeleted.add(ht);
			}
		}

		System.out.println("hashtagDeleted");
		for ( String ht : hashtagDeleted ) {
			System.out.println(ht);
		}

		// Update db regarding hashtags deleted
		// Delete pictures that are connected to these, and only these, hashtags
		if (!hashtagDeleted.isEmpty()) {
			dbMan.removeHashtags(hashtagDeleted);
			dbMan.removePicturesWithoutHashtagFromDB();
		}

		// Update db with new hashtags
		if (!hashtagAdded.isEmpty()) {
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