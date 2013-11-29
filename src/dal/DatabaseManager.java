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
		
		List<PictureData> pics = DatabaseHandler.listOfPicturesFromDB();
		for(PictureData p : pics){
			DatabaseHandler.removePictureDataFromDB(p.getId());
		}
		
		for (PictureData pd: pictureData){
			System.out.println(pd.getId());
			for ( Hashtag ht : pd.getHashtags())
				System.out.println(ht.getHashtag());
			System.out.println("");

			DatabaseHandler.addPictureToDB(pd);
		}
	}

	public void setRemoveFlag(Set<String> pictureDataId) {
		for (String s: pictureDataId){
			DatabaseHandler.setRemoveFlag(s);
			System.out.println("setRemoveflag, ID: "+s);
		}
	}

	public Set<String> getHashtags() {
		Set<String> hashtags = new HashSet<String>();
		for ( String ht : DatabaseHandler.listOfHashtagsFromDB() )
			hashtags.add(ht);
		
		return hashtags;
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