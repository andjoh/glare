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

public class HibernateAddHashtagAndPictureAndConnectTest {
	
	ArrayList<PictureData> pictures;

	@Test
	public void AddHashTagAndPictureAndConnect_ReturnListOfPictures(){
		Hashtag h1 = new Hashtag("winter");
		DatabaseHandler.addHashtagToDB(h1);
		
		PictureData pic = new PictureData();
		pic.setId("1247845_2455");
		pic.setUrlStd("www.twitter.com");
		pic.setUrlThumb("www.instagram.com");
		pic.setCreatedTime(123456);
		pic.setRemoveFlag(false);
		pic.addHashtag(h1);
		DatabaseHandler.addPictureToDB(pic);
		
		pictures = (ArrayList<PictureData>) DatabaseHandler.listOfPicturesFromDB();
		
		assertThat(pictures.size(), is(1));
	}

}