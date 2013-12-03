package resources;

import java.util.*;

import bll.PictureDataComparator;

import dal.*;

public class DatabaseManagerDummy extends DatabaseManager {
	private List<PictureData> pictureDataFromDb;
	private List<String> sources;
	private Set<String> hashtags;

	private final int MAX_SIZE = 100;

	public DatabaseManagerDummy(DatabaseHandler databaseHandler) {
		super(databaseHandler);
		pictureDataFromDb = new ArrayList<PictureData>();
		sources           = new ArrayList<String>();
		hashtags          = new HashSet<String>();
	}

	/**
	 * Return sorted List of PictureData objects that can be displayed, i.e. no inappropriate.
	 * @return List of PictureData
	 */
	public List<PictureData> getSortedPictureData() {		
		List<PictureData> pictureData = new ArrayList<PictureData>();

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
	
	public List<PictureData> getPictureDataFromDb() {
		return pictureDataFromDb;
	}
	
	public void removePicturesWithoutHashtagFromDB(Set<String> hashtagsDeleted){
		System.out.println("DatabaseManagerDummy: removePicturesWithoutHashtagFromDB");

		// Process current pictureData list
		Set<String> pdToBeRemoved = new HashSet<String>();
		Set<Hashtag> hashtagObj;
		for ( PictureData pd : pictureDataFromDb ) {
			
			// Check and delete hashtags for current picture data
			hashtagObj = pd.getHashtags();
			for ( String htDel : hashtagsDeleted ) {
				//System.out.println("Searching for hashtag " + htDel + " If found - delete hashtag obj for picture data " + pd.getId());
				for ( Hashtag htObj : hashtagObj ) {
					if ( htObj.getHashtag().equalsIgnoreCase(htDel) ) {
						//System.out.println("Hashtag found for picture data " + pd.getId() + " Remove ht obj");
						hashtagObj.remove(htObj);
					}				
				}
			}
			
			// Check if all hashtags for current picture is removed
			// If removed - delete picture data
			//System.out.println("Check if hashtaglist is empty for picture data " + pd.getId());
			if ( pd.getHashtags().isEmpty() ) {
				//System.out.println("Hashtaglist is empty for picture data " + pd.getId() + " remove picture data from all lists");
				pdToBeRemoved.add(pd.getId());
			}
		}

		System.out.println("");
		System.out.println("DatabaseManagerDummy: removePicturesWithoutHashtagFromDB: PicIds to be removed:");		
		for ( String picId : pdToBeRemoved ) {
			for ( PictureData pd : pictureDataFromDb ) {						
				
				if ( pd.getId().equalsIgnoreCase(picId) ) {
					System.out.println(pd.getId());
					for ( Hashtag htObj : pd.getHashtags() ) {
						System.out.println(" - " + htObj.getHashtag());
					}	
					pictureDataFromDb.remove(pd);
					break;
				}
			}				
		}
		System.out.println("");				
	}
	
	public void setRemoveFlag(Set<String> pictureDataId) {
		for (String s: pictureDataId){
			DatabaseHandler.setRemoveFlag(s);
			System.out.println("setRemoveflag, ID: "+s);
		}
	}
	public void setPictureDataFromDb(List<PictureData> pictureData) {
		this.pictureDataFromDb = pictureData;
	}

	public void savePictureDataToDb(List<PictureData> pictureData) {
		this.pictureDataFromDb = pictureData;
	}
	
	public void setSources(List<String> sources) {
		this.sources = sources;
	}
	
	public List<String> getSources() {		
		return sources;
	}
	
	public Set<String> getHashtags() {
		return hashtags;
	}

	public void addHashtags(Set<String> hashtags) {
		for (String s: hashtags){
			hashtags.add(s);
		}
	}

	public void removeHashtags(Set<String> hts) {
		System.out.println("DatabaseManagerDummy: removeHashtags");	
		for(String ht: hts){
			System.out.println("Hashtag to be removed from db: " + ht);
			hashtags.remove(ht);
		}
		
		// Remove picture data without any hashtag
		removePicturesWithoutHashtagFromDB(hts);
	}
	
	public void setHashtags(Set<String> hashtags) {
		this.hashtags = hashtags;
	}	
}
