package bll;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

import javax.imageio.ImageIO;

import dal.*;

public class SettingsController {
	private DisplayController dispCtrl;
	private PictureController picCtrl;
	private DatabaseManager dbManager;
	private final int MAX_SIZE = 100;
	
	private List<SettingsPicture> settingsPictures;
	private Set<String> hashtags;
	public static enum ViewMode {SEQUENTIAL, RANDOM};
	private ViewMode currentMode;
	private int displayTime;

	
	public SettingsController(DisplayController dispCtrl, PictureController picCtrl, DatabaseManager dbManager) {
		this.dispCtrl  = dispCtrl;
		this.picCtrl   = picCtrl;
		this.dbManager = dbManager;		
	}

	public List<SettingsPicture> getSettingsPictures() throws IOException {			
			settingsPictures          = new ArrayList<SettingsPicture>();
			List<PictureData> picData = picCtrl.getCurrentPictureData();
			String id, url;

			for ( int i = 0; i < MAX_SIZE; i++ ) {
				id  = picData.get(i).getId();
				url = picData.get(i).getUrlThumb();
				
				settingsPictures.add(new SettingsPicture(id, getBufImage(url)));
			}
		
		return settingsPictures;
	}
	
	private BufferedImage getBufImage(String url) throws IOException{
		URL imageUrl        = new URL(url);
		InputStream in      = imageUrl.openStream();
		BufferedImage image = ImageIO.read(in);
		in.close();
		return image;
	}

	public void setRemoveFlag(List<String> pictureId) {
		dbManager.setRemoveFlag(pictureId);
	}
	
	public Set<String> getHashtags() {
		hashtags = dbManager.getHashtags();
		return hashtags;
	}

	/**
	 * Check and remove duplicates 
	 * Add to class variable and database
	 * @param newHashtags
	 */
	public void addHashtags(Set<String> newHashtags) {	
		// TODO To be used if return success from add to db
		//Set<String> hashtagsBeforeAddNew = hashtags; 
		
		for ( String newHt : newHashtags ) {
			if ( hashtags.contains(newHt) )
				newHashtags.remove(newHt);
			else
				hashtags.add(newHt);
		}
		
		if ( !newHashtags.isEmpty() )
			dbManager.addHashtags(newHashtags);
	}

	/**
	 * Check if hashtags to be removed exists. 
	 * If: Remove from class variable and database
	 * @param remHashtags
	 */
	public void remvoveHashtags(Set<String> remHashtags) {
		// TODO To be used if return success when remove from db
		//Set<String> hashtagsBeforeRemove = hashtags;
		
		for ( String remHt : remHashtags ) {
			if ( hashtags.contains(remHt) )
				hashtags.remove(remHt);
			else
				remHashtags.remove(remHt);
		}		
		
		if ( !remHashtags.isEmpty() )
			dbManager.removeHashtags(remHashtags);
	}

	public ViewMode getCurrentMode() {
		// TODO Get setting from db
//		String mode = dbManager.getMode();
//		if ( mode.equalsIgnoreCase(ViewMode.RANDOM.toString()) )
//			currentMode = ViewMode.RANDOM;
//		else
			currentMode = ViewMode.SEQUENTIAL;

		return currentMode;
	}

	public void setCurrentMode(ViewMode currentMode) {
		this.currentMode = currentMode;
		// TODO Set setting in DisplayController
		//dispCtrl.setMode(currentMode);
		// TODO Save setting in db
		//dbManager.setMode(currentMode);
	}

	public int getDisplayTime() {
		// TODO Get setting from db
		//displayTime = dbManager.getDisplayTime();
		
		return displayTime;
	}

	public void setDisplayTime(int displayTime) {
		this.displayTime = displayTime;
		// TODO Not sure if this should be set in disp ctrl or display gui
		//dispCtrl.setDisplayTime(displayTime);
		// TODO Save setting in db
		//dbManager.setDisplayTime(displayTime);
	}
}