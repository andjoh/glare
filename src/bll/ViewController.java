package bll;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import dal.*;

/**
 * Single point of contact for display and settings panel.
 * 
 * This controller gets picture data from database so that pictures
 * can be displayed in full screen.
 * This controller holds display time and view mode which can be
 * manipulated by the settings dialog.
 * This controller holds thumbnails to be used by settings dialog.
 * 
 * @author Simen Sollie, Kristine Svaboe, Petter Austerheim
 * @since 2013-11-04
 */

public class ViewController {

	private DatabaseManager dbMan;
	private List<PictureData> sortedPictureList;
	private List<PictureData> randomPictureList;
	private List<PictureData> pictureDataList;
	private Set<String> hashtags;
	private boolean isRandom;
	private int displayTime;

	
	public ViewController(DatabaseManager dbMan) {
		this.dbMan        = dbMan;
		sortedPictureList = new ArrayList<PictureData>();

		// Default settings
		isRandom    = false;
		displayTime = 1000;
	}

	public BufferedImage getCurrentPicture() {
		
		BufferedImage image = null;
		
		if (sortedPictureList.isEmpty() || randomPictureList.isEmpty())
			getSortedList();

		while ( true ) {
			if ( (sortedPictureList.isEmpty() || randomPictureList.isEmpty()) ) {
				break;
			}	
			
			PictureData p;

			if ( isRandom ) {
				p = randomPictureList.remove(0);
				sortedPictureList.remove(p);
			} else {
				p = sortedPictureList.remove(0);
				randomPictureList.remove(p);
			}
			
			image = getBufImage(p.getUrlStd());

			if ( image != null ) {
				break;
			}	
		}
		
		return image;
	}

	public void getSortedList() {
		pictureDataList = dbMan.getSortedPictureData();

		System.out.println("");
		System.out.println("ViewController: getSortedList from DatabaseManager");
		for ( PictureData pd : pictureDataList) {
			System.out.println(pd.getId());
			for ( Hashtag htObj : pd.getHashtags() ) {
				System.out.println(" - " + htObj.getHashtag());
			}
		}
		System.out.println("");

		sortedPictureList = new ArrayList<PictureData>(pictureDataList);
		randomPictureList = new ArrayList<PictureData>(pictureDataList);
		Collections.shuffle(randomPictureList);
	}

	private BufferedImage getBufImage(String url) {
		URL imageUrl;
		BufferedImage image = null;

		try {
			imageUrl = new URL(url);
			HttpURLConnection urlConn =( HttpURLConnection) imageUrl.openConnection();
			InputStream is = urlConn.getInputStream();
			image = ImageIO.read(is);
			is.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("IO EXEPTION !!!!!!! url: " + url);
		}

		return image;
	}

	public List<List<SettingsPicture>> getSettingsPictures(int rows, int cols) {

		BufferedImage image;
		List<SettingsPicture> setPic = new ArrayList<SettingsPicture>();
		
		for ( PictureData p : pictureDataList ) {
			image = getBufImage(p.getUrlThumb());
			if ( image != null ) {
				setPic.add(new SettingsPicture(p.getId(), image));
			}
		}
				
		List<List<SettingsPicture>> settingsPictures = new ArrayList<List<SettingsPicture>>();;
		List<SettingsPicture> tmp= new ArrayList<SettingsPicture>();
		
		if ( !setPic.isEmpty() ) {
			int s=0;
			for (int r=0;r < setPic.size()/cols; r++) {
				tmp= new ArrayList<SettingsPicture>();
				for (int c=0;c<cols;c++){					
					tmp.add(setPic.get(s));
					s++;
				}
				settingsPictures.add(tmp);					
			}
		}
		
		else 
			settingsPictures.add(tmp);		

		return settingsPictures;
		
////////////////////////////////////////////////////////////////////////////////////////////////
// OLD CODE		
//		List<List<SettingsPicture>> settingsPictures = new ArrayList<List<SettingsPicture>>();;
//		List<SettingsPicture> tmp= new ArrayList<SettingsPicture>();
		
//		if ( !pictureDataList.isEmpty() ) {
//
//			PictureData pic=null;
//			String id="",url;
//			int s=0;
//			for (int r=0;r < pictureDataList.size()/cols; r++){
//				tmp= new ArrayList<SettingsPicture>();
//				for (int c=0;c<cols;c++){
//					pic=pictureDataList.get(s);
//					url=pic.getUrlThumb();
//					id=pic.getId();
//					image = this.getBufImage(url);
//					if ( image != null )
//						tmp.add(new SettingsPicture(id,image));
//					s++;
//				}
//				settingsPictures.add(tmp);				
//			}
//		}
//		
//		else 
//			settingsPictures.add(tmp);		
//
//		return settingsPictures;
	}

	public void removePictures(List<List<SettingsPicture>> list2d) {
		Set<String> flaggedList = new HashSet<String>();
		String id = null;
		for(List<SettingsPicture> list:list2d){

			for(SettingsPicture pic :list){
				if(pic.getIsFlagged()){
					id=pic.getId();
					flaggedList.add(id);
					System.out.println("Got a flagged SettingsPicture object, sending ID: "+id+"to dbMan");
				}
			}
		}
		
		// Remove picture data from current lists and database
		removeCurrentPictureData(flaggedList);
		dbMan.setRemoveFlag(flaggedList);		
	}

	private void removeCurrentPictureData(Set<String> pictureIds) {
		
		for ( String picId : pictureIds ) {
			for ( PictureData pd : pictureDataList ) {						
				
				if ( pd.getId().equalsIgnoreCase(picId) ) {	
					pictureDataList.remove(pd);
					sortedPictureList.remove(pd);
					randomPictureList.remove(pd);
					break;
				}
			}				
		}
	}

	private void removeCurrentPicturesWithoutHashtags(Set<String> hashtagsDeleted) {
		
		// Process current pictureData list
		Set<String> pdToBeRemoved = new HashSet<String>();
		Set<Hashtag> hashtagObj;
		for ( PictureData pd : pictureDataList ) {
			
			// Check and remove hashtags for current picture data
			hashtagObj = pd.getHashtags();
			for ( String htDel : hashtagsDeleted) {
				for ( Hashtag htObj : hashtagObj ) {
					if ( htObj.getHashtag().equalsIgnoreCase(htDel) ) {
						hashtagObj.remove(htObj);
					}				
				}
			}
			
			// Check if all hashtags for current picture is removed. If - Add to remove list
			if ( pd.getHashtags().isEmpty() ) {
				pdToBeRemoved.add(pd.getId());
			}
		}
		removeCurrentPictureData(pdToBeRemoved);
	}
	
	public Set<String> getHashtags() {
		hashtags = dbMan.getHashtags();
		return hashtags;
	}

	public void updateHashtags(Set<String> hashtagList) {

		// Check if hashtags have been added
		Set<String> hashtagAdded = new HashSet<String>();
		for (String ht : hashtagList) {
			if (!hashtags.contains(ht)) {
				hashtagAdded.add(ht);
			}
		}

		// Check if hashtags have been deleted
		Set<String> hashtagDeleted = new HashSet<String>();
		for (String ht : hashtags) {
			if (!hashtagList.contains(ht)) {
				hashtagDeleted.add(ht);
			}
		}

		// Remove picture data from current lists without any hashtags connected
		// and update db regarding hashtags deleted
		if (!hashtagDeleted.isEmpty()) {
			removeCurrentPicturesWithoutHashtags(hashtagDeleted);
			dbMan.removeHashtags(hashtagDeleted);
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