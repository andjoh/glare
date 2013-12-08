package integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

import dal.DatabaseHandler;
import dal.Hashtag;
import dal.PictureData;

public class HibernatePictureTests {
	ArrayList<PictureData> pictures;

	@Test
	public void UpdateExistingPictureWithNewHashtag_ReturnListOfPictures() {
		Hashtag hash = new Hashtag("winter");
		
		PictureData pic = new PictureData();
		pic.setId("1247845_2455");
		pic.setUrlStd("www.twitter.com");
		pic.setUrlThumb("www.instagram.com");
		pic.setCreatedTime(123456);
		pic.setRemoveFlag(false);
		pic.addHashtag(hash);
		DatabaseHandler.addPictureToDB(pic);
		
		Hashtag h2 = new Hashtag("summer");
		
		PictureData pic2 = new PictureData();
		pic2.setId("1247845_2455");
		pic2.setUrlStd("www.twitter.com");
		pic2.setUrlThumb("www.instagram.com");
		pic2.setCreatedTime(123456);
		pic2.setRemoveFlag(false);
		pic2.addHashtag(h2);
		DatabaseHandler.addPictureToDB(pic2);
		
		
		pictures = (ArrayList<PictureData>) DatabaseHandler.listOfPicturesFromDB();
		
		assertThat(pictures.size(), is(1));
	}

	@Test
	public void AddAndRemovePictureDataTest_ReturnsEmptyListOfPicturesFromDB() {
		Hashtag hash = new Hashtag("winter");
		
		PictureData pic = new PictureData();
		pic.setId("1247845_2455");
		pic.setUrlStd("www.twitter.com");
		pic.setUrlThumb("www.instagram.com");
		pic.setCreatedTime(123456);
		pic.setRemoveFlag(false);
		pic.addHashtag(hash);
		DatabaseHandler.addPictureToDB(pic);
		
		DatabaseHandler.removePictureDataFromDB(pic.getId());
		pictures = (ArrayList<PictureData>) DatabaseHandler.listOfPicturesFromDB();
		
		assertThat(pictures.size(), is(0));
	}
	
	
	@Test
	public void AddExistingPictureToDB_ReturnListWithPicturesFromDB_CheckThatSizeIsOne(){
		PictureData pic = new PictureData();
		pic.setId("1247845_2455");
		pic.setUrlStd("www.twitter.com");
		pic.setUrlThumb("www.instagram.com");
		pic.setCreatedTime(123456);
		pic.setRemoveFlag(false);
		DatabaseHandler.addPictureToDB(pic);
		
		ArrayList<PictureData> pics = (ArrayList<PictureData>) DatabaseHandler.listOfPicturesFromDB();
		
		assertThat(pics.size(), is(1));
	}

	
	@Test
	public void SetRemoveFlagOnPicture_ReturnListOfPicturesFromDB() {
		PictureData pic = new PictureData();
		pic.setId("1247845_2455");
		pic.setUrlStd("www.twitter.com");
		pic.setUrlThumb("www.instagram.com");
		pic.setCreatedTime(123456);
		pic.setRemoveFlag(false);
		
		DatabaseHandler.addPictureToDB(pic);
		DatabaseHandler.setRemoveFlag(pic.getId());
		
		pictures = (ArrayList<PictureData>) DatabaseHandler.listOfPicturesFromDB();
		
		assertThat(pictures.get(0).isRemoveFlag(), is(true));
	}
	
	
	@Test
	public void RemovePicturesWithoutHashtagFromDB_ReturnListOfPicturesFromDB(){
		Hashtag hash = new Hashtag("winter");
		
		PictureData pic = new PictureData();
		pic.setId("1247845_2455");
		pic.setUrlStd("www.twitter.com");
		pic.setUrlThumb("www.instagram.com");
		pic.setCreatedTime(123456);
		pic.setRemoveFlag(false);
		pic.addHashtag(hash);
		DatabaseHandler.addPictureToDB(pic);
		DatabaseHandler.setRemoveFlag(pic.getId());
		
		PictureData pic2 = new PictureData();
		pic2.setId("142672");
		pic2.setUrlStd("www.twitter.com");
		pic2.setUrlThumb("www.instagram.com");
		pic2.setCreatedTime(123456);
		pic2.setRemoveFlag(false);
		pic2.addHashtag(hash);
		DatabaseHandler.addPictureToDB(pic2);
		
		DatabaseHandler.removeHashtagFromDB(hash.getHashtag());
		DatabaseHandler.removePicturesWithoutHashTagFromDB();
		
		pictures = (ArrayList<PictureData>) DatabaseHandler.listOfPicturesFromDB();
		
		assertThat(pictures.size(), is(1));
	}

}
