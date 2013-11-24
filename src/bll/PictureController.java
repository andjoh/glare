package bll;

import glare.*;
import dal.*;

import java.util.*;

public class PictureController {
	private DatabaseManager databaseManager;
	
	private List<PictureData> pictureDataFromSources;
	private List<PictureData> pictureDataNew;
	private List<PictureIdAndHashtags> existingPicIdNewHashtags;
	
	private final int MAX_SIZE = 100;
	
	public PictureController(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}
	
	/*
	 * This is the method that the service/server application should call.
	 * This method will use internal methods to retrieve pictureData
	 * from sources, process the data, and save to database
	 */
	public void getNewPictureData() {
		
		// Search for picturesData
		boolean success = searchPictureData();
		
		// If found pictureData: Process the data and save to db
		if ( success ) {
			processPictureData();
			
			// If new pictureData found: Add to db
			if ( !pictureDataNew.isEmpty() ) {			
				//databaseManager.saveNewPictureData(pictureDataNew);
				
				System.out.println("PictureData before save to db");
				for ( PictureData pd : pictureDataNew )
				{
					System.out.println(pd.getId());
				}
				System.out.println("");				
				databaseManager.savePictureDataToDb(pictureDataNew);
			}
			
			// If new hashtags on existing pictureData: Save to db
			if ( !existingPicIdNewHashtags.isEmpty() ) {			
				//databaseManager.saveHashtagsToExistingPictureData(existingPicIdNewHashtags);
			}	
		}
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
				pictureDataFromSources.addAll(searchPictureDataFromHashtag(reader, hashtag));	
			}
		}
		if ( pictureDataFromSources.isEmpty() )
			return false;
				
		return true;
	}

	private List<PictureData> searchPictureDataFromHashtag(IReader reader, String hashtag) {	
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
	 * Get existing pictureData from db and process new pictureData from sources
	 * Outcome will be a list of new pictureData, and a list of
	 * new hashtags connected to existing pictureData
	 * These lists can be used to update db
	 */	
	public void processPictureData() {
		List<PictureData> pictureDataExisting = databaseManager.getPictureDataFromDb();
		pictureDataNew = new ArrayList<PictureData>();
		existingPicIdNewHashtags = new ArrayList<PictureIdAndHashtags>();

		System.out.println("PictureData from db");
		for ( PictureData pd : pictureDataExisting )
		{
			System.out.println(pd.getId());
		}
		System.out.println("");	
		
		System.out.println("PictureData from sources");
		for ( PictureData pd : pictureDataFromSources )
		{
			System.out.println(pd.getId());
		}
		System.out.println("");			
		
		// Iterate over pictureData from sources and add to new pictureData
		// If pictureData exists: add new hashtags
		boolean pictureDataFound, hashtagFound;
		Set<String> hashtagsExisting;
		String hashtagPossibleNew;
		Set<String> hashtagsNew;
		
		for ( PictureData pictureDataPossibleNew : pictureDataFromSources ) {
			
			pictureDataFound = false;
			
			for ( PictureData pictureDataExt : pictureDataExisting ) {
				
				// Check if picture data already exists
				if ( pictureDataExt.getId() == pictureDataPossibleNew.getId() ) {			
					
					// PictureData already exists. Add new hashtags				
					hashtagsExisting = new HashSet<String>();
					
					for ( Hashtag hashtagObject : pictureDataExt.getHashtags() ){
						hashtagsExisting.add(hashtagObject.getHashtag());
					}
					
					hashtagsNew = new HashSet<String>();
					for ( Hashtag hashtagObject : pictureDataPossibleNew.getHashtags() ){
						hashtagPossibleNew = hashtagObject.getHashtag();
						hashtagFound = false;
						for ( String extHt : hashtagsExisting ) {
							if ( hashtagPossibleNew == extHt ) {
								hashtagFound = true;
								break;
							}
						}
						
						if ( hashtagFound ) {
							hashtagsNew.add(hashtagPossibleNew);
						}						
					}
					
					// Don't think we need this anymore
					//for ( Hashtag hashtagObject : pictureDataPossibleNew.getHashtags() )
					//	hashtagsExisting.add(hashtagObject.getHashtag());

					//for ( String hashtag : hashtagsExisting )
					//	pictureDataExt.addHashtag(new Hashtag(hashtag));					
					
					if ( !hashtagsNew.isEmpty() ) {
						PictureIdAndHashtags idHash = new PictureIdAndHashtags(pictureDataExt.getId(), hashtagsNew);
						existingPicIdNewHashtags.add(idHash);	
					}
				
					pictureDataFound = true;
					break;
				}				
			}
			
			// Picture data doesn't exist - add it
			if ( !pictureDataFound ) {
				// TODO Instead of adding new to existing we could add second hashtag to new picture...
				pictureDataNew.add(pictureDataPossibleNew);
				pictureDataExisting.add(pictureDataPossibleNew);   // To avoid adding duplicates
			}
		}
	}
		
	/**
	 * Return sorted List of PictureData objects that can be displayed, i.e. no inappropriate.
	 * @return List of PictureData
	 */
	public List<PictureData> getSortedPictureData() {		
		List<PictureData> pictureData = new ArrayList<PictureData>();
		List<PictureData> pictureDataFromDb = databaseManager.getPictureDataFromDb();
		
		int i = 0;
		for ( PictureData pD : pictureDataFromDb ) {
			if ( !pD.isRemoveFlag() ) {
				pictureData.add(pD);
				if (++i >= MAX_SIZE){ 
					break;
				}
			}
		}
		
		Collections.sort(pictureData, new PictureDataComparator());
		
		return pictureData;
	}
	
	public List<PictureData> getPictureDataFromSources() {
		return pictureDataFromSources;
	}
}