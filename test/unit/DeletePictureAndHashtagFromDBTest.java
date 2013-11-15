package unit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dal.DatabaseHandler;
import dal.Hashtag;
import dal.PictureData;

public class DeletePictureAndHashtagFromDBTest {
	ArrayList<PictureData> pictures;
	ArrayList<String> hashtags;
	
	@Before
	public void setUp() throws Exception {
		Hashtag h1 = new Hashtag("winter");
		Hashtag h2 = new Hashtag("summer");
		
		PictureData pic = new PictureData();
		pic.setId("1247845_2455");
		pic.setUrlStd("www.twitter.com");
		pic.setUrlThumb("www.instagram.com");
		pic.setCreatedTime(123456);
		pic.setRemoveFlag(false);
		pic.addHashtag(h1);
		DatabaseHandler.addPictureToDB(pic);
		
		PictureData pic2 = new PictureData();
		pic2.setId("1247845_57891A");
		pic2.setUrlStd("www.twitter.com");
		pic2.setUrlThumb("www.instagram.com");
		pic2.setCreatedTime(123456);
		pic2.setRemoveFlag(false);
		pic2.addHashtag(h2);
		pic2.addHashtag(h1);
		DatabaseHandler.addPictureToDB(pic2);
		
		DatabaseHandler.removeHashtagFromDB("winter");
		DatabaseHandler.removePicturesWithoutHashTagFromDB();		
		
		pictures = (ArrayList<PictureData>) DatabaseHandler.listOfPicturesFromDB();
		hashtags = (ArrayList<String>) DatabaseHandler.listOfHashtagsFromDB();
	}

	@After
	public void tearDown() throws Exception {
		pictures = new ArrayList<PictureData>();
		hashtags = new ArrayList<String>();
	}

	//@Test
	public void DeletePicturesWithoutHashtag_ReturnListOfPicturesLeft(){
		assertThat(pictures.size(), is(1));
	}
	
	@Test
	public void DeleteHashtagFromDB_ReturnListOfHashtagsLeft(){
		assertThat(hashtags.size(), is(1));
	}

}
