package bll;

import java.util.*;

import dal.*;

public class AdministrationController {
	private PictureController pictureController;
	private DatabaseManager databaseManager;
	
	
	public AdministrationController(PictureController pictureController, DatabaseManager databaseManager) {
		this.pictureController = pictureController;
		this.databaseManager   = databaseManager;
	}
	
	// Andreas: Kanskje vi kan bruke en metode som dekker på add og delete?
	// Kanskje vi ikke trenger å tenke på om vi sletter eller legger til
	// Vi bare oppdaterer lista enkelt og greit. Hva synes du?
	public void updateHashtagBySource(String source, List<String> hashtag) {
		databaseManager.updateHashtagBySource(source, hashtag);
	}
	
//	public void addHashtagBySource(String source, List<String> hashtag) {
//		databaseManager.addHashtagBySource(source, hashtag);
//	}
	
	
//	public void deleteHashtagBySource(String source, List<String> hashtag) {
//		databaseManager.deleteHashtagBySource(source, hashtag);
//	}	
	
	
//	public void setAppropriateFlag(List<ThumbnailViewModel> thumbnailViewModel) {
//		
//	}
}