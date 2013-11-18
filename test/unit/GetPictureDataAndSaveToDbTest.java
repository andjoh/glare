package unit;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.*;

import glare.ClassFactory;

import java.util.*;

import bll.*;
import dal.*;

public class GetPictureDataAndSaveToDbTest {

	
	@Before
	public void setUp() {		
	}
	

	@After
	public void tearDown() {
	}

	@Test
	public void SearchPictureData_Instagram_And_Twitter_WhenCalled_SavePictureDataToDb() {

		PictureController picCtrl = (PictureController) ClassFactory.getBeanByName("pictureController");
		DatabaseManager dbMan     = (DatabaseManager) ClassFactory.getBeanByName("databaseManager");

		// Set test data for source and hashtag
		Set<String> hashtagsDummy = new HashSet<String>();
		hashtagsDummy.add("twittermotjavatesting");
		hashtagsDummy.add("raskebriller");
		dbMan.addHashtags(hashtagsDummy);
		
		picCtrl.searchPictureData();
		picCtrl.processPictureData();
		
		List<PictureData> pictureData = dbMan.getPictureDataFromDb();

		// Additional output
		System.out.println("PictureData from Database");
		for ( PictureData pd : pictureData ) {
			System.out.println(pd.getId());
			System.out.println(pd.getUrlStd());
		}
		System.out.println("");
		
		assertThat(pictureData.isEmpty(),is(false));
	}
}