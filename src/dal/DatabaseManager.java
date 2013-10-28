package dal;

import java.util.*;

public class DatabaseManager {

	private List<HashtagBySource> hashtagBySource;
	private List<PictureData> pictureDataFromDb;


	public DatabaseManager(){
		pictureDataFromDb = new ArrayList<PictureData>();
		hashtagBySource   = new ArrayList<HashtagBySource>();
	}


	public void updateHashtagBySource(String source, List<String> hashtag) {
		// TODO Auto-generated method stub

	}


	public List<HashtagBySource> getHashtagBySource() {
		return hashtagBySource;
	}


	public void setHashtagBySource(List<HashtagBySource> htbs) {
		hashtagBySource = htbs;
	}


	public List<PictureData> getPictureDataFromDb() {
		return pictureDataFromDb;
	}


	public void setPictureDataFromDb(List<PictureData> pictureDataFromDb) {
		this.pictureDataFromDb = pictureDataFromDb;
	}


	public void savePictureData(List<PictureData> pictureData) {
		// TODO Auto-generated method stub

	}
}