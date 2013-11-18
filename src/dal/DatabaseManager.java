package dal;

import java.util.*;

public class DatabaseManager {

	public DatabaseManager(DatabaseHandler databaseHandler){	
	}

	public List<PictureData> getPictureDataFromDb() {
		return DatabaseHandler.listOfPicturesFromDB();
	}
	
	public void removePicturesWithoutHashtagFromDB(){
		DatabaseHandler.removePicturesWithoutHashTagFromDB();
	}
	
	public void savePictureDataToDb(List<PictureData> pictureData) {
		for (PictureData pd: pictureData){
			DatabaseHandler.addPictureToDB(pd);
		}
	}

	public void setRemoveFlag(List<String> pictureDataId) {
		for (String s: pictureDataId){
			DatabaseHandler.setRemoveFlag(s);
		}
	}

	@SuppressWarnings("unchecked")
	public Set<String> getHashtags() {
		return (Set<String>) DatabaseHandler.listOfHashtagsFromDB();
	}

	public void addHashtags(Set<String> hashtags) {
		for (String s: hashtags){
			Hashtag ht = new Hashtag(s);
			DatabaseHandler.addHashtagToDB(ht);
		}
	}

	public void removeHashtags(Set<String> hashtags) {
		for(String s: hashtags){
			DatabaseHandler.removeHashtagFromDB(s);
		}
	}

	public List<String> getSources() {
		// TODO This should not be hardcoded
		List<String> sources = new ArrayList<String>();
		sources.add("Instagram");
		sources.add("Twitter");

		return sources;
	}
}