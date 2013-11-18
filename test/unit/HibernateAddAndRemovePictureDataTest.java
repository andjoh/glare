package unit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import dal.DatabaseHandler;
import dal.PictureData;

public class HibernateAddAndRemovePictureDataTest {
	ArrayList<PictureData> pictures;
	
	@Test
	public void test() {
		PictureData pic = new PictureData();
		pic.setId("1247845_2455");
		pic.setUrlStd("www.twitter.com");
		pic.setUrlThumb("www.instagram.com");
		pic.setCreatedTime(123456);
		pic.setRemoveFlag(false);
		DatabaseHandler.addPictureToDB(pic);
		
		DatabaseHandler.removePictureDataFromDB();
		pictures = (ArrayList<PictureData>) DatabaseHandler.listOfPicturesFromDB();
		
		assertThat(pictures.size(), is(0));
	}

}
