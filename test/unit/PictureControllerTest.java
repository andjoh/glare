package unit;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.*;

import glare.ClassFactory;

import java.util.*;

import bll.*;
import dal.*;

public class PictureControllerTest {

	private PictureController picCtrl;
	private DatabaseManager dbManager;
	ArrayList<HashtagBySource> htbsDummy;
	ArrayList<PictureData> pdfdDummy;
	
	@Before
	public void setUp() {
		dbManager = (DatabaseManager) ClassFactory.getBeanByName("databaseManager");
		picCtrl   = new PictureController(dbManager);

		// Set test data for source and hashtag
		htbsDummy = new ArrayList<HashtagBySource>();
		HashtagBySource h = new HashtagBySource();
		h.setSource("instagramReaderDummy");
		h.setHashtag("raskebriller");
		htbsDummy.add(h);
		
		h = new HashtagBySource();
		h.setSource("twitterReaderDummy");
		h.setHashtag("twittermotjavatesting");
		htbsDummy.add(h);
					
		// Add dummy picturedata from db
		pdfdDummy = new ArrayList<PictureData>();

		PictureData p = new PictureData();
		p.setId("picID 1");
		p.setCreatedTime(1);
		p.setUrlStd("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_6.jpg");
		p.setUrlThumb("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_5.jpg");
		p.setHashtag("db1");
		pdfdDummy.add(p);
		
		p = new PictureData();
		p.setId("picID 3");
		p.setCreatedTime(3);
		p.setUrlStd("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_8.jpg");
		p.setUrlThumb("http://images.ak.instagram.com/profiles/profile_186344368_75sq_1380238572.jpg");
		p.setHashtag("db3");
		pdfdDummy.add(p);
		
		p = new PictureData();
		p.setId("picID 4");
		p.setCreatedTime(4);
		p.setUrlStd("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_8.jpg");
		p.setUrlThumb("http://images.ak.instagram.com/profiles/profile_186344368_75sq_1380238572.jpg");
		p.setHashtag("hashtag4");
		pdfdDummy.add(p);		
			
	}
	

	@After
	public void tearDown() {
		htbsDummy = null;
	pdfdDummy = null;
	}
	
	
	@Test
	public void SearchPicturesFromSource_UsingDummyHashtagBySource_UsingDummyPictureData_WhenCalled_ReturnsListOfPictureData() {
		
		dbManager.setHashtagBySource(htbsDummy);
		
		HashtagBySource htbs = dbManager.getHashtagBySource().get(0);		
		
		ArrayList<PictureData> pictureData = picCtrl.searchPicturesFromSource(htbs);
		
		assertThat(pictureData.isEmpty(),is(false));
	}	
	
	
	@Test
	public void SearchPictureData_UsingDummyHashtagBySource_UsingDummyPictureData_WhenCalled_ReturnsListOfPictureData() {
		dbManager.setHashtagBySource(htbsDummy);
		
		picCtrl.searchPictureData();
		
		List<PictureData> pictureData = picCtrl.getPictureDataFromSources();
		
		assertThat(pictureData.isEmpty(),is(false));
	}	

	
	@Test
	public void ProcessPictureData_UsingDummyHashtagBySource_UsingDummyPictureData_NoPictureDataFromDb_WhenCalled_ReturnsListOfPictureData() {
		dbManager.setHashtagBySource(htbsDummy);
		
		picCtrl.searchPictureData();

		picCtrl.processPictureData();		
		
		List<PictureData> pictureDataAll = picCtrl.getPictureData();
		
		assertThat(pictureDataAll.size() == 5,is(true));
	}
	

	@Test
	public void ProcessPictureData_UsingDummyHashtagBySource_UsingDummyPictureData_NoHashtagBySource_WhenCalled_ReturnsNotSuccess() {
		
		boolean success = picCtrl.searchPictureData();
		
		assertThat(success,is(false));
	}
	
	
	@Test
	public void ProcessPictureData_UsingDummyHashtagBySource_UsingDummyPictureData_WhenCalled_Returns6PictureDataInOrder() {
		dbManager.setHashtagBySource(htbsDummy);
		dbManager.setPictureDataFromDb(pdfdDummy);
		
		picCtrl.searchPictureData();

		picCtrl.processPictureData();		
		
		List<PictureData> pictureDataAll = picCtrl.getPictureData();
		
		assertThat(pictureDataAll.size() == 6,is(true));
	}
	
	
	@Test
	public void SearchPictureData_Instagram_WhenCalled_ReturnsListOfPictureData() {

		// Set test data for source and hashtag
		List<HashtagBySource> htbs = new ArrayList<HashtagBySource>();
		HashtagBySource h = new HashtagBySource();
		h.setSource("instagramReader");
		h.setHashtag("raskebriller");		
		h.setHashtag("dennekanikkefinnesdeterjegheltsikkerpaa");
		htbs.add(h);
		dbManager.setHashtagBySource(htbs);
		
		boolean success = picCtrl.searchPictureData();

		assertThat(success,is(true));
		
		List<PictureData> pictureData = picCtrl.getPictureDataFromSources();
		
		assertThat(pictureData.isEmpty(),is(false));
	}
	
	
	@Test
	public void SearchPictureData_Twitter_WhenCalled_ReturnsListOfPictureData() {

		// Set test data for source and hashtag
		List<HashtagBySource> htbs = new ArrayList<HashtagBySource>();
		HashtagBySource h = new HashtagBySource();
		h.setSource("twitterReader");
		h.setHashtag("twittermotjavatesting");		
		h.setHashtag("dennekanikkefinnesdeterjegheltsikkerpaa");
		htbs.add(h);
		dbManager.setHashtagBySource(htbs);
		
		boolean success = picCtrl.searchPictureData();

		assertThat(success,is(true));
		
		List<PictureData> pictureData = picCtrl.getPictureDataFromSources();
		
		assertThat(pictureData.isEmpty(),is(false));
	}
}