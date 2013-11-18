package unit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import dal.DatabaseHandler;
import dal.PictureData;

public class HibernateAddAndListPicturesTest {
	ArrayList<PictureData> pictures;
	
	@Test
	public void AddPictureToDB_ReturnsListWithPicturesFromDB() {
		
		PictureData pic = new PictureData();
		pic.setId("1247845_2455");
		pic.setUrlStd("www.twitter.com");
		pic.setUrlThumb("www.instagram.com");
		pic.setCreatedTime(123456);
		pic.setRemoveFlag(false);
		DatabaseHandler.addPictureToDB(pic);
		
		pictures = (ArrayList<PictureData>) DatabaseHandler.listOfPicturesFromDB();
		
//		for(PictureData pic1: pictures){
//			System.out.println(pic1.getId() + " " + pic1.getUrlStd() + ": " + pic1.getUrlThumb() + " "
//					+ pic1.getCreatedTime() + " " + pic1.isRemoveFlag());
//		}
		
		assertThat(pictures.size(), is(not(nullValue())));
	}
}
