package unit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dal.DatabaseHandler;
import dal.Hashtag;
import dal.PictureData;

public class HibernateAddAndRemovePictureDataTest {
	ArrayList<PictureData> pictures;
	
	@Test
	public void test() {
		Hashtag hash = new Hashtag("summer");
		
		PictureData pic = new PictureData();
		pic.setId("1247845_2455");
		pic.setUrlStd("www.twitter.com");
		pic.setUrlThumb("www.instagram.com");
		pic.setCreatedTime(123456);
		pic.setRemoveFlag(false);
		pic.addHashtag(hash);
		DatabaseHandler.addPictureToDB(pic);
		
		List<PictureData> result = DatabaseHandler.listOfPicturesFromDB();
		DatabaseHandler.removePictureDataFromDB(result.get(0).getId());
		pictures = (ArrayList<PictureData>) DatabaseHandler.listOfPicturesFromDB();
		
		assertThat(pictures.size(), is(0));
	}

}
