package unit;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dal.DatabaseHandler;
import dal.PictureData;

public class HibernateTest{
	ArrayList<PictureData> pictures;

	@Before
	public void setUp() throws Exception {
		PictureData pic = new PictureData();
		pic.setId("1247845_2455");
		pic.setUrlStd("www.twitter.com");
		pic.setUrlThumb("www.instagram.com");
		pic.setCreatedTime(123456);
		pic.setRemoveFlag(false);
		DatabaseHandler.addPictureToDB(pic);
		
		PictureData pic2 = new PictureData();
		pic2.setId("1247845_57891A");
		pic2.setUrlStd("www.twitter.com");
		pic2.setUrlThumb("www.instagram.com");
		pic2.setCreatedTime(123456);
		pic2.setRemoveFlag(false);
		DatabaseHandler.addPictureToDB(pic2);
		
		pictures = (ArrayList<PictureData>) DatabaseHandler.listOfPicturesFromDB();
		
		for(PictureData pic1: pictures){
			System.out.println(pic1.getId() + " " + pic1.getUrlStd() + ": " + pic1.getUrlThumb() + " "
					+ pic1.getCreatedTime() + " " + pic1.isRemoveFlag());
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void AddPictureToDB_ReturnsListWithPicturesFromDB() {
		assertThat(pictures.size(), is(not(nullValue())));
	}
}
