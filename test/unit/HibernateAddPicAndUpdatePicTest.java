package unit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.util.ArrayList;

import java.util.Set;
import org.junit.Test;

import dal.DatabaseHandler;
import dal.Hashtag;
import dal.PictureData;

public class HibernateAddPicAndUpdatePicTest {
	ArrayList<PictureData> pictures;

	@Test
	public void AddPictureToDB_UpdatePicture_ReturnListOfPictures() {
		Hashtag h1 = new Hashtag("winter");
		//DatabaseHandler.addHashtagToDB(h1);
		
		PictureData pic = new PictureData();
		pic.setId("1247845_2455");
		pic.setUrlStd("www.twitter.com");
		pic.setUrlThumb("www.instagram.com");
		pic.setCreatedTime(123456);
		pic.setRemoveFlag(false);
		pic.addHashtag(h1);
		DatabaseHandler.addPictureToDB(pic);
		
		Hashtag h2 = new Hashtag("summer");
//		DatabaseHandler.addHashtagToDB(h2);
		
		Hashtag h3 = new Hashtag("winter");
		DatabaseHandler.addHashtagToDB(h3);
		
		PictureData pic2 = new PictureData();
		pic2.setId("1247845_2455");
		pic2.setUrlStd("www.twitter.com");
		pic2.setUrlThumb("www.instagram.com");
		pic2.setCreatedTime(123456);
		pic2.setRemoveFlag(false);
		pic2.addHashtag(h2);
		DatabaseHandler.addPictureToDB(pic2);
		
		pictures = (ArrayList<PictureData>) DatabaseHandler.listOfPicturesFromDB();
		
		for(PictureData pic1: pictures){
			System.out.println(pic1.getId() + " " + pic1.getUrlStd() + ": " + pic1.getUrlThumb() + " "
					+ pic1.getCreatedTime() + " " + pic1.isRemoveFlag());
		}
		
		assertThat(pictures.size(), is(1));
	}

}
