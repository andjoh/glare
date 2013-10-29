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
	
	// Andreas: Kanskje vi kan bruke en metode som dekker p� add og delete?
	// Kanskje vi ikke trenger � tenke p� om vi sletter eller legger til
	// Vi bare oppdaterer lista enkelt og greit. Hva synes du?
	// N�r jeg tenker meg om s� kan du f� velge det som er lettest for deg :)
	
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