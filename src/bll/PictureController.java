package bll;

import glare.*;
import dal.*;

import java.util.*;

import resources.TwitterReaderDummy;


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
			
			// Get source reader and search for picture data
			String beanName = source.toLowerCase() + "Reader";		
			IReader reader  = (IReader) ClassFactory.getBeanByName(beanName);	
			
			for ( String hashtag : hashtags ) {				
				pictureDataFromSources.addAll(searchPictureDataFromHashtags(reader, hashtag));	
			}
		}
		if ( pictureDataFromSources.isEmpty() )
			return false;
				
		return true;
	}

	private List<PictureData> searchPictureDataFromHashtags(IReader reader, String hashtag) {
		
		ArrayList<PictureData> pictureData = null;
		try {
			return (ArrayList<PictureData>) reader.getPictures(hashtag);
			
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

		List<PictureData> pictureDataExisting = databaseManager.getPictureDataFromDb();

		// Iterate over new picture data and add to existing 
		boolean found;
		for ( PictureData pictureDataNew : pictureDataFromSources ) {
			
			found = false;
			
			for ( PictureData pictureDataExt : pictureDataExisting ) {
				
				// Check if picture data already exists
				if ( pictureDataExt.getId() == pictureDataNew.getId() ) {			
					
					// PictureData already exists. Add hashtag to existing PictureData.				
					Set<String> existingHashtag = new HashSet<String>();
					
					for ( Hashtag hashtagObject : pictureDataExt.getHashtags() ){
						existingHashtag.add(hashtagObject.getHashtag());
					}
					
					for ( Hashtag hashtagObject : pictureDataNew.getHashtags() ){
						existingHashtag.add(hashtagObject.getHashtag());
					}
					
					for ( String hashtag : existingHashtag )
						pictureDataExt.addHashtag(new Hashtag(hashtag));
					
					found = true;
					break;
				}				
			}
			
			// Picture data doesn't exist - add it
			if ( !found ) 
				pictureDataExisting.add(pictureDataNew);
		}
		
		// Sort and save to database	
		databaseManager.savePictureDataToDb(sortPictureData(pictureDataExisting));
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
		/*TwitterReaderDummy trd = new TwitterReaderDummy();
		List<PictureData> pictureData = (List<PictureData>) trd;*/
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
