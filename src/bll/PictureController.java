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

	private List<PictureData> pictureDataExisting;              // Contains all pictureData from db. New will be added to complete list 
	private List<PictureData> pictureDataFromSources;           // PictureData from Instagram etc.
	private List<PictureData> pictureDataNew;                   // PictureData not already in db
	private List<PictureData> pictureDataModified;              // Existing pictureData which contains new- in addition to existing hashtags
	private List<PictureIdHashtags> existingPicIdNewHashtags;   // List of objects containing existing pictureId and new hashtags

	
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
			processPictureData();

			List<PictureData> pictureDataToSave = new ArrayList<PictureData>();
			
			if ( !pictureDataNew.isEmpty() || !pictureDataModified.isEmpty() ) {			

				pictureDataToSave.addAll(pictureDataExisting);
				
				//System.out.println("");				
				//System.out.println("PictureController, getNewPictureData: All PictureData before save to db");
				for ( PictureData pd : pictureDataNew ) {
					//System.out.println(pd.getId());
				}
				//System.out.println("");				

			}						
			
			if ( !pictureDataToSave.isEmpty() ) {
				databaseManager.savePictureDataToDb(pictureDataToSave);
				success = true;
			}
			
			// TODO: This might be used if new hashtags explicitly should be updated in database
			// If new hashtags on existing pictureData: Save to db
			//if ( !existingPicIdNewHashtags.isEmpty() ) {				
			//	databaseManager.saveHashtagsToExistingPictureData(existingPicIdNewHashtags);
			//  success = true;
			//}	
			
			return false;
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
		List<PictureData> pictureData = new ArrayList<PictureData>();

		// Get sources and hashtags
		List<String> sources = databaseManager.getSources();
		Set<String> hashtags = databaseManager.getHashtags();

		//System.out.println("");				
		//System.out.println("PictureController, searchPictureData: Sources from db");
		for ( String s : sources ) {
			System.out.println(s);
		}
		//System.out.println("");	
		
		//System.out.println("");				
		//System.out.println("PictureController, searchPictureData: Hashtag from db");
		for ( String s : hashtags ) {
		//	System.out.println(s);
		}
		//System.out.println("");	
		
		
		if ( hashtags.isEmpty() || sources.isEmpty() )
			return false;

		// Get new picture data from sources
		for ( String source : sources ) {

			// Get source reader and search for picture data
			String beanName = source.toLowerCase() + "Reader";		
			IReader reader  = (IReader) ClassFactory.getBeanByName(beanName);	

			for ( String hashtag : hashtags ) {				
				pictureData.addAll(searchPictureDataFromHashtags(reader, hashtag));	
			}
		}
		if ( pictureData.isEmpty() )
			return false;
		
		//System.out.println("");	
		//System.out.println("PictureController, searchPictureData: Searched PictureData before remove duplicates");
		for ( PictureData pd : pictureData )
			//System.out.println(pd.getId());
	//	System.out.println("");		
		
		// Remove duplicates from pictureData got from sources
		pictureDataFromSources = removePictureDataDuplicates(pictureData);

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
	 * Remove duplicated picture data from list
	 * @param pictureData
	 * @return - picture data without duplicates
	 */
	private List<PictureData> removePictureDataDuplicates(List<PictureData> pictureData) {
		List<PictureData> pictureDataWithoutDuplicates = new ArrayList<PictureData>();
		boolean pictureDataExists;

		for ( PictureData pd : pictureData ) {
			pictureDataExists = false;

			for ( PictureData pdNotDuplicated : pictureDataWithoutDuplicates ) {

				if ( pd.getId() == pdNotDuplicated.getId() ) {

					pictureDataExists = true;

					// PictureData exists. Need to check if there are new hashtags to add
					Set<String> newHashtags = checkForNewHashtags(pdNotDuplicated, pd);

					if ( !newHashtags.isEmpty() ) {
						
						// Add new hashtags to existing pictureData
						for ( String ht : newHashtags ) {
							pdNotDuplicated.addHashtag(new Hashtag(ht));
						}
					}					

					break;
				}
			}

			// Picture data doesn't exists - add to list containing new pictureData
			if ( !pictureDataExists ) {
				pictureDataWithoutDuplicates.add(pd);
			}					
		}

		return pictureDataWithoutDuplicates;
	}

	/**
	 * Find new pictureData and store in list pictureDataNew
	 * Find existing pictureData which have got new hashtags and store in list pictureDataModified
	 * List of existing pictureData will in the end of this method contain all pictureData objects with all hashtags
	 */		
	public void processPictureData() {
		// Get pictureData from db. Assume no duplicates.
		pictureDataExisting      = databaseManager.getPictureDataFromDb();

		// Init lists
		pictureDataNew           = new ArrayList<PictureData>();
		pictureDataModified      = new ArrayList<PictureData>();
		existingPicIdNewHashtags = new ArrayList<PictureIdHashtags>();
		
		// Iterate over picture data from sources
		boolean pictureDataExists;
		for ( PictureData pdPossibleNew : pictureDataFromSources ) {

			pictureDataExists = false;

			// Check if this pictureData already exists in the db
			for ( PictureData pdExisting : pictureDataExisting ) {

				if ( pdPossibleNew.getId() == pdExisting.getId() ) {			
					pictureDataExists = true;

					// PictureData exists. Need to check if there are new hashtags to add
					Set<String> newHashtags = checkForNewHashtags(pdExisting, pdPossibleNew);

					if ( !newHashtags.isEmpty() ) {
						// Add new hashtags to existing pictureData and add to modified list
						for ( String ht : newHashtags ) {
							pdExisting.addHashtag(new Hashtag(ht));
						}
						pictureDataModified.add(pdExisting);
						
						// Create object holding existing pictureId and new hashtags
						PictureIdHashtags idHash = new PictureIdHashtags(pdExisting.getId(), newHashtags);
						existingPicIdNewHashtags.add(idHash);	
					}

					break;
				}				
			}

			// Picture data doesn't exist - add to lists
			if ( !pictureDataExists ) {
				pictureDataNew.add(pdPossibleNew);
				pictureDataExisting.add(pdPossibleNew);
			}
		}	
		// Sort - Don't think this is needed
		//Collections.sort(pictureDataNew, new PictureDataComparator());
		//Collections.sort(pictureDataModified, new PictureDataComparator());
	}

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

	public List<PictureData> getPictureDataExisting() {
		return pictureDataExisting;
	}

	public List<PictureData> getPictureDataNew() {
		return pictureDataNew;
	}

	public List<PictureData> getPictureDataModified() {
		return pictureDataModified;
	}
}