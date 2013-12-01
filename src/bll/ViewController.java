package bll;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
	private DatabaseManager dbMan;

	private List<PictureData> sortedPictureList;
	private List<PictureData> randomPictureList;
	private List<PictureData> pictureDataList;
	private Set<String> hashtags;
	private boolean isRandom;
	private int displayTime;

	public ViewController(DatabaseManager dbMan) {
		this.dbMan = dbMan;
		sortedPictureList = new ArrayList<PictureData>();

		// Default settings
		isRandom    = true;
		displayTime = 9000;
	}

	public BufferedImage getCurrentPicture() throws IOException {
		if (sortedPictureList.isEmpty()) {
			//System.out.println("Henter ny liste");
			getSortedList();
			//System.out.println("Ferdig å hente liste");
		}

		//System.out.println("PictureData left in sorted list: " + sortedPictureList.size());

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
		pictureDataList = dbMan.getSortedPictureData();

//		System.out.println("");
//		System.out.println("ViewController: getSortedList from PictureController");
//		for ( PictureData pd : pictureDataList)
//			System.out.println(pd.getId());
//		System.out.println("");


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
			e.printStackTrace();
		}
		return image;
	}

	public List<List<SettingsPicture>> getSettingsPictures(int rows, int cols) {
//		System.out.println("");
//		System.out.println("ViewController: getSettingsPictures. Print pictureDataList");
//		System.out.println("Size: " + pictureDataList.size());
//
//		for ( PictureData pd : pictureDataList)
//			System.out.println(pd.getId());
//		System.out.println("");

		List<List<SettingsPicture>> settingsPictures=null;

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
			
					s++;
				}
				settingsPictures.add(tmp);				
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
		
		removeCurrentPictureData(flaggedList);
	}

	private void removeCurrentPictureData(Set<String> flaggedList) {
		
		for ( String picId : flaggedList ) {
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

	public Set<String> getHashtags() {
		hashtags = dbMan.getHashtags();
		return hashtags;
	}

	public void updateHashtags(Set<String> hashtagList) {
//		System.out.println("Hashtag i viewCtrl fra gui");
//		for ( String ht : hashtagList ) {
//			System.out.println(ht);
//		}
//		System.out.println("");
//		System.out.println("Hashtag vi har fra før i viewCtrl");
//		for ( String ht : hashtags ) {
//			System.out.println(ht);
//		}
//		System.out.println("");

		// Check if hashtags have been added
		Set<String> hashtagAdded = new HashSet<String>();
		for (String ht : hashtagList) {
			if (!hashtags.contains(ht)) {
				hashtagAdded.add(ht);
			}
		}

//		System.out.println("hashtagAdded");
//		for ( String ht : hashtagAdded ) {
//			System.out.println(ht);
//		}

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
			removeCurrentPicturesWithoutHashtags(hashtagDeleted);
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
	
	private void removeCurrentPicturesWithoutHashtags(Set<String> hashtagsDeleted) {
		System.out.println("Size pictureDataList " + pictureDataList.size());
		
		// Process current pictureData list
		List<PictureData> pdToBeRemoved = new ArrayList<PictureData>();
		Set<Hashtag> hashtagObj;
		for ( PictureData pd : pictureDataList) {
			
			// Check and delete hashtags for current picture data
			hashtagObj = pd.getHashtags();
			for ( String htDel : hashtagsDeleted) {
				//System.out.println("Searching for hashtag " + htDel + " If found - delete hashtag obj for picture data " + pd.getId());
				for ( Hashtag htObj : hashtagObj ) {
					if ( htObj.getHashtag().equalsIgnoreCase(htDel) ) {
						//System.out.println("Hashtag found for picture data " + pd.getId() + " Remove ht obj");
						hashtagObj.remove(htObj);
					}				
				}
			}
			
			// Check if all hashtags for current picture is removed
			// If removed - delete picture data
			//System.out.println("Check if hashtaglist is empty for picture data " + pd.getId());
			if ( pd.getHashtags().isEmpty() ) {
				//System.out.println("Hashtaglist is empty for picture data " + pd.getId() + " remove picture data from all lists");
				pdToBeRemoved.add(pd);
			}
		}

		//System.out.println("Print pictureData to be removed: ");
		//System.out.println("");
		// Delete from lists
		for ( PictureData pd : pdToBeRemoved) {
			//System.out.println(pd.getId());
			pictureDataList.remove(pd);
			sortedPictureList.remove(pd);
			randomPictureList.remove(pd);
			//System.out.println("");	
		}
		
		System.out.println("Size pdToBeRemoved " + pdToBeRemoved.size());
		System.out.println("Size pictureDataList " + pictureDataList.size());	
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