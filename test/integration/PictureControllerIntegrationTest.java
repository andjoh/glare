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
	private ArrayList<PictureData> pictureDataFromDbDummy;
	private List<String> sourcesReal;
	private List<String> sourcesDummy;
	
	@Before
	public void setUp() {
		
		// New up PictureController with dummy DatabaseHandler
		DatabaseHandler dbHandler = new DatabaseHandler();
		dbManager = new DatabaseManager(dbHandler);
		dbManagerDummy = new DatabaseManagerDummy(dbHandler);
		dbManagerDummyOnlySourcesAndHashtag = new DatabaseManagerDummyOnlySourcesAndHashtags(dbHandler);

		// Set real sources
		sourcesReal = new ArrayList<String>();
		sourcesReal.add("instagram");
		sourcesReal.add("twitter");
		
		// Set dummy sources
		sourcesDummy = new ArrayList<String>();
		sourcesDummy.add("dummyinstagram");
		sourcesDummy.add("dummytwitter");
		
		// Set dummy hashtag
		hashtagsDummy = new HashSet<String>();
		hashtagsDummy.add("winter");
		hashtagsDummy.add("raskebriller");
					
		// Set dummy picturedata from db
		pictureDataFromDbDummy = new ArrayList<PictureData>();

		PictureData p = new PictureData();
		p.setId("picID 1");
		p.setCreatedTime(1);
		p.setUrlStd("http://distilleryimage3.s3.amazonaws.com/3abc20ec398811e3b44322000a1f92df_8.jpg");
		p.setUrlThumb("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_5.jpg");
		p.addHashtag(new Hashtag("hashtag 1"));
		pictureDataFromDbDummy.add(p);
		
		p = new PictureData();
		p.setId("picID 3");
		p.setCreatedTime(3);
		p.setUrlStd("http://distilleryimage9.s3.amazonaws.com/bcb0a0d038d111e3911522000a9e087e_8.jpg");
		p.setUrlThumb("http://images.ak.instagram.com/profiles/profile_186344368_75sq_1380238572.jpg");
		p.addHashtag(new Hashtag("hashtag 3"));
		pictureDataFromDbDummy.add(p);
		
		p = new PictureData();
		p.setId("picID 4");
		p.setCreatedTime(4);
		p.setUrlStd("http://distilleryimage9.s3.amazonaws.com/77ef4eca367811e3a4cb22000a9e0859_8.jpg");
		p.setUrlThumb("http://images.ak.instagram.com/profiles/profile_186344368_75sq_1380238572.jpg");
		p.addHashtag(new Hashtag("hashtag 4"));
		pictureDataFromDbDummy.add(p);			
	}
	

	@After
	public void tearDown() {
		picCtrl                = null;
		dbManager              = null;
		dbManagerDummy         = null;
		sourcesDummy           = null;
		hashtagsDummy          = null;
		pictureDataFromDbDummy = null;
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

		// Additional output
		System.out.println("PictureData from Instagram and Twitter");
		for ( PictureData pd : pictureData )
		{
			System.out.println(pd.getId());
			System.out.println(pd.getUrlStd());
			for ( Hashtag ht : pd.getHashtags() ) {
				System.out.println(" - " + ht.getHashtag());
			}			
		}
		System.out.println("");
		
		assertThat(pictureData.isEmpty(),is(false));
	}
	
	/**
	 * Db must be empty before running this test
	 */
	@Test
	public void GetNewPictureData_DummyPictureData_EmptyDb_SaveToDb_GetFromDb_WhenCalled_ReturnsListOf5PictureData() {

		dbManagerDummyOnlySourcesAndHashtag.setSources(sourcesDummy);
		dbManagerDummyOnlySourcesAndHashtag.setHashtags(hashtagsDummy);
		
		picCtrl = new PictureController(dbManagerDummyOnlySourcesAndHashtag);
				
		// Run test
		picCtrl.getNewPictureData();
		
		List<PictureData> pictureData = dbManager.getPictureDataFromDb();

		// Additional output
		System.out.println("PictureData from db should return 5 pictureData");
		for ( PictureData pd : pictureData )
		{
			System.out.println(pd.getId());
			System.out.println(pd.getUrlStd());
			for ( Hashtag ht : pd.getHashtags() ) {
				System.out.println(" - " + ht.getHashtag());
			}			
		}
		System.out.println("");
		
		assertThat(pictureData.size() == 5,is(true));
	}

	@Test
	public void GetNewPictureData_DummyPictureData_SaveToDb_GetFromDb_WhenCalled_ReturnsListOfPictureData() {

		dbManagerDummyOnlySourcesAndHashtag.setSources(sourcesDummy);
		dbManagerDummyOnlySourcesAndHashtag.setHashtags(hashtagsDummy);
		
		picCtrl = new PictureController(dbManagerDummyOnlySourcesAndHashtag);
				
		// Run test
		picCtrl.getNewPictureData();
		
		List<PictureData> pictureData = dbManager.getPictureDataFromDb();

		// Additional output
		System.out.println("PictureData from db should return");
		for ( PictureData pd : pictureData )
		{
			System.out.println(pd.getId());
			System.out.println(pd.getUrlStd());
			for ( Hashtag ht : pd.getHashtags() ) {
				System.out.println(" - " + ht.getHashtag());
			}			
		}
		System.out.println("");
		
		assertThat(pictureData.isEmpty(),is(false));
	}
	
	@Test
	public void GetNewPictureData_DummyHashtags_SearchInstagramAndTwitter_SaveToDb_GetFromDb_WhenCalled_ReturnsListOfPictureData() {
		
		dbManagerDummyOnlySourcesAndHashtag.setSources(sourcesReal);
		dbManagerDummyOnlySourcesAndHashtag.setHashtags(hashtagsDummy);
		
		picCtrl = new PictureController(dbManagerDummyOnlySourcesAndHashtag);
				
		// Run test
		picCtrl.getNewPictureData();
		
		List<PictureData> pictureData = dbManager.getPictureDataFromDb();

		// Additional output
		System.out.println("");
		System.out.println("PictureData from db");
		for ( PictureData pd : pictureData )
		{
			System.out.println(pd.getId());
			System.out.println(pd.getUrlStd());
			for ( Hashtag ht : pd.getHashtags() ) {
				System.out.println(" - " + ht.getHashtag());
			}			
		}
		System.out.println("");
		
		assertThat(pictureData.isEmpty(),is(false));
	}
	
	@Test
	public void GetNewPictureData_SearchInstagramAndTwitter_SaveToDb_GetFromDb_WhenCalled_ReturnsListOfPictureData() {
		
		picCtrl = new PictureController(dbManager);
				
		// Run test
		picCtrl.getNewPictureData();
		
		List<PictureData> pictureData = dbManager.getPictureDataFromDb();

		// Additional output
		System.out.println("");
		System.out.println("PictureData from db");
		for ( PictureData pd : pictureData )
		{
			System.out.println(pd.getId());
			System.out.println(pd.getUrlStd());
			for ( Hashtag ht : pd.getHashtags() ) {
				System.out.println(" - " + ht.getHashtag());
			}			
		}
		System.out.println("");
		
		assertThat(pictureData.isEmpty(),is(false));
	}
}
