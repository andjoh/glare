package unit;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dal.PictureData;
import dal.TwitterReader;

public class TwitterReaderTest {

		private List<PictureData> pictures;
		
		@Before
		public void setUp() throws Exception {
			pictures = new ArrayList<PictureData>();
			TwitterReader tr = new TwitterReader();
			pictures = tr.getPictures("#twittermotjavatesting");
		}
		@After
		public void tearDown() {
			pictures = null;
		}
		
		@Test
		public void ReturnsListWithPictures(){
			assertThat(pictures.size(), is(not(nullValue())));
		}
		/*
		@Test
		public void FirstPictureHasUrlStandardRes(){
			assertThat(pictures.get(0).getUrlStd(), is(not(nullValue())));
		}
		
		@Test
		public void FirstPictureHasUrlThumbnailRes(){
			assertThat(pictures.get(0).getUrlThumb(), is(not(nullValue())));
		}*/
}
