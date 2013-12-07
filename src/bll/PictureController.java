package bll;

import glare.*;
import dal.*;
import java.util.*;

/**
 * Controller for getting, processing and sending picture data to database
 * @author Petter Austerheim
 */
public class PictureController {
	private DatabaseManager databaseManager;

	private List<PictureData> pictureDataFromSources;  // PictureData from Instagram etc.
	private List<PictureData> pictureDataNew;          // PictureData not in db
	private List<PictureData> pictureDataModified;     // Existing pictureData with new hashtag

	/**
	 * Constructor
	 * @param databaseManager
	 */
	public PictureController(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}

	/**
	 * This is the method that the service/server application should call
	 * This method will use internal methods to retrieve pictureData
	 * from sources, process the data, and save to database.
	 * 
	 * @return - True if new or modified picture data are saved to db
	 */
	public boolean getNewPictureData() {

		// Search for pictureData
		boolean success = searchPictureData();

		// If found pictureData: Process the data and save to db
		if ( success ) {
			success = false;

			processPictureData();

			List<PictureData> pictureDataToSave = new ArrayList<PictureData>();

			if ( !pictureDataNew.isEmpty() ) 
				pictureDataToSave.addAll(pictureDataNew);

			if ( !pictureDataModified.isEmpty() )
				pictureDataToSave.addAll(pictureDataModified);				

			if ( !pictureDataToSave.isEmpty() ) {
				databaseManager.savePictureDataToDb(pictureDataToSave);
				success = true;
			}
		}			

		return success;
	}

	/**
	 * Search for picture data on sources with given hashtags
	 * Save list, without duplicates, to class variable pictureDataFromSources.
	 * 
	 * @return - True if new picture data are found
	 */
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

			for ( String hashtag : hashtags )			
				pictureDataFromSources.addAll(searchPictureDataFromHashtags(reader, hashtag));
		}	
		
		if ( pictureDataFromSources.isEmpty() )
			return false;
	
		return true;
	}

	/**
	 * Search picture data for a specific source and hashtag
	 * @param reader
	 * @param hashtag
	 * @return
	 */
	private List<PictureData> searchPictureDataFromHashtags(IReader reader, String hashtag) {	
		
		ArrayList<PictureData> pictureData = null;
		
		try {
			return (ArrayList<PictureData>) reader.getPictures(hashtag);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return pictureData;
	}

	/**
	 * Find new pictureData and store in list pictureDataNew
	 * Find existing pictureData which have got new hashtags and store in list pictureDataModified
	 */		
	private void processPictureData() {

		// Get pictureData from db.
		List<PictureData> pictureDataExisting = databaseManager.getPictureDataFromDb();
		
		pictureDataNew      = new ArrayList<PictureData>();
		pictureDataModified = new ArrayList<PictureData>();		
		Set<String> newHashtags;
		boolean pictureDataExists;

		// Iterate over picture data from sources
		for ( PictureData pdPossibleNew : pictureDataFromSources ) {
			pictureDataExists = false;
			
			// Check if this pictureData already exists in the db
			for ( PictureData pdExisting : pictureDataExisting ) {

				if ( pdPossibleNew.getId().equals(pdExisting.getId()) ) {			
					pictureDataExists = true;

					// Check if hashtag connected to possible new is already connected to existing picture data
					newHashtags = new HashSet<String>();
					newHashtags = checkForNewHashtags(pdExisting, pdPossibleNew);
					
					// If new hashtags - add to existing for further check of possible new picture data,
					// and add possible new, with existing remove flag to modified list
					if ( !newHashtags.isEmpty() ) {
						for ( String ht : newHashtags ) 
							pdExisting.addHashtag(new Hashtag(ht));
						pdPossibleNew.setRemoveFlag(pdExisting.isRemoveFlag());
						pictureDataModified.add(pdPossibleNew);
					}
					break;
				}
			}
			
			// Picture data doesn't exist - add to list
			if ( !pictureDataExists )			
				pictureDataNew.add(pdPossibleNew);
		}	
	}

	/**
	 * Check if new picture data has new hashtags compared with the same existing picture data
	 * @param pdExisting
	 * @param pdNew
	 * @return List of hashtags as string
	 */
	private Set<String> checkForNewHashtags(PictureData pdExisting, PictureData pdNew) {
		Set<String> newHashtags = new HashSet<String>();
		String htPossibleNew;
		boolean hashtagExists;

		for ( Hashtag htObjPossibleNew : pdNew.getHashtags() ){
			htPossibleNew = htObjPossibleNew.getHashtag();
			hashtagExists = false;

			for ( Hashtag htObjExisting : pdExisting.getHashtags() ){

				if ( htPossibleNew == htObjExisting.getHashtag() ) {
					hashtagExists = true;
					break;
				}				
			}	

			if ( !hashtagExists ) {
				newHashtags.add(htPossibleNew);
			}
		}

		return newHashtags;
	}

	public List<PictureData> getPictureDataFromSources() {
		return pictureDataFromSources;
	}

	public List<PictureData> getPictureDataNew() {
		return pictureDataNew;
	}

	public List<PictureData> getPictureDataModified() {
		return pictureDataModified;
	}
}