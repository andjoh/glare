package unit;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.*;

import resources.*;
import java.util.*;

import bll.*;
import dal.*;

public class PictureControllerTest {

	private PictureController picCtrl;
	private DatabaseManagerDummy dbManagerDummy;
	private Set<String> hashtagsDummy;
	private ArrayList<PictureData> pictureDataFromDbDummy;
	private List<String> sourcesDummy;

	
	@Before
	public void setUp() {
		
		// New up PictureController with dummy DatabaseHandler
		DatabaseHandler dbHandler = new DatabaseHandler();
		dbManagerDummy = new DatabaseManagerDummy(dbHandler);
		picCtrl        = new PictureController(dbManagerDummy);

		// Set dummy sources
		sourcesDummy = new ArrayList<String>();
		sourcesDummy.add("dummyinstagram");
		sourcesDummy.add("dummytwitter");
		
		// Set test data for source and hashtag
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
		dbManagerDummy         = null;
		sourcesDummy           = null;
		hashtagsDummy          = null;
		pictureDataFromDbDummy = null;
	}
	
//	@Test
//	public void SearchPictureData_UsingDummyHashtags_UsingDummyPictureData_WhenCalled_ReturnsListOfPictureData() {
//		
//		// Set dummy sources and hashtag
//		dbManagerDummy.setSources(sourcesDummy);
//		dbManagerDummy.setHashtags(hashtagsDummy);
//		
//		// Run test
//		picCtrl.searchPictureData();
//		
//		List<PictureData> pictureData = picCtrl.getPictureDataFromSources();
//
//		// Additional output
//		System.out.println("5 Dummy Picturedata from Instagram and Twitter");
//		for ( PictureData pd : pictureData ) {
//			System.out.println(pd.getId());
//			for ( Hashtag ht : pd.getHashtags() ) {
//				System.out.println(" - " + ht.getHashtag());
//			}
//		}
//		System.out.println("");
//		
//		assertThat(pictureData.isEmpty(),is(false));
//	}	
	
	@Test
	public void GetNewPictureData_UsingDummyHashtags_UsingDummyPictureData_NoPictureDataFromDb_WhenCalled_5PictureDataAreSavedToDb() {
		
		// Set dummy sources and hashtag
		dbManagerDummy.setSources(sourcesDummy);
		dbManagerDummy.setHashtags(hashtagsDummy);
		
		// Run test
		picCtrl.getNewPictureData();	

		// Additional output
		System.out.println("PictureData from sources");
		for ( PictureData pd : picCtrl.getPictureDataFromSources() ) {
			System.out.println(pd.getId());
			for ( Hashtag ht : pd.getHashtags() ) {
				System.out.println(" - " + ht.getHashtag());
			}
		}
		System.out.println("");		
		
		List<PictureData> pictureData = dbManagerDummy.getPictureDataFromDb();

		// Additional output
		System.out.println("PictureData saved to db");
		for ( PictureData pd : pictureData ) {
			System.out.println(pd.getId());
			for ( Hashtag ht : pd.getHashtags() ) {
				System.out.println(" - " + ht.getHashtag());
			}
		}
		System.out.println("");
			
		assertThat(pictureData.size() == 5,is(true));
	}
	
	@Test
	public void GetNewPictureData_UsingDummyHashtags_UsingDummyPictureData_DummyPictureDataFromDb_WhenCalled_TotalNumberPictureDataIs6() {

		// Set dummy sources, hashtag and picturedata
		dbManagerDummy.setSources(sourcesDummy);
		dbManagerDummy.setHashtags(hashtagsDummy);
		dbManagerDummy.setPictureDataFromDb(pictureDataFromDbDummy);
		
		// Run test
		picCtrl.getNewPictureData();			
		
		List<PictureData> pictureData = dbManagerDummy.getPictureDataFromDb();

		// Additional output
		System.out.println("All available PictureData after get from sources and db");
		for ( PictureData pd : pictureData ) {
			System.out.println(pd.getId());
			for ( Hashtag ht : pd.getHashtags() ) {
				System.out.println(" - " + ht.getHashtag());
			}
		}
		System.out.println("");
		
		assertThat(pictureData.size() == 6,is(true));
	}

	@Test
	public void GetNewPictureData_NoSources_NoHashtags_WhenCalled_ReturnsNotSuccess() {
		
		boolean success = picCtrl.getNewPictureData();
		
		assertThat(success,is(false));
	}
	
	@Test
	public void GetNewPictureData_NoHashtags_WhenCalled_ReturnsNotSuccess() {
		// Set dummy sources
		dbManagerDummy.setSources(sourcesDummy);
		
		boolean success = picCtrl.getNewPictureData();
		
		assertThat(success,is(false));
	}
	
	@Test
	public void GetNewPictureData_NoHashtags_DummyPictureDataFromDb_WhenCalled_ReturnsNotSuccess() {
		// Set dummy sources
		dbManagerDummy.setSources(sourcesDummy);
		dbManagerDummy.setPictureDataFromDb(pictureDataFromDbDummy);
		
		boolean success = picCtrl.getNewPictureData();
		
		assertThat(success,is(false));
	}
}