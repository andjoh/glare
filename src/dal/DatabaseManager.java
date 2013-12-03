package dal;

import java.util.*;

import bll.PictureDataComparator;

public class DatabaseManager {
	private final int MAX_SIZE = 100;

	public DatabaseManager(DatabaseHandler databaseHandler){	
	}

	/**
	 * Return sorted List of PictureData objects that can be displayed, i.e. no inappropriate.
	 * @return List of PictureData
	 */
	public List<PictureData> getSortedPictureData() {		
		List<PictureData> pictureData = new ArrayList<PictureData>();
		List<PictureData> pictureDataFromDb = DatabaseHandler.listOfPicturesFromDB();

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
		
		// Remove picture data without any hashtag
		removePicturesWithoutHashtagFromDB();
	}

	public List<String> getSources() {
		// TODO This should not be hardcoded
		List<String> sources = new ArrayList<String>();
		sources.add("Instagram");
		sources.add("Twitter");

		return sources;
	}
}