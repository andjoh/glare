package dal;

import java.util.*;

public class DatabaseManager {
	private DatabaseHandler databaseHandler;

	public DatabaseManager(DatabaseHandler databaseHandler){		
	}

	public List<PictureData> getPictureDataFromDb() {
		// TODO Invoke method from DatabaseHandler
		return DatabaseHandler.listOfPicturesFromDB();
	}
	
	public void savePictureDataToDb(List<PictureData> pictureData) {
		// TODO Call metode to update db
	}

	public void setRemoveFlag(List<String> pictureDataId) {
		// TODO Call metode to update db
	}

	@SuppressWarnings("unchecked")
	public Set<String> getHashtags() {
		return (Set<String>) DatabaseHandler.listOfHashtagsFromDB();
	}

	public void addHashtags(Set<String> hashtags) {
		// TODO Invoke method to add hashtags
		// Consider creating Hashtag objekts here		
	}

	public void removeHashtags(Set<String> hashtags) {
		// TODO Invoke metod to remove hashtags		
	}

	public List<String> getSources() {
		// TODO This should not be hardcoded
		List<String> sources = new ArrayList<String>();
		sources.add("Instagram");
		sources.add("Twitter");

		return sources;
	}
}