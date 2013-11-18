package unit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import dal.DatabaseHandler;
import dal.Hashtag;
import dal.PictureData;

public class HibernateRemovePicturesWithoutHashtagTest {

	ArrayList<PictureData> pictures;
	
	@Test
	public void AddPicturesToDB_ReturnsListOfPicturesFromDB() {
		Hashtag h1 = new Hashtag("test1");
		Hashtag h2 = new Hashtag("test2");
		
		//Insert picture with hashtag
		PictureData pic = new PictureData();
		pic.setId("1247845_2455");
		pic.setUrlStd("www.twitter.com");
		pic.setUrlThumb("www.instagram.com");
		pic.setCreatedTime(123456);
		pic.setRemoveFlag(false);
		pic.addHashtag(h1);
		DatabaseHandler.addPictureToDB(pic);

		//Insert picture without hashtag
		PictureData pic2 = new PictureData();
		pic2.setId("1247845_57891A");
		pic2.setUrlStd("www.twitter.com");
		pic2.setUrlThumb("www.instagram.com");
		pic2.setCreatedTime(123456);
		pic2.setRemoveFlag(false);
		DatabaseHandler.addPictureToDB(pic2);
		
		PictureData pic3 = new PictureData();
		pic3.setId("2031_57891A");
		pic3.setUrlStd("www.twitter.com");
		pic3.setUrlThumb("www.instagram.com");
		pic3.setCreatedTime(145122);
		pic3.setRemoveFlag(true);
		pic3.addHashtag(h2);
		DatabaseHandler.addPictureToDB(pic3);
		
		DatabaseHandler.removeHashtagFromDB("test2");
		
		DatabaseHandler.removePicturesWithoutHashTagFromDB();
		pictures = (ArrayList<PictureData>) DatabaseHandler.listOfPicturesFromDB();
		
		assertThat(pictures.size(), is(2));
	}

}
