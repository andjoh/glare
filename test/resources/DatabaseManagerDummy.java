package resources;

import java.util.*;

import dal.*;


public class DatabaseManagerDummy extends DatabaseManager {
	private List<PictureData> pictureDataFromDb;
	private List<String> sources;
	private Set<String> hashtags;
	
	public DatabaseManagerDummy(DatabaseHandler databaseHandler) {
		super(databaseHandler);
		pictureDataFromDb = new ArrayList<PictureData>();
		sources           = new ArrayList<String>();
		hashtags          = new HashSet<String>();
	}

	public List<PictureData> getPictureDataFromDb() {
		return pictureDataFromDb;
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
	
	public void setHashtags(Set<String> hashtags) {
		this.hashtags = hashtags;
	}	
}
