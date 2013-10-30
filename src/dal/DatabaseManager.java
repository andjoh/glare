package dal;

import java.util.*;

public class DatabaseManager {

	private List<HashtagBySource> hashtagBySource;
	private List<PictureData> pictureDataFromDb;


	public DatabaseManager(){
		pictureDataFromDb = new ArrayList<PictureData>();
		hashtagBySource   = new ArrayList<HashtagBySource>();
	}


	public List<HashtagBySource> getHashtagBySource() {
		return hashtagBySource;
	}

	/*
	 * Replace the existing List with new objects
	 */
	public void setHashtagBySource(List<HashtagBySource> htbs) {
		hashtagBySource = htbs;
	}

	/*
	 * Add new hashtag, from source, to existing List
	 */
	public void updateHashtagBySource(String source, Set<String> hashtag) {
		boolean hashtagAdded = false;
		
		for ( HashtagBySource htbs : hashtagBySource ) {
			if ( htbs.getSource().equalsIgnoreCase(source) ) {
				// If this should work for both add and remove we need to clear before add
				//htbs.clearHashtag();
				htbs.setHashtag(hashtag);				
				hashtagAdded = true;
			}
		}
		
		if ( !hashtagAdded ) {
			HashtagBySource newHtbs = new HashtagBySource();
			newHtbs.setSource(source);
			newHtbs.setHashtag(hashtag);
			hashtagBySource.add(newHtbs);
		}

		// TODO Call metode to update db
	}


	public List<PictureData> getPictureDataFromDb() {
		return pictureDataFromDb;
	}


	public void setPictureDataFromDb(List<PictureData> pictureDataFromDb) {
		this.pictureDataFromDb = pictureDataFromDb;
	}


	public void savePictureDataToDb(List<PictureData> pictureData) {
		// TODO Call metode to update db
	}
	
	
	public void setRemoveFlag(List<String> pictureDataId) {
		// TODO Call metode to update db
	}
}