package integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import resources.*;
import bll.*;
import dal.*;

public class PictureControllerIntegrationTest {
	
	private PictureController picCtrl;
	private DatabaseManager dbManager;
	private DatabaseManagerDummy dbManagerDummy;
	private DatabaseManagerDummyOnlySourcesAndHashtags dbManagerDummyOnlySourcesAndHashtag;
	private Set<String> hashtagsDummy;
	private List<String> sourcesReal;
	
	@Before
	public void setUp() {
		
		// New up PictureController with DatabaseManager
		DatabaseHandler dbHandler = new DatabaseHandler();
		dbManager = new DatabaseManager(dbHandler);
		dbManagerDummy = new DatabaseManagerDummy(dbHandler);
		dbManagerDummyOnlySourcesAndHashtag = new DatabaseManagerDummyOnlySourcesAndHashtags(dbHandler);

		// Set real sources
		sourcesReal = new ArrayList<String>();
		sourcesReal.add("instagram");
		sourcesReal.add("twitter");
		
		// Set dummy hashtag
		hashtagsDummy = new HashSet<String>();
		hashtagsDummy.add("winter");
		hashtagsDummy.add("raskebriller");				
	}
	
	@After
	public void tearDown() {
		
		picCtrl                             = null;
		dbManager                           = null;
		dbManagerDummy                      = null;
		dbManagerDummyOnlySourcesAndHashtag = null;
		hashtagsDummy                       = null;
	}	
	
	@Test
	public void GetNewPictureData_DummyHashtag_DummyDb_From_Instagram_And_Twitter_WhenCalled_ReturnsListOfPictureData() {
		
		// Using dummy DatabaseManager
		picCtrl = new PictureController(dbManagerDummy);

		// Set real sources and add to dummy DatabaseManager
		dbManagerDummy.setSources(sourcesReal);
		dbManagerDummy.setHashtags(hashtagsDummy);
		
		// Run test
		picCtrl.getNewPictureData();
		
		List<PictureData> pictureData = dbManagerDummy.getPictureDataFromDb();
		
		assertThat(pictureData.isEmpty(),is(false));
	}
	
	@Test
	public void GetNewPictureData_DummyHashtags_SearchInstagramAndTwitter_SaveToDb_GetFromDb_WhenCalled_ReturnsListOfPictureData() {
		
		// Using dummy DatabaseManager, but only for sources and hashtags
		dbManagerDummyOnlySourcesAndHashtag.setSources(sourcesReal);
		dbManagerDummyOnlySourcesAndHashtag.setHashtags(hashtagsDummy);
		
		picCtrl = new PictureController(dbManagerDummyOnlySourcesAndHashtag);
				
		// Run test
		picCtrl.getNewPictureData();
		
		List<PictureData> pictureData = dbManager.getPictureDataFromDb();
		
		assertThat(pictureData.isEmpty(),is(false));
	}

	/**
	 * DATABASE MUST CONTAIN HASHTAG(S) BEFORE RUNNING THIS TEST
	 */
	@Test
	public void GetNewPictureData_SearchInstagramAndTwitter_SaveToDb_GetFromDb_WhenCalled_ReturnsListOfPictureData() {
		
		picCtrl = new PictureController(dbManager);
				
		// Run test
		picCtrl.getNewPictureData();
		
		List<PictureData> pictureData = dbManager.getPictureDataFromDb();
		
		assertThat(pictureData.isEmpty(),is(false));
	}
}
