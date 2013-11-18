package unit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import dal.DatabaseHandler;
import dal.Hashtag;
import dal.PictureData;

public class HibernateAddAndListHashtagTest {

	ArrayList<String> hashtags;
	
	@Test
	public void AddHashtagsToDB_ReturnListWithHashtagsFromDB() {
		Hashtag h1 = new Hashtag("winter");
		Hashtag h2 = new Hashtag("summer");
		Hashtag h3 = new Hashtag("hibernate");
		
//		PictureData pic = new PictureData();
//		pic.setId("1247845_2455");
//		pic.setUrlStd("www.twitter.com");
//		pic.setUrlThumb("www.instagram.com");
//		pic.setCreatedTime(123456);
//		pic.setRemoveFlag(false);
//		pic.addHashtag(h1);
//		pic.addHashtag(h2);
//		DatabaseHandler.addPictureToDB(pic);
//
//		PictureData pic2 = new PictureData();
//		pic2.setId("1247845_57891A");
//		pic2.setUrlStd("www.twitter.com");
//		pic2.setUrlThumb("www.instagram.com");
//		pic2.setCreatedTime(123456);
//		pic2.setRemoveFlag(false);
//		pic2.addHashtag(h2);
//		DatabaseHandler.addPictureToDB(pic2);
		
		DatabaseHandler.addHashtagToDB(h1);
		DatabaseHandler.addHashtagToDB(h2);
		DatabaseHandler.addHashtagToDB(h3);
		
		hashtags = (ArrayList<String>) DatabaseHandler.listOfHashtagsFromDB();
		
		for(String ht: hashtags){
			System.out.println(ht.toString());
		}
		
		assertThat(hashtags.size(), is(not(nullValue())));
	}

}
