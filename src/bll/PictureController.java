package bll;

import glare.*;
import dal.*;

import java.util.*;


public class PictureController {
	private DatabaseManager databaseManager;
	private List<PictureData> pictureDataFromSources;
	
	
	public PictureController(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}
	
	public boolean searchPictureData() {	

		pictureDataFromSources = new ArrayList<PictureData>();
		
		// Get sources and hashtags
		List<String> sources = databaseManager.getSources();
		Set<String> hashtags = databaseManager.getHashtags();
		
		if ( hashtags.isEmpty() || sources.isEmpty() )
			return false;
		
		// Get new picture data from sources
		for ( String source : sources ) {
			
			// Get source reader and search picture data
			String beanName = source.toLowerCase() + "Reader";		
			IReader reader  = (IReader) ClassFactory.getBeanByName(beanName);	
			
			for ( String hashtag : hashtags ) {				
				pictureDataFromSources.addAll(searchPicturesDataFromHashtag(reader, hashtag));	
			}

		}
		if ( pictureDataFromSources.isEmpty() )
			return false;
				
		return true;
	}

	private ArrayList<PictureData> searchPicturesDataFromHashtag(IReader reader, String hashtag) {
		
		ArrayList<PictureData> pictureData = null;
		try {
			pictureData = (ArrayList<PictureData>) reader.getPictures(hashtag);
			
			return pictureData;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pictureData;
	}
	
	/**
	 * Add list of new picturedata, from sources, to existing picturedata list
	 * Sort and save to database
	 */	
	public void processPictureData() {

		List<PictureData> pictureDataFromDb = databaseManager.getPictureDataFromDb();

		// Iterate over new picture data and add new 
		boolean found;
		for ( PictureData p : pictureDataFromSources ) {
			
			found = false;
			
			for ( PictureData pdfd : pictureDataFromDb ) {
				
				// Check if picture data already exists
				if ( pdfd.getId() == p.getId() ) {			
					// PictureData already exists. Add hashtag to existing PictureData.
					for ( Hashtag ht : p.getHashtags() )
						pdfd.addHashtag(ht);
					
					found = true;
					break;
				}				
			}
			
			// Picture data doesn't exist - add it
			if ( !found ) 
				pictureDataFromDb.add(p);
		}
		
		// Sort and save to database	
		databaseManager.savePictureDataToDb(sortPictureData(pictureDataFromDb));
	}
	

	private List<PictureData> sortPictureData(List<PictureData> pictureData) {
		Collections.sort(pictureData, new PictureDataComparator());
		return pictureData;
	}
		
	/**
	 * Return sorted List of PictureData objects that can be displayed, i.e. no inappropriate.
	 * @return List of PictureData
	 */
	public List<PictureData> getPictureDataToDisplay() {

		List<PictureData> pictureData = databaseManager.getPictureDataFromDb();
		for ( PictureData pD : pictureData ) {
			if ( pD.isRemoveFlag() )
				pictureData.remove(pD);
		}
		return sortPictureData(pictureData);
	}

	
	public List<PictureData> getPictureDataFromSources() {
		return pictureDataFromSources;
	}
}
