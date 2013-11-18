package unit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dal.DatabaseHandler;
import dal.Hashtag;
import dal.PictureData;

public class HibernateDuplicatesTest {
	ArrayList<String> hashtags;
	ArrayList<PictureData> pics;

	@Test
	public void AddHashtagsToDB_ReturnListWithHashtagsFromDB() {
		Hashtag h1 = new Hashtag("winter");
		Hashtag h2 = new Hashtag("winter");
		Hashtag h3 = new Hashtag("summer");
		
		DatabaseHandler.addHashtagToDB(h1);
		DatabaseHandler.addHashtagToDB(h2);
		DatabaseHandler.addHashtagToDB(h3);
		
		hashtags = (ArrayList<String>) DatabaseHandler.listOfHashtagsFromDB();
		
		for(String ht: hashtags){
			System.out.println(ht.toString());
		}
		
		assertThat(hashtags.size(), is(2));
	}
	
	@Test
	public void AddPicturesToDB_ReturnListWithPicturesFromDB(){
		PictureData pic = new PictureData();
		pic.setId("1247845_2455");
		pic.setUrlStd("www.twitter.com");
		pic.setUrlThumb("www.instagram.com");
		pic.setCreatedTime(123456);
		pic.setRemoveFlag(false);
		DatabaseHandler.addPictureToDB(pic);
		
		PictureData pic3 = new PictureData();
		pic3.setId("1247845_2455");
		pic3.setUrlStd("www.twitter.com");
		pic3.setUrlThumb("www.instagram.com");
		pic3.setCreatedTime(123456);
		pic3.setRemoveFlag(false);
		DatabaseHandler.addPictureToDB(pic3);
		
		PictureData pic2 = new PictureData();
		pic2.setId("10_2455");
		pic2.setUrlStd("www.twitter.com");
		pic2.setUrlThumb("www.instagram.com");
		pic2.setCreatedTime(123456);
		pic2.setRemoveFlag(true);
		DatabaseHandler.addPictureToDB(pic2);
		
		pics = (ArrayList<PictureData>) DatabaseHandler.listOfPicturesFromDB();
		
		for(PictureData pic1: pics){
			System.out.println(pic1.getId() + " " + pic1.getUrlStd() + ": " + pic1.getUrlThumb() + " "
					+ pic1.getCreatedTime() + " " + pic1.isRemoveFlag());
		}
		
		assertThat(pics.size(), is(2));
	}

}
