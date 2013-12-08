package resources;

import java.util.*;
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

		// Process current pictureData list
		Set<String> pdToBeRemoved = new HashSet<String>();
		Set<Hashtag> hashtagObj;
		for ( PictureData pd : pictureDataFromDb ) {

			// Check and delete hashtags for current picture data
			hashtagObj = pd.getHashtags();
			for ( String htDel : hashtagsDeleted ) {
				for ( Hashtag htObj : hashtagObj ) {
					if ( htObj.getHashtag().equalsIgnoreCase(htDel) ) {
						hashtagObj.remove(htObj);
					}				
				}
			}

			// Check if all hashtags for current picture is removed
			// If removed - delete picture data
			if ( pd.getHashtags().isEmpty() ) {
				pdToBeRemoved.add(pd.getId());
			}
		}

		for ( String picId : pdToBeRemoved ) {
			for ( PictureData pd : pictureDataFromDb ) {						
				if ( pd.getId().equalsIgnoreCase(picId) ) {
					pictureDataFromDb.remove(pd);
					break;
				}
			}				
		}
	}

	public void setRemoveFlag(Set<String> pictureDataId) {
		for (String s: pictureDataId){
			DatabaseHandler.setRemoveFlag(s);
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
		removePicturesWithoutHashtagFromDB(hts);
	}

	public void setHashtags(Set<String> hashtags) {
		this.hashtags = hashtags;
	}	

	private class PictureDataComparator implements Comparator<PictureData> {
		public int compare(PictureData pd1, PictureData pd2) {
			return (int) (pd2.getCreatedTime() - pd1.getCreatedTime());
		}
	}
}
