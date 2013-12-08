package integration;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import dal.DatabaseHandler;
import dal.Hashtag;
import dal.PictureData;
import static org.hamcrest.CoreMatchers.*;

public class Hibernate100LatestPicsTest {
	List<PictureData> picList;

	@Test
	public void ReturnHundredLatestPicturesFromDB() {
		Hashtag h1 = new Hashtag("winter");
		
		PictureData pic = new PictureData();
		pic.setId("1247845_2455");
		pic.setUrlStd("www.twitter.com");
		pic.setUrlThumb("www.instagram.com");
		pic.setCreatedTime(123456);
		pic.setRemoveFlag(false);
		pic.addHashtag(h1);
		DatabaseHandler.addPictureToDB(pic);
		
		picList = DatabaseHandler.listHundredNewestPicturesFromDB();
		
		assertThat(picList.size(), is(not(nullValue())));
	}

}
