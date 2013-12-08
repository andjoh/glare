package bll;

import glare.*;
import dal.*;

import java.util.*;

/**
 * Controller for getting, processing and sending new picture data to database
 * @author Petter Austerheim
 */
public class PictureController {

	private DatabaseManager databaseManager;
	private List<PictureData> pictureDataFromSources;
	private List<PictureData> pictureDataToSave; 
	
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

			if ( !pictureDataToSave.isEmpty() ) {
				databaseManager.savePictureDataToDb(pictureDataToSave);
				success = true;
			}
		}			

		return success;
	}

	/**
	 * Search for picture data on sources with given hashtags
	 * Save list to class variable pictureDataFromSources.
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

		// Remove duplicated picture data fetched from sources
		List<PictureData> pictureDataPossibleNew = new ArrayList<PictureData>(pictureDataFromSources);
		pictureDataPossibleNew = removeDuplicatePictureData(pictureDataPossibleNew);

		// Get existing pictureData from db
		List<PictureData> pictureDataExisting = databaseManager.getPictureDataFromDb();

		pictureDataToSave = new ArrayList<PictureData>();	
		Set<String> newHashtags;
		boolean pictureDataExists;

		// Iterate over picture data from sources
		for ( PictureData pdPossibleNew : pictureDataPossibleNew ) {
			pictureDataExists = false;
			
			// Check if this pictureData already exists in the db
			for ( PictureData pdExisting : pictureDataExisting ) {

				if ( pdPossibleNew.getId().equals(pdExisting.getId()) ) {			
					pictureDataExists = true;

					// Check if hashtag connected to possible new is already connected to existing picture data
					// TODO Denne må flyttes opp
					newHashtags = new HashSet<String>();
					newHashtags = checkForNewHashtags(pdExisting, pdPossibleNew);
					
					// If new hashtags - add to existing for further check of possible new picture data,
					// and add possible new, with existing remove flag to data-to-save list
					if ( !newHashtags.isEmpty() ) {
						for ( String ht : newHashtags ) 
							pdExisting.addHashtag(new Hashtag(ht));
						pdPossibleNew.setRemoveFlag(pdExisting.isRemoveFlag());
						pictureDataToSave.add(pdPossibleNew);
					}
					break;
				}
			}
			
			// Picture data doesn't exist - add to list
			if ( !pictureDataExists )			
				pictureDataToSave.add(pdPossibleNew);
		}	
	}

	/**
	 * Search on Instagram and Twitter might give same picture with the same hashtag, 
	 * i.e. duplicate PictureData object. This method removes those duplicates
	 * @param pictureData
	 * @return List of PictureData without duplicates
	 */
	private List<PictureData> removeDuplicatePictureData(List<PictureData> pictureData) {

		// Make sure the list is sorted
		Collections.sort(pictureData, new SortPictureDataByIdAndHt());

		// We only need one object with same id and hashtag
		Iterator<PictureData> iter = pictureData.iterator();
		PictureData firstPd = iter.next();
		PictureData secPd;
		String firstHt, secHt;
		while ( iter.hasNext() ) {
			secPd = iter.next();
			if ( secPd.getId().equals(firstPd.getId()) ) {
				firstHt = firstPd.getHashtags().iterator().next().getHashtag();
				secHt   = secPd.getHashtags().iterator().next().getHashtag();
				if ( secHt.equals(firstHt) ) {
					iter.remove();
				}
			}
			firstPd = secPd;
		}
		
		return pictureData;
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
	
	public List<PictureData> getPictureDataToSave() {
		return pictureDataToSave;
	}
	
	/**
	 * Utility to sort picturedata by id and hashtag
	 * @author Petter Austerheim
	 *
	 */
	private class SortPictureDataByIdAndHt implements Comparator<PictureData> {
	    public int compare(PictureData pd1, PictureData pd2) {
	    	int value = (int) (pd1.getId().compareTo(pd2.getId()));
	    	if ( value == 0 ) {
	    		value+= (int) (pd1.getHashtags().iterator().next().getHashtag().compareTo(pd2.getHashtags().iterator().next().getHashtag()));
	    	}
	    	return value;
	    }
	}
}