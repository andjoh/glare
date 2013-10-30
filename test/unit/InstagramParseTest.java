package unit;

import static org.junit.Assert.*;
import glare.ClassFactory;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dal.InstagramReader;
import dal.PictureData;
import static org.hamcrest.CoreMatchers.*;

public class InstagramParseTest {
	private List<PictureData> pictures;
	private InstagramReader ir;
	
	@Before
	public void setUp() throws FileNotFoundException, UnsupportedEncodingException{
		pictures = new ArrayList<PictureData>();

		ir = (InstagramReader) ClassFactory.getBeanByName("instagramReader");
		pictures = ir.getPictures("winter");
	}
	@After
	public void tearDown(){
		pictures = null;
	}
	
	@Test
	public void Parse_GivenJsonWithPictures_ReturnsListWithPictures(){
		assertThat(pictures.size(), is(not(nullValue())));
	}
	
	@Test
	public void Parse_GivenValidJson_FirstPictureHasUrlStandardRes(){
		assertThat(pictures.get(0).getUrlStd(), is(not(nullValue())));
	}
	
	@Test
	public void Parse_GivenValidJson_FirstPictureHasUrlThumbnailRes(){
		assertThat(pictures.get(0).getUrlThumb(), is(not(nullValue())));
	}
	
	@Test
	public void Parse_GivenValidJson_FirstPictureHasTime(){
		assertThat(pictures.get(0).getCreatedTime(), is(not(nullValue())));
	}
	
	@Test
	public void Parse_GivenValidJson_FirstPictureHasID(){
		assertThat(pictures.get(0).getId(), is(not(nullValue())));
	}
	@Test
	public void GivenFirstPicture_FirstPictureHasRemoveFlag(){
		assertThat(pictures.get(0).isRemoveFlag(), is(false));
	}
	@Test
	public void GivenFirstPicture_FirstPictureHasHashtag(){
		assertThat(pictures.get(0).getHashtag(), is(not(nullValue())));
	}

}
