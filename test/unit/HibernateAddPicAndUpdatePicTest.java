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
		Hashtag h2 = new Hashtag("summer");
		
		PictureData pic = new PictureData();
		pic.setId("1247845_2455");
		pic.setUrlStd("www.twitter.com");
		pic.setUrlThumb("www.instagram.com");
		pic.setCreatedTime(123456);
		pic.setRemoveFlag(false);
		pic.addHashtag(h1);
		pic.addHashtag(h2);
		DatabaseHandler.addPictureToDB(pic);
		
		Hashtag h3 = new Hashtag("yajog");
		
		PictureData pic2 = new PictureData();
		pic2.setId("1247845_2455");
		pic2.setUrlStd("www.twitter.com");
		pic2.setUrlThumb("www.instagram.com");
		pic2.setCreatedTime(123456);
		pic2.setRemoveFlag(false);
		pic2.addHashtag(h3);
		DatabaseHandler.addPictureToDB(pic2);
		
		PictureData pic3 = new PictureData();
		pic3.setId("132894");
		pic3.setUrlStd("www.twitter.com");
		pic3.setUrlThumb("www.instagram.com");
		pic3.setCreatedTime(123456);
		pic3.setRemoveFlag(false);
		pic3.addHashtag(h3);
		DatabaseHandler.addPictureToDB(pic3);
		
		pictures = (ArrayList<PictureData>) DatabaseHandler.listOfPicturesFromDB();
		
		for(PictureData pic1: pictures){
			System.out.println(pic1.getId() + " " + pic1.getUrlStd() + ": " + pic1.getUrlThumb() + " "
					+ pic1.getCreatedTime() + " " + pic1.isRemoveFlag());
		}
		
		assertThat(pictures.size(), is(2));
	}

}
