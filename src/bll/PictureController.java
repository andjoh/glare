package bll;

import glare.*;
import dal.*;

import java.util.*;


public class PictureController {
	private DatabaseManager databaseManager;
	private List<PictureData> pictureData;
	private List<PictureData> pictureDataFromSources;

	
	public PictureController(DatabaseManager databaseManager)
	{
		this.databaseManager = databaseManager;
	}

	
	public boolean searchPictureData() {	
		pictureDataFromSources = new ArrayList<PictureData>();
		
		// Get sources, with appurtenant hashtags, to search from
		List<HashtagBySource> hashtagBySource = databaseManager.getHashtagBySource();
		if ( hashtagBySource.isEmpty() )
			return false;
		
		// Get new picture data from sources like Instagram
		for ( HashtagBySource htbs : hashtagBySource )
			pictureDataFromSources.addAll(searchPicturesFromSource(htbs));
		
		if ( pictureDataFromSources.isEmpty() )
			return false;
				
		return true;
	}


	public ArrayList<PictureData> searchPicturesFromSource(HashtagBySource htbs) {
		
		String source       = htbs.getSource(); 
		Set<String> hashtag = htbs.getHashtag();
		
		IReader reader      = (IReader) ClassFactory.getBeanByName(source);	

		for ( String ht : hashtag )
		{
			try {
				return (ArrayList<PictureData>) reader.getPictures(ht);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}
		
		return new ArrayList<PictureData>();
	}
	
	
	public void processPictureData() {

		List<PictureData> pictureDataFromDb = databaseManager.getPictureDataFromDb();

		// Iterate over new picture data and add new 
		boolean found;
		for ( PictureData p : pictureDataFromSources ) {
			
			found = false;
			
			for ( PictureData pdfd : pictureDataFromDb ) {
				
				// Check if picture data already exists
				if ( pdfd.getId() == p.getId() ) {			
					found = true;
					break;
				}				
			}
			
			// Picture data doesn't exist - add it
			if ( !found ) 
				pictureDataFromDb.add(p);
		}
		
		// Update and sort instance variable
		pictureData = pictureDataFromDb;
		sortPictureData(pictureData);
		
		databaseManager.savePictureDataToDb(pictureData);
	}

	
	private void sortPictureData(List<PictureData> pictureData) {
		Collections.sort(pictureData, new PictureDataComparator());
	}
		
	
	public List<PictureData> getPictureData() {
		return pictureData;
	}

	
	public void setPictureData(List<PictureData> pictureData) {
		this.pictureData = pictureData;
	}

	
	public List<PictureData> getPictureDataFromSources() {
		return pictureDataFromSources;
	}
}
