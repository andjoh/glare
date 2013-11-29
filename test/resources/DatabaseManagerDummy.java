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

	public void removeHashtags(Set<String> hashtags) {
		for(String s: hashtags){
			hashtags.remove(s);
		}
	}
	
	public void setHashtags(Set<String> hashtags) {
		this.hashtags = hashtags;
	}	
}
