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

	public Set<String> getHashtags() {
		Set<String> hashtags     = new HashSet<String>();
		List<Hashtag> hashtagObj = databaseHandler.listOfHashtagsFromDB();
		for ( Hashtag ht : hashtagObj )
			hashtags.add(ht.getHashtag());

		return hashtags;
	}

	public void addHashtags(Set<String> hashtags) {
		// TODO Invoke metod to add hashtags
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