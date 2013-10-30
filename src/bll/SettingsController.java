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
	
	
	public List<SettingsViewModel> getSettingsViewModel() {
		// TODO Convert from List og PictureData to List of SettingsViewModel
		
		
		return null;
	}

	
	public void setRemoveFlag(List<String> pictureDataId) {
		// Call method in DatabaseManager or PictureController to set remove flag
	}
	

	public List<HashtagBySource> getHashtagBySource() {	
		return databaseManager.getHashtagBySource();
	}
	
	
	// Andreas: Kanskje vi kan bruke en metode som dekker p� add og delete?
	// Kanskje vi ikke trenger � tenke p� om vi sletter eller legger til
	// Vi bare oppdaterer lista enkelt og greit. Hva synes du?
	// N�r jeg tenker meg om s� kan du f� velge det som er lettest for deg :)
	
	public void updateHashtagBySource(String source, Set<String> hashtag) {
		databaseManager.updateHashtagBySource(source, hashtag);
	}
}