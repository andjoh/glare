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
	// Når jeg tenker meg om så kan du få velge det som er lettest for deg :)
	
	public void updateHashtagBySource(String source, Set<String> hashtag) {
		databaseManager.updateHashtagBySource(source, hashtag);
	}
	
//	public void addHashtagBySource(String source, Set<String> hashtag) {
//		databaseManager.addHashtagBySource(source, hashtag);
//	}
	
	
//	public void deleteHashtagBySource(String source, Set<String> hashtag) {
//		databaseManager.deleteHashtagBySource(source, hashtag);
//	}	
	
	
//	public void setAppropriateFlag(Set<ThumbnailViewModel> thumbnailViewModel) {
//		
//	}
}