package bll;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import dal.*;

/**
 * Single point of contact for display and settings panel.
 * 
 * This controller gets picture data from database so that pictures can be
 * displayed in full screen. This controller holds display time and view mode
 * which can be manipulated by the settings dialog. This controller holds
 * thumbnails to be used by settings dialog.
 * 
 * @author Simen Sollie, Kristine Svaboe, Petter Austerheim
 * @since 2013-11-04
 */
public class ViewController {

	private DatabaseManager dbMan;
	private List<PictureData> pictureDataList;
	private List<PictureData> sortedPictureList;
	private List<PictureData> randomPictureList;
	private Set<String> hashtags;
	private boolean isRandom;
	private int displayTime;

	public ViewController(DatabaseManager dbMan) {
		this.dbMan = dbMan;
		sortedPictureList = new ArrayList<PictureData>();

		// Default settings
		isRandom = false;
		displayTime = 1000;
	}

	/**
	 * If picture data is present: Get picture to display
	 * New list of picture data is fetched from db when needed
	 * 
	 * @return BufferedImage
	 */
	public BufferedImage getCurrentPicture() {

		BufferedImage image = null;

		if (sortedPictureList.isEmpty() || randomPictureList.isEmpty())
			getSortedList();

		while (true) {
			if ((sortedPictureList.isEmpty() || randomPictureList.isEmpty())) {
				break;
			}

			PictureData p;

			if (isRandom) {
				p = randomPictureList.remove(0);
				sortedPictureList.remove(p);
			} else {
				p = sortedPictureList.remove(0);
				randomPictureList.remove(p);
			}

			image = getBufImage(p.getUrlStd());

			if (image != null) {
				break;
			}
		}

		return image;
	}

	/**
	 * Process picture data using threads and make list of SettingsPictures
	 * @return List of SettingsPictures
	 */
	public List<SettingsPicture> getSettingsPictures(){

		List<SettingsPicture> setPics = new ArrayList<SettingsPicture>();
		ExecutorService executor = Executors.newFixedThreadPool(10);
		List<Future<SettingsPicture>> list = new ArrayList<Future<SettingsPicture>>();
		for (PictureData  pd: pictureDataList) {
			Callable<SettingsPicture> worker = new CallableSettingsPictureTask(pd.getId(),pd.getUrlThumb());
			Future<SettingsPicture> submit = executor.submit(worker);
			list.add(submit);
		}

		SettingsPicture pic;
		for (Future<SettingsPicture> future : list) {
			try {
				pic= future.get();
				if(pic!=null)
					setPics.add(future.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		executor.shutdown();

		return setPics;	
	}

	/**
	 * Load list of pict
	 * Use data from database 
	 * Prepare lists for sequential and random view
	 */
	public void getSortedList() {
		pictureDataList = dbMan.getSortedPictureData();
		sortedPictureList = new ArrayList<PictureData>(pictureDataList);
		randomPictureList = new ArrayList<PictureData>(pictureDataList);
		Collections.shuffle(randomPictureList);
	}

	/**
	 * Get BufferedImage for current url
	 * 
	 * @param url
	 * @return BufferedImage
	 */
	private BufferedImage getBufImage(String url) {
		URL imageUrl;
		BufferedImage image = null;

		try {

			imageUrl = new URL(url);
			HttpURLConnection urlConn = (HttpURLConnection) imageUrl
			.openConnection();

			InputStream is = urlConn.getInputStream();
			image = ImageIO.read(is);
			is.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}

	/**
	 * List of pictures to be marked inappropriate in database
	 * 
	 * @param list
	 */
	public void removePictures(List<SettingsPicture> list) {
		Set<String> flaggedList = new HashSet<String>();
		String id = null;

		for (SettingsPicture pic : list) {
			if (pic == null)

			if (pic.getIsFlagged()) {
				id = pic.getId();
				flaggedList.add(id);
			}
		}

		// Remove picture data from current lists and database
		removeCurrentPictureData(flaggedList);
		dbMan.setRemoveFlag(flaggedList);
	}

	/**
	 * Remove pictures from current lists
	 * 
	 * @param pictureIds
	 */
	private void removeCurrentPictureData(Set<String> pictureIds) {

		for (String picId : pictureIds) {
			for (PictureData pd : pictureDataList) {

				if (pd.getId().equalsIgnoreCase(picId)) {
					pictureDataList.remove(pd);
					sortedPictureList.remove(pd);
					randomPictureList.remove(pd);
					break;
				}
			}
		}
	}

	/**
	 * Remove picture data, without hashtags, from current lists
	 * 
	 * @param hashtagsDeleted
	 */
	private void removeCurrentPicturesWithoutHashtags(
			Set<String> hashtagsDeleted) {

		// Process current pictureData list
		Set<String> pdToBeRemoved = new HashSet<String>();
		Set<Hashtag> hashtagObj;
		for (PictureData pd : pictureDataList) {

			// Check and remove hashtags for current picture data
			hashtagObj = pd.getHashtags();
			for (String htDel : hashtagsDeleted) {
				for (Hashtag htObj : hashtagObj) {
					if (htObj.getHashtag().equalsIgnoreCase(htDel)) {
						hashtagObj.remove(htObj);
					}
				}
			}

			// Check if all hashtags for current picture is removed. If - Add to
			// remove list
			if (pd.getHashtags().isEmpty()) {
				pdToBeRemoved.add(pd.getId());
			}
		}
		removeCurrentPictureData(pdToBeRemoved);
	}

	public Set<String> getHashtags() {
		hashtags = dbMan.getHashtags();
		return hashtags;
	}

	/**
	 * Add and delete hashtags according to input
	 * 
	 * @param hashtagList
	 */
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
		this.isRandom = isRandom;
	}

	public int getDisplayTime() {
		return displayTime / 1000;
	}

	public void setDisplayTime(int displayTime) {
		this.displayTime = displayTime * 1000;
	}
}