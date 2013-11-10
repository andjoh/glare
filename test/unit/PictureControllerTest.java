package unit;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.*;

import resources.DatabaseManagerDummy;
import java.util.*;
import bll.*;
import dal.*;

public class PictureControllerTest {

	private PictureController picCtrl;
	private DatabaseManagerDummy dbManager;
	private Set<String> hashtagsDummy;
	private ArrayList<PictureData> pDFromDbDummy;
	
	@Before
	public void setUp() {
		DatabaseHandler dbHandler = new DatabaseHandler();
		dbManager = new DatabaseManagerDummy(dbHandler);
		picCtrl   = new PictureController(dbManager);

		// Set test data for source and hashtag
		hashtagsDummy = new HashSet<String>();
		hashtagsDummy.add("raskebriller");
		hashtagsDummy.add("twittermotjavatesting");
					
		// Add dummy picturedata from db
		pDFromDbDummy = new ArrayList<PictureData>();

		PictureData p = new PictureData();
		p.setId("picID 1");
		p.setCreatedTime(1);
		p.setUrlStd("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_6.jpg");
		p.setUrlThumb("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_5.jpg");
		pDFromDbDummy.add(p);
		
		p = new PictureData();
		p.setId("picID 3");
		p.setCreatedTime(3);
		p.setUrlStd("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_8.jpg");
		p.setUrlThumb("http://images.ak.instagram.com/profiles/profile_186344368_75sq_1380238572.jpg");
		pDFromDbDummy.add(p);
		
		p = new PictureData();
		p.setId("picID 4");
		p.setCreatedTime(4);
		p.setUrlStd("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_8.jpg");
		p.setUrlThumb("http://images.ak.instagram.com/profiles/profile_186344368_75sq_1380238572.jpg");
		pDFromDbDummy.add(p);		
			
	}
	

	@After
	public void tearDown() {
		hashtagsDummy = null;
		pDFromDbDummy = null;
	}
	
		
	@Test
	public void SearchPictureData_UsingDummyHashtags_UsingDummyPictureData_WhenCalled_ReturnsListOfPictureData() {
		dbManager.setHashtags(hashtagsDummy);
		
		picCtrl.searchPictureData();
		
		List<PictureData> pictureData = picCtrl.getPictureDataFromSources();
		
		assertThat(pictureData.isEmpty(),is(false));
	}	

	
	@Test
	public void ProcessPictureData_UsingDummyHashtags_UsingDummyPictureData_NoPictureDataFromDb_WhenCalled_ReturnsListOfPictureData() {
		dbManager.setHashtags(hashtagsDummy);
		
		picCtrl.searchPictureData();

		picCtrl.processPictureData();		
		
		List<PictureData> pictureDataAll = dbManager.getPictureDataFromDb();
		
		assertThat(pictureDataAll.size() == 5,is(true));
	}
	

	@Test
	public void ProcessPictureData_UsingDummyHashtags_UsingDummyPictureData_NoHashtags_WhenCalled_ReturnsNotSuccess() {
		
		boolean success = picCtrl.searchPictureData();
		
		assertThat(success,is(false));
	}
	
	
	@Test
	public void ProcessPictureData_UsingDummyHashtags_UsingDummyPictureData_WhenCalled_Returns6PictureDataInOrder() {
		dbManager.setHashtags(hashtagsDummy);
		dbManager.setPictureDataFromDb(pDFromDbDummy);
		
		picCtrl.searchPictureData();

		picCtrl.processPictureData();		
		
		List<PictureData> pictureDataAll = dbManager.getPictureDataFromDb();
		
		assertThat(pictureDataAll.size() == 6,is(true));
	}
	
	
	@Test
	public void SearchPictureData_Instagram_And_Twitter_WhenCalled_ReturnsListOfPictureData() {

		dbManager.setHashtags(hashtagsDummy);
		
		boolean success = picCtrl.searchPictureData();

		assertThat(success,is(true));
		
		List<PictureData> pictureData = picCtrl.getPictureDataFromSources();
		
		assertThat(pictureData.isEmpty(),is(false));
	}
}