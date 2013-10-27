package unit;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dal.DatabaseHandler;
import dal.PictureData;

public class HibernateTest {
	ArrayList<PictureData> pictures;

	@Before
	public void setUp() throws Exception {
//		PictureData pic = new PictureData("132264_416784", "www.instagram,com", "www.twitter.com", 124551, "tull", false);
		PictureData pic = new PictureData();
		pic.setId("1247845_2455");
		pic.setUrlStd("www.twitter.com");
		pic.setUrlThumb("www.instagram.com");
		pic.setTime(123456);
		pic.setHashtag("tull");
		pic.setRemoveFlag(false);
		DatabaseHandler.addPictureToDB(pic);
		pictures = (ArrayList<PictureData>) DatabaseHandler.listOfPicturesFromDB();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void AddPictureToDB_ReturnsListWithPicturesFromDB() {
		assertThat(pictures.size(), is(not(nullValue())));
	}

}
