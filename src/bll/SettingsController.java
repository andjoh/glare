package bll;

import java.util.*;

import dal.*;

public class SettingsController {
	private PictureController pictureController;
	private DatabaseManager databaseManager;
	
	
	public SettingsController(PictureController pictureController, DatabaseManager databaseManager) {
		this.pictureController = pictureController;
		this.databaseManager   = databaseManager;
	}
	
	public Set<String> getHashtags() {	
		return databaseManager.getHashtags();
	}

	public void addHashtags(Set<String> hashtags) {	
		databaseManager.addHashtags(hashtags);
	}

	public void remvoveHashtags(Set<String> hashtags) {	
		databaseManager.removeHashtags(hashtags);
	}
	
	public List<SettingsViewModel> getPicturesToDisplay() {
		return null;
	}
	
	public void setRemoveFlag(List<String> pictureDataId) {
		databaseManager.setRemoveFlag(pictureDataId);
	}
}