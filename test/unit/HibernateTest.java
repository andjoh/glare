package unit;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dal.DatabaseHandler;
import dal.Hashtag;
import dal.PictureData;

public class HibernateTest{
	ArrayList<PictureData> pictures;
	ArrayList<String> hashtags;

	@Before
	public void setUp() throws Exception {
		Hashtag h1 = new Hashtag("winter");
		Hashtag h2 = new Hashtag("summer");
		Hashtag h3 = new Hashtag("hibernate");
		
		PictureData pic = new PictureData();
		pic.setId("1247845_2455");
		pic.setUrlStd("www.twitter.com");
		pic.setUrlThumb("www.instagram.com");
		pic.setCreatedTime(123456);
		pic.setRemoveFlag(false);
		pic.addHashtag(h1);
		pic.addHashtag(h3);
		DatabaseHandler.addPictureToDB(pic);
		
		PictureData pic2 = new PictureData();
		pic2.setId("1247845_57891A");
		pic2.setUrlStd("www.twitter.com");
		pic2.setUrlThumb("www.instagram.com");
		pic2.setCreatedTime(123456);
		pic2.setRemoveFlag(false);
		pic2.addHashtag(h2);
		pic2.addHashtag(h3);
		DatabaseHandler.addPictureToDB(pic2);
		
		PictureData pic3 = new PictureData();
		pic3.setId("3_218419");
		pic3.setUrlStd("www.twitter.com");
		pic3.setUrlThumb("www.instagram.com");
		pic3.setCreatedTime(123456);
		pic3.setRemoveFlag(false);
		pic3.addHashtag(h2);
		DatabaseHandler.addPictureToDB(pic3);

		//DatabaseHandler.removeHashtagFromDB("summer");
		//DatabaseHandler.removePicturesWithoutHashTagFromDB();		
		
		pictures = (ArrayList<PictureData>) DatabaseHandler.listOfPicturesFromDB();
		hashtags = (ArrayList<String>) DatabaseHandler.listOfHashtagsFromDB();
		
		
		for(PictureData pic1: pictures){
			System.out.println(pic1.getId() + " " + pic1.getUrlStd() + ": " + pic1.getUrlThumb() + " "
					+ pic1.getCreatedTime() + " " + pic1.isRemoveFlag());
		}
		
		for(String ht: hashtags){
			System.out.println(ht.toString());
		}
	}

	@After
	public void tearDown() throws Exception {
		//pictures = new ArrayList<PictureData>();
		//hashtags = new ArrayList<String>();
	}

	@Test
	public void AddPictureToDB_ReturnsListWithPicturesFromDB() {
		assertThat(pictures.size(), is(not(nullValue())));
	}
	@Test
	public void ListHashtagsFromDB_ReturnsListWithHashtagsFromDB(){
		assertThat(hashtags.size(), is(not(nullValue())));
	}
	//@Test
	public void DeletePicturesWithoutHashtag_CheckHowManyPicturesLeft(){
		assertThat(pictures.size(), is(2));
	}
}
